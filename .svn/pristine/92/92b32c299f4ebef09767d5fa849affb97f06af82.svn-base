package com.wasion.meterreading.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

/**
 * Java Web Token Util
 * 
 * @author w24882 xieningjie
 * @date 2019年11月21日
 */
public class JwtUtil {

	public static final Long EXPIRE_MILISECOND = 1000 * 60 * 15L;

	public static final String TOKEN_SECRET = "169bb8cec0c043f7986cdf7c74443383";

	public static String sign(String username, String userId) {
		Date date = new Date(System.currentTimeMillis() + EXPIRE_MILISECOND);
		try {
			Algorithm hmac256 = Algorithm.HMAC256(TOKEN_SECRET);
			Map<String, Object> header = new HashMap<>(2);
			header.put(PublicClaims.TYPE, "JWT");
			header.put(PublicClaims.ALGORITHM, "HS256");
			return JWT.create().withHeader(header).withClaim("loginName", username).withClaim("userId", userId)
					.withExpiresAt(date).sign(hmac256);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 校验token中的信息
	 * 
	 * @param token
	 * @return 解码解密后的Java Web Token对象（header，payload，signature）
	 * @author w24882 xieningjie
	 * @date 2019年11月21日
	 */
	public static DecodedJWT verify(String token, String loginName) {
		try {
			Algorithm hmac256 = Algorithm.HMAC256(TOKEN_SECRET);
			Verification require = JWT.require(hmac256);
			// 可以增强校验payload中的参数是否与本地保存的一致
			require.withClaim("loginName", loginName);
			JWTVerifier build = require.build();
			// 底层主要校验了payload中的参数，过期时间，签名是否正确
			DecodedJWT verify = build.verify(token);
			return verify;
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
