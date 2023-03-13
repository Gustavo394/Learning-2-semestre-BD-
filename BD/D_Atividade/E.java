package D_Atividade;

import java.util.Scanner;

public class E {
    Scanner scan = new Scanner(System.in);

    int[] entrada = new int[2];
    Integer altura;
    Integer comprimento;
    int[] xs = new int[1];
    Integer quantidade = 0;
    Integer anterior = 0;

    public void altura_comprimento(){
        entrada[1] = scan.nextInt();
        entrada[2] = scan.nextInt();
        while (entrada[1] != 0){
            altura = entrada[1];
            comprimento = entrada[2];
            xs[1] = scan.nextInt();
            quantidade = altura - xs[1];
            anterior = xs[1];

            for(int x = 0; x <= xs.length; x++){
                if (x < anterior){
                    quantidade += anterior - x;
                    break;
                }
                anterior = x;
            }
            System.out.println(quantidade);
            entrada[1] = scan.nextInt();
            entrada[2] = scan.nextInt();
        }
    }
}