package com.kedu.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityUtil {
	
    private static Pattern pattern1 = Pattern.compile("\'|\"|<script>|</script>|<object>|</object>", Pattern.CASE_INSENSITIVE);
    
    /**
     * SQL Injection 을 막기위해 알파벳과 숫자를 제외한 나머지 문자들과([^\\p{Alnum}])과
     * SQL 예약어들을 ""(Empty String)로 치환하는 역할을 수행
     * @param str
     * @param length
     * @return
     */
    public static String makeSecureString(String str){
    	
    	Pattern unsecuredCharPattern;
    	String UNSECURED_CHAR_REGULAR_EXPRESSION = "[^\\p{Alnum}]|select|delete|update|insert|create|alter|drop";
    	//[^\\p{Alnum}] => 알파벳과 숫자를 제외한 나머지 문자들을 의미
    	
    	unsecuredCharPattern = Pattern.compile(UNSECURED_CHAR_REGULAR_EXPRESSION, Pattern.CASE_INSENSITIVE);
    	
//    	String secureStr = str.substring(0, length);
    	Matcher matcher = unsecuredCharPattern.matcher(str);
    	return matcher.replaceAll("");
    }
    
	/**
	 * XSS(Cross Site Scripting)공격을 막기위해 JavaScript 및  html과 관련된 특정 문자를 escape한다.
	 * 예) "&"=>"&amp;", "<"=>"&lt;", ">"=>"&gt;", """=>"&quot;", "\'"=>"&#039;", "&"=>"&amp;" 
	 * @param str String 
	 */
	public static String escape(String str) {
		
		if ( str == null ){
			return null;
		}

		StringBuffer escapedStr = new StringBuffer();
		char[] ch = str.toCharArray();
		int charSize = ch.length;
		for ( int i=0; i < charSize; i++) {
			if ( ch[i] == '&' )
				escapedStr.append("&amp;");
			else if ( ch[i] == '<' )
				escapedStr.append("&lt;");
			else if ( ch[i] == '>' )
				escapedStr.append("&gt;");
			else if ( ch[i] == '"' )
				escapedStr.append("&quot;");
			else if ( ch[i] == '\'')
				escapedStr.append("&#039;");
			else
				escapedStr.append(ch[i]);
		}
		
		return escapedStr.toString();
	}
	
	/**
	 * escape와 반대의 기능을 한다.
	 * 예) "&"<="&amp;", "<"<="&lt;", ">"<="&gt;", """<="&quot;", "\'"<="&#039;", "&"<="&amp;" 
	 * @param str String 
	 */
	public static String unEscape(String str) {
		
		if ( str == null ){
			return null;
		}
		
		str = str.replaceAll("&amp;",  "&");
		str = str.replaceAll("&lt;",   "<");
		str = str.replaceAll("&gt;",   ">");
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&#039;", "'");
		
		return str;
	}
	
	/**
	 * Quotation 문자를 escape한다.
	 * 예) """ => "&quot;", "'" => "&#039;" 
	 * @param str String 
	 */
	public static String escapeQuot(String str) {
		
		if ( str == null ){
			return null;
		}

		StringBuffer escapedStr = new StringBuffer();
		char[] ch = str.toCharArray();
		int charSize = ch.length;
		for ( int i=0; i < charSize; i++) {
			if ( ch[i] == '"' )
				escapedStr.append("&quot;");
			else if ( ch[i] == '\'')
				escapedStr.append("&#039;");
			else
				escapedStr.append(ch[i]);
		}
		
		return escapedStr.toString();
	}
	
	/**
	 * escapeQuot와 반대의 기능을 한다.
	 * 예) """ <= "&quot;", "'" <= "&#039;" 
	 * @param str String 
	 */
	public static String unEscapeQuot(String str) {
		
		if ( str == null ){
			return null;
		}
		
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&#039;", "'");
		
		return str;
	}
	
	/**
	 * 파일 다운로드 요청 파라미터에서 상위 경로를 참조하는지 여부를 확인한다.
	 * @param fileStr String
	 */
	public static String checkFileParam(String fileStr) throws Exception {
		
		if (fileStr == null) {
			System.out.println("SecurityUtil : 파일경로 값이 없습니다.");
			throw new Exception("SecurityUtil : 파일경로 값이 없습니다.");
		}
		
		if ( (fileStr.indexOf("../") != -1) || (fileStr.indexOf(".\\./") != -1) || (fileStr.indexOf("..\\") != -1) ) {
			System.out.println("SecurityUtil : 파일경로에 사용 불가능한 문자가 있습니다. : " + fileStr);
			throw new Exception("SecurityUtil : 파일경로에 사용 불가능한 문자가 있습니다.");
		}
		
		return fileStr;
	}
	
	/**
	 * 보안에 문제를 발생시키는 문자열을 대체시킨다.
	 * 
	 * <script></script> => 제거
	 * <object></object> => 제거
	 * ' => ''
	 * " => ""
	 * 
	 * @param str String 대체 대상 String
	 * @return String 변횐된 String
	 */
	public static String replaceForSecurity(String str)
	{
		if ( str == null ){
			return null;
		}
		
        Matcher m = pattern1.matcher(str);
        StringBuffer sb = new StringBuffer();
        String replaceStr = "";
        while (m.find()) {

        	if(m.group().toLowerCase().equals("\'")) replaceStr = "\'\'";
        	//else if(m.group().toLowerCase().equals("\"")) replaceStr = "\"\"";
        	else replaceStr = "";
        	System.out.println("Invalid String [" + m.group() + "] was replaced " + "["+ replaceStr +"]");
            m.appendReplacement(sb, replaceStr);
        }
        
        m.appendTail(sb);

		return sb.toString();
	}
}
