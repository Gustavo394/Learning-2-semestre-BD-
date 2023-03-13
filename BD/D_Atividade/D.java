package D_Atividade;

import java.util.Scanner;

public class D {
    Scanner scan = new Scanner(System.in);

    int[] entrada1 = new int[3];
    int[] entrada2 = new int[3];
    Integer resposta = 0;
    Integer ca;
    Integer ba;
    Integer pa;
    Integer cr;
    Integer br;
    Integer pr;

    public void pacaba_prcrbr() {
        ca = entrada1[1] = scan.nextInt();
        ba = entrada1[2] = scan.nextInt();
        pa = entrada1[3] = scan.nextInt();

        cr = entrada2[1] = scan.nextInt();
        br = entrada2[2] = scan.nextInt();
        pr = entrada2[3] = scan.nextInt();

        if (cr>ca){
            resposta += (cr-ca);
            System.out.println(resposta);
        }
        if (br>ba){
            resposta += (pr-pa);
            System.out.println(resposta);
        }
        if (pr>pa){
            resposta += (pr-pa);
            System.out.println(resposta);
        }
        
    }
    
}