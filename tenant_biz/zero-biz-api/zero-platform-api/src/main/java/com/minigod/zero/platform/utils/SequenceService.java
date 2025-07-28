package com.minigod.zero.platform.utils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SequenceService {

	@Resource
	private JdbcTemplate jdbcTemplate;

	private final static String INSERT_SQL = "INSERT INTO `platform_sys_sequence` (`seq_name`) VALUES(?) ON DUPLICATE KEY UPDATE `seq_version` = LAST_INSERT_ID(`seq_version` + ?);";
	private final static String UPDATE_SQL = "UPDATE `platform_sys_sequence` SET `seq_version` = LAST_INSERT_ID(`seq_version` + ?) WHERE `seq_name` = ?";
	private final static String SELECT_SQL = "SELECT LAST_INSERT_ID();";

	private static final Map<String, Integer> keysMaps = new ConcurrentHashMap<String, Integer>();

	private static final Boolean isCreate = false;

	//根据标示生产
	public Long nextId(String seqName) {
		try {
			return findSequence(seqName, 1);
		} catch (Exception e) {
			throw new SequenceException(e);
		}
	}

	//根据标示生产多个
	public Long[] nextIds(String seqName, int count) {
		if (count < 1) {
			throw new SequenceException("count must be greater than 0.");
		}
		try {
			Long current = findSequence(seqName, count);
			Long[] nextIds = new Long[count];
			for (int i = 1; i <= count; i++) {
				nextIds[i - 1] = current - (count - i);
			}
			return nextIds;
		} catch (Exception e) {
			throw new SequenceException(e);
		}
	}

	//重试一次
	private Long findSequence(String seqName, int step) throws Exception {
		Long versionId = findId(seqName, step);
		if (versionId == null || versionId < 1) {
			if (isCreate) {
				//如果获取不到值则新添加一个key
				versionId = createId(seqName, step);
				//如果是创建的情况下默认的step是无效的，必须重新findId一遍获取新的versionId
				if (versionId == null || versionId < 1) {
					versionId = findId(seqName, step);
				}
			} else {
				versionId = findId(seqName, step);
			}
		}
		return versionId;
	}

	private Long sequence(String seqName, int step, final String SQL, SequenceType type) throws Exception {
		seqName = seqName.toLowerCase();
		//防止seqName 为new String()的情况,synchronized锁不住的问题
		Integer value = keysMaps.get(seqName);
		if (value == null) {
			value = seqName.hashCode();
			keysMaps.put(seqName, value);
		}

		Long versionId = null;
		//线程安全锁
		synchronized (value) {
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				conn = jdbcTemplate.getDataSource().getConnection();
				stmt = conn.prepareStatement(SQL);
				conn.setAutoCommit(false);

				if (type.equals(SequenceType.UPDATE)) {
					stmt.setInt(1, step);
					stmt.setString(2, seqName);
				} else if (type.equals(SequenceType.CREATE)) {
					stmt.setString(1, seqName);
					stmt.setInt(2, step);
				}
				int result = stmt.executeUpdate();
				if (result != -1) {
					rs = stmt.executeQuery(SELECT_SQL);
					//只有一个结果集
					while (rs.next()) {
						versionId = rs.getLong(1);
					}
				}
				conn.commit();
			} catch (Exception e) {
				if (conn != null) {
					conn.rollback();
				}
				throw new SequenceException(e);
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			}
		}
		return versionId;
	}

	private Long findId(String seqName, int step) throws Exception {
		return sequence(seqName, step, UPDATE_SQL, SequenceType.UPDATE);
	}

	private Long createId(String seqName, int step) throws Exception {
		return sequence(seqName, step, INSERT_SQL, SequenceType.CREATE);
	}


	public static class SequenceException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public SequenceException() {
			super();
		}

		public SequenceException(String message, Throwable cause) {
			super(message, cause);
		}

		public SequenceException(String message) {
			super(message);
		}

		public SequenceException(Throwable cause) {
			super(cause);
		}
	}

	public enum SequenceType {
		CREATE,
		UPDATE
	}

}
