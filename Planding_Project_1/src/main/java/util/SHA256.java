package util;

import java.security.MessageDigest;//SHA256 알고리즘을 사용하기위해 import
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

//단방향 암호화(SHA256) : 복호화가 불가능 (비밀번호 찾을 때 반드시 임시비밀번호 발급)
public class SHA256 {
	
	/* 암호를 보호하는 가장 좋은 방법은 '소금을 친 해싱'을 사용하는 것이다.
	 * 소금이 고정이 아니면 로그인할 때 비밀번호의 소금도 변경되어 로그인이 안 됨
	 * (소금이 바뀌면 비밀번호도 계속 바뀜. 따라서, 소금값을 고정)
	 */
	
	private static final String salt = "맥카페";//'맥카페'라는 키워드(=소금salt)를 암호화하는 데 사용
	
	//private생성자 : 외부에서 객체 생성하지 못하도록 막음 (모든 멤버가 static)
	private SHA256() {}	
	
	//비밀번호를 암호화한 결과 '암호화된 비밀번호'를 리턴
	public static String encodeSHA256(String password){
		String result = "";
		
		try {
			byte[] saltByte = salt.getBytes(); //'맥카페'를 byte타입으로 변경 -> byte배열
			byte[] passwordByte = password.getBytes("utf-8"); //'비밀번호'를 utf-8인코딩에 맞춰 byte타입으로 변경 -> byte배열
		
			/*
			 * 기본값(0)으로 채워진 byte객체에 바이트로 변경된 소금값과 비번으로 채움 
			 */
			byte[] saltPassword = new byte[saltByte.length + passwordByte.length];//소금+비번 크기의 배열
			
			//소금값을 붙일 때 암호 앞/뒤 상관없으나, 암호 앞에 붙이는 게 일반적
			//복사할배열, 복사할시작index, 붙여넣기할배열, 붙여넣기할시작index, 얼마나 복사?
			System.arraycopy(saltByte, 0, saltPassword, 0, saltByte.length);
			System.arraycopy(passwordByte, 0, saltPassword, saltByte.length, passwordByte.length);
			
			//SHA-256 알고리즘을 사용하여 단방향암호화
			MessageDigest md = MessageDigest.getInstance("SHA-256");//싱글톤패턴이므로 알고리즘으로 객체를 얻어냄
			
			//md.update() : 입력한 값을 해싱(암호화)하는 함수. 매개값은 반드시 byte배열만 가능.
			md.update(saltPassword);
			
			byte[] saltPasswordDigest = md.digest();//md객체의 다이제스트를 얻어 saltPassword를 갱신
			
			/**
			 * StringBuffer(멀티쓰레드) StringBuilder(싱글쓰레드) 사용
			 * 문자열 연산이 많을 때 String 대신 사용하여 메모리의 효율을 높인다. 
			 */
			//String result = ""; 대신
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<saltPasswordDigest.length; i++) {
				//(saltPasswordDigest[i]&0xFF)+0x100 //256 = 16진수 100 = 0x100
				//result += Integer.toString((saltPasswordDigest[i]&0xFF)+256, 16).substring(1); //toString(int i) <- cpu에서 연산할때 정수는 int로 변환
				
				/* [&0xff?]
				 * &비트연산자 : 둘 다 1이어야 1. 둘 다 1이 아니면 0으로 환산.
				 * 컴퓨터에서 음수는 1로 시작해 앞의 빈곳이 1로 채워짐 (2의 보수)
				 * 		   양수는 0으로 시작해 앞의 빈곳이 0으로 채워짐
				 * &비트연산자? 음수 1로 채워진 것을 모두 0으로 바꾸는 작업. FF(00... 1111 1111) 
				 * 
				 * 이 연산을 하는 이유? 연산 시 CPU의 연산장치는 정수 int를 기준으로 함
				 * (예) 나머지 부분은 부호비트로 채워짐
				 *  	byte 00001010 (0으로시작->양수) => int 000000000000000000000000'00001010' (양수)
				 * 		byte 10001010 (1로시작->음수) => int 111111111111111111111111'10001010' (음수)
				 * 	위 결과처럼 음수 '10001010'만 고스란히 가지고 오기 위해 111...의 부호비트를 전부 000...으로 바꿈
				 *  그렇게 바꾸기 위해 비트연산자를 사용 &0xFF (음수값 1로 채워진 부분을 전부 0으로 바꿈)
				 *  									& 000000000000000000000000'11111111'
				 *  									------------------------------------
				 *  								 	 000000000000000000000000'10001010'
				 */
				
				/* <바이트 배열값 하나하나를 16진수로 만드는 방법>
				 * saltPasswordDigest[i]&0xFF) + 0x100
				 * => 10진수 100(0x100) 더하는 이유? 더하여 강제로 3자리의 String으로 변경하기 위해
				 * => 더하지 않았을 때는 자릿수가 다 제각각. 더하면 자릿수가 3자리로 동일해짐
				  	  0x100더함  아직더하지않음(&0xFF)
				  1 ->  101		  1
				  2 ->  102		  2
				  ...
				  10 -> 10a		  a
				  11 -> 10b		  b
				  12 -> 10c		  c
				  13 -> 10d		  d
				  14 -> 10e		  e
				  15 -> 10f		  f
				  16 -> 110		 10
				  
				  위 결과에서
				  substring()으로 제일 앞의 1을 제외시킨 나머지 2자리가 바로 16진수
				  (즉, 갱신된 암호화된 비밀번호 데이터를 16진수로 바꾸는 과정)
				  => 왜 16진수?
				   : 데이터는 2진수로 전달됨. 10진수는 2진수로 변환하는 작업이 필요.
				     16진수는 1자리가 2진수 4자리이므로, 데이터를 변환하는 작업이 필요없음
				     (16진수 말고도 64진수로 표현하기도 함) 
				 */
				
				sb.append(Integer.toString((saltPasswordDigest[i]&0xFF)+0x100, 16).substring(1));
				sb.append(Integer.toString((saltPasswordDigest[i]&0xFF)+256, 16).substring(1));

			}
			result = sb.toString();		
			
		} catch(Exception e) {//암호화시 여러 예외가 발생가능
			e.printStackTrace();
		}
		System.out.println("encodeSHA256 : "+result);
		return result;
	}
	
	
	//임시 비밀번호 생성 (비밀번호 찾기에서 '이미 암호화된 비밀번호'를 다시 복호화해서 보내줄 수 없기 때문)
	public static String getRandomPassword(int size) { //size? (size가 8이면 8자리 임시 비밀번호 생성)
		char[] charSet = {//비밀번호 안에 넣을 수 있는 글자들을 일일이 나열 (예: 8자리 영어대소문자+숫자)(특수문자의 경우, 회원가입시 사용가능한 특수문자를 넣을 것)
							'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
							'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 
							'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
						 };//특수문자 제외 (포함한다면 '!','~','_' 등등 하나하나 넣어주기)
		
		StringBuffer sb = new StringBuffer();
		
		//'강력한 난수 발생'시키기 위해 SecureRandom 사용
		SecureRandom sr = new SecureRandom();
		sr.setSeed(new Date().getTime());//오늘날짜의 시간을 가져와서 시드값으로 세팅해줌
		
		int len = charSet.length;
		int idx = 0;
		for(int i=0; i<size ; i++) {
			//[방법-1]
			//idx = (int)(Math.random() * len ) + 0;			
			
			//[방법-2] 강력한 난수 발생
			//idx = sr.nextInt(len); //charSet길이값으로 랜덤으로 다음값을 얻어옴
			idx = sr.nextInt(charSet.length); //예:idx=10 -> idx=0
			sb.append(charSet[idx]);//charSet[10]의 값은 'A' -> charSet[0] = '0'
		}
		
		return sb.toString();//바로 return
	}
	
}
