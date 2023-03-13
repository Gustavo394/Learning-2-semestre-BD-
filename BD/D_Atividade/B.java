package D_Atividade;

import java.util.Scanner;

public class B {
    Scanner scan = new Scanner(System.in);
    
    int[] entrada = new int[2];
    Integer a;
    Integer b;

    public void entrada() {
        a = entrada [1] = scan.nextInt();
        b = entrada [2] = scan.nextInt();
        System.out.printf("%.2f", (a/b));
    }
}