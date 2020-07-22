package com.jay.util;

import java.sql.Date;

/**
 * this method is customized for converting Date type FROM SQLdate TO JAVAdate
 * 
 * @author Jay
 *
 */
public class DateUtils {
	public static Date changeDateType(java.util.Date date) {
		return new java.sql.Date(date.getYear() - 1900, date.getMonth() - 1, date.getDate() + 1);
	}

}
