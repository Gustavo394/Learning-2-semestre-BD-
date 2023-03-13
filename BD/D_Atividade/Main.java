package D_Atividade;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);

        Integer escolha;

        System.out.println("Escolha uma das opções"+
        "\n1 = A" + "\n2 = B" + "\n3 = C" + "\n4 = D" + "\n5 = E");
        escolha = scan.nextInt();
        
        switch (escolha){
            case 1:
                A a = new A();
                a.resp();
            case 2:
                B b = new B();
                b.entrada();
            case 3:
                C c = new C();
                c.xis();
            case 4:
                D d = new D();
                d.pacaba_prcrbr();
            case 5:
                E e = new E();
                e.altura_comprimento();
        }
        scan.close();
    }
}