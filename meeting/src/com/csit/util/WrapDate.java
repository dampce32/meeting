package com.csit.util;

import java.util.Date;
/**
 * @Description:日期包装
 * @Copyright: 福州骏华信息有限公司 (c)2014
 * @Created Date : 2014-2-20
 * @author lhy
 * @vesion 1.0
 */
public class WrapDate extends java.sql.Date{
	private long date;
	public WrapDate(long date) {
		super(date);
		this.date = date;
		// TODO Auto-generated constructor stub
	}
	@SuppressWarnings("deprecation")
	public WrapDate(int year, int month, int day) {
		super(year, month, day);
		// TODO Auto-generated constructor stub
	}
	/**   
	  * serialVersionUID:TODO
	  *   
	  * @since v1.0  
	 **/   
	
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		int year = super.getYear() + 1900;
	    int month = super.getMonth() + 1;
	    int day = super.getDate();
		Date date = new Date(this.date);
		int hour = date.getHours();
		int min = date.getMinutes();
		int sconds = date.getSeconds();
		char buf[] = "2000-00-00 00:00:00".toCharArray();
			buf[0] = Character.forDigit(year/1000,10);
			buf[1] = Character.forDigit((year/100)%10,10);
			buf[2] = Character.forDigit((year/10)%10,10);
			buf[3] = Character.forDigit(year%10,10);
			buf[5] = Character.forDigit(month/10,10);
			buf[6] = Character.forDigit(month%10,10);
			buf[8] = Character.forDigit(day/10,10);
			buf[9] = Character.forDigit(day%10,10);

			buf[11] = Character.forDigit(hour/10,10);
			buf[12] = Character.forDigit(hour%10,10);
			buf[14] = Character.forDigit(min/10,10);
			buf[15] = Character.forDigit(min%10,10);

			buf[17] = Character.forDigit(sconds/10,10);
			buf[18] = Character.forDigit(sconds%10,10);
        return new String(buf);
	}
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
}
