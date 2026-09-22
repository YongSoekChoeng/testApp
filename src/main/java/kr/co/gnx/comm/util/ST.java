package kr.co.gnx.comm.util;

import java.text.DecimalFormat;

public class ST
{
	public static String s;
	public static final OsType osType;
	
	static
	{
		s = System.getProperty ("file.separator");
		
		String osName = "Windows";
		
		// 운영체제 이름을 알아온다.
		try 
		{
			osName = System.getProperty ("os.name");
		}
		catch (RuntimeException sr) { }
		
		if (osName.indexOf ("Windows") == -1)
			osType = OsType.LINUX;
		else
			osType = OsType.WINDOWS;
	}
	
	/**
	 * 5자리 숫자포맷
	 */
	private static DecimalFormat fiveDf = new DecimalFormat ("00000");
	
	/**
	 * 10자리 숫자포맷
	 */
	private static DecimalFormat tenDf = new DecimalFormat ("0000000000");
	
	/**
	 * 5자리 숫자 포맷을 만든다.
	 * 
	 * @param value
	 * @return
	 */
	public static String fiveFormat (int value)
	{
		return fiveDf.format (value);
	}
	
	/**
	 * 5자리 숫자 포맷을 만든다.
	 * 
	 * @param value
	 * @return
	 */
	public static String fiveFormat (short value)
	{
		return fiveDf.format (value);
	}
	
	/**
	 * 10자리 숫자 포맷을 만든다.
	 * 
	 * @param value
	 * @return
	 */
	public static String tenFormat (int value)
	{
		return tenDf.format (value);
	}
	
	/**
	 * 10자리 숫자 포맷을 만든다.
	 * 
	 * @param value
	 * @return
	 */
	public static String tenFormat (short value)
	{
		return tenDf.format (value);
	}
	
	/**
	 * 숫자를 1자리 부터 10자리 중 선택하여 String 으로 리턴
	 * ttinfo 오후 3:33:11
	 * @param serialPositionalNum
	 * @param val
	 * @return
	 * String
	 */
	public static String cipherFormat (int serialPositionalNum, int val)
	{
		int[] datas = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		String[] values = { "0", "00", "000", "0000", "00000", "000000", "0000000", "00000000", "000000000", "0000000000" };

		DecimalFormat value = null;

		for (int i = 0; i < datas.length; i++)
		{
			if ((i + 1) == serialPositionalNum)
			{
				value = new DecimalFormat (values[i]);
				break;
			}
		}

		return value.format (val);
	}
}
