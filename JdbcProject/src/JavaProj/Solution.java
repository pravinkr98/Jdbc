package JavaProj;

import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the password.");
		String password=sc.next();
		sc.close();
		String result=Validate(password);
		System.out.println(result);
	}
	
	public static String Validate(String password) {
		String result=null;
		boolean flag=false;
		if(password.length()>=7) {
			if(password.charAt(0)>=65 && password.charAt(0)<=90) {
				for(int i=1;i<password.length();++i) {
					if((password.charAt(i)>=65 && password.charAt(i)<=90) || (password.charAt(i)>=97 && password.charAt(i)<=122) || (password.charAt(i)>=48 && password.charAt(i)<=57) && (password.charAt(i)!=35)) {
						flag=true;						
					}					
				}
				if(flag==true){
					for(int i=1;i<password.length();++i){
						if(password.charAt(i)>=48 && password.charAt(i)<=57){
							return password.substring(0, password.length()-1);
						}
						else{
							result="password must contains at least one number";
							return result;
						}
				}
					//return password.substring(0, password.length()-1);
					
				}else {
					result="Must contains characters or numbers.";
					return result;
				}					
			} else {
				result="First character must be capital letter.";
				return result;
			}
		} else {
			result="Password must contains at least 7 character.";
			return result;
		}
		//return result;
		return result;
	}//validate
}//class
