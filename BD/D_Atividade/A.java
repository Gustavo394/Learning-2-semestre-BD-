package D_Atividade;

import java.util.Scanner;

public class A {
    Scanner scan = new Scanner(System.in);

    Integer n = scan.nextInt();
    Integer bobo = scan.nextInt();
    String resp;

    public void resp() {
        resp = ("S");
        for (int i = 0; i <= n; i += 2){
            if (scan.nextInt() > bobo){
                resp = ("N");
                System.out.println(resp);
            }
        }
    }    
}