package com.minigod.zero.bpmn.utils;

import com.minigod.zero.bpmn.module.account.properties.VideoCompressOptions;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.ImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.IIOImage;

/**
 * 视频压缩工具类
 */
@Slf4j
public class FileCompressorUtil {

	private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
	/**
	 * 压缩视频文件
	 *
	 * @param sourceFile 源视频文件
	 * @param options    压缩选项
	 * @return 压缩后的视频文件
	 */
	public static File compressVideo(MultipartFile sourceFile, VideoCompressOptions options) {
		if (sourceFile == null || sourceFile.isEmpty()) {
			throw new IllegalArgumentException("视频源文件不能为空!");
		}

		File inputFile = null;
		File outputFile = null;
		try {
			// 创建临时输入文件
			String originalFilename = sourceFile.getOriginalFilename();
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			inputFile = new File(TEMP_DIR, UUID.randomUUID() + "_input" + extension);
			sourceFile.transferTo(inputFile);

			// 创建临时输出文件
			outputFile = new File(TEMP_DIR, UUID.randomUUID() + "_output" + extension);

			// 配置FFmpeg
			FFmpeg ffmpeg = new FFmpeg(options.getPath());
			FFmpegBuilder builder = new FFmpegBuilder()
				.setInput(inputFile.getAbsolutePath())
				.overrideOutputFiles(true)
				.addOutput(outputFile.getAbsolutePath())
				.setVideoCodec(options.getVideoCodec())
				.setVideoFrameRate(options.getFrameRate()) // 设置帧率
				.setVideoBitRate(options.getBitRate() * 1000) // 设置比特率（将kbps转换为bps）
				.setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
				.done();

			// 执行压缩
			FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
			executor.createJob(builder).run();

			log.info("视频压缩完成. 输出文件: {}", outputFile.getAbsolutePath());
			return outputFile;

		} catch (Exception e) {
			log.error("视频压缩异常,详情: ", e);
			throw new RuntimeException("视频压缩异常: " + e.getMessage());
		} finally {
			// 清理临时输入文件
			if (inputFile != null && inputFile.exists()) {
				try {
					Files.deleteIfExists(inputFile.toPath());
				} catch (IOException e) {
					log.warn("删除临时目录下的源文件异常,异常详情: {}", inputFile.getAbsolutePath());
				}
			}

			// 注册JVM关闭钩子来清理输出文件
			if (outputFile != null) {
				final Path outputPath = outputFile.toPath();
				Runtime.getRuntime().addShutdownHook(new Thread(() -> {
					try {
						Files.deleteIfExists(outputPath);
						log.info("已删除临时目录下的压缩文件: {}", outputPath);
					} catch (IOException e) {
						log.warn("删除临时目录下输出的压缩文件异常,异常详情: {}", outputPath);
					}
				}));
			}
		}
	}

	/**
	 * 检查文件大小是否超过限制
	 *
	 * @param file        要检查的文件
	 * @param maxSizeInMB 最大允许的文件大小（MB）
	 * @return 如果文件大小超过限制返回true，否则返回false
	 */
	public static boolean isFileSizeExceeded(File file, long maxSizeInMB) {
		long maxSizeInBytes = maxSizeInMB * 1024 * 1024;
		log.info("检查文件大小是否超过限制,文件大小:{}", file.length());
		return file.length() > maxSizeInBytes;
	}

	/**
	 * 检查文件大小是否超过限制
	 *
	 * @param size        要检查的文件大小
	 * @param maxSizeInMB 最大允许的文件大小（MB）
	 * @return 如果文件大小超过限制返回true，否则返回false
	 */
	public static boolean isFileSizeExceeded(long size, long maxSizeInMB) {
		long maxSizeInBytes = maxSizeInMB * 1024 * 1024;
		log.info("检查文件大小是否超过限制,文件大小:{}", size);
		return size > maxSizeInBytes;
	}

	/**
	 * 压缩图片
	 *
	 * @param sourceFile 源图片文件
	 * @param quality 压缩质量(1-100)
	 * @return 压缩后的图片文件
	 */
	public static File compressImage(MultipartFile sourceFile, int quality) {
		if (sourceFile == null || sourceFile.isEmpty()) {
			throw new IllegalArgumentException("图片源文件不能为空!");
		}
		if (quality < 1 || quality > 100) {
			throw new IllegalArgumentException("压缩质量必须在1-100之间!");
		}

		File inputFile = null;
		File outputFile = null;
		try {
			// 创建临时输入文件
			String originalFilename = sourceFile.getOriginalFilename();
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			inputFile = new File(TEMP_DIR, UUID.randomUUID() + "_input" + extension);
			sourceFile.transferTo(inputFile);

			// 创建临时输出文件
			outputFile = new File(TEMP_DIR, UUID.randomUUID() + "_output" + extension);

			// 读取原图
			BufferedImage originalImage = ImageIO.read(inputFile);

			// 创建压缩参数
			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extension.substring(1));
			ImageWriter writer = writers.next();
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality / 100f);

			// 压缩图片
			try (ImageOutputStream output = ImageIO.createImageOutputStream(outputFile)) {
				writer.setOutput(output);
				IIOImage image = new IIOImage(originalImage, null, null);
				writer.write(null, image, param);
			}

			log.info("图片压缩完成. 输出文件: {}", outputFile.getAbsolutePath());
			return outputFile;

		} catch (Exception e) {
			log.error("图片压缩异常,详情: ", e);
			throw new RuntimeException("图片压缩异常: " + e.getMessage());
		} finally {
			// 清理临时输入文件
			if (inputFile != null && inputFile.exists()) {
				try {
					Files.deleteIfExists(inputFile.toPath());
				} catch (IOException e) {
					log.warn("删除临时目录下的源文件异常,异常详情: {}", inputFile.getAbsolutePath());
				}
			}

			// 注册JVM关闭钩子来清理输出文件
			if (outputFile != null) {
				final Path outputPath = outputFile.toPath();
				Runtime.getRuntime().addShutdownHook(new Thread(() -> {
					try {
						Files.deleteIfExists(outputPath);
						log.info("已删除临时目录下的压缩文件: {}", outputPath);
					} catch (IOException e) {
						log.warn("删除临时目录下输出的压缩文件异常,异常详情: {}", outputPath);
					}
				}));
			}
		}
	}
}
