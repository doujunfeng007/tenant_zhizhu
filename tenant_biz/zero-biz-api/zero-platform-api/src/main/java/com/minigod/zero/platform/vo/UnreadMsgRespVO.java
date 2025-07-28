/*
 * FileName: UnreadMsgRespVO.java
 * Copyright: Copyright 2014-12-7 Sunline Tech. Co. Ltd.All right reserved.
 * Description:
 *
 */
package com.minigod.zero.platform.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @description 这里描述类的用处
 *
 * @author Sunline
 * @date 2015-4-17 下午4:24:08
 * @version v1.0
 */
@Data
public class UnreadMsgRespVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<UnReadMsgRespVO_Data_Orign> data;
	private Long version;
	private Integer unreadCount;
	private Long minMsgId;

	public static class UnReadMsgRespVO_Data_Orign  implements Serializable{
		private static final long serialVersionUID = -5477671317891610778L;
		private Long msgId;
		private Long version;
		private String msgType;
		private String title;
		private String msgCon;
		private Integer messageGroup;
		private Long uId;
		private String uImg;
		private String uName;
		private Integer uType;
		private Long ts;
		private Integer isRead; // 信息是否已读
        private String extend;
        private String url;

		public Long getMsgId() {
			return msgId;
		}

		public void setMsgId(Long msgId) {
			this.msgId = msgId;
		}

		public Long getVersion() {
			return version;
		}

		public void setVersion(Long version) {
			this.version = version;
		}

		public String getMsgType() {
			return msgType;
		}

		public void setMsgType(String msgType) {
			this.msgType = msgType;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getMsgCon() {
			return msgCon;
		}

		public void setMsgCon(String msgCon) {
			this.msgCon = msgCon;
		}

		public Integer getMessageGroup() {
			return messageGroup;
		}

		public void setMessageGroup(Integer messageGroup) {
			this.messageGroup = messageGroup;
		}

		public Long getuId() {
			return uId;
		}

		public void setuId(Long uId) {
			this.uId = uId;
		}

		public String getuImg() {
			return uImg;
		}

		public void setuImg(String uImg) {
			this.uImg = uImg;
		}

		public String getuName() {
			return uName;
		}

		public void setuName(String uName) {
			this.uName = uName;
		}

		public Integer getuType() {
			return uType;
		}

		public void setuType(Integer uType) {
			this.uType = uType;
		}

		public Long getTs() {
			return ts;
		}

		public void setTs(Long ts) {
			this.ts = ts;
		}

		public Integer getIsRead() {
			return isRead;
		}

		public void setIsRead(Integer isRead) {
			this.isRead = isRead;
		}

		public String getExtend() {
			return extend;
		}

		public void setExtend(String extend) {
			this.extend = extend;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
}
