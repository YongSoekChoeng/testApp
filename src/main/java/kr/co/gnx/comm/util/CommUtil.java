package kr.co.gnx.comm.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.co.gnx.comm.util.crypto.CryptoUtil;
import kr.co.gnx.config.Constants;
import kr.co.gnx.config.Constants.UPLOADS;
import kr.co.gnx.system.file.FileVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 공통 유틸
 * @author khko
 * 
 */
@Component(value = "CommUtil")
public class CommUtil {
	
	
	private static final Logger logger = LoggerFactory.getLogger(CommUtil.class);
	
	/**
     * 두 시간에 대한 차리를 분 단위로 계산한다.
     * @param startDate yyyyMMddHHmmss
     * @param endDAte yyyyMMddHHmmss
     * @return 차이 분
     */
    public static long getDifferSec(String startDate, String endDAte){
        
        try {
            Date frDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(startDate);
            Date toDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(endDAte);
            
            long diffMil = toDate.getTime() - frDate.getTime();
            long diffSec = diffMil/1000;
            return diffSec;
            
        } catch (ParseException e) {
            
            return -1;
        }

    }//:
	
	/**
     * @param start millisec
     * @param end millisec
     * @return 차이 시/분/초/밀리초
     */
    public static String getDifferTime(Long start, Long end){
        
        try {
        	Long diffMil = end - start;
        	Long diffSec = diffMil / 1000;
        	
        	String hour = String.valueOf(diffSec / 3600);
        	String min = String.valueOf((diffSec % 3600) / 60);
        	String sec = String.valueOf(diffSec % 60);
        	String millis = String.valueOf(diffMil % 60);
        	String time ="";
        	if(!"0".equals(hour)) {
        		time += hour + "h ";
        	}
        	if(!"0".equals(min)) {
        		time += min + "m ";
        	}
        	if(!"0".equals(min)) {
        		time += sec + "s ";
        	}
        	if(!"0".equals(millis)) {
        		time += millis + "ms";
        	}
            return time;
            
        } catch (Exception e) {
        	return e.getMessage();
        }

    }//:
    
	/**
	 * 접속환경 확인(웹/모바일)
	 * @author JJT
	 * @since 2018.06.11
	 * @return boolean
	 */
	@SuppressWarnings("unused")
	public static boolean isMobile(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        boolean mobile1 = userAgent.matches(".*(iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*");
        boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
        if(mobile1 || mobile2) {
            return true;
        }
        return false;
    }
	/**
     * 휴대폰번호양식에 맞는지 확인
     * @param str
     * @return boolean
     */
	public static boolean isCellphone(String str) {
	    //010, 011, 016, 017, 018, 019
	    return str.matches("(01[016789])\\d{7,8}");
	}
	/**
	 * 이메일 양식에 맞는지 확인
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmail(String str) {
	    //aaa@bbb.com
	    return str.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
	}
	
	/**
	 * 특정자리수만큼 난수 발생
	 * @since 2018.06.11
	 **/
	public static String generateNumber(int length) {
		String numStr = "1";
		String plusNumStr = "1";

		for (int i = 0; i < length; i++) {
			numStr += "0";
			if (i != length - 1) {
				plusNumStr += "0";
			}
		}

		Random random = new Random();
		int result = random.nextInt(Integer.parseInt(numStr)) + Integer.parseInt(plusNumStr);
		if (result > Integer.parseInt(numStr)) {
			result = result - Integer.parseInt(plusNumStr);
		}
		return "" + result;
	}
	
	/**
	 * 널값 "" 로 변경
	 * @since 2018.06.11
	 **/
	public static String nullToEmpty(String str) {
		return str == null ? "" : str;
	}
	
	/**
	 * 현재 일시에 해당하는 Calendar 객체를 반환함.
	 *
	 * <pre>
	 *  ex) Calendar cal = DateUtil.getCalendarInstance()
	 * </pre>
	 *
	 * @return 결과 calendar객체
	 */
	public static Calendar getCalendarInstance() {
		Calendar retCal = Calendar.getInstance();
		return retCal;
	}

	
	
	/**
	 * 입력한 년, 월, 일에 해당하는 Calendar 객체를 반환함. 일자를 바르게 입력하지않으면 엉뚱한 결과가 나타남..
	 *
	 * <pre>
	 *  ex) Calendar cal = DateUtil.getCalendarInstance(1982, 12, 02)
	 * </pre>
	 *
	 * @param year
	 *            년
	 * @param month
	 *            월
	 * @param date
	 *            일
	 * @return 결과 calendar객체
	 */
	public static Calendar getCalendarInstance(int year, int month, int date) {
		Calendar retCal = Calendar.getInstance();
		month--;

		retCal.set(year, month, date);

		return retCal;
	}

	/**
	 * 입력한 년, 월, 일, 시, 분, 초에 해당하는 Calendar 객체를 반환함.<br>
	 * 일자를 바르게 입력하지않으면 엉뚱한 결과가 나타남..
	 *
	 * <pre>
	 *  ex) Calendar cal = DateUtil.getCalendarInstance(1982, 12, 02, 12, 59, 59)
	 * </pre>
	 *
	 * @param year
	 *            년
	 * @param month
	 *            월
	 * @param date
	 *            일
	 * @param hour
	 *            시
	 * @param minute
	 *            분
	 * @param second
	 *            초
	 * @return 결과 calendar객체
	 */
	public static Calendar getCalendarInstance(int year, int month, int date,
			int hour, int minute, int second) {
		Calendar retCal = Calendar.getInstance();
		month--;

		retCal.set(year, month, date, hour, minute, second);

		return retCal;
	}

	/**
	 * calendar에 해당하는 일자를 type의 날짜형식으로 반환합니다.<br>
	 * 타입의 형식을 반드시 지켜야 합니다.<br>
	 * (자세한 사항은 SimpleDateFormat java document 참조.)
	 *
	 * <pre>
	 *  ex) Calendar cal = DateUtil.getCalendarInstance(1982, 12, 02, 12, 59, 59);
	 *      DateUtil.getDateFormat(cal, "yyyyMMddHHmmssSSS")
	 *      DateUtil.getDateFormat(cal, "yyyy-MM-dd hh:mm:ss")
	 *      DateUtil.getDateFormat(cal, "yyyy년MM월dd일 hh시mm분ss초")
	 * </pre>
	 *
	 * @param cal
	 *            calender객체
	 * @param type
	 *            변환타입
	 * @return 변환된 문자열
	 */
	public static String getDateFormat(Calendar cal, String type) {
		SimpleDateFormat dfmt = new SimpleDateFormat(type);
		return dfmt.format(cal.getTime());
	}

	/**
	 * 현재 일자를 입력된 type의 날짜로 반환합니다.<br>타입의 형식을 반드시 지켜야 합니다.<br>
	 * (자세한 사항은 SimpleDateFormat java document 참조.)
	 *
	 * <pre>
	 *  ex) DateUtil.getDateFormat("yyyyMMddHHmmssSSS")
	 *      DateUtil.getDateFormat("yyyy-MM-dd hh:mm:ss")
	 *      DateUtil.getDateFormat("yyyy년MM월dd일 hh시mm분ss초")
	 * </pre>
	 *
	 * @param type
	 *            날짜타입
	 * @return 결과 문자열
	 */
	public static String getDateFormat(String type) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		return sdf.format(date);
	}

	/**
	 * Calender에 해당하는 날짜와 시각을 yyyyMMdd 형태로 변환 후 return.
	 *
	 * <pre>
	 *  ex) String date = DateUtil.getYyyymmdd()
	 * </pre>
	 *
	 * @param cal
	 *            Calender객체
	 * @return 결과 일자
	 */
	public static String getYyyymmdd(Calendar cal) {
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMdd";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				currentLocale);
		return formatter.format(cal.getTime());
	}

	/**
	 * 현재 날짜와 시각을 yyyyMMddhhmmss 형태로 변환 후 return.
	 *
	 * <pre>
	 *
	 *  ex) String date = DateUtil.getCurrentDateTime()
	 * </pre>
	 *
	 * @return 현재 년월일시분초
	 */
	public static String getCurrentDateTime() {
		//자바 1.4 버전 현재 비스타에서 동작시 로컬타임을 잘못가져옴
		//표준시에서 9시간 차이나는것 만큼 표시해야 하는데..
		//비스타에서는 표준시로만 나타냅니다.
		//해결방안으로 setproperty를 추가합니다.
		// 2007.10.10 고재선
		System.setProperty("user.timezone", "Asia/Seoul");
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				currentLocale);
		return formatter.format(today);
	}

	/**
	 * 현재 시각을 hhmmss 형태로 변환 후 return.
	 *
	 * <pre>
	 *  ex) String date = DateUtil.getCurrentDateTime()
	 * </pre>
	 *
	 * @return 현재 시분초
	 */
	public static String getCurrentTime() {
		Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "HHmmss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				currentLocale);
		return formatter.format(today);

	}

	/**
	 * 현재 날짜를 yyyyMMdd 형태로 변환 후 return.
	 *
	 * <pre>
	 *
	 *  ex) String date = DateUtil.getCurrentDate()
	 * </pre>
	 *
	 * @return 현재 년월일
	 */
	public static String getCurrentDate() {
		return getCurrentDateTime().substring(0, 8);
	}
	
	/**
	 * 현재 날짜를 yyyy-MM-dd 형태로 변환 후 return.
	 *
	 * <pre>
	 *
	 *  ex) String date = DateUtil.getCurrentDate()
	 * </pre>
	 *
	 * @return 현재 년월일
	 */
	public static String getCurrentDateYYYYMMDD() {
		return getCurrentDateTime().substring(0, 4)+"-"+getCurrentDateTime().substring(4, 6)+"-"+getCurrentDateTime().substring(6, 8);
	}

	
	/**
     * 입력된 일자를 더한 날짜를 yyyyMMdd 형태로 변환 후 return.
     *
     *
     * @param yyyymmdd
     *            기준일자
     * @param addDay
     *            추가일
     * @return 연산된 일자
     * @see java.util.Calendar
     */
    public static String getDate(String yyyymmdd, int addDay) {
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        int new_yy = Integer.parseInt(yyyymmdd.substring(0, 4));
        int new_mm = Integer.parseInt(yyyymmdd.substring(4, 6));
        int new_dd = Integer.parseInt(yyyymmdd.substring(6, 8));

        cal.set(new_yy, new_mm - 1, new_dd);
        cal.add(Calendar.DATE, addDay);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
       
        return sdf.format(cal.getTime());
        
        // TODO
    }
    
    
	/**
	 * 년월주로 일자를 구하는 메소드.
	 *
	 * <pre>
	 *  ex) String date = DateUtil.getWeekToDay("200801" , 1, "yyyyMMdd")
	 * </pre>
	 *
	 * @param yyyymm
	 *            년월
	 * @param week
	 *            몇번째 주
	 * @param pattern
	 *            리턴되는 날짜패턴 (ex:yyyyMMdd)
	 * @return 연산된 날짜
	 * @see java.util.Calendar
	 */
	public static String getWeekToDay(String yyyymm, int week, String pattern) {

		Calendar cal = Calendar.getInstance(Locale.FRANCE);

		int new_yy = Integer.parseInt(yyyymm.substring(0, 4));
		int new_mm = Integer.parseInt(yyyymm.substring(4, 6));
		int new_dd = 1;

		cal.set(new_yy, new_mm - 1, new_dd);

		// 임시 코드
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			week = week - 1;
		}

		cal.add(Calendar.DATE, (week - 1) * 7
				+ (cal.getFirstDayOfWeek() - cal.get(Calendar.DAY_OF_WEEK)));

		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				Locale.FRANCE);

		return formatter.format(cal.getTime());

	}

	/**
	 * 지정된 플래그에 따라 calendar에 해당하는 연도 , 월 , 일자를 연산한다.<br>
	 * (calendar객체의 데이터는 변하지 않는다.)
	 *
	 * <pre>
	 *  ex) Calendar cal = DateUtil.getCalendarInstance(1982, 12, 02, 12, 59, 59)
	 *      String date = DateUtil.getComputeDate(java.util.Calendar.MONTH , -10, cal);
	 *      결과 : 19820202
	 * </pre>
	 *
	 * @param field
	 *            연산 필드
	 * @param amount
	 *            더할 수
	 * @param cal
	 *            연산 대상 calendar객체
	 * @return 연산된 날짜
	 * @see java.util.Calendar
	 */
	public static String getComputeDate(int field, int amount, Calendar cal) {
		Calendar cpCal = (Calendar) cal.clone();
		cpCal.add(field, amount);
		return getYyyymmdd(cpCal);
	}

	/**
	 * 지정된 플래그에 따라 현재의 연도 , 월 , 일자를 연산한다.
	 *
	 * <pre>
	 *  ex) DateUtil.getComputeDate(java.util.Calendar.MONTH , 10);
	 *      결과 : 19820202
	 * </pre>
	 *
	 * @param field
	 *            연산 필드
	 * @param amount
	 *            더할 수
	 * @return 연산된 날짜
	 * @see java.util.Calendar
	 */
	public static String getComputeDate(int field, int amount) {
		return getComputeDate(field, amount, CommUtil.getCalendarInstance());
	}

	/**
	 * 입력된 일자를 더한 주를 구하여 return한다
	 *
	 * <pre>
	 *  ex) int date = DateUtil.getWeek(DateUtil.getCurrentYyyymmdd() , 0)
	 * </pre>
	 *
	 * @param yyyymmdd
	 *            년도별
	 * @param addDay
	 *            추가일
	 * @return 연산된 주
	 * @see java.util.Calendar
	 */
	public static int getWeek(String yyyymmdd, int addDay) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		int new_yy = Integer.parseInt(yyyymmdd.substring(0, 4));
		int new_mm = Integer.parseInt(yyyymmdd.substring(4, 6));
		int new_dd = Integer.parseInt(yyyymmdd.substring(6, 8));

		cal.set(new_yy, new_mm - 1, new_dd);
		cal.add(Calendar.DATE, addDay);

		int week = cal.get(Calendar.DAY_OF_WEEK);
		return week;
	}

	/**
	 * 입력된 년월의 마지막 일수를 return 한다.
	 *
	 * <pre>
	 *  ex) int date = DateUtil.getLastDayOfMon(2008 , 1)
	 * </pre>
	 *
	 * @param year
	 *            년
	 * @param month
	 *            월
	 * @return 마지막 일수
	 * @see java.util.Calendar
	 */
	public static int getLastDayOfMon(int year, int month) {

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	}// :

	/**
	 * 입력된 년월의 마지막 일수를 return한다
	 *
	 * <pre>
	 *  ex) int date = DateUtil.getLastDayOfMon("2008")
	 * </pre>
	 *
	 * @param yyyymm
	 *            년월
	 * @return 마지막 일수
	 */
	public static int getLastDayOfMon(String yyyymm) {

		Calendar cal = Calendar.getInstance();
		int yyyy = Integer.parseInt(yyyymm.substring(0, 4));
		int mm = Integer.parseInt(yyyymm.substring(4)) - 1;

		cal.set(yyyy, mm, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 입력된 날자가 올바른지 확인합니다.
	 *
	 * <pre>
	 *  ex) boolean b = DateUtil.isCorrect("20080101")
	 * </pre>
	 *
	 * @param yyyymmdd
	 * @return boolean
	 */
	public static boolean isCorrect(String yyyymmdd) {
		boolean flag = false;
		if (yyyymmdd.length() < 8)
			return false;
		try {
			int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
			int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
			int dd = Integer.parseInt(yyyymmdd.substring(6));
			flag = CommUtil.isCorrect(yyyy, mm, dd);
		} catch (Exception ex) {
			return false;
		}
		return flag;
	}

	/**
	 * 입력된 날자가 올바른 날자인지 확인합니다.
	 *
	 * <pre>
	 *  ex) boolean b = DateUtil.isCorrect(2008,1,1)
	 * </pre>
	 *
	 * @param yyyy
	 * @param mm
	 * @param dd
	 * @return boolean
	 */
	public static boolean isCorrect(int yyyy, int mm, int dd) {
		if (yyyy < 0 || mm < 0 || dd < 0)
			return false;
		if (mm > 12 || dd > 31)
			return false;

		String year = "" + yyyy;
		String month = "00" + mm;
		String year_str = year + month.substring(month.length() - 2);
		int endday = CommUtil.getLastDayOfMon(year_str);

		if (dd > endday)
			return false;

		return true;

	}//:

	/**
	 * 현재의 요일을 구한다.
	 *
	 * <pre>
	 *  ex) int day = DateUtil.getDayOfWeek()
	 *      SUNDAY    = 1
	 *      MONDAY    = 2
	 *      TUESDAY   = 3
	 *      WEDNESDAY = 4
	 *      THURSDAY  = 5
	 *      FRIDAY    = 6
	 * </pre>
	 *
	 * @return 요일
	 * @see java.util.Calendar
	 */
	public static int getDayOfWeek() {
		Calendar rightNow = Calendar.getInstance();
		int day_of_week = rightNow.get(Calendar.DAY_OF_WEEK);
		return day_of_week;
	}//:

	
    /**
     * 입력받은 날짜의 요일을 반환한다.
     * @param yyyymmdd
     * @return
     */
    public static int getDayOfWeek(String yyyymmdd) {
        Calendar cal = Calendar.getInstance(Locale.KOREA);
        int new_yy = Integer.parseInt(yyyymmdd.substring(0, 4));
        int new_mm = Integer.parseInt(yyyymmdd.substring(4, 6));
        int new_dd = Integer.parseInt(yyyymmdd.substring(6, 8));

        cal.set(new_yy, new_mm - 1, new_dd);

        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
        return day_of_week;
    }//:
    
    

	/**
	 * 현재주가 올해 전체의 몇째주에 해당되는지 계산한다.
	 *
	 * <pre>
	 *  ex) int day = DateUtil.getWeekOfYear()
	 * </pre>
	 *
	 * @return 주
	 * @see java.util.Calendar
	 */
	public static int getWeekOfYear() {
		Locale LOCALE_COUNTRY = Locale.KOREA;
		Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
		int week_of_year = rightNow.get(Calendar.WEEK_OF_YEAR);
		return week_of_year;
	}//:

	/**
	 * 입력받은 yyyymmdd 가 전체의 몇주에 해당되는지 계산한다.
	 * @param yyyymmdd
	 * @return 주
	 */
    public static int getWeekOfYear(String yyyymmdd) {
        Calendar cal = Calendar.getInstance(Locale.KOREA);
        int new_yy = Integer.parseInt(yyyymmdd.substring(0, 4));
        int new_mm = Integer.parseInt(yyyymmdd.substring(4, 6));
        int new_dd = Integer.parseInt(yyyymmdd.substring(6, 8));

        cal.set(new_yy, new_mm - 1, new_dd);

        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }//:

    
    
    /**
     * 현재주가 현재월에 몇째주에 해당되는지 계산한다.
     *
     * <pre>
     *  ex) int day = DateUtil.getWeekOfMonth()
     * </pre>
     *
     * @return 주
     * @see java.util.Calendar
     */
    public static int getWeekOfMonth() {
        Locale LOCALE_COUNTRY = Locale.KOREA;
        Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
        int week_of_month = rightNow.get(Calendar.WEEK_OF_MONTH);
        return week_of_month;
    }//:

    
	/**
	 * 입력받은 yyyymmdd 해당월에 몇째주에 해당되는지 계산한다.
	 *
	 * <pre>
	 *  ex) int day = DateUtil.getWeekOfMonth("20110401")
	 * </pre>
	 *
	 * @return 주
	 * @see java.util.Calendar
	 */
	public static int getWeekOfMonth(String yyyymmdd) {

        Calendar cal = Calendar.getInstance(Locale.KOREA);
        int new_yy = Integer.parseInt(yyyymmdd.substring(0, 4));
        int new_mm = Integer.parseInt(yyyymmdd.substring(4, 6));
        int new_dd = Integer.parseInt(yyyymmdd.substring(6, 8));
    
        cal.set(new_yy, new_mm - 1, new_dd);
    
        int week = cal.get(Calendar.WEEK_OF_MONTH);
        return week;
    
	}//:
	



	/**
	 * 두 날짜간의 날짜수를 반환(윤년을 감안함)
	 *
	 * <pre>
	 *  ex) long date = DateUtil.getDifferDays("20080101", "20080202")
	 * </pre>
	 *
	 * @param startDate
	 *            시작 날짜
	 * @param endDate
	 *            끝 날짜
	 * @return 날수
	 * @see java.util.GregorianCalendar
	 */
	public static long getDifferDays(String startDate, String endDate) {
		GregorianCalendar StartDate = getGregorianCalendar(startDate);
		GregorianCalendar EndDate = getGregorianCalendar(endDate);
		long difer = (EndDate.getTime().getTime() - StartDate.getTime()
				.getTime()) / 86400000;
		return difer;
	}//:



	   /**
     * 두 시간에 대한 차리를 분 단위로 계산한다.
     * @param startDate yyyyMMddHHmmss
     * @param endDAte yyyyMMddHHmmss
     * @return 차이 분
     */
    public static long getDifferMin(String startDate, String endDAte){
        
        try {
            Date frDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(startDate);
            Date toDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(endDAte);
            
            long diffMil = toDate.getTime() - frDate.getTime();
            long diffSec = diffMil/1000;
            long  Min = (diffSec) / 60;
            
            if(Min < 0){
                Min = Min*-1;
            }
            
            return Min;
            
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return -1;
        }

    }//:
    

	/**
	 * 두 날짜간의 월수를 반환
	 *
	 * <pre>
	 *  ex) long date = DateUtil.getDifferMonths("20080101", "20080202")
	 * </pre>
	 *
	 * @param startDate
	 *            시작 날짜
	 * @param endDate
	 *            끝 날짜
	 * @return 월수
	 * @see java.util.GregorianCalendar
	 */
	public static int getDifferMonths(String startDate, String endDate) {
        GregorianCalendar cal1 = getGregorianCalendar(startDate);
        GregorianCalendar cal2 = getGregorianCalendar(endDate);
        
        int m = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        int months = (m * 12) + (cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH));
        return Math.abs(months);
	}//:


	/**
	 * GregorianCalendar 객체를 반환함.
	 *
	 * <pre>
	 *  ex) Calendar cal = DateUtil.getGregorianCalendar(DateUtil.getCurrentYyyymmdd())
	 * </pre>
	 *
	 * @param yyyymmdd
	 *            날짜 인수
	 * @return GregorianCalendar
	 * @see java.util.Calendar
	 * @see java.util.GregorianCalendar
	 */

	private static GregorianCalendar getGregorianCalendar(String yyyymmdd) {

		int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
		int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
		int dd = Integer.parseInt(yyyymmdd.substring(6));

		GregorianCalendar calendar = new GregorianCalendar(yyyy, mm - 1, dd, 0,
				0, 0);

		return calendar;

	}//:
	
	
	
	
	/**
	 * 날짜 형식 출력
	 * <p/>
	 * yyyy-MM-dd 형식
	 *
	 * @param date 대상 날짜
	 * @return 문자열
	 */
	public  String formatDate(Timestamp date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");


		return format.format(date);
	}
	
	/**
	 * 날짜 형식 출력
	 * <p/>
	 * yyyy-MM-dd HH:mm:ss 형식
	 *
	 * @param date 대상 날짜
	 *
	 * @return 문자열
	 */
	public  String formatDateWithTime(Timestamp date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return format.format(date);
	}
	/**
	 * 날짜 형식 출력
	 *
	 * @param date 대상 날짜 문자열
	 * @param separator 구분자
	 *
	 * @return formatted date string
	 */
	public  String formatDate(String date, String separator) {
		if (date == null || date.length() != 8) {
			return "";
		}
		if (separator == null) {
			separator = ". ";
		}

		StringBuilder sb = new StringBuilder();
		sb.append(date.substring(0, 4))
			.append(separator)
			.append(date.substring(4, 6))
			.append(separator)
			.append(date.substring(6, 8));
		return sb.toString();
	}
	/**
	 * 월 컨트롤
	 *
	 * @param c 대상 날짜
	 * @param gap 변경할 월
	 * @param format 날짜의 포맷 지정
	 *
	 * @return formatted date string
	 */
	public static String getYearMonth(Calendar c,int gap,String format){
		String resultString = "";
		int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1 + gap;
        c.set(year, month - 1, 1);
        resultString = getDateFormat(c,format);
		return resultString;
	}
	
	/**
	 * 입력된 yyyyMMddHHmmss 문자열에 해당하는 millesecond를 반환
	 * @param datetime
	 * @return long
	 */
	public static long getDateTimeMillisecond(String datetime) {
		long ret = 0L;
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d = null;
		try {
			d = df.parse(datetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ret = d.getTime();
		return ret;
	}
	
	
	/**
	 * {@code URLDecoder}를 이용하여 decoding된 URL을 반환한다.
	 *
	 * @param url 대상 문자열
	 *
	 * @return decoding된 URL
	 */
	public  String decodeURL(String url) {
		String decodedURL = "";

		if (StringUtils.isNotEmpty(url)) {
			try {
				decodedURL = URLDecoder.decode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {

			}
		}

		return decodedURL;
	}

	/**
	 * nvl
	 * <p/>
	 * value값이 null이면 replacement를 리턴하고,
	 * value값이 null이 아니면 value값을 리턴한다.
	 *
	 * @param value 원래 문자열
	 * @param replacement 치환문자열
	 *
	 * @return 결과문자열
	 */
	public  String nvl(String value, String replacement) {
		if (value == null || "".equals(value)) {
			return replacement;
		}
		return value;
	}
	
	/**
	 * nvl0
	 * <p/>
	 * value값이 null이거나 '0'이면 replacement를 리턴하고,
	 * value값이 null이 아니면 value값을 리턴한다.
	 *
	 * @param value 원래 문자열
	 * @param replacement 치환문자열
	 *
	 * @return 결과문자열
	 */
	public  String nvl0(String value, String replacement) {
		if (value == null || "".equals(value) || "0".equals(value)) {
			return replacement;
		}
		return value;
	}

	/**
	 * 문자열로부터 HTML/XML 태그를 제거함
	 *
	 * @param message
	 *
	 * @return String message without XML or HTML
	 */
	public  String stripHTMLTags(String message) {
		String noHTMLString = message.replaceAll("\\<.*?\\>", "");
		return noHTMLString;
	}
	
	/**
	 * 정규화 표현식을 문자열로 변환
	 * @param str
	 * @return
	 */
	public static  String codeToStr(String str) {
		str = str.replaceAll("&lt;", "<")
		 		 .replaceAll("&gt;", ">")
		 		 .replaceAll("&#039;", "'")
		 		 .replaceAll("&#034;", "\"")
		 		 .replaceAll("&amp;", "&");
		
		return str;
	}
	
	/**
	 * 문자열을 정규화 표현식으로 변환
	 * @param str
	 * @return
	 */
	public static String strToCode(String str) {
		str = str.replaceAll("&", "&amp;")
		 		 .replaceAll("<", "&lt;")
		 		 .replaceAll(">", "&gt;")
		 		 .replaceAll("'", "&#039;")
		 		 .replaceAll("\"", "&#034;");
		
		return str;
	}
	/**
	 * 문자열 길이만큼 줄임
	 *
	 * @param content 문자열
	 * @param length 길이
	 *
	 * @return 잘린 문자열
	 */
	public  String getFitString(String content, int length) {
		if (content == null) {
			return "";
		}
		String tmp = content;
		int slen = 0;
		int blen = 0;
		if (tmp.getBytes().length > length) {
			while (blen + 1 < length && slen < tmp.length()) {
				char c = tmp.charAt(slen);
				blen++;
				slen++;
				if (c > '\177') {
					blen++;
				}
			}
			tmp = tmp.substring(0, slen);
			if (content.length() > tmp.length()) {
				tmp += "...";
			}
		}
		return tmp;
	}
	
	/**
	 * 소문자 -> 대문자로 변경
	 *
	 * @param str {@code String}
	 * @return 대문자 반환
	 */
	public  String toUpperCase(String str) {
		if (StringUtils.isNotEmpty(str)) {
			return str.toUpperCase();
		}
		return "";
	}

	/**
	 * 대문자 -> 소문자로 변경
	 * @param str {@code String}
	 * @return 소문자 반환
	 */
	public  String toLowerCase(String str) {
		if (StringUtils.isNotEmpty(str)) {
			return str.toLowerCase();
		}
		return "";
	}
	
    /**
     * CLOB로 되어 있는 것을 String 으로 변경한다.
     * @param clob
     * @return
     */
	public static String getStringFromCLOB(java.sql.Clob clob) {
		StringBuffer sbf = new StringBuffer();
		java.io.Reader br = null;
		char[] buf = new char[1024];
		int readcnt;
		try {
			br = clob.getCharacterStream();
			while ((readcnt = br.read(buf, 0, 1024)) != -1) {
				sbf.append(buf, 0, readcnt);
			}
		} catch (Exception e) {

		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {

				}
		}
		return sbf.toString();
	}
    
	/**
	 * 이름을 숨길때 사용
	 * 예) 홍길동  ==> 홍○동
	 * 
	 * @param name
	 * @return
	 */
	public  String getHiddenName(String name) {
		String tmp = "";
		
		if(name.length() > 1) {
			tmp = name.substring(0, 1) + "○" + name.substring(2);
		} else {
			tmp = name;
		}
		
		return tmp;
	}
	
	/**
	 * Compute the hash value to check for "real person" submission.
	 * 
	 * @param  value  the entered value
	 * @return  its hash value
	 */
	public String rpHash(String value) {
		int hash = 5381;
		value = value.toUpperCase();
		for(int i = 0; i < value.length(); i++) {
			hash = ((hash << 5) + hash) + value.charAt(i);
		}
		return String.valueOf(hash);
	}
	
	
	/**
	 * 만 20세 이상인지 확인
	 * 
	 * @param year 년
	 * @param month 월 (1~12)
	 * @param day 일
	 * 
	 * @return 만 20세 이상인지 여부
	 */
	public boolean isOver20Years(int year, int month, int day) {
		Calendar birthday = Calendar.getInstance();
		birthday.set(Calendar.YEAR, year);
		birthday.set(Calendar.MONTH, month-1);
		birthday.set(Calendar.DAY_OF_MONTH, day);
		
		// 14년전 오늘 Calendar
		Calendar theday = Calendar.getInstance();
		theday.add(Calendar.YEAR, -19);

		return birthday.before(theday);
	}

	
	/**
	 * 주민번호를 받아서 생년월일(yyyyMMdd)을 리턴한다.
	 * 
	 * @param ssn 주민번호
	 * @return 생년월일
	 */
	public String getBirth8(String ssn) {
		if (ssn == null) {
			//"ssn is null."
			return null;
		}
		if (ssn.length() < 7) {
			//ssn.length()
			return null;
		}
		String prefix = "20";
		char sexCode = ssn.charAt(6);
		if (sexCode == '1' || sexCode == '2' ||
				sexCode == '5' || sexCode == '6') {
			prefix = "19";
		}
		return prefix + ssn.substring(0, 6);
	}
	
    /**
     * 응답을 HTML로 보낸다.
     *
     * @param response 응답
     * @param data HTML DATA
     */
	public static  void outHTML(HttpServletResponse response, String data, String charset) {
        charset = (charset == null) ? "utf-8" : charset;
        response.setContentType("text/html; charset=" + charset);
        //response.setStatus(response.SC_OK);  // 정상
        
        response.setStatus(HttpServletResponse.SC_OK);  // 정상
        
        printToClient(response, data, charset);
    }//:
    
    /**
     * data를 응답으로 보낸다.
     *
     * @param response 웹응답
     * @param data 응답데이타
     * @param charset 문자셋
     */
    public static  void printToClient(HttpServletResponse response, String data, String charset) {
        PrintWriter out = null;

        try {
            out = new PrintWriter(response.getWriter());
            out.print(data);
            out.flush();
        } catch (Exception e) {
            response.setStatus(500); 
            logger.error(e.getMessage());
        } finally {
            if (out != null)
                out.close();
        }
    }

    /**
     * 난수 생성
	 * @param scerno 난수영역 시작 값
	 * @param ecerno 난수영역 끝 값
	 * @return 생성된 난수
	 * @throws Exception
	 */
	public int makeRandomInt(int scerno , int ecerno) {
		int result = 0;
		double cerno_range = ecerno - scerno +1; 
    
		try{
    		Random randomGenerator = new Random();
			result= (int) (randomGenerator.nextDouble() * cerno_range + scerno);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * request 정보 로그 출력
	 * @param request
	 */
	@SuppressWarnings("rawtypes")
	public void logforRequestParameter(HttpServletRequest request){
		
	 Map map = request.getParameterMap();
	 Iterator it = map.keySet().iterator();
	 Object key = null;
	 String[] value = null;

	 while(it.hasNext()){
		  key = it.next();
		  value = (String[]) map.get(key);
		  for(int i = 0 ; i < value.length; i++) {
			 // logger.info("key ==> " + key +  " value ===> " +value[i]  + " index i ==> " + i);
		  }
	 }

	}
	
	/**
	 * shell 명령어 실행
	 * @param ContentsDwonloadPath
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public boolean callShellCommand(String ContentsDwonloadPath, String url) {
		
		boolean result = true;
		String command  = ContentsDwonloadPath + "getContents.sh " + ContentsDwonloadPath + "temp.html " + url + " "+ ContentsDwonloadPath + "temputf8.html";
		
		logger.debug("************************");
		logger.debug("command  ==> {}", command);
		logger.debug("************************");
		
		BufferedReader br = null;
		
		try {
			java.lang.Runtime runTime = java.lang.Runtime.getRuntime();
			java.lang.Process process = runTime.exec(command);
			//logger.info(process.waitFor());
			
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			for(String str;(str = br.readLine())!=null;){
				//logger.info(str);
			}
			  
			if(process.exitValue()!=0){
				logger.error("************************");
				logger.error("셀이 정상종료 되지 않았습니다.");
				logger.error("************************");
				result = false;
			}
			
			logger.debug("************************");
			logger.debug("프로그램 종료");
			logger.debug("************************");
		} catch(IOException ioE) {
			logger.error(ioE.getMessage());
		} catch(Exception e) {
			logger.error(e.getMessage());
		} finally {
			if(br != null) {
				try { 
					br.close();
				} catch(IOException ioE) {
					ioE.getMessage();
				}
			}
		}
		
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj)
	{
		if( obj instanceof String ) return obj==null || "".equals(obj.toString().trim());
		else if( obj instanceof List ) return obj==null || ((List<?>)obj).isEmpty();
		else if( obj instanceof Map ) return obj==null || ((Map)obj).isEmpty();
		else if( obj instanceof Object[] ) return obj==null || Array.getLength(obj)==0;
		else return obj==null;
	}

	public static boolean isNotEmpty(Object obj)
	{
		return !isEmpty(obj);
	}
	
	public static boolean isEquals(Object sobj, Object tobj)
	{
		if(CommUtil.isNotEmpty(sobj))
		{
			return sobj.equals(tobj);
		}
		return false;
	}
	public static boolean isNotEquals(Object sobj, Object tobj)
	{
		return !isEquals(sobj,tobj);
	}
	
	public static String subString(String str, int start, int end)
	{
		return str.substring(start, end);	
	}
	
	
    /**
     * joson JSONObject를  response에 전달한다.
     * @param response
     * @param jsononject
     * @param resultcode 결과코드 200 정상 500 에러 etc...
     * @throws Exception
     */
    public void sendjson(HttpServletResponse response, JSONObject jsononject ,int resultcode) {

    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	response.setStatus(resultcode);
    	PrintWriter out = null;
		
		try {
			out = response.getWriter();
			
			out.println(jsononject.toString());
		} catch(IOException ioE) {
			logger.error(ioE.getMessage());
    	} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if(out != null) out.close();
		}
	}
    
    /**
     * joson JSONArray를  response에 전달한다.
     * @param response
     * @param jsononarry
     * @param resultcode 결과코드 200 정상 500 에러 etc...
     * @throws Exception
     */
    public void sendjson(HttpServletResponse response, JSONArray jsononarry , int resultcode) {

    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	response.setStatus(resultcode);
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			
			out.println(jsononarry.toString());	
		} catch(IOException ioE) {
			logger.error(ioE.getMessage());
    	} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if(out != null) out.close();
		}
	}
    
    /**
     * 
     * convertMapToObject
     *
     * 최초 생성일  : 2014. 4. 22. : 오후 4:09:10
     * file : CommUtil.java 
     * @param map
     * @param objClass
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static Object convertMapToObject(Map map, Object objClass){
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;
        Iterator itr = map.keySet().iterator();
        
        while(itr.hasNext()){

            keyAttribute = (String) itr.next();
            methodString = setMethodString + keyAttribute.substring(0,1).toUpperCase() + keyAttribute.substring(1);
            
            try {
            	Class<? extends Object> paramClass = objClass.getClass();
            	
                Method[] methods = paramClass.getDeclaredMethods();	//VO
                Method[] superClassmethods = paramClass.getSuperclass().getDeclaredMethods();	//BaseVO
                
                for(int i=0; i <= methods.length-1; i++) {
                	if(methodString.equals(methods[i].getName())) {
                		if(map.get(keyAttribute) instanceof Boolean) {
                			if((Boolean) map.get(keyAttribute)) {
                				methods[i].invoke(objClass, true);
                			} else {
                				methods[i].invoke(objClass, false);
                			}
                		}else if(!(map.get(keyAttribute) instanceof ArrayList)) {
                			if(map.get(keyAttribute) == null || "null".equals(map.get(keyAttribute).toString())) {
                				methods[i].invoke(objClass, "");
                			}else {
                				methods[i].invoke(objClass, map.get(keyAttribute).toString());
                			}
                		}
                	}
                }
                
                for(int i=0; i <= superClassmethods.length-1; i++) {
                	
                    if(methodString.equals(superClassmethods[i].getName())) {
                    	if(map.get(keyAttribute) instanceof Boolean) {
                    		if((Boolean) map.get(keyAttribute)) {
                    			superClassmethods[i].invoke(objClass, true);
                    		} else {
                    			superClassmethods[i].invoke(objClass, false);
                    		}
                    	}else if(!(map.get(keyAttribute) instanceof ArrayList)) {
                    		if(map.get(keyAttribute) == null || "null".equals(map.get(keyAttribute).toString())) {
                    			superClassmethods[i].invoke(objClass, "");
                    		}else {
                    			superClassmethods[i].invoke(objClass, map.get(keyAttribute).toString());
                    		}
                    	}
                    }
                }
            } catch (SecurityException e) {
                
            } catch (IllegalAccessException e) {
                
            } catch (IllegalArgumentException e) {
                
            } catch (InvocationTargetException e) {
                
            }
        }
        return objClass;
    }
    
    /**
     * 
     * ConverObjectToMap
     *
     * 최초 생성일  : 2014. 4. 22. : 오후 4:09:18
     * file : CommUtil.java 
     * @param obj
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map ConverObjectToMap(Object obj){
        try {
            //Field[] fields = obj.getClass().getFields(); //private field는 나오지 않음.
            Field[] fields = obj.getClass().getDeclaredFields();
            Map resultMap = new HashMap();
            for(int i=0; i<=fields.length-1;i++){
                fields[i].setAccessible(true);
                resultMap.put(fields[i].getName(), fields[i].get(obj));
            }
            
            logger.debug("resultMap[{}]" , resultMap);
            
            return resultMap;
        } catch (IllegalArgumentException e) {
            
        } catch (IllegalAccessException e) {
            
        }
        return null;
    }

	/**
	 * 렌덤 스트링 생성 
	 * @return
	 */
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	/**
	 * Description  : 숫자인지 여부 리턴
	 * 최초 생성일  : 2014. 6. 16.
	 * file         : CommUtil.java
	 * RETURN       : boolean 
	 * @param s
	 * @return
	 */
	public static boolean isStringDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
	}
	
    public static Object convJonObjToPojo(JSONObject jobj, Class<?> c){
        
        
        return JSONObject.toBean(jobj, c);
    } 

    

    /**
     * Description  : JSONObject to Pojo
     * 최초 생성일  : 2014. 7. 25.
     * file         : CommUtil.java
     * RETURN       : Object 
     * @param map
     * @param objClass
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Object convertJSONToObject(JSONObject json, Object objClass){
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;
        Iterator itr = json.keySet().iterator();
        while(itr.hasNext()){

            keyAttribute = (String) itr.next();
            methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
            try {
                Method[] methods = objClass.getClass().getDeclaredMethods();
                
                for(int i=0;i<=methods.length-1;i++){
                    
                    if(methodString.equals(methods[i].getName())){
                        
                          if(json.get(keyAttribute) instanceof Boolean )
                          {
                              if((Boolean) json.get(keyAttribute))
                              {
                                  methods[i].invoke(objClass, true);
                              }else{
                                  methods[i].invoke(objClass, false);
                              }
                          }
//                        else if((map.get(keyAttribute) instanceof ArrayList)){
//                          methods[i].invoke(objClass, (ArrayList)map.get(keyAttribute));
//                        }
                          else if(!(json.get(keyAttribute) instanceof ArrayList))
                          {
                              if(json.get(keyAttribute) == null){
                                  methods[i].invoke(objClass, "");
                              }
                              else
                              {
                                  methods[i].invoke(objClass, json.get(keyAttribute).toString());
                              }
                          }
                    }
                }
            } catch (SecurityException e) {
                
            } catch (IllegalAccessException e) {
                
            } catch (IllegalArgumentException e) {
                
            } catch (InvocationTargetException e) {
                
            }
        }
        return objClass;
    }
	
//	public static JSONArray JsonFromObject(Object obj){
//	    JSONArray rtnArr = JSONArray.fromObject(obj);
//	    
//	    for(int i=0;i<rtnArr.size();i++)
//	    {
//	        JsonConfig.DEFAULT_JSON_VALUE_PROCESSOR_MATCHER
//	        rtnArr.fromObject(arg0, arg1)
//	    }
//	}
    
    /**
     * Map을 json으로 변환한다.
     *
     * @param map Map<String, Object>.
     * @return JSONObject.
     */
    public static JSONObject getJsonStringFromMap( Map<String, Object> map )
    {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        
        return jsonObject;
    }
    
    /**
     * List<Map>을 jsonArray로 변환한다.
     *
     * @param list List<Map<String, Object>>.
     * @return JSONArray.
     */
    public static JSONArray getJsonArrayFromList( List<Map<String, Object>> list )
    {
        JSONArray jsonArray = new JSONArray();
        for( Map<String, Object> map : list ) {
            jsonArray.add( getJsonStringFromMap( map ) );
        }
        
        return jsonArray;
    }
    
    /**
     * List<Map>을 jsonString으로 변환한다.
     *
     * @param list List<Map<String, Object>>.
     * @return String.
     */
    public static String getJsonStringFromList( List<Map<String, Object>> list )
    {
        JSONArray jsonArray = getJsonArrayFromList( list );
        return jsonArray.toString();
    }
 
    /**
     * JsonObject를 Map<String, String>으로 변환한다.
     *
     * @param jsonObj JSONObject.
     * @return Map<String, Object>.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromJsonObject( JSONObject jsonObj )
    {
        Map<String, Object> map = null;
        
        try {
            
            map = new ObjectMapper().readValue(jsonObj.toString(), Map.class) ;
            
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return map;
    }
    
    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> getHashMapFromJsonObject( JSONObject jsonObj )
    {
        HashMap<String, Object> map = null;
        
        try {
            
            map = new ObjectMapper().readValue(jsonObj.toString(), HashMap.class) ;
            
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return map;
    }
 
    /**
     * JsonArray를 List<Map<String, String>>으로 변환한다.
     *
     * @param jsonArray JSONArray.
     * @return List<Map<String, Object>>.
     */
    public static List<Map<String, Object>> getListMapFromJsonArray( JSONArray jsonArray )
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        if( jsonArray != null )
        {
            int jsonSize = jsonArray.size();
            for( int i = 0; i < jsonSize; i++ )
            {
                Map<String, Object> map = getMapFromJsonObject( ( JSONObject ) jsonArray.get(i) );
                list.add( map );
            }
        }
        
        return list;
    }
    
    public static List<HashMap<String, Object>> getListHashMapFromJsonArray( JSONArray jsonArray )
    {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        
        if( jsonArray != null )
        {
            int jsonSize = jsonArray.size();
            for( int i = 0; i < jsonSize; i++ )
            {
                HashMap<String, Object> map = getHashMapFromJsonObject( ( JSONObject ) jsonArray.get(i) );
                list.add( map );
            }
        }
        
        return list;
    }
    
    /*
     * globals.properties 파일 읽기
     */
    public static Properties loadPropertiesFile(String pathandFile) {
    	Properties prop = System.getProperties();
		String webRoot = prop.getProperty("web.root");
		logger.info("tmpRoot="+webRoot);
		String properties_dir = "";
		if( webRoot == null ) {
			properties_dir = prop.getProperty("user.dir")+"/src/main/resources/properties/";
		} else {
			properties_dir = webRoot+"WEB-INF/classes/resources/properties/";
		}
		
		String properties_file = "";
		if( pathandFile == null ) {
			properties_file = properties_dir + "globals.xml";
		} else {
			properties_file = pathandFile;
		}
		
		logger.info("properties_file="+properties_file);
		
		// read globals.properties
		Properties global_properties = new Properties();
		try {
			global_properties.load(new FileInputStream(properties_file));
		} catch (IOException ioe) {
			ioe.getMessage();
		}
		
//		// Write globals.properties file
//		try {
//			global_properties.setProperty("keyname", "keyvalue");
//			global_properties.store(new FileOutputStream(properties_file), null);
//		} catch ( IOException ioe2) {
//			ioe2.printStackTrace();
//		}
		
    	return global_properties;
    }
	

    public static String XmlEncrypt(String encryptkey,Map<String,String> input_data){
    	
    	try {
	    	if(null != encryptkey){
		    	StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		        encryptor.setPassword(encryptkey);
		        
		        if(null != input_data){
			        for(String mapkey : input_data.keySet()){
			        	System.out.println("key:"+mapkey);
			        	System.out.println("value:"+input_data.get(mapkey));
			        	System.out.println("encrypt:"+encryptor.encrypt(input_data.get(mapkey)));
			        	System.out.println("###############################################");
			        }
		        }else{
		        	return "암호화 대상 데이터가 없습니다.";
		        }
		        
	        }else{
	        	return "암호화키를 입력하세요";
	        }
    	}catch (Exception e){
			return "오류 발생";
		}
    	return "암호화 성공";
	}

    public static String XmlDecrypt(String decryptkey,Map<String,String> input_data){
    	
    	try {
	    	if(null != decryptkey){
		    	StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		        encryptor.setPassword(decryptkey);
		        
		        if(null != input_data){
			        for(String mapkey : input_data.keySet()){
			        	System.out.println("key:"+mapkey);
			        	System.out.println("value:"+input_data.get(mapkey));
			        	System.out.println("encrypt:"+encryptor.decrypt(input_data.get(mapkey)));
			        }
		        }else{
		        	return "복호화 대상 데이터가 없습니다.";
		        }
		        
	        }else{
	        	return "복호화키를 입력하세요";
	        }
    	}catch (Exception e){
			return "오류 발생";
		}
    	return "복호화 성공";
	}

    public static String generateToken() {
        String token = null;
        try {
        	SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(secureRandom.generateSeed(128));
            token= CryptoUtil.makeSHA256(new String((String.valueOf(secureRandom.nextLong())).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            
        }
        return token;
    }
    
    /**
     * genexon.alert 호출
     */
    public static void sendGenexonAlert(HttpServletResponse response, String info, String contents, String message) {

    	String sContents = "";
    	PrintWriter out = null;
    	try {
	    	response.setContentType("text/html;charset=utf-8");
	    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    	out = response.getWriter();
			sContents += "<script type='text/javascript' src='/resources/js/jquery/jquery.min.js'></script>";
			sContents += "<script type='text/javascript' src='/resources/js/common/genexon.js'></script>";
			sContents += "<script type='text/javascript'>";
			sContents += "$(document).ready(function(){";
			sContents += "genexon.initKendoUI_notification();";
			sContents += "genexon.alert('"+ info +"', '"+ contents +"', '" + message + "');";
			sContents += "})";
			sContents += "</script>";
			
			out.println(sContents);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if(out != null) out.close();
		}
    }
    
    /**
     * alert 호출
     * MOBILE
     * @author JJT
     * @since 2020.04.13
     */
    public static void sendMobileAlert(HttpServletResponse response, String info, String contents, String message) {

    	String sContents = "";
    	PrintWriter out = null;
    	try {
	    	response.setContentType("text/html;charset=utf-8");
	    	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    	out = response.getWriter();
			sContents += "<script type='text/javascript' src='/resources/js/jquery/jquery.min.js'></script>";
			//sContents += "<script type='text/javascript' src='/resources/js/common/genexon.js'></script>";
			sContents += "<script type='text/javascript'>";
			sContents += "$(document).ready(function(){";
			//sContents += "genexon.initKendoUI_notification();";
			sContents += "alert('["+ info +":"+ contents +"] " + message + "');";
			sContents += "})";
			sContents += "</script>";
			
			out.println(sContents);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if(out != null) out.close();
		}
    }
    
    /**
     * alert 메시지 전송후 메인으로 이동
     * @param response
     * @param message
     */
    public static void sendAlertMsg(HttpServletResponse response, String message) {

    	String sContents = "";
    	PrintWriter out = null;
    	
		try {
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			
			sContents = "<script type='text/javascript'>";
			sContents += "alert('" + message + "');";
			sContents += "window.parent.location.href='/index.go';";
			sContents += "</script>";
			
			out.println(sContents);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if(out != null) out.close();
		}

	}

	// 임시비밀번호 만들기
	public static String temporaryPassword(int size) {

		StringBuffer buffer = new StringBuffer();
		SecureRandom random = new SecureRandom();
		random.setSeed(new Date().getTime());

		String chars[] = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9".split(",");
		String SpecialChars[] = "~,!,@,#,$,%,^,&,*,(,),+,-".split(",");

		int SpCnt = random.nextInt(9);

		for (int i = 0; i < size; i++) {
			if (SpCnt == i) {
				// 특수문자
				buffer.append(SpecialChars[random.nextInt(SpecialChars.length)]);
			} else {
				// 영어+숫자
				buffer.append(chars[random.nextInt(chars.length)]);
			}
		}

		return buffer.toString();
	}
	
	public static String parseMessage(String message, String...args) {
	      if (message == null || message.trim().length() <= 0)
	         return message;
	  
	      if (args == null || args.length <= 0) return message;
	  
	      String[] splitMsgs = message.split("%");
	      if (splitMsgs == null || splitMsgs.length <= 1)
	         return message;
	  
	      for (int i = 0; i < args.length; i++) {
	         String replaceChar = "%" + (i + 1);
	         message = message.replaceFirst(replaceChar, args[i]);
	      }
	      return message;
	}
	
	public static String getClientIp(HttpServletRequest request) {
		String ip = InetUtil.getClientIP(request);
		
		if(ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
			ip = InetUtil.getCurrentEnvironmentNetworkIp();
		}
		
		return ip;
	}
	
	/**
	 * HttpURLConnection으로 ERP 데이터 받아오기(JSONObject 형식)
	 * @param erp_synk_url
	 * @return JSONObject
	 */
	public static JSONObject getErpDataConnection(String erp_sync_url, String mb_id) throws IOException, Exception {
		JSONObject jsonObj = new JSONObject();
		BufferedReader bReader = null;
		OutputStream os = null;
		OutputStreamWriter writer = null;
		int HttpResult = 0;
		String param = "";
		
		
		try {
			URL url = new URL(erp_sync_url+"&mb_id="+mb_id);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoInput(true);			// 입력스트림 사용여부
			httpConn.setDoOutput(true);			// 출력스트림 사용여부
			httpConn.setUseCaches(false);		// 캐시사용 여부
			httpConn.setRequestProperty("Accept", "application/json");
			httpConn.setRequestProperty("Content-type", "application/json");
			httpConn.setReadTimeout(10000);		// 타임아웃 설정 ms단위
			httpConn.setRequestMethod("POST");	// or GET
			os = httpConn.getOutputStream();
			writer = new OutputStreamWriter(os);
			writer.write("&mb_id="+URLEncoder.encode(mb_id, "UTF-8"));
			writer.flush();
			
			StringBuilder stringBuilder = new StringBuilder();
			HttpResult = httpConn.getResponseCode();
			
			if( HttpResult == HttpURLConnection.HTTP_OK ) {
				bReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				String line=null;
				
				while( (line = bReader.readLine()) != null ) {
					stringBuilder.append(line + "\n");
				}
				
				param = stringBuilder.toString();
				jsonObj = (JSONObject) JSONObject.fromObject(param);
				
			} else {
				return null;
			}
			
		}catch(IOException ioe) {
			return null;
		}catch(Exception e) {
			return null;
		}finally {
			if(bReader != null) bReader.close();
			if(writer != null) writer.close();
			if(os != null) os.close();
		}
		
		return jsonObj;
	}
	
	/**
	 * HttpURLConnection으로 ERP 데이터 받아오기(JSONObject 형식)
	 * @param erp_synk_url
	 * @return JSONObject
	 */
	public static JSONObject getApiData(String apiUrl, String reqMethod, Map<String, String> paramMap) throws IOException, Exception {
		JSONObject jsonObj = new JSONObject();
		BufferedReader bReader = null;
		OutputStream os = null;
		OutputStreamWriter writer = null;
		int HttpResult = 0;
		String param = "";
		
		try {
			URL conUrl = new URL(apiUrl);
			
			if(apiUrl.contains("https://")) {
				logger.info("HttpsURLConnection!");
				HttpsURLConnection connection = (HttpsURLConnection) conUrl.openConnection();
				
				connection.setHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return false;
					}
				});
				
				// SSL setting  
				SSLContext context = SSLContext.getInstance("TLS");  
				context.init(null, null, null);  // No validation for now  
				connection.setSSLSocketFactory(context.getSocketFactory());
				
				connection.setRequestProperty("dataType", "json");
				connection.setDoInput(true);            // 입력스트림 사용여부
		        connection.setDoOutput(true);            // 출력스트림 사용여부
		        connection.setUseCaches(false);        // 캐시사용 여부
		        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        connection.setReadTimeout(10000);        // 타임아웃 설정 ms단위
		        connection.setRequestMethod(reqMethod);  // or GET
		        
				os = connection.getOutputStream();
				writer = new OutputStreamWriter(os);
				
				Iterator<String> iterator = paramMap.keySet().iterator();
				while (iterator.hasNext()) {
			        String key = iterator.next();
			        if(null != paramMap.get(key) && !"null".equals(paramMap.get(key).toString())){
			        	writer.write("&"+key+"="+URLEncoder.encode(paramMap.get(key).toString(), "UTF-8"));
			        }else{
			        	writer.write("&"+key+"="+"");
			        }
			    }
				
				writer.flush();
				
				HttpResult = connection.getResponseCode();
				
				StringBuilder stringBuilder = new StringBuilder();
				logger.info("connection Code = " + HttpResult);
				
				if( HttpResult == HttpURLConnection.HTTP_OK ) {
					bReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
					String line = null;
					while( (line = bReader.readLine()) != null ) {
						stringBuilder.append(line + "\n");
					}
					
					param = stringBuilder.toString();
					jsonObj = (JSONObject) JSONObject.fromObject(param);
					
				} else {
					logger.info("connection.getResponseMessage()="+connection.getResponseMessage());
				}
			}else{
				logger.info("HttpURLConnection!");
				HttpURLConnection connection = (HttpURLConnection) conUrl.openConnection();
				connection.setRequestProperty("Content-Type", "application/json; utf-8");
//				connection.setRequestProperty("dataType", "json");
				connection.setRequestProperty("Accept", "application/json");
				connection.setDoInput(true);            // 입력스트림 사용여부
		        connection.setDoOutput(true);            // 출력스트림 사용여부
		        connection.setUseCaches(false);        // 캐시사용 여부
		        // connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        
		        connection.setReadTimeout(10000);        // 타임아웃 설정 ms단위
		        connection.setRequestMethod(reqMethod);  // or GET
		        
				os = connection.getOutputStream();
				writer = new OutputStreamWriter(os);
				
				Iterator<String> iterator = paramMap.keySet().iterator();
				
				while (iterator.hasNext()) {
			        String key = iterator.next();
			        if(null != paramMap.get(key) && !"null".equals(paramMap.get(key).toString())){
			        	writer.write("&"+key+"="+URLEncoder.encode(paramMap.get(key).toString(), "UTF-8"));
			        }else{
			        	writer.write("&"+key+"="+"");
			        }
			    }
				
				writer.flush();
				
				HttpResult = connection.getResponseCode();
				
				StringBuilder stringBuilder = new StringBuilder();
				logger.info("connection Code = " + HttpResult);
				
				if( HttpResult == HttpURLConnection.HTTP_OK ) {
					bReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
					String line = null;
					while( (line = bReader.readLine()) != null ) {
						stringBuilder.append(line + "\n");
					}
					param = stringBuilder.toString();
					jsonObj = (JSONObject) JSONObject.fromObject(param);
				} else {
					logger.info("connection.getResponseMessage()="+connection.getResponseMessage());
				}
			}
			
		}catch(IOException ioe) {
			return null;
		}catch(Exception e) {
			return null;
		}finally {
			if(bReader != null) bReader.close();
			if(writer != null) writer.close();
			if(os != null) os.close();
		}
		
		return jsonObj;
	}
	
	/**
	}
	
	/**
	 * CSV 파일을 XLSX로 변경
	 * @author KIMDONGUK
	 * @since 2020-01-13
	 * @return String
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static String convertCSVToXLSX(FileVO fileVO, UPLOADS u) throws Exception {
		String file_no = fileVO.getFile_no();
		
		String originalCSVFilePath = fileVO.getFile_path();
		String convertXLSXFilePath = Constants.getPATH(u) + file_no + ".xlsx";
		
		SXSSFWorkbook workBook =null;
		FileInputStream  fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		FileOutputStream fileOutputStream = null;
		
		try {
			workBook = new SXSSFWorkbook();
			Sheet sheet = workBook.createSheet("Sheet1");
			String currentLine = null;
			
	        int RowNum = 0;
	        
	        fileInputStream = new FileInputStream(originalCSVFilePath);
	        inputStreamReader = new InputStreamReader(fileInputStream, "EUC-KR");
	        BufferedReader br = new BufferedReader(inputStreamReader);
	        
	        while ((currentLine = br.readLine()) != null) {
	            String str[] = currentLine.split(",");
	            Row currentRow = sheet.createRow(RowNum);
	            
	            for (int i = 0; i < str.length; i++) {
	                currentRow.createCell(i).setCellValue(str[i]);
	            }
	            
	            RowNum++;
	        }
	        
	        fileOutputStream = new FileOutputStream(convertXLSXFilePath);
	        workBook.write(fileOutputStream);
	        workBook.close();
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			throw e;
		}finally {
			workBook.close();
			fileOutputStream.close();
			inputStreamReader.close();
			fileInputStream.close();
		}
		
		return convertXLSXFilePath;
	}
	
	/**
	 * XLS 파일을 XLSX로 변경
	 * @author KIMDONGUK
	 * @since 2020-02-11
	 * @return String
	 * @throws Exception
	 */
	public static String convertXLSToXLSX(FileVO fileVO, UPLOADS u) throws Exception {
		String file_no = fileVO.getFile_no();
		
		String originalCSVFilePath = fileVO.getFile_path();
		String convertXLSXFilePath = Constants.getPATH(u) + file_no + ".xlsx";
		
		InputStream in = null;
		Workbook wbIn = null;
		Workbook wbOut = null;
		OutputStream out = null;
		
		try {
			//입력 파일 세팅
			in = new BufferedInputStream(new FileInputStream(originalCSVFilePath));
			wbIn = new HSSFWorkbook(in);
            
			//저장될 파일 존재하는지 확인
			File outF = new File(convertXLSXFilePath);
			
			//똑같은 파일 있으면 삭제
            if (outF.exists())
            	outF.delete();

            wbOut = new XSSFWorkbook();
            int sheetCnt = wbIn.getNumberOfSheets();
            
            //for (int i = 0; i < sheetCnt; i++) {
            for (int i = 0; i < 1; i++) {		//첫번째시트만 업로드 할 것이므로 i = 0일때만 for문 돔
                Sheet sIn = wbIn.getSheetAt(i);
                Sheet sOut = wbOut.createSheet(sIn.getSheetName());
                Iterator<Row> rowIt = sIn.rowIterator();
                
                while (rowIt.hasNext()) {
                    Row rowIn = rowIt.next();
                    Row rowOut = sOut.createRow(rowIn.getRowNum());

                    Iterator<Cell> cellIt = rowIn.cellIterator();
                    
                    while (cellIt.hasNext()) {
                        Cell cellIn = cellIt.next();
                        Cell cellOut = rowOut.createCell(cellIn.getColumnIndex(), cellIn.getCellType());

                        switch (cellIn.getCellType()) {
	                        case BLANK:
	                            break;
	
	                        case BOOLEAN:
	                            cellOut.setCellValue(cellIn.getBooleanCellValue());
	                            break;
	
	                        case ERROR:
	                            cellOut.setCellValue(cellIn.getErrorCellValue());
	                            break;
	
	                        case FORMULA:
	                            cellOut.setCellFormula(cellIn.getCellFormula());
	                            break;
	
	                        case NUMERIC:
	                        	if (DateUtil.isCellDateFormatted(cellIn)) {

									Date date = cellIn.getDateCellValue();

									cellOut.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(date));
								} else {
									cellOut.setCellValue(cellIn.getNumericCellValue());
								}
	                        	
								break;
	
	                        case STRING:
	                            cellOut.setCellValue(cellIn.getStringCellValue());
	                            break;
	                        
	                        default :
	                        	cellOut.setCellValue("");
	                        	break;
                        }

                        CellStyle styleIn = cellIn.getCellStyle();
                        CellStyle styleOut = cellOut.getCellStyle();
                        
                        styleOut.setDataFormat(styleIn.getDataFormat());
                        cellOut.setCellComment(cellIn.getCellComment());
                    }
                }
            }
            
            out = new BufferedOutputStream(new FileOutputStream(outF));
            wbOut.write(out);
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			throw e;
		}finally {
			if(wbOut != null)
				wbOut.close();
			
			if(out != null)
				out.close();
			
			if(wbIn != null)
				wbIn.close();
			
			if(in != null)
				in.close();
			
		}
		
		return convertXLSXFilePath;
	}
	
	/**
	 * HttpURLConnection으로 ERP 데이터 받아오기(JSONObject 형식)
	 * @param erp_synk_url
	 * @return JSONObject
	 */
	public static JSONObject getFCWorldAPIData(String api_url, String reqMethod, HashMap<String,Object> paramMap) throws IOException, Exception {
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		BufferedReader bReader = null;
		OutputStream os = null;
		OutputStreamWriter writer = null;
		int HttpResult = 0;
		String param = "";
		String jsonString ="";
		
		ObjectMapper mapper = new ObjectMapper();
		jsonString = mapper.writeValueAsString(paramMap);
		
		try {
			logger.debug("API URL : " + api_url);
			URL url = new URL(api_url);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoInput(true);			// 입력스트림 사용여부
			httpConn.setUseCaches(false);		// 캐시사용 여부
			httpConn.setRequestProperty("Authorization", (String)paramMap.get("token"));
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Content-type", "application/json");
			httpConn.setReadTimeout(10000);		// 타임아웃 설정 ms단위
			httpConn.setRequestMethod(reqMethod);	// POST or GET
			if ("GET".equals(reqMethod)||"DELETE".equals(reqMethod)){
				httpConn.setDoOutput(false);		// 출력스트림 사용여부 GET인경우는 사용하지 않음				
			} else {
				httpConn.setDoOutput(true);			// 출력스트림 사용여부 GET이 아닌 경우는 사용함.				
				os = httpConn.getOutputStream();
				os.write(jsonString.getBytes("UTF-8"));
				os.flush();
//				writer = new OutputStreamWriter(os);
//				writer.write(jsonString.getBytes("UTF-8"));
//				writer.flush();
			}

			
			StringBuilder stringBuilder = new StringBuilder();
			HttpResult = httpConn.getResponseCode();
			
			if( HttpResult == HttpURLConnection.HTTP_OK ) {
				logger.debug("HttpResult : " + HttpResult + ", HTTP OK");
				bReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				String line=null;
								
				while( (line = bReader.readLine()) != null ) {
					stringBuilder.append(line + "\n");
					logger.debug("line ===== " + line);
				}
				param = stringBuilder.toString();
				
				if (param.startsWith("[")){
					jsonArray = (JSONArray) JSONArray.fromObject(param);
					jsonObj.put("list", jsonArray);
					
				} else {
					jsonObj = (JSONObject) JSONObject.fromObject(param);
				}
				
				logger.debug("jsonObj ---------------------- ");
				logger.debug(jsonObj.toString());
				
			} else {
				logger.debug("HttpResult : " + HttpResult);
				bReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				// logger.debug("bReader : " + bReader);
				String line=null;
				
				while( (line = bReader.readLine()) != null ) {
					stringBuilder.append(line + "\n");
				}
				
				param = stringBuilder.toString();
				// logger.debug("param : " + param);
				jsonObj = (JSONObject) JSONObject.fromObject(param);
				logger.debug("jsonObj ---------------------- ");
				logger.debug(jsonObj.toString());
				return null;
			}
			
		}catch(IOException ioe) {
			return null;
		}catch(Exception e) {
			return null;
		}finally {
			if(bReader != null) bReader.close();
			if(writer != null) writer.close();
			if(os != null) os.close();
		}
		
		logger.debug("returning jsonObj @CommUtil.getFCWorldAPIData()");
		return jsonObj;
	}

	/**
	 * HttpURLConnection으로  파일 받아오기
	 * @param erp_synk_url
	 * @return JSONObject
	 */
	public static void getFCWorldAPIFile(String api_url, String reqMethod, HashMap<String,Object> paramMap,HttpServletResponse response) throws IOException, Exception {

		int HttpResult = 0;
		
		OutputStream output = null;
		InputStream is = null;
		BufferedInputStream in = null;
		String fileName = "";
		
		try {
			logger.debug("API URL : " + api_url);
			URL url = new URL(api_url);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoInput(true);			// 입력스트림 사용여부
			httpConn.setUseCaches(false);		// 캐시사용 여부
			httpConn.setRequestProperty("Authorization", (String)paramMap.get("token"));
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Content-type", "application/octet-stream");
			httpConn.setReadTimeout(10000);		// 타임아웃 설정 ms단위
			httpConn.setRequestMethod(reqMethod);	// POST or GET

			HttpResult = httpConn.getResponseCode();
			fileName = api_url.substring(api_url.lastIndexOf("/") + 1,
					api_url.length());
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition","attachment;filename="+java.net.URLEncoder.encode(fileName, "UTF-8")+";");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setDateHeader("Last-Modified", new Date().getTime());
			
			if( HttpResult == HttpURLConnection.HTTP_OK ) {
				logger.debug("HttpResult : " + HttpResult + ", HTTP OK");
				is = httpConn.getInputStream();
				int fileLength = httpConn.getContentLength();
				output = response.getOutputStream();
				int buf_size = is.available();
				in = new BufferedInputStream(is);
				System.out.println("buf_size=["+buf_size+"]");
				byte[] buffer = new byte[buf_size];

				int count = 0;
                while ((count = in.read(buffer, 0, 1024)) != -1) {
                    output.write(buffer, 0, count);
                }
                
			} else {
				logger.debug("HttpResult : " + HttpResult);

			}
			
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(is != null)is.close();
			if(output != null)output.close();
		}
		
		logger.debug("returning jsonObj @CommUtil.getFCWorldAPIData()");
	}
	
	/**
	 * 페이징 쿼리에서 페이지 시작점 구하기
	 * @param String
	 * @param String
	 * @return Integer
	 */
	public static Integer getPageOffset(String page, String pageSize) {		
		return ((Integer.parseInt(page) - 1) * Integer.parseInt(pageSize));
	}
	
	/**
	 * request 매핑
	 * @param HttpServletRequest
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> setParams(HttpServletRequest request) throws Exception {
		Enumeration params = request.getParameterNames();
		Map<String, String> map = new HashMap<String, String>();
		String key = "";
		String mapData = "";
		
		while (params.hasMoreElements()){
		    key = (String)params.nextElement();
		    mapData = request.getParameter(key) == null ? "" : request.getParameter(key).toString();
		    if(mapData.length() > 0){
		    	if("request".equals(key)){
		    		continue;
		    	}
		    	map.put(key, mapData);
	        }
		}
		
		return map;
	}

	
	/**
	 * 테스트 용
	 * @param args
	 */
	public static void main(String[] args) {
		long ret = CommUtil.getDateTimeMillisecond("20191231235959");
		logger.debug("timeMillis : " + ret + "");
		
	}
	
	/**
	 * HttpServletRequest에 들어있는 JSON 데이터 꺼내기(화면상에서 ajax로 JSON 형태의 데이터를 던질때)
	 * @param HttpServletRequest
	 * @return JSONObject
	 * @since 2020-11-11
	 * @author KIMDONGUK
	 */
	public static JSONObject readJSONStringFromRequestBody(HttpServletRequest request){
        StringBuffer json = new StringBuffer();
        String line = null;
        JSONObject jsonObj = null;

        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                json.append(line);
            }
            
            jsonObj = JSONObject.fromObject(json.toString());

        }catch(Exception e) {
        	logger.info("Error reading JSON string: " + e.toString());
        }
        
        return jsonObj;
    }
}