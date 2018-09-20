package com.stackroute.qna.util;

import java.util.Date;
import java.util.ResourceBundle;

import com.stackroute.qna.TO.UserTO;
import com.stackroute.qna.entity.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserAuthUtil {
	
	static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

	public static UserTO getTOfromEntity(UserEntity userEntity) {
		if(null==userEntity) {
			return null;
		}

		UserTO userTO = new UserTO();
		userTO.setId(userEntity.getId());
		userTO.setEmail(userEntity.getEmail());
		userTO.setPassword(userEntity.getPassword());
		userTO.setFirstName(userEntity.getFirstName());
		userTO.setLastName(userEntity.getLastName());
		userTO.setCreated(userEntity.getCreated());
		return userTO;
	}

	public static UserEntity getEntityFromTO(UserTO userTO) {
		if(null==userTO) {
			return null;
		}

		UserEntity userEntity = new UserEntity();
		userEntity.setId(userTO.getId());
		userEntity.setEmail(userTO.getEmail());
		userEntity.setPassword(userTO.getPassword());
		userEntity.setFirstName(userTO.getFirstName());
		userEntity.setLastName(userTO.getLastName());
		return userEntity;
	}

	public static String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, applicationProperties.getString("qna.jwt.secrect.key"))
				.compact();
	}
}
