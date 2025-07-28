package com.minigod.minigodinformation;

import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ZeroCloudApplication
@ComponentScan({"com.minigod.**"})
public class MinigodInformationApplication {

	public static void main(String[] args) {
		ZeroApplication.run("minigod-information", MinigodInformationApplication.class, args);
	}

}
