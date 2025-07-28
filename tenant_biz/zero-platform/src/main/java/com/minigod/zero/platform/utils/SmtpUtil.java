package com.minigod.zero.platform.utils;

import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.sun.mail.smtp.SMTPTransport;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClientException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author Zhe.Xiao
 */
@Slf4j
public class SmtpUtil {

	private static final String SZ_DOWNLOAD_DIR = "/data/temp/";

	public static void main(String[] args) {
		List<String> accepts = List.of("123@123.com", "456@456.com","");
		InternetAddress[] adds = new InternetAddress[accepts.size()];
		for (int i = 0; i < accepts.size(); i++) {
			if (accepts.get(i) == null || accepts.get(i).trim().isEmpty()) {
				log.error("收件人地址格式错误：{}", accepts.get(i));
				continue; // 跳过空字符串
			}
			try {
				adds[i] = new InternetAddress(accepts.get(i));
			} catch (AddressException e) {
				log.error("收件人地址格式错误：{}", accepts.get(i));
				continue;
			}
		}
		for (InternetAddress add : adds) {
			System.out.println(add);
		}

	}
	/**
	 * 发送邮件，收件人有多个的情况
	 *
	 * @param emailId
	 * @param host
	 * @param port
	 * @param userName
	 * @param password
	 * @param sender
	 * @param fromName
	 * @param accepts
	 * @param title
	 * @param content
	 * @param type
	 * @param cc
	 * @param bcc
	 * @param fileUrls
	 * @return
	 */
	public static R sendSmtpEmail(String emailId, String host, String port, String userName, String password, String sender, String fromName, List<String> accepts,
								  String title, String content, String type, String cc, String bcc, String fileUrls) {
		try {
			//配置发送邮件的环境属性
			final Properties props = new Properties();
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			//表示SMTP发送邮件，需要进行身份验证
			props.put("mail.smtp.auth", "true");
			//设置服务器主机名
			props.put("mail.smtp.host", host);
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			//设置端口
			props.put("mail.smtp.port", port);
			props.setProperty("mail.smtp.socketFactory.port", port);
			//mailfrom 参数
			props.put("mail.smtp.from", sender);
			//发件人的账号（在控制台创建的发信地址）
			props.put("mail.user", userName);
			//发信地址的smtp密码（在控制台选择发信地址进行设置）
			props.put("mail.password", password);
			//props.setProperty("mail.smtp.ssl.enable", "true");
			//用于解决附件名过长导致的显示异常
			System.setProperty("mail.mime.splitlongparameters", "false");
			//连接时间限制，单位毫秒。是关于与邮件服务器建立连接的时间长短的。默认是无限制。
			props.put("mail.smtp.connectiontimeout", 50000);
			//邮件接收时间限制，单位毫秒。这个是有关邮件接收时间长短。默认是无限制。
			props.put("mail.smtp.timeout", 15000);
			//邮件发送时间限制，单位毫秒。有关发送邮件时内容上传的时间长短。默认同样是无限制。
			props.put("mail.smtp.writetimeout", 15000);

			if (StringUtil.isNotBlank(emailId)) {
				props.put("emailId", emailId);
			}

			// 构建授权信息，用于进行SMTP进行身份验证
			Authenticator authenticator = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// 用户名、密码
					String userName = props.getProperty("mail.user");
					String password = props.getProperty("mail.password");
					return new PasswordAuthentication(userName, password);
				}
			};

			//使用环境属性和授权信息，创建邮件会话
			Session mailSession = Session.getInstance(props, authenticator);
			//开启debug模式
			//mailSession.setDebug(true);

			//返回的唯一id 可查询状态
			String emailIds = null;
			//创建邮件消息
			MimeMessage message = new MimeMessage(mailSession) {
				@Override
				protected void updateMessageID() throws MessagingException {
					//设置自定义Message-ID值
					setHeader("Message-ID", emailId);//创建Message-ID
				}
			};

			//设置发件人邮件地址和名称。填写控制台配置的发信地址。和上面的mail.user保持一致。名称用户可以自定义填写。
			InternetAddress from = new InternetAddress(sender, fromName);
			//from 参数,可实现代发，注意：代发容易被收信方拒信或进入垃圾箱。
			message.setFrom(from);

			//可选。设置回信地址
			Address[] a = new Address[1];
			a[0] = new InternetAddress(sender);
			message.setReplyTo(a);

			//设置收件人邮件地址
			//InternetAddress to = new InternetAddress(accept);
			//message.setRecipient(MimeMessage.RecipientType.TO, to);

			//如果同时发给多人，才将上面两行替换为如下（因为部分收信系统的一些限制，尽量每次投递给一个人；同时我们限制单次允许发送的人数是60人）：
			List<InternetAddress> validAddresses = new ArrayList<>(); // 用于存储有效的 InternetAddress 对象

			for (int i = 0; i < accepts.size(); i++) {
				String email = accepts.get(i);
				if (email == null || email.trim().isEmpty()) {
					System.out.println("Invalid email address: empty or null string");
					continue; // 跳过空字符串或 null
				}
				try {
					// 创建 InternetAddress 对象并验证
					InternetAddress address = new InternetAddress(email);
					address.validate(); // 验证地址
					validAddresses.add(address); // 添加到有效地址列表
				} catch (AddressException e) {
					System.out.println("Invalid email address: " + email + " - " + e.getMessage());
					// 处理无效地址的逻辑，例如可以选择跳过或记录错误
				}
			}
			InternetAddress[] adds = validAddresses.toArray(new InternetAddress[0]);
			message.setRecipients(Message.RecipientType.TO, adds);

			//设置时间
			message.setSentDate(new Date());
			//设置多个抄送地址
			if (null != cc && !cc.isEmpty()) {
				log.info("设置多个抄送地址->cc:" + cc);
				@SuppressWarnings("static-access")
				InternetAddress[] internetAddressCC = new InternetAddress().parse(cc);
				message.setRecipients(Message.RecipientType.CC, internetAddressCC);
			}
			//设置多个密送地址
			if (null != bcc && !bcc.isEmpty()) {
				log.info("设置多个密送地址->bcc:" + bcc);
				@SuppressWarnings("static-access")
				InternetAddress[] internetAddressBCC = new InternetAddress().parse(bcc);
				message.setRecipients(Message.RecipientType.BCC, internetAddressBCC);
			}
			//设置邮件标题
			message.setSubject(title);
//            //若需要开启邮件跟踪服务，请使用以下代码设置跟踪链接头。前置条件和约束见文档"如何开启数据跟踪功能？"
//            String tagName = "Test";
//            HashMap<String, String> trace = new HashMap<>();
//            //这里为字符串"1"
//            trace.put("OpenTrace", "1");      //打开邮件跟踪
//            trace.put("LinkTrace", "1");     //点击邮件里的URL跟踪
//            trace.put("TagName", tagName);   //控制台创建的标签tagname
//            String jsonTrace = new GsonBuilder().setPrettyPrinting().create().toJson(trace);
//            //System.out.println(jsonTrace);
//            String base64Trace = new String(Base64.getEncoder().encode(jsonTrace.getBytes()));
//            //设置跟踪链接头
//            message.addHeader("X-AliDM-Trace", base64Trace);
//            //邮件eml原文中的示例值：X-AliDM-Trace: eyJUYWdOYW1lIjoiVGVzdCIsIk9wZW5UcmFjZSI6IjEiLCJMaW5rVHJhY2UiOiIxIn0=


			if (StringUtil.isNotBlank(fileUrls)) {
				//发送附件和内容：
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(content, type);// 纯文本："text/plain;charset=UTF-8" //设置邮件的内容
				//创建多重消息
				Multipart multipart = new MimeMultipart();
				//设置文本消息部分
				multipart.addBodyPart(messageBodyPart);
				//附件部分
				String[] fileUrlArray = fileUrls.split(";");
				for (int index = 0; index < fileUrlArray.length; index++) {
					String url = fileUrlArray[index];
					MimeBodyPart mimeBodyPart = new MimeBodyPart();
					File file = getFile(url);
					FileDataSource filesdata = new FileDataSource(file);
					mimeBodyPart.setDataHandler(new DataHandler(filesdata));
					String fileName = fileUrlArray[index].substring(fileUrlArray[index].lastIndexOf("/") + 1);
					String s = "?filename=";
					if (fileName.contains(s)) {
						fileName = fileName.substring(fileName.lastIndexOf(s) + s.length());
					}
					//处理附件名称中文（附带文件路径）乱码问题
					mimeBodyPart.setFileName(MimeUtility.encodeWord(fileName));
					mimeBodyPart.addHeader("Content-Transfer-Encoding", "base64");
					multipart.addBodyPart(mimeBodyPart);
				}
				//发送含有附件的完整消息
				message.setContent(multipart);
			} else {
				message.setContent(content, type);
			}

			//发送动作
			log.info("============SMTP邮件发送开始 emailId:{}|title:{}|accept:{}============", emailId, title, accepts);
			int maxRetries = 3;
			int currentRetry = 0;

			while (currentRetry < maxRetries) {
				try {
					//创建Transport对象，用于发送邮件
					SMTPTransport transport = (SMTPTransport) mailSession.getTransport("smtp");

					//添加一个TransportListener，用于监听邮件发送的结果
					transport.addTransportListener(new TransportListener() {
						@SneakyThrows
						@Override
						public void messageDelivered(TransportEvent te) {
							log.info("messageDelivered: emailId:{}|subject={}|to={}", te.getMessage().getSession().getProperty("emailId"), te.getMessage().getSubject(), te.getMessage().getRecipients(MimeMessage.RecipientType.TO));
						}

						@SneakyThrows
						@Override
						public void messageNotDelivered(TransportEvent te) {
							log.info("messageNotDelivered: emailId:{}|subject={}|to={}", te.getMessage().getSession().getProperty("emailId"), te.getMessage().getSubject(), te.getMessage().getRecipients(MimeMessage.RecipientType.TO));
						}

						@SneakyThrows
						@Override
						public void messagePartiallyDelivered(TransportEvent te) {
							log.info("messagePartiallyDelivered: emailId:{}|subject={}|to={}", te.getMessage().getSession().getProperty("emailId"), te.getMessage().getSubject(), te.getMessage().getRecipients(MimeMessage.RecipientType.TO));
						}
					});

					//连接邮件服务器并发送邮件
					transport.connect();
					transport.sendMessage(message, message.getAllRecipients());
					emailIds = getMessage(transport.getLastServerResponse());
					transport.close();
					log.info("============SMTP邮件发送结束 emailIds:{}============", emailIds);
					break;
				} catch (Exception e) {
					// 捕获异常，并增加重试次数
					log.error("发送邮件消息失败，正在重试... 尝试次数: {}，错误信息: {}", currentRetry + 1, e.getMessage(), e);
					currentRetry++;

					// 可以在这里添加一些延迟，比如使用Thread.sleep()，以避免过于频繁的重试
					if (currentRetry < maxRetries) {
						try {
							// 等待一段时间再重试，这里以秒为单位
							Thread.sleep(1000); // 等待1秒
						} catch (InterruptedException ie) {
							// 如果当前线程在等待过程中被中断，可以记录日志或抛出异常
							Thread.currentThread().interrupt(); // 保持中断状态
							log.error("发送邮件消息重试过程中被中断", ie);
							return R.fail(ResultCode.INTERNAL_ERROR, "发送邮件消息过程中被中断");
						}
					}

					// 如果重试次数已达上限，则跳出循环
					if (currentRetry == maxRetries) {
						log.error("发送邮件消息失败，已达到最大重试次数");
						return R.fail(ResultCode.INTERNAL_ERROR, "发送邮件消息失败，已达到最大重试次数");
					}
				}
			}
			log.info("emailIds: {}", emailIds);
			return R.data(emailIds);
		} catch (Exception e) {
			log.error("emailId:{}, sendSmtpEmail error:{}", emailId, e.getMessage(), e);
			return R.fail(e.getMessage());
		}
	}
	private static String getMessage(String reply) {
		String[] arr = reply.split("#");

		String messageId = null;

		if (arr[0].equalsIgnoreCase("250 ")) {
			messageId = arr[1];
		}

		return messageId;
	}

	/**
	 * 发送邮件收件人只有一个的情况
	 *
	 * @param emailId
	 * @param host
	 * @param port
	 * @param userName
	 * @param password
	 * @param sender
	 * @param fromName
	 * @param accept
	 * @param title
	 * @param content
	 * @param type
	 * @param cc
	 * @param bcc
	 * @param fileUrls
	 * @return
	 */
	public static R sendSmtpEmail(String emailId, String host, String port, String userName, String password, String sender, String fromName, String accept,
								  String title, String content, String type, String cc, String bcc, String fileUrls) {
		try {
			//配置发送邮件的环境属性
			final Properties props = new Properties();
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			//表示SMTP发送邮件，需要进行身份验证
			props.put("mail.smtp.auth", "true");
			//设置服务器主机名
			props.put("mail.smtp.host", host);
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			//设置端口
			props.put("mail.smtp.port", port);
			props.setProperty("mail.smtp.socketFactory.port", port);
			//mailfrom 参数
			props.put("mail.smtp.from", sender);
			//发件人的账号（在控制台创建的发信地址）
			props.put("mail.user", userName);
			//发信地址的smtp密码（在控制台选择发信地址进行设置）
			props.put("mail.password", password);
			//props.setProperty("mail.smtp.ssl.enable", "true");
			//用于解决附件名过长导致的显示异常
			System.setProperty("mail.mime.splitlongparameters", "false");
			//连接时间限制，单位毫秒。是关于与邮件服务器建立连接的时间长短的。默认是无限制。
			props.put("mail.smtp.connectiontimeout", 50000);
			//邮件接收时间限制，单位毫秒。这个是有关邮件接收时间长短。默认是无限制。
			props.put("mail.smtp.timeout", 15000);
			//邮件发送时间限制，单位毫秒。有关发送邮件时内容上传的时间长短。默认同样是无限制。
			props.put("mail.smtp.writetimeout", 15000);

			if (StringUtil.isNotBlank(emailId)) {
				props.put("emailId", emailId);
			}
			//返回的唯一id 可查询状态
			String emailIds = null;

			// 构建授权信息，用于进行SMTP进行身份验证
			Authenticator authenticator = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// 用户名、密码
					String userName = props.getProperty("mail.user");
					String password = props.getProperty("mail.password");
					return new PasswordAuthentication(userName, password);
				}
			};

			//使用环境属性和授权信息，创建邮件会话
			Session mailSession = Session.getInstance(props, authenticator);
			//开启debug模式
			//mailSession.setDebug(true);

			//创建邮件消息
			String finalEmailId = emailId;
			MimeMessage message = new MimeMessage(mailSession) {
				@Override
				protected void updateMessageID() throws MessagingException {
					//设置自定义Message-ID值
					setHeader("Message-ID", finalEmailId);//创建Message-ID
				}
			};

			//设置发件人邮件地址和名称。填写控制台配置的发信地址。和上面的mail.user保持一致。名称用户可以自定义填写。
			InternetAddress from = new InternetAddress(sender, fromName);
			//from 参数,可实现代发，注意：代发容易被收信方拒信或进入垃圾箱。
			message.setFrom(from);

			//可选。设置回信地址
			Address[] a = new Address[1];
			a[0] = new InternetAddress(sender);
			message.setReplyTo(a);

			//设置收件人邮件地址
			InternetAddress to = new InternetAddress(accept);
			message.setRecipient(MimeMessage.RecipientType.TO, to);
			//如果同时发给多人，才将上面两行替换为如下（因为部分收信系统的一些限制，尽量每次投递给一个人；同时我们限制单次允许发送的人数是60人）：
			//InternetAddress[] adds = new InternetAddress[2];
			//adds[0] = new InternetAddress("收信地址");
			//adds[1] = new InternetAddress("收信地址");
			//message.setRecipients(Message.RecipientType.TO, adds);

			//设置时间
			message.setSentDate(new Date());
			//设置多个抄送地址
			if (null != cc && !cc.isEmpty()) {
				log.info("设置多个抄送地址->cc:" + cc);
				@SuppressWarnings("static-access")
				InternetAddress[] internetAddressCC = new InternetAddress().parse(cc);
				message.setRecipients(Message.RecipientType.CC, internetAddressCC);
				log.info("设置抄送地址->Recipients:{}", message.getRecipients(MimeMessage.RecipientType.CC));

			}
			//设置多个密送地址
			if (null != bcc && !bcc.isEmpty()) {
				log.info("设置多个密送地址->bcc:" + bcc);
				@SuppressWarnings("static-access")
				InternetAddress[] internetAddressBCC = new InternetAddress().parse(bcc);
				message.setRecipients(Message.RecipientType.BCC, internetAddressBCC);
				log.info("设置密送地址->Recipients:{}", message.getRecipients(MimeMessage.RecipientType.BCC));

			}
			//设置邮件标题
			message.setSubject(title);
//            //若需要开启邮件跟踪服务，请使用以下代码设置跟踪链接头。前置条件和约束见文档"如何开启数据跟踪功能？"
//            String tagName = "Test";
//            HashMap<String, String> trace = new HashMap<>();
//            //这里为字符串"1"
//            trace.put("OpenTrace", "1");      //打开邮件跟踪
//            trace.put("LinkTrace", "1");     //点击邮件里的URL跟踪
//            trace.put("TagName", tagName);   //控制台创建的标签tagname
//            String jsonTrace = new GsonBuilder().setPrettyPrinting().create().toJson(trace);
//            //System.out.println(jsonTrace);
//            String base64Trace = new String(Base64.getEncoder().encode(jsonTrace.getBytes()));
//            //设置跟踪链接头
//            message.addHeader("X-AliDM-Trace", base64Trace);
//            //邮件eml原文中的示例值：X-AliDM-Trace: eyJUYWdOYW1lIjoiVGVzdCIsIk9wZW5UcmFjZSI6IjEiLCJMaW5rVHJhY2UiOiIxIn0=


			if (StringUtil.isNotBlank(fileUrls)) {
				//发送附件和内容：
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(content, type);// 纯文本："text/plain;charset=UTF-8" //设置邮件的内容
				//创建多重消息
				Multipart multipart = new MimeMultipart();
				//设置文本消息部分
				multipart.addBodyPart(messageBodyPart);
				//附件部分
				String[] fileUrlArray = fileUrls.split(";");
				for (int index = 0; index < fileUrlArray.length; index++) {
					String url = fileUrlArray[index];
					MimeBodyPart mimeBodyPart = new MimeBodyPart();
					File file = getFile(url);
					FileDataSource filesdata = new FileDataSource(file);
					mimeBodyPart.setDataHandler(new DataHandler(filesdata));
					String fileName = fileUrlArray[index].substring(fileUrlArray[index].lastIndexOf("/") + 1);
					String s = "?filename=";
					if (fileName.contains(s)) {
						fileName = fileName.substring(fileName.lastIndexOf(s) + s.length());
					}
					//处理附件名称中文（附带文件路径）乱码问题
					mimeBodyPart.setFileName(MimeUtility.encodeWord(fileName));
					mimeBodyPart.addHeader("Content-Transfer-Encoding", "base64");
					multipart.addBodyPart(mimeBodyPart);
				}
				//发送含有附件的完整消息
				message.setContent(multipart);
			} else {
				message.setContent(content, type);
			}

			//发送动作
			log.info("============SMTP邮件发送开始 emailId:{}|title:{}|accept:{}============", emailId, title, accept);
			int maxRetries = 3;
			int currentRetry = 0;

			while (currentRetry < maxRetries) {
				try {
					//创建Transport对象，用于发送邮件
					SMTPTransport transport = (SMTPTransport) mailSession.getTransport("smtp");

					//添加一个TransportListener，用于监听邮件发送的结果
					transport.addTransportListener(new TransportListener() {
						@SneakyThrows
						@Override
						public void messageDelivered(TransportEvent te) {
							log.info("messageDelivered: emailId:{}|subject={}|to={}", te.getMessage().getSession().getProperty("emailId"), te.getMessage().getSubject(), te.getMessage().getRecipients(MimeMessage.RecipientType.TO));
						}

						@SneakyThrows
						@Override
						public void messageNotDelivered(TransportEvent te) {
							log.info("messageNotDelivered: emailId:{}|subject={}|to={}", te.getMessage().getSession().getProperty("emailId"), te.getMessage().getSubject(), te.getMessage().getRecipients(MimeMessage.RecipientType.TO));
						}

						@SneakyThrows
						@Override
						public void messagePartiallyDelivered(TransportEvent te) {
							log.info("messagePartiallyDelivered: emailId:{}|subject={}|to={}", te.getMessage().getSession().getProperty("emailId"), te.getMessage().getSubject(), te.getMessage().getRecipients(MimeMessage.RecipientType.TO));
						}
					});

					//连接邮件服务器并发送邮件
					transport.connect();
					transport.sendMessage(message, message.getAllRecipients());
					emailIds = getMessage(transport.getLastServerResponse());
					transport.close();
					log.info("============SMTP邮件发送结束 emailIds:{}============", emailIds);
					break;
				} catch (Exception e) {
					// 捕获异常，并增加重试次数
					log.error("发送邮件消息失败，正在重试... 尝试次数: {}，错误信息: {}", currentRetry + 1, e.getMessage(), e);
					currentRetry++;

					// 可以在这里添加一些延迟，比如使用Thread.sleep()，以避免过于频繁的重试
					if (currentRetry < maxRetries) {
						try {
							// 等待一段时间再重试，这里以秒为单位
							Thread.sleep(1000); // 等待1秒
						} catch (InterruptedException ie) {
							// 如果当前线程在等待过程中被中断，可以记录日志或抛出异常
							Thread.currentThread().interrupt(); // 保持中断状态
							log.error("发送邮件消息重试过程中被中断", ie);
							return R.fail(ResultCode.INTERNAL_ERROR, "发送邮件消息过程中被中断");
						}
					}

					// 如果重试次数已达上限，则跳出循环
					if (currentRetry == maxRetries) {
						log.error("发送邮件消息失败，已达到最大重试次数");
						return R.fail(ResultCode.INTERNAL_ERROR, "发送邮件消息失败，已达到最大重试次数");
					}
				}
			}
			log.info("emailId:{}", emailIds);
			return R.data(emailIds);
		} catch (Exception e) {
			log.error("emailId:{}, sendSmtpEmail error:{}", emailId, e.getMessage(), e);
			return R.fail(e.getMessage());
		}
	}

	/**
	 * 将图片转为file
	 *
	 * @param url 图片url
	 * @return File
	 */
	public static File getFile(String url) throws Exception {
		//读取图片类型
		String fileName = "";
		String prefix = "";
		int index1 = url.lastIndexOf("/") + 1;
		int index2 = url.lastIndexOf(".");
		if (index1 >= index2 || index2 == -1) {
			fileName = url.substring(index1);
		} else {
			fileName = url.substring(index1, index2);
			prefix = url.substring(url.lastIndexOf("."), url.length());
		}
		if (url.contains("filename=")) {
			fileName = url.substring(url.lastIndexOf("=") + 1, url.lastIndexOf("."));
			url = url.substring(0, url.lastIndexOf("=")) + URLEncoder.encode(fileName, "UTF-8") + prefix;
		}
		File file = null;

		URL urlfile;
		InputStream inStream = null;
		OutputStream os = null;
		try {
			fileName = URLDecoder.decode(fileName, "UTF-8");
			file = new File(SZ_DOWNLOAD_DIR + fileName + prefix);
			File pf = file.getParentFile();
			if (!pf.exists()) {
				pf.mkdirs();
			}
			//获取文件
			urlfile = new URL(url);
			inStream = urlfile.openStream();
			os = new FileOutputStream(file);

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			log.error("getFile error", e);
		} finally {
			try {
				if (null != os) {
					os.close();
				}
				if (null != inStream) {
					inStream.close();
				}
				file.deleteOnExit();
			} catch (Exception e) {
				log.error("Stream close error", e);
			}
		}
		return file;
	}
}
