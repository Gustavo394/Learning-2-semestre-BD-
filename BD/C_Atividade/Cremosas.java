package C_Atividade;

import java.util.Scanner;

public class Cremosas {
    public static void main(String[] args) {
        Integer n, m, k;

        Scanner scan = new Scanner(System.in);

        do{
            System.out.println("Informe a quantidade de cremosas avistadas no sábado: ");
            n = scan.nextInt();
        } while (n <= 0 || n >= 100);
        do{
            System.out.println("Informe a quantidade de cremosas avistadas no domingo: ");
            m = scan.nextInt();
        } while (m <= 0 || m >= 100);
        k = n + m;
        System.out.println("Jãojão avistou: " + k + " cremosas!");
        scan.close();
    }
}