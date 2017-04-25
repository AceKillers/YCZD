package zz.zept.yczd.utils;


import android.annotation.SuppressLint;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



/**
 * 处理String 与Date类型的转换
 * 
 * @author
 * 
 */
public class TimeUtil {

	/**
	 * 删除时间字符串中的毫秒
	 * 
	 * @param time yyyy-MM-dd HH:mm:ss.mmm
	 * @return
	 */
	public static String delMsel(String time) {
		if (time == null || "".equals(time)) {
			return time;
		}
		int dotPosition = time.indexOf('.');
		if (dotPosition != -1) {
			return time.substring(0, dotPosition);
		}
		return time;
	}

	/**
	 * 转换时间为字符串
	 * 
	 * @param time
	 * @param pattern yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2StringDft(Date time) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);  // new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return df.format(time);
	}


	/**
	 * 转换时间为字符串
	 * 
	 * @param time
	 * @param pattern yyyy-MM-dd
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String date2StringYMD(Date time) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(time);
	}
	
 
	
	/**
	 * 修改日期时间，加上一个指定的值，如：加指定的分，小时，天等
	 * 
	 * @param cal
	 * @param calFiled
	 * @param amount
	 * @return
	 */
	public static Date AddHour(Date time, int hours) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.HOUR, hours);
		// String result = sdf.format(cal.getTime());
		return cal.getTime();
	}

	
	/**
	 * 转换时间为字符串
	 * 
	 * @param time
	 * @param pattern yyyyMMddHHmmss
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String date2StringSimple(Date time) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(time);
	}

	
	/**
	 * 转换时间为字符串
	 * 
	 * @param time
	 * @param pattern yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String date2String(Date time,String pattern) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(time);
	}

	/**
	 * 转换时间为字符串
	 * 
	 * @param time
	 * @param pattern yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date string2Date(String timeString,String pattern) {
		if (timeString == null || "".equals(timeString.trim())) {
			return null;
		}
		int timeLength = timeString.trim().length();
		if (timeLength > 0 && timeLength <= 10) {
			timeString += " 00:00:00"; // 只有日期字段

		} else if (timeLength <= 16 && timeLength > 10) {
			timeString += ":00";// 没有秒
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = df.parse(timeString);
		} catch (ParseException e) {
			//LogHandler.errorHandle(StringDateUtil.class, e, "字符串转换成日期发生异常！");
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将String型的日期转换成java.sql.Date 类型
	 * 
	 * @param timeString
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Date toDate2(String timeString,String dateTimeFormatter) {
		
		if (timeString == null || "".equals(timeString.trim())) {
			return null;
		}
		
		int timeLength = timeString.trim().length();
		if (timeLength > 0 && timeLength <= 10) {
			timeString += " 00:00:00"; // 只有日期字段

		} else if (timeLength <= 16 && timeLength > 10) {
			timeString += ":00";// 没有秒
		}
		SimpleDateFormat df = new SimpleDateFormat( dateTimeFormatter );
		java.sql.Date date;
		try {
			date = new java.sql.Date(df.parse(timeString).getTime());
		} catch (ParseException e) {
			//LogHandler.errorHandle(StringDateUtil.class, e, "字符串转化成日期时发生异常！");
			return null;
		}
		return date;
	}
	
	/**
	 * 将String型的日期转换成java.sql.Date 类型
	 * 
	 * @param timeString
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Date toDate(String timeString) {
		if (timeString == null || "".equals(timeString.trim())) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	
		
		int timeLength = timeString.trim().length();
		if (timeLength > 0 && timeLength <= 10) {
			timeString += " 00:00:00"; // 只有日期字段

		} else if (timeLength <= 16 && timeLength > 10) {
			timeString += ":00";// 没有秒
		}
		else if( timeLength == 21  ){  // 有毫秒  0
			
			timeString =  timeString.substring(0, 19);//
			 
		}
		
		 
		java.sql.Date date;
		try {
			date = new java.sql.Date(df.parse(timeString).getTime());
		} catch (ParseException e) {
			//LogHandler.errorHandle(StringDateUtil.class, e, "字符串转化成日期时发生异常！");
			e.printStackTrace();
			return null;
		}
		return date;
	}

	/**
	 * 如果传入时间参数 不为null，返回Timestamp类型的时间， 如果 为null ，返回""，不能返回null ，否则 数据插入的时候 会抛
	 * 无效的列类型异常。
	 * 
	 * @param timeString
	 * @return
	 */
	public static Object toTimestamp(String timeString) {
		if (timeString == null || "".equals(timeString.trim())) {
			return "";
		}
		timeString = timeString.trim();
		int timeLength = timeString.trim().length();
		if (timeLength > 0 && timeLength <= 10) {
			timeString += " 00:00:00"; // 只有日期字段

		} else if (timeLength <= 16 && timeLength > 10) {  
			timeString += ":00";// 没有秒
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts;
		try {
			ts = new Timestamp(df.parse(timeString).getTime());
		} catch (ParseException e) {
			//有的系统是以20090610083545  这种形式，需要排除这种情况
			df = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				ts = new Timestamp(df.parse(timeString).getTime());
			} catch (ParseException e1) {
				//LogHandler.errorHandle(StringDateUtil.class, e, "字符串转化成日期时发生异常！");
				return ""; // 
			}
			
		}
		return ts;
	}

	/**
	 * 计算时间之差 time1-time2
	 * 
	 * @param time1
	 * @param time2
	 * @return 分钟
	 */
	public static long sub(String time1, String time2) {
		if (time2.length() < 17) {
			time2 += " 00:00:00";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long mins = 0;
		try {
			Date gatherDate = sdf.parse(time1);
			Date lastGatherDate = sdf.parse(time2);
			long interval = gatherDate.getTime() - lastGatherDate.getTime();
			long perMin = 60 * 1000;

			mins = (interval % perMin == 0 ? interval / perMin : interval
					/ perMin + 1);
		} catch (ParseException e) {
			//LogHandler.errorHandle(StringDateUtil.class, e, "字符串转化成日期时发生异常！");
			return 0;
		}
		return mins;
	}

	
	/**
	 *  返回两个时间只差的分钟数
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static  int getMinuteDiffer(Date start  , Date  end) throws Exception {
		//java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-M-d HH:mm:ss");
		//java.util.Date start = sdf.parse(date1);
		//java.util.Date end = sdf.parse(date2);
		long cha = end.getTime() - start.getTime();
		double result = cha *1.0/1000/60;   // ms->s, the next is hours cha * 1.0 / (1000 * 60 * 60);
		return (int)result;
	}
	
	
	
	/**
	 *  返回两个时间只差的秒数
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static  double getSecondsDiffer(String date1, String date2) throws Exception {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-M-d HH:mm:ss");
		java.util.Date start = sdf.parse(date1);
		java.util.Date end = sdf.parse(date2);
		long cha = end.getTime() - start.getTime();
		double result = cha *1.0/1000;   // ms->s, the next is hours cha * 1.0 / (1000 * 60 * 60);
		return result;
	}
	
	public static String formatReal(short dotN, double d ){
		int i;
		String sformat = "0.";
		for(i=0;i<dotN;i++){
			sformat +="0";
		}
 		NumberFormat  nf = new DecimalFormat(sformat);
 		return nf.format(  d );
 	}
	
	
	/**
	 * 返回两个时间只差的秒数
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static  double getSecondsDiffer(Date date1, Date date2) throws Exception {
		 double rel =date2.getTime()-date1.getTime(); 
		 return rel/(1000); 
	}

	
	
	/**
	 * 返回两个时间只差的小时
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static  double getHourDiffer(Date date1, Date date2) throws Exception {
		 double rel =  getSecondsDiffer(date1,date2);
 		 return rel/(60 * 60); 
 		 
	}
	
	/**
	 * 返回两个时间只差的小时
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static double getHourDiffer(String date1, String date2) throws Exception {
		 double rel =  getSecondsDiffer(date1, date2);
 		 return rel/(60 * 60); 
 		 
	}
	
	
	
	/**
	 * 修改日期时间，加上一个指定的值，如：加指定的分，小时，天等
	 * 
	 * @param cal
	 * @param calFiled
	 * @param amount
	 * @return
	 */
	public static Date modifyTime(Date time, int calFiled, int amount) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(calFiled, amount);
		// String result = sdf.format(cal.getTime());
		return cal.getTime();
	}

	/**
	 * 修改日期时间，加上一个指定的值，如：加指定的分，小时，天等
	 * 
	 * @param cal
	 * @param calFiled
	 * @param amount
	 * @return
	 */
	public static String modifyTime(String time, int calFiled, int amount) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal = Calendar.getInstance();
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			//LogHandler.errorHandle(StringDateUtil.class, e, "字符串转化成日期时发生异常！");
			e.printStackTrace();
			return "";
		}

		cal.setTime(date);
		cal.add(calFiled, amount);

		String result = sdf.format(cal.getTime());
		return result;
	}
	//天数+1，
	public static Date addOneDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();
	}
	
	
	public static void main(String arg[]){
		
//		Date dt = new Date();
//		 
//		
//		for(long j=0 ; j<222220000;j ++)
//			for( int k=0 ; k<10;k ++)
//			;
//		
//		Date dtest = new Date();
//		
//		String str1 =StringDateUtil.date2StringDft( dt ) ;
//		String str2 = StringDateUtil.date2StringDft( dtest );
//		double dettm =0;
//		try {
//			 dettm = StringDateUtil.getSecondsDiffer(str1 , str2 );
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2015, 7, 1, 0, 0, 0);  // 8,1
		Date dt = cal1.getTime();
		
		System.out.println(  "dt1 =" +  TimeUtil.date2StringDft( dt ));
		
		
		
	}
	
	//得到随机字符串
	public static String createRandomString(int length) {
        String str = "0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(str.charAt((int) (Math.random() * 36)));
        }
        return builder.toString().toUpperCase();
    }
	
	

		/**
	 * 返回两个时间之差的小时数
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static  int getHourDifferAbs(Date date1, Date date2)  {
		double rel=0;
		try {
			rel = Math.abs( getSecondsDiffer(date1,date2) );
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
 		 return (int)(rel/(60 * 60)); 
 		 
	}
	
	
	
}