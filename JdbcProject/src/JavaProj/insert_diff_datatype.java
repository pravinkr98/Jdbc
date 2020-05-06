package JavaProj;

import java.util.Scanner;

public class insert_diff_datatype {

	public static void main(String[] args) {
		 Scanner sc = new Scanner(System.in);
		 System.out.println("Enter the loop range : ");
	        int t=sc.nextInt();

	        for(int i=0;i<t;i++)
	        {

	            try
	            {
	            	System.out.println("Enter the value : ");
	                long x=sc.nextLong();
	                System.out.println(x+" can be fitted in:");
	                if(x>=-128 && x<=127)
	                {
	                    System.out.println("* byte");
	                    System.out.println("* short");
	                    System.out.println("* int");
	                    System.out.println("* long");
	                }
	                else if(x>=-32768 && x<=32767)
	                {
	                    System.out.println("* short");
	                    System.out.println("* int");
	                    System.out.println("* long");
	                }
	                else if(x>=-2147483648 && x<=2147483647)
	                {
	                    System.out.println("* int");
	                    System.out.println("* long");
	                }
	                else if(x>=Long.MIN_VALUE && x<=Long.MAX_VALUE)
	                {                 
	                    System.out.println("* long");
	                }
	            }//try
	            catch(Exception e)
	            {
	                System.out.println(sc.next()+" can't be fitted anywhere.");
	            }
	        }//for
	        System.out.println("Loop range Completed.");
            		sc.close();
		}//main class
	} //class