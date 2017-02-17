package com.kingschan.blog.common.sina;

import weibo4j.Oauth;
import weibo4j.http.AccessToken;
import com.kingschan.blog.util.UnixDate;

/**
 * sina weibo 
 * @author kings.chan
 *2016-07-08
 */
public class SinaWeiBoUtil {
	
	public static AccessToken token(String code) throws Exception{
		Oauth oauth = new Oauth();
		oauth.authorize("code");
		return oauth.getAccessTokenByCode(code);
	}
	
	public static void main(String[] args) throws Exception {
		//AccessToken [accessToken=2.00WNYXADXProCE8038d0c9cbtjvtVD, expireIn=157679999, refreshToken=,uid=2756394854]
		System.out.println(new UnixDate().formatUnixDate(157679999));
	}
}
	
	

