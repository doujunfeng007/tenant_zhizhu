import org.springframework.util.Base64Utils;

import java.util.Base64;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/18 11:28
 * @description：
 */
public class Test {
	public static void main(String[] args) {
		String a = "user_client:123456";
		System.out.println(Base64Utils.encodeToString(a.getBytes()));
	}
}
