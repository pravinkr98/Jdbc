package JavaProj;

import java.util.Scanner;

public class Solutions2 {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Value : ");
		int x = sc.nextInt();
		for (int i = 1; i <= 10; i++) {
			System.out.println(x +" x " +i+" = "+x * i);
		}
	}
}
