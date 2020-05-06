package JavaProj;

import java.util.Scanner;

public class Solutions {

	public static void main(String[] args) {
		int j=0,k=0;
		  Scanner sc=new Scanner(System.in);
          System.out.println("================================");
          for(int i=0;i<3;i++){
        	  System.out.println("Enter the string : ");
              String s1=sc.next();
              System.out.println("Enter the Value : ");
              int x=sc.nextInt();            	  
              j=s1.length();
              if(j<=10&&x<1000) {
            	  System.out.print(s1);
              for(k=j;k<15;k++) {
            	  System.out.print(" ");
              }
              if(x<10)
            	  System.out.println("00"+x);
              else if(x>=10&&x<100)
            	  System.out.println("0"+x);
              else
            	  System.out.println(x);
              }
              else {
            	  System.out.println(" you entered wrong details.");
            	  break;
              }
          }
          System.out.println("================================");


	}

}
