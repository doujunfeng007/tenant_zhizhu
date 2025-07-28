package com.minigod.zero.auth.factorys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebInputException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/11/5 19:59
 * @description：
 */
@Slf4j
@Component
@RefreshScope
public class OperatingEnvironment {

    private static final String PRD = "prd";
	private static Boolean OPEN_TRANSACTION_PASSWORD;

	@Value("${open.transaction.password: true}")
	public void setEnableVerification(Boolean enableVerification){
		OperatingEnvironment.OPEN_TRANSACTION_PASSWORD = enableVerification;
	}
    public static boolean isPrdEnvironment(){
        ConfigurableEnvironment environment = new StandardEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();

        List<String> profiles = Arrays.asList(activeProfiles);
        List<String> activeProfileList = new ArrayList(profiles);

        Function<Object[], String> joinFun = StringUtils::arrayToCommaDelimitedString;
        String activePros = (String)joinFun.apply(activeProfileList.toArray());

		log.info("active profiles: " + activePros);
        return PRD.equals(activePros);
    }

    public static Boolean openTransactionPassword(){
		if (isPrdEnvironment()){
			log.warn("openTransactionPassword : true");
			return true;
		}else {
			log.warn("openTransactionPassword: " + OPEN_TRANSACTION_PASSWORD);
			return OPEN_TRANSACTION_PASSWORD;
		}
    }
}
