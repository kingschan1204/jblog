package com.kingschan.blog.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author kingschan
 *
 */
public class Base64ImgUtil {
	/**
	 * 将网络图片进行Base64位编码
	 * 
	 * @param imgUrl
	 *			图片的url路径，如http://.....xx.jpg
	 * @return
	 */
	public static String encodeImgageToBase64(URL imageUrl) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		ByteArrayOutputStream outputStream = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(imageUrl);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", outputStream);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		Base64 encoder = new Base64();
		return String.format("%s%s","data:image/png;base64,", new String(encoder.encode(outputStream.toByteArray())));// 返回Base64编码过的字节数组字符串
	}

	/**
	 * 将本地图片进行Base64位编码
	 * 
	 * @param imgUrl
	 *			图片的url路径，如http://.....xx.jpg
	 * @return
	 */
	public static String encodeImgageToBase64(File imageFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		ByteArrayOutputStream outputStream = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(imageFile);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", outputStream);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		Base64 encoder = new Base64();
		return String.format("%s%s","data:image/png;base64,", new String(encoder.encode(outputStream.toByteArray())));// 返回Base64编码过的字节数组字符串
	}

	/**
	 * 将Base64位编码的图片进行解码，并保存到指定目录
	 * 
	 * @param base64
	 *			base64编码的图片信息
	 * @return
	 */
	public static void decodeBase64ToImage(String base64, String path,
			String imgName) {
		Base64 decoder = new Base64();
		try {
			FileOutputStream write = new FileOutputStream(new File(path
					+ imgName));
			byte[] decoderBytes = decoder.decode(base64);
			write.write(decoderBytes);
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws MalformedURLException {
		String s="data:image/png;base64,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";
		String[] data=s.split(",");
		System.out.println(RegexUtil.findStrByRegx(data[0], "/\\w+").substring(1));
		System.out.println(data[1]);
		/*
		Base64ImgUtil.decodeBase64ToImage(s,
				"C:\\", "demo.png");*/
	}
}
