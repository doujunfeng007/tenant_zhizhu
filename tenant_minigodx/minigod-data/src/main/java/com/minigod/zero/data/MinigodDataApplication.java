package com.minigod.zero.data;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import org.springframework.context.annotation.ComponentScan;

@ZeroCloudApplication
@ComponentScan({"com.minigod.zero.**"})
public class MinigodDataApplication {

	public static void main(String[] args) {
		ZeroApplication.run("minigod-data", MinigodDataApplication.class, args);
	}
}
