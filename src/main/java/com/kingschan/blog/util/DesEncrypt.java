package com.kingschan.blog.util;

/*
 * DesEncrypt.java
 *
 *字符串对称加密
 */

//思路： 因为   任意一个字符串，都是由若干字节表示的，每个字节实质就是一个
//              有8位的进进制数，
//      又因为   一个8位二进制数，可用两位16进制字符串表示.
//        因此   任意一个字符串可以由两位16进制字符串表示。
//          而   DES是对8位二进制数进行加密，解密。
//        所以   用DES加密解密时，可以把加密所得的8位进进制数，转成
//               两位16进制数进行保存，传输。
//    具体方法：1 把一个字符串转成8位二进制数，用DES加密，得到8位二进制数的
//                密文
//              2 然后把（由1）所得的密文转成两位十六进制字符串
//              3 解密时，把（由2)所得的两位十六进制字符串，转换成8位二进制
//                数的密文
//              4 把子3所得的密文，用DES进行解密，得到8位二进制数形式的明文，
//                并强制转换成字符串。
// 思考：为什么要通过两位16进制数字符串保存密文呢？
//       原因是：一个字符串加密后所得的8位二进制数，通常不再时字符串了，如果
//              直接把这种密文所得的8位二进制数强制转成字符串，有许多信息因为异
//              常而丢失，导制解密失败。因制要把这个8位二制数，直接以数的形式
//              保存下来，而通常是用两位十六进制数表示。

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 
 * <pre>
 * 类名称：DesEncrypt 
 * 类描述：   
 * 创建人：陈国祥   (kingschan)
 * 创建时间：2012-4-31 上午11:22:41   
 * 修改人：Administrator   
 * 修改时间：2012-4-31 上午11:22:41   
 * 修改备注：   
 * @version V1.0
 * 使用DES加密与解密,可对byte[],String类型进行加密与解密 密文可使用String,byte[]存储. 
 * 方法: void getKey(String strKey)从strKey的字条生成一个Key 
 * String getEncString(String strMing)对strMing进行加密,返回String密文 
 * String getDesString(String strMi)对strMin进行解密,返回String明文 
 * byte[] getEncCode(byte[] byteS)byte[]型的加密
 * byte[] getDesCode(byte[] byteD)byte[]型的解密
 * demo:
 * 	System.out.println("原文：这是一个测试");
 * 		DesEncrypt des = DesEncrypt.getInstance("key");
 * 		String strEnc = des.getEncString("这是一个测试");// 加密字符串,返回String的密文
 * 		System.out.println("加密后："+strEnc);
 * 		String strDes = des.getDesString(strEnc);// 把String 类型的密文解密
 * 		System.out.println("解密后："+strDes);
 * </pre>
 * 
 */

public class DesEncrypt {
	Key key;
	private static DesEncrypt instance = null;

	private DesEncrypt() {
	}

	/**
	 * 
	 * @param key_str
	 *            密钥
	 * @return
	 */
	public static synchronized DesEncrypt getInstance(String key_str) {
		if (null == instance) {
			instance = new DesEncrypt();
		}
		instance.getKey(key_str);
		return instance;
	}

	/**
	 * 根据参数生成KEY
	 * 
	 * @param strKey
	 */

	public void getKey(String strKey) {
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec(strKey.getBytes());
			this.key = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密String明文输入,String密文输出
	 * 
	 * @param strMing
	 * @return
	 */
	public String getEncString(String strMing) {
		String strMi = "";
		try {
			return byte2hex(getEncCode(strMing.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strMi;
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public String getDesString(String strMi) {
		String strMing = "";
		try {
			return new String(getDesCode(hex2byte(strMi.getBytes())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strMing;
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 二行制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) { // 一个字节的数，
		// 转成16进制字符串
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			// 整数转成十六进制表示
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase(); // 转成大写
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}

		return b2;
	}

}
