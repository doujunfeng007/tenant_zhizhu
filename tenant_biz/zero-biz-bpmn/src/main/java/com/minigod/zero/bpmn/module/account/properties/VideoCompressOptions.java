package com.minigod.zero.bpmn.module.account.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 视频压缩配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "minigod.tenant.ffmpeg")
public class VideoCompressOptions {
	private String path="//usr//local//ffmpeg//ffmpeg"; // ffmpeg路径
	private int crf = 23; // 默认压缩质量
	private String videoCodec="libx264"; // 使用H.264编码
	private String preset = "medium"; // 默认压缩速度
	private String resolution = "1280x720"; // 默认分辨率
	private int frameRate = 30; // 设置帧率为30fps
	private String audioBitrate = "128k"; // 默认音频比特率
	private String maxBitrate = "2000k"; // 默认最大视频比特率
	private String bufSize = "4000k"; // 默认缓冲大小
	private int bitRate = 1000; // 设置比特率为1000kbps
}
