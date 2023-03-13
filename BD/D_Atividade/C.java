package D_Atividade;

import java.util.Scanner;

public class C {
    Scanner scan = new Scanner(System.in);

    int[] x = new int[3];
    Integer a;
    Integer b;
    Integer c;

    public void xis() {
        a = x[1] = scan.nextInt();
        b = x[2] = scan.nextInt();
        c = x[3] = scan.nextInt();
        if ((a + b == c) || (a + c == a) || (b + c == a) || (a == b) || (a == c) || (b == c)){
            System.out.println("S");
        }
        else{
            System.out.println("N");
        }
    }
    
}