package com.petex.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordUtil {

	public static String genrateRandomPassword() {
		String characters = "0123456789";
		String pwd = RandomStringUtils.random(6, characters);
		return pwd;
	}

}