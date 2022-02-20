package com.cuscatlan.backendsr.lib.util;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class Utilities {

	/*
	 * genera md5 de cadena de texto
	 */
	public static String hashMd5(String toMd5) {
		return  DigestUtils.md5Hex(toMd5);
	}
	
	public static String getUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
