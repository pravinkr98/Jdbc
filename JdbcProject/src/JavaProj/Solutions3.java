package JavaProj;

import java.util.Scanner;

public class Solutions3 {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Repetation : ");
		int t=sc.nextInt();
        for(int i=0;i<t;i++){
        	System.out.println("Enter the Value 1 : ");
            int a = sc.nextInt();
            System.out.println("Enter the Value 2 : ");
            int b = sc.nextInt();
            System.out.println("Enter the Range  : ");
            int n = sc.nextInt();
            int c=a,q=1;
            for(int j=1;j<=n;j++) {
            	for(int k=1;k<=j;k++) {
            		c=c+q*b;
            		q=q*2;
            	}
            	System.out.print(c+" ");
            	q=1;
            	c=a;
            }
            System.out.print("\n");
        }
        sc.close();
	}
}
