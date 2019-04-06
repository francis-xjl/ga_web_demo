package com.xiajl.google_authenticator_web_demo;
 
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import static com.xiajl.google_authenticator_web_demo.GoogleAuthenticator.SEED;

/**
 * 
 * 
 * 身份认证测试
 * 
 * @author yangbo
 * 
 * @version 创建时间：2017年8月14日 上午11:09:23
 *
 * 
 */
public class AuthTest {
	//当测试authTest时候，把genSecretTest生成的secret值赋值给它
	private static String secret="4ZEC5MIESV3PHPE7";
 
	@Test
	public void genSecretTest() {// 生成密钥
		 secret = GoogleAuthenticator.generateSecretKey();
		// 把这个qrcode生成二维码，用google身份验证器扫描二维码就能添加成功
		String qrcode = GoogleAuthenticator.getQRBarcode("francis.xjl@qq.com", secret);
		System.out.println("qrcode:" + qrcode + ",key:" + secret);
	}
	/**
	 * 对app的随机生成的code,输入并验证
	 */
	 @Test
	public void verifyTest() {
		long code = 838534;
		long t = System.currentTimeMillis();
		GoogleAuthenticator ga = new GoogleAuthenticator();
		ga.setWindowSize(5); 
		boolean r = ga.checkCode(secret, code, t);
		System.out.println("检查code是否正确？" + r);
	}

	@Test
	public void test() {
		System.out.println(Base64.encodeBase64String("[B@3453b9ce".getBytes()));
	}
}