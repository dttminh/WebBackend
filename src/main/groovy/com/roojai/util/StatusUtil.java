package com.roojai.util;

import com.roojai.saleforce.Partner;
import org.grails.web.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class
StatusUtil {

	private static final String NOT_SURE = "Not sure";
	private static final String THREE_OR_MORE = "3 or more";
	private static final String SOCIAL = "Social, pleasure and travelling";
	private static final String GO_TO_BUSINESS = "To support a business";
	private static final String WORK = "Driving to and back from work";
	private static final String ANY_DRIVER = "Any Driver";
	private static final String TYPE1 = "Type1";
	private static final String TYPE2 = "Type2";
	private static final String TYPE3 = "Type3";
	private static final String TYPE2PLUS = "Type2Plus";
	private static final String TYPE3PLUS = "Type3Plus";
	private static final String ANYDRIVER = "AnyDriver";
	private static final String ANYOVER25 = "AnyOver25";
	private static final String ANYOVER30 = "AnyOver30";
	private static final String NAMED_DRIVER = "Named Driver";
	private static final String NAMED = "Named";

	static Logger logger = LoggerFactory.getLogger("com.roojai.util.StatusUtil");

	private StatusUtil() {
	}

	public static String getSaleForceCompulsoryValue(String compulsory) {
		if (compulsory.equals("Comp")) {
			return "Yes";
		} else {
			return "No";
		}
	}

	public static String getLastAccident(String hdLastAccident) {
		String lastAccident;
		if(hdLastAccident == null){
			lastAccident = NOT_SURE;
		}else{
			switch (hdLastAccident) {
				case "0":
					lastAccident = "None";
					break;
				case "1":
					lastAccident = "1";
					break;
				case "2":
					lastAccident = "2";
					break;
				case "3":
					lastAccident = THREE_OR_MORE;
					break;
				default:
					lastAccident = NOT_SURE;
					break;
			}
		}

		return lastAccident;
	}

	public static String getDriverAccidentToRadar(String hdLastAccident) {
		String lastAccident;
		if(hdLastAccident == null){
			lastAccident = NOT_SURE;
		}else{
			switch (hdLastAccident) {
				case "0":
					lastAccident = "0";
					break;
				case "1":
					lastAccident = "1";
					break;
				case "2":
					lastAccident = "2";
					break;
				case "3":
					lastAccident = THREE_OR_MORE;
					break;
				default:
					lastAccident = NOT_SURE;
					break;
			}
		}

		return lastAccident;
	}

	public static String getCarOwnerShip(String carOwner) {
		String carOwnerShip = "";
		if(carOwner.equals("0")){
			carOwnerShip = "Private";
		}else if(carOwner.equals("1")){
			carOwnerShip = "Corporate";
		}
		return carOwnerShip;
	}

	public static double getDerivedNCB(String hdDeclareNCB, String hdLastAccident) {
		double derivedNCB = 0;

		if(hdDeclareNCB.equals("")){
			derivedNCB = 0;
		} else if (!hdDeclareNCB.equals("N")) {
			try{
				derivedNCB = Integer.parseInt(hdDeclareNCB);
			}catch (NumberFormatException e){
				JSONObject jsonException = new JSONObject();
				jsonException.put("hdDeclareNCB", hdDeclareNCB);
				jsonException.put("hdLastAccident", hdLastAccident);
				logger.error(jsonException.toString(), e);

				derivedNCB = 0;
			}
		} else if (hdDeclareNCB.equals("N")) {
			if (hdLastAccident.equals("0")) {
				derivedNCB = 30;
			} else if (hdLastAccident.equals("1")) {
				derivedNCB = 20;
			} else if (hdLastAccident.equals("2") || hdLastAccident.equals("3")) {
				derivedNCB = 0;
			}
		}

		return derivedNCB / 100;
	}

	public static String reverseIncludeCompulsory(String includeCompulsory) {
		if (includeCompulsory.equalsIgnoreCase("Yes")) {
			return "Comp";
		} else if (includeCompulsory.equalsIgnoreCase("No")) {
			return "NoComp";
		} else {
			return "";
		}
	}

	public static String driverNamedConvert(String driverNamed) {
		String named = "";
		if (driverNamed != null) {
			if (driverNamed.equalsIgnoreCase(ANY_DRIVER)) {
				named = ANYDRIVER;
			} else if (driverNamed.equalsIgnoreCase(NAMED_DRIVER)) {
				named = NAMED;
			} else if (driverNamed.equalsIgnoreCase("Driver > 25")) {
				named = ANYOVER25;
			} else if (driverNamed.equalsIgnoreCase("Driver > 30")) {
				named = ANYOVER30;
			} else if (driverNamed.equalsIgnoreCase(ANYDRIVER)) {
				named = ANY_DRIVER;
			} else if (driverNamed.equalsIgnoreCase(NAMED)) {
				named = NAMED_DRIVER;
			} else if (driverNamed.equalsIgnoreCase(ANYOVER25)) {
				named = "Driver > 25";
			} else if (driverNamed.equalsIgnoreCase(ANYOVER30)) {
				named = "Driver > 30";
			}
		} else {
			named = "";
		}
		return named;
	}

	public static String getCoverDriverPlanToRadar(String driverNamed) {
		String named = "";
		if (driverNamed != null) {
			if (driverNamed.equalsIgnoreCase(ANYDRIVER)) {
				named = ANY_DRIVER;
			} else if (driverNamed.equalsIgnoreCase(NAMED)) {
				named = NAMED_DRIVER;
			} else if (driverNamed.equalsIgnoreCase(ANYOVER25)) {
				named = "Any Driver More Than 25";
			} else if (driverNamed.equalsIgnoreCase(ANYOVER30)) {
				named = "Any Driver More Than 30";
			}
		}
		return named;
	}

	public static String getCoverDriverPlanFromRadar(String driverNamed) {
		String named = "";
		if (driverNamed != null) {
			if (driverNamed.equalsIgnoreCase(ANY_DRIVER)) {
				named = ANYDRIVER;
			} else if (driverNamed.equalsIgnoreCase(NAMED_DRIVER)) {
				named = NAMED;
			} else if (driverNamed.equalsIgnoreCase("Any Driver More Than 25")) {
				named = ANYOVER25;
			} else if (driverNamed.equalsIgnoreCase("Any Driver More Than 30")) {
				named = ANYOVER30;
			}
		}
		return named;
	}

	public static String reverseFromHomeToWork(String vehicleUsage) {
		if(vehicleUsage == null){
			return "";
		}else if (vehicleUsage.equalsIgnoreCase(SOCIAL)) {
			return "1";
		} else if (vehicleUsage.equalsIgnoreCase(GO_TO_BUSINESS) || vehicleUsage.equalsIgnoreCase(WORK)) {
			return "0";
		} else {
			return "";
		}
	}

	public static String reverseInTheCourseOfWork(String vehicleUsage) {
		if (vehicleUsage == null) {
			return "";
		} else if (vehicleUsage.equalsIgnoreCase(GO_TO_BUSINESS)) {
			return "0";
		} else if (vehicleUsage.equalsIgnoreCase(WORK) || vehicleUsage.equalsIgnoreCase(SOCIAL)) {
			return "1";
		} else {
			return "";
		}
	}

	public static String reverseDrivingExperience(String drivingExperience) {
		if (drivingExperience != null) {
			return drivingExperience.equalsIgnoreCase("More than 5") ? "6+" : drivingExperience;
		} else {
			return "";
		}
	}

	public static String reverseDriverAccidents(String driverAccidents) {
		if (driverAccidents != null) {
			if (driverAccidents.equalsIgnoreCase("None")) {
				return "0";
			} else if (driverAccidents.equalsIgnoreCase(NOT_SURE)) {
				return "4";
			} else if (driverAccidents.equalsIgnoreCase(THREE_OR_MORE)) {
				return "3";
			} else {
				return driverAccidents;
			}
		} else {
			return "";
		}
	}

	public static String reverseResidentStatus(String residentStatus) {
		return (residentStatus == null || residentStatus.equalsIgnoreCase("Thai")) ? "0" : "1";
	}

	public static String reverseCarOwnerShip(String carOwner) {
		return carOwner.equals("Private") ? "0" : "1";
	}

	public static String getCoverTypeConvert(String coverType) {
		if(coverType != null){
			HashMap<String, String> coverTypes = new HashMap<>();
			coverTypes.put(TYPE1.toLowerCase(),  "Type 1");
			coverTypes.put(TYPE2.toLowerCase(),  "Type 2");
			coverTypes.put(TYPE3.toLowerCase(),  "Type 3");
			coverTypes.put(TYPE2PLUS.toLowerCase(),  "Type 2+");
			coverTypes.put(TYPE3PLUS.toLowerCase(),  "Type 3+");
			coverTypes.put("Type 1".toLowerCase(),  TYPE1);
			coverTypes.put("1",  TYPE1);
			coverTypes.put("Type 2".toLowerCase(),  TYPE2);
			coverTypes.put("2",  TYPE2);
			coverTypes.put("Type 3".toLowerCase(),  TYPE3);
			coverTypes.put("3",  TYPE3);
			coverTypes.put("Type 2+".toLowerCase(),  TYPE2PLUS);
			coverTypes.put("2+",  TYPE2PLUS);
			coverTypes.put("Type 3+".toLowerCase(),  TYPE3PLUS);
			coverTypes.put("3+",  TYPE3PLUS);

			coverType = coverTypes.get(coverType.toLowerCase());
		}

		if(coverType == null){
			coverType = "";
		}

		return coverType;
	}

	public static String reverseDeclaredNCB(String declaredNCB) {
		if (declaredNCB != null) {
			if (declaredNCB.equalsIgnoreCase("I don't know")) {
				return "N";
			} else {
				return declaredNCB.replace("%", "");
			}
		} else {
			return "";
		}
	}

	public static String getResidentStatus(String residentStatus) {
		if (residentStatus == null) {
			residentStatus = "";
		}else{
			switch (residentStatus) {
				case "0":
					residentStatus = "Thai";
					break;
				case "1":
					residentStatus = "Non-Thai resident";
					break;
				default:
					residentStatus = "";
			}
		}
		return residentStatus;
	}

	public static String getDeclareNCB(String hdDeclareNCB) {
		return hdDeclareNCB.equalsIgnoreCase("N") ? "I don't know" : hdDeclareNCB + "%";
	}

	public static String reverseHowLongBeenInsured(String howlongbeeninsured) {
		if (howlongbeeninsured == null) {
			howlongbeeninsured = "";
		}
		String hdhowlongbeeninsured = "";
		if (howlongbeeninsured.equals("1 year")) {
			hdhowlongbeeninsured = "1";
		} else if (howlongbeeninsured.equals("2 years")) {
			hdhowlongbeeninsured = "2";
		} else if (howlongbeeninsured.equals(">2 years")) {
			hdhowlongbeeninsured = "M2";
		}
		return hdhowlongbeeninsured;
	}

	public static String convertCreditNo(String creditNo) {
		String newCreditNo = "";
		if (!creditNo.equals("")) {
			String end = creditNo.substring(creditNo.length() - 4);
			int size = 16;
			String rps = "%" + size + "s";
			newCreditNo = String.format(rps, end).replace(' ', 'X');
		}
		return newCreditNo;
	}

	public static String getDriver1DOB(String strDate) {
		String rtVal = "";
		try {
			String dob = DateTimeUtil.toEngDateInString(strDate);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdfrt = new SimpleDateFormat("dd-MMM-yyy");
			Date dobDate = sdf.parse(dob);
			rtVal = sdfrt.format(dobDate) + " 12:00:00 AM";
		} catch (Exception e) {
			rtVal = "";
		}
		return rtVal;
	}

	public static String convertJsonToHtmlFormat(String jsonText) {
		StringBuilder stringBuilder = new StringBuilder();
		int tabCount = 0;
		int countDBC = 0;
		for (int i = 0; i < jsonText.length(); i++) {
			char c = jsonText.charAt(i);
			if (c == ',') {
				if (countDBC % 2 == 0) {
					stringBuilder.append(c);
					stringBuilder.append("<br>" + getTab(tabCount, "html"));
				} else {
					stringBuilder.append(c);
				}
			} else if (c == '"') {
				stringBuilder.append(c);
				countDBC++;
			} else if (c == '{' || c == '[') {
				stringBuilder.append(c);
				tabCount++;
				stringBuilder.append("<br>" + getTab(tabCount, "html"));
			} else if (c == '}' || c == ']') {
				tabCount--;
				stringBuilder.append("<br>" + getTab(tabCount, "html"));
				stringBuilder.append(c);
			} else {
				stringBuilder.append(c);
			}
		}
		return stringBuilder.toString();
	}

	public static String convertJsonToFileformat(String jsonText) {
		StringBuilder stringBuilder = new StringBuilder();
		int tabCount = 0;
		int countDBC = 0;
		for (int i = 0; i < jsonText.length(); i++) {
			char c = jsonText.charAt(i);
			if (c == ',') {
				if (countDBC % 2 == 0) {
					stringBuilder.append(c);
					stringBuilder.append("\n" + getTab(tabCount, "file"));
				} else {
					stringBuilder.append(c);
				}
			} else if (c == '"') {
				stringBuilder.append(c);
				countDBC++;
			} else if (c == '{' || c == '[') {
				stringBuilder.append(c);
				tabCount++;
				stringBuilder.append("\n" + getTab(tabCount, "file"));
			} else if (c == '}' || c == ']') {
				tabCount--;
				stringBuilder.append("\n" + getTab(tabCount, "file"));
				stringBuilder.append(c);
			} else {
				stringBuilder.append(c);
			}
		}
		return stringBuilder.toString();
	}

	public static String getTab(int tabCount, String type) {
		StringBuilder stringBuilder = new StringBuilder();
		String tabString = "";
		if (type.equals("html")) {
			tabString = "&emsp;";
		} else {
			tabString = "\t";
		}
		for (int i = 0; i < tabCount; i++) {
			stringBuilder.append(tabString);
		}
		return stringBuilder.toString();
	}

	public static String convertXMLToHtmlFormat(String xmlText) {
		return xmlText.trim().replace("<", "&lt;").replace(">", "&gt;<br>");
	}

	public static String getPaymentStatus(String status) {
		String paymentStatus;
		if(status == null){
			paymentStatus = "";
		}else{
			switch (status) {
				case "Paid":
					paymentStatus = "00";
					break;
				case "Pending":
					paymentStatus = "99";
					break;
				case "00":
					paymentStatus = "00";
					break;
				case "99":
					paymentStatus = "99";
					break;
				default:
					paymentStatus = "";
					break;
			}
		}

		return paymentStatus;
	}

	public static String getPaymentMethod(String payment) {
		String paymentMethod;
		if(payment == null){
			paymentMethod = "";
		}else{
			switch (payment) {
				case "Credit Card":
					paymentMethod = "02";
					break;
				case "Internet banking":
					paymentMethod = "05";
					break;
				case "Bill payment":
					paymentMethod = "06";
					break;
				default:
					paymentMethod = "";
					break;
			}
		}

		return paymentMethod;
	}

	public static String getDriverAge(String age) {
		return age.equals("0") || age.equals("") ? null : age;
	}

	public static String getLead(String lead){
		String channel = "";
		if(lead != null){
			for (Partner p : Partner.values()) {
				if(lead.equalsIgnoreCase(p.toString())){
					channel = p.text();
					break;
				}
			}
		}
		return channel;
	}

	public static String getWorkshop(String workshop) {
		if (workshop.equalsIgnoreCase("0")) {
			workshop = "PanelWorkshop";
		} else if (workshop.equalsIgnoreCase("1")) {
			workshop = "AnyWorkshop";
		}
		return workshop;
	}

	public static String getDOBOver30() {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR) - 34;

		return "01/01/" + year;
	}

	public static int getAgeFromDOB(String dob) {
		int age = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date d = sdf.parse(dob);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int date = c.get(Calendar.DATE);
			LocalDate l1 = LocalDate.of(year, month, date);
			LocalDate now1 = LocalDate.now();
			Period diff1 = Period.between(l1, now1);
			age = diff1.getYears();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return age;
	}

	public static String getWorkshopType(String workshop){
		String workshopType;
		String [] workshops = {"AnyWorkshop","PanelWorkshop"};

		if(workshop == null || workshop.trim().isEmpty()){
			workshopType = "";
		}else if(Arrays.asList(workshops).contains(workshop)){
			workshopType = workshop.replaceAll("Workshop","") + " Workshop";
		}else{
			workshopType = "Panel Workshop";
		}

		return workshopType;
	}
}
