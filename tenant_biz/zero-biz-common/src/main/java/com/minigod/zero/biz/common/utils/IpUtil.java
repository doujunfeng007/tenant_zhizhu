package com.minigod.zero.biz.common.utils;

import com.googlecode.ipv6.IPv6Address;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.vo.exception.IPFormatException;
import com.minigod.zero.biz.common.vo.exception.InvalidDatabaseException;
import com.minigod.zero.biz.common.vo.ip.City;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class IpUtil {

	public static boolean enableFileWatch = false;
	private static int offset;
	private static int[] index = new int[256];
	private static ByteBuffer dataBuffer;
	private static ByteBuffer indexBuffer;
	private static Long lastModifyTime = 0L;
	private static File ipFile ;
	private static ReentrantLock lock = new ReentrantLock();
	//private static String filepath = "17monipdb.dat";
	//private static String filepath2 = "17monipdb.datx";
	private static String filepath3 = "ipipfree.ipdb";
	public final static List<String> outAddr = new ArrayList<>();
	private static City city = null;

	static{
		try {
//			IpUtil.class.getResource("/ipipfree.ipdb").getPath();
			city = new City(filepath3);
		} catch (IOException e) {
			e.printStackTrace();
		}

		outAddr.add("香港");
		outAddr.add("台湾");
		outAddr.add("澳门");

//		String file = IP.class.getClassLoader().getResource(filepath).getFile();
//		ipFile = new File(file);
//		load();
//		if (enableFileWatch) {
//			watch();
//		}
	}

	/*public static String randomIp() {
		Random r = new Random();
		StringBuffer str = new StringBuffer();
		str.append(r.nextInt(1000000) % 255);
		str.append(".");
		str.append(r.nextInt(1000000) % 255);
		str.append(".");
		str.append(r.nextInt(1000000) % 255);
		str.append(".");
		str.append(0);

		return str.toString();
	}*/


	public static String getIpAddr(String ip) {
		if (StringUtils.isNotBlank(ip) && ip.contains(",")) {
			String ips[] = ip.split(",");
			ip = ips[0];
		}
		boolean isIp = ipCheck(ip);
		String result;
		if (isIp) {
			String[] addrs = find(ip);
			if (addrs != null) {
				result = addrs[0] + "," + addrs[1];
			} else {
				result = CommonConstant.OUT_LAND_ID;
			}
		} else {
			if (StringUtils.isNotBlank(ip)) {
				IPv6Address ipv6 = IPv6Address.fromString(ip);
				if (IPv6APNICRegionUtil.inChina(ipv6)) {
					result = CommonConstant.IN_LAND_ID + "," + CommonConstant.IN_LAND_ID;
				} else {
					result = CommonConstant.OUT_LAND_ID + "," + CommonConstant.OUT_LAND_ID;
				}
			}else{
				result = CommonConstant.OUT_LAND_ID + "," + CommonConstant.OUT_LAND_ID;
			}
		}

		return result;
	}

	public static void load(String filename) {
//        ipFile = new File(filename);
//        load();
//        if (enableFileWatch) {
//            watch();
//        }
	}

	public static void load(String filename, boolean strict) throws Exception {
		ipFile = new File(filename);
		if (strict) {
			int contentLength = Long.valueOf(ipFile.length()).intValue();
			if (contentLength < 512 * 1024) {
				throw new Exception("ip data file error.");
			}
		}
		load();
		if (enableFileWatch) {
			watch();
		}
	}

	public static String[] find(String ip) {

		try {
			return city.find(ip, "CN");
		} catch (InvalidDatabaseException e) {
			e.printStackTrace();
		} catch (IPFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void watch() {
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				long time = ipFile.lastModified();
				if (time > lastModifyTime) {
					lastModifyTime = time;
					load();
				}
			}
		}, 1000L, 5000L, TimeUnit.MILLISECONDS);
	}

	private static void load() {
		lastModifyTime = ipFile.lastModified();
		FileInputStream fin = null;
		lock.lock();
		try {
			dataBuffer = ByteBuffer.allocate(Long.valueOf(ipFile.length()).intValue());
			fin = new FileInputStream(ipFile);
			int readBytesLength;
			byte[] chunk = new byte[4096];
			while (fin.available() > 0) {
				readBytesLength = fin.read(chunk);
				dataBuffer.put(chunk, 0, readBytesLength);
			}
			dataBuffer.position(0);
			int indexLength = dataBuffer.getInt();
			byte[] indexBytes = new byte[indexLength];
			dataBuffer.get(indexBytes, 0, indexLength - 4);
			indexBuffer = ByteBuffer.wrap(indexBytes);
			indexBuffer.order(ByteOrder.LITTLE_ENDIAN);
			offset = indexLength;

			int loop = 0;
			while (loop++ < 256) {
				index[loop - 1] = indexBuffer.getInt();
			}
			indexBuffer.order(ByteOrder.BIG_ENDIAN);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
			} catch (IOException e){
				e.printStackTrace();
			}
			lock.unlock();
		}
	}

	private static long bytesToLong(byte a, byte b, byte c, byte d) {
		return int2long((((a & 0xff) << 24) | ((b & 0xff) << 16) | ((c & 0xff) << 8) | (d & 0xff)));
	}

	private static int str2Ip(String ip)  {
		String[] ss = ip.split("\\.");
		int a, b, c, d;
		a = Integer.parseInt(ss[0]);
		b = Integer.parseInt(ss[1]);
		c = Integer.parseInt(ss[2]);
		d = Integer.parseInt(ss[3]);
		return (a << 24) | (b << 16) | (c << 8) | d;
	}

	private static long ip2long(String ip)  {
		return int2long(str2Ip(ip));
	}

	private static long int2long(int i) {
		long l = i & 0x7fffffffL;
		if (i < 0) {
			l |= 0x080000000L;
		}
		return l;
	}

	public static boolean ipCheck(String text) {
		if (text != null && !text.isEmpty()) {
			// 定义正则表达式
			String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
			// 判断ip地址是否与正则表达式匹配
			if (text.matches(regex)) {
				// 返回判断信息
				return true;
			} else {
				// 返回判断信息
				return false;
			}
		}
		return false;
	}

	public static boolean isIPv4LiteralAddress(String src) {
		return textToNumericFormatV4(src) != null;
	}

	public static boolean isIPv6LiteralAddress(String src) {
		return textToNumericFormatV6(src) != null;
	}

	/*
	 * Converts IPv4 address in its textual presentation form
	 * into its numeric binary form.
	 *
	 * @param src a String representing an IPv4 address in standard format
	 * @return a byte array representing the IPv4 numeric address
	 */
	public static byte[] textToNumericFormatV4(String src)
	{
		byte[] res = new byte[INADDR4SZ];

		long tmpValue = 0;
		int currByte = 0;
		boolean newOctet = true;

		int len = src.length();
		if (len == 0 || len > 15) {
			return null;
		}
		/*
		 * When only one part is given, the value is stored directly in
		 * the network address without any byte rearrangement.
		 *
		 * When a two part address is supplied, the last part is
		 * interpreted as a 24-bit quantity and placed in the right
		 * most three bytes of the network address. This makes the
		 * two part address format convenient for specifying Class A
		 * network addresses as net.host.
		 *
		 * When a three part address is specified, the last part is
		 * interpreted as a 16-bit quantity and placed in the right
		 * most two bytes of the network address. This makes the
		 * three part address format convenient for specifying
		 * Class B net- work addresses as 128.net.host.
		 *
		 * When four parts are specified, each is interpreted as a
		 * byte of data and assigned, from left to right, to the
		 * four bytes of an IPv4 address.
		 *
		 * We determine and parse the leading parts, if any, as single
		 * byte values in one pass directly into the resulting byte[],
		 * then the remainder is treated as a 8-to-32-bit entity and
		 * translated into the remaining bytes in the array.
		 */
		for (int i = 0; i < len; i++) {
			char c = src.charAt(i);
			if (c == '.') {
				if (newOctet || tmpValue < 0 || tmpValue > 0xff || currByte == 3) {
					return null;
				}
				res[currByte++] = (byte) (tmpValue & 0xff);
				tmpValue = 0;
				newOctet = true;
			} else {
				int digit = digit(c, 10);
				if (digit < 0) {
					return null;
				}
				tmpValue *= 10;
				tmpValue += digit;
				newOctet = false;
			}
		}
		if (newOctet || tmpValue < 0 || tmpValue >= (1L << ((4 - currByte) * 8))) {
			return null;
		}
		switch (currByte) {
			case 0:
				res[0] = (byte) ((tmpValue >> 24) & 0xff);
			case 1:
				res[1] = (byte) ((tmpValue >> 16) & 0xff);
			case 2:
				res[2] = (byte) ((tmpValue >>  8) & 0xff);
			case 3:
				res[3] = (byte) ((tmpValue >>  0) & 0xff);
		}
		return res;
	}

	/*
	 * Convert IPv6 presentation level address to network order binary form.
	 * credit:
	 *  Converted from C code from Solaris 8 (inet_pton)
	 *
	 * Any component of the string following a per-cent % is ignored.
	 *
	 * @param src a String representing an IPv6 address in textual format
	 * @return a byte array representing the IPv6 numeric address
	 */
	public static byte[] textToNumericFormatV6(String src)
	{
		// Shortest valid string is "::", hence at least 2 chars
		if (src.length() < 2) {
			return null;
		}

		int colonp;
		char ch;
		boolean saw_xdigit;
		int val;
		char[] srcb = src.toCharArray();
		byte[] dst = new byte[INADDR16SZ];

		int srcb_length = srcb.length;
		int pc = src.indexOf ('%');
		if (pc == srcb_length -1) {
			return null;
		}

		if (pc != -1) {
			srcb_length = pc;
		}

		colonp = -1;
		int i = 0, j = 0;
		/* Leading :: requires some special handling. */
		if (srcb[i] == ':')
			if (srcb[++i] != ':')
				return null;
		int curtok = i;
		saw_xdigit = false;
		val = 0;
		while (i < srcb_length) {
			ch = srcb[i++];
			int chval = digit(ch, 16);
			if (chval != -1) {
				val <<= 4;
				val |= chval;
				if (val > 0xffff)
					return null;
				saw_xdigit = true;
				continue;
			}
			if (ch == ':') {
				curtok = i;
				if (!saw_xdigit) {
					if (colonp != -1)
						return null;
					colonp = j;
					continue;
				} else if (i == srcb_length) {
					return null;
				}
				if (j + INT16SZ > INADDR16SZ)
					return null;
				dst[j++] = (byte) ((val >> 8) & 0xff);
				dst[j++] = (byte) (val & 0xff);
				saw_xdigit = false;
				val = 0;
				continue;
			}
			if (ch == '.' && ((j + INADDR4SZ) <= INADDR16SZ)) {
				String ia4 = src.substring(curtok, srcb_length);
				/* check this IPv4 address has 3 dots, ie. A.B.C.D */
				int dot_count = 0, index=0;
				while ((index = ia4.indexOf ('.', index)) != -1) {
					dot_count ++;
					index ++;
				}
				if (dot_count != 3) {
					return null;
				}
				byte[] v4addr = textToNumericFormatV4(ia4);
				if (v4addr == null) {
					return null;
				}
				for (int k = 0; k < INADDR4SZ; k++) {
					dst[j++] = v4addr[k];
				}
				saw_xdigit = false;
				break;  /* '\0' was seen by inet_pton4(). */
			}
			return null;
		}
		if (saw_xdigit) {
			if (j + INT16SZ > INADDR16SZ)
				return null;
			dst[j++] = (byte) ((val >> 8) & 0xff);
			dst[j++] = (byte) (val & 0xff);
		}

		if (colonp != -1) {
			int n = j - colonp;

			if (j == INADDR16SZ)
				return null;
			for (i = 1; i <= n; i++) {
				dst[INADDR16SZ - i] = dst[colonp + n - i];
				dst[colonp + n - i] = 0;
			}
			j = INADDR16SZ;
		}
		if (j != INADDR16SZ)
			return null;
		byte[] newdst = convertFromIPv4MappedAddress(dst);
		if (newdst != null) {
			return newdst;
		} else {
			return dst;
		}
	}

	/*
	 * Convert IPv4-Mapped address to IPv4 address. Both input and
	 * returned value are in network order binary form.
	 *
	 * @param src a String representing an IPv4-Mapped address in textual format
	 * @return a byte array representing the IPv4 numeric address
	 */
	public static byte[] convertFromIPv4MappedAddress(byte[] addr) {
		if (isIPv4MappedAddress(addr)) {
			byte[] newAddr = new byte[INADDR4SZ];
			System.arraycopy(addr, 12, newAddr, 0, INADDR4SZ);
			return newAddr;
		}
		return null;
	}

	private static boolean isIPv4MappedAddress(byte[] addr) {
		if (addr.length < INADDR16SZ) {
			return false;
		}
		if ((addr[0] == 0x00) && (addr[1] == 0x00) &&
			(addr[2] == 0x00) && (addr[3] == 0x00) &&
			(addr[4] == 0x00) && (addr[5] == 0x00) &&
			(addr[6] == 0x00) && (addr[7] == 0x00) &&
			(addr[8] == 0x00) && (addr[9] == 0x00) &&
			(addr[10] == (byte)0xff) &&
			(addr[11] == (byte)0xff))  {
			return true;
		}
		return false;
	}

	public static int digit(char ch, int radix) {
		if (ALLOW_AMBIGUOUS_IPADDRESS_LITERALS_SP_VALUE) {
			return Character.digit(ch, radix);
		} else {
			return parseAsciiDigit(ch, radix);
		}
	}

	// Parse ASCII digit in hexadecimal radix
	private static int parseAsciiHexDigit(char digit) {
		char c = Character.toLowerCase(digit);
		if (c >= 'a' && c <= 'f') {
			return c - 'a' + 10;
		}
		return parseAsciiDigit(c, DECIMAL);
	}
	// Parse ASCII digit in given radix
	private static int parseAsciiDigit(char c, int radix) {
		assert radix == OCTAL || radix == DECIMAL || radix == HEXADECIMAL;
		if (radix == HEXADECIMAL) {
			return parseAsciiHexDigit(c);
		}
		int val = c - '0';
		return (val < 0 || val >= radix) ? -1 : val;
	}

	private static final int INADDR4SZ = 4;
	private static final int INADDR16SZ = 16;
	private static final int INT16SZ = 2;
	// Supported radixes
	private static final int HEXADECIMAL = 16;
	private static final int DECIMAL = 10;
	private static final int OCTAL = 8;
	// 允许有歧义的ipaddress
	private static final boolean ALLOW_AMBIGUOUS_IPADDRESS_LITERALS_SP_VALUE = false;
}
