
package com.minigod.zero.resource.builder.oss;

import com.minigod.zero.resource.entity.Oss;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import com.minigod.zero.core.oss.OssTemplate;
import com.minigod.zero.core.oss.MinioTemplate;
import com.minigod.zero.core.oss.props.OssProperties;
import com.minigod.zero.core.oss.rule.OssRule;

/**
 * Minio云存储构建类
 *
 * @author Chill
 */
public class MinioOssBuilder {

	@SneakyThrows
	public static OssTemplate template(Oss oss, OssRule ossRule) {
		MinioClient minioClient = MinioClient.builder()
			.endpoint(oss.getEndpoint())
			.credentials(oss.getAccessKey(), oss.getSecretKey())
			.build();
		OssProperties ossProperties = new OssProperties();
		ossProperties.setEndpoint(oss.getEndpoint());
		ossProperties.setAccessKey(oss.getAccessKey());
		ossProperties.setSecretKey(oss.getSecretKey());
		ossProperties.setBucketName(oss.getBucketName());
		return new MinioTemplate(minioClient, ossRule, ossProperties);
	}

}
