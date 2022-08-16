package com.roojai.util;

import org.grails.web.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.PatternSyntaxException;

public class DateTimeUtil {

	static Logger logger = LoggerFactory.getLogger("com.roojai.util.DateTimeUtil");

	private static final String ENG_DATE_FORMAT = "dd/MM/yyyy";

	private DateTimeUtil() {
	}

	public static Date addDay(Date d, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, day);
		return c.getTime();
	}

	public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date d) {
		XMLGregorianCalendar date2 = null;
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(d);
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			logger.error(e.getMessage(), e);
		}
		return date2;
	}

	public static String toEngDateInString(String inputDate) {
		String engDate = "";
		try {
			String[] date = inputDate.replaceAll("[^0-9/]", "").substring(0, 10).split("/");
			ThaiBuddhistDate thaiBuddhistDate = ThaiBuddhistDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
			LocalDate localDate = LocalDate.from(thaiBuddhistDate);
			engDate = localDate.format(DateTimeFormatter.ofPattern(ENG_DATE_FORMAT));
		} catch (PatternSyntaxException | IndexOutOfBoundsException | NumberFormatException | DateTimeException e) {
			JSONObject jsonException = new JSONObject();
			jsonException.put("inputDate", inputDate);
			logger.error(jsonException.toString(), e);
		}
		return engDate;
	}

	public static String toThaiDateInString(String dateInString) {
		String thaiDate = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(ENG_DATE_FORMAT);
			Date date = sdf.parse(dateInString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			DateFormat dfdd = new SimpleDateFormat("dd");
			DateFormat dfmm = new SimpleDateFormat("MM");
			thaiDate = dfdd.format(date) + "/" + dfmm.format(date) + "/" + (cal.get(Calendar.YEAR) + 543);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return thaiDate;
	}

	public static String converseThaiDateToISODate(String strdate){
		String isoDate = "";
		try{
			if(strdate != null && !strdate.equals("")){
				String[] date = strdate.replaceAll("[^0-9/]", "").substring(0, 10).split("/");
				ThaiBuddhistDate tbd = ThaiBuddhistDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
				isoDate = tbd.format(DateTimeFormatter.ISO_LOCAL_DATE);
			}
		}catch(PatternSyntaxException | NumberFormatException | DateTimeException | ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e){
			isoDate = "";
		}
		return isoDate;
	}

	public static String converseISODateToThaiDate(String strdate){
		String thaiDate = "";
		try{
			if(strdate != null){
				String[] date = strdate.replaceAll("[^0-9-]", "").substring(0, 10).split("-");
				LocalDate isoDate = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
				ThaiBuddhistDate tbd = ThaiBuddhistDate.from(isoDate);
				thaiDate = tbd.format(DateTimeFormatter.ofPattern(ENG_DATE_FORMAT));
			}
		}catch(PatternSyntaxException | NumberFormatException | DateTimeException | ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e){
			thaiDate = "";
		}
		return thaiDate;
	}

	public static long diffTodayDate(String diffDateStr, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date diffDate = null;
		try {
			diffDate = sdf.parse(diffDateStr);
		} catch (ParseException ex) {
			logger.error(ex.getMessage(), ex);
		}
		Date todayDate = new Date();
		long diff = diffDate.getTime() - todayDate.getTime();

		return diff / (24 * 60 * 60 * 1000);
	}
}
