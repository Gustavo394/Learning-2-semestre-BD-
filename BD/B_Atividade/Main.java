package B_Atividade;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Integer angulos;
        Double centimetros;
        Double altura;
        Integer opcao;
        Boolean continuar = true;
        
        Scanner scan = new Scanner(System.in);

        Poligono poligono = new Poligono();

        while (continuar == true){
            System.out.println("Escolha o que deseja fazer\n0 - Sair"+
            "\n1 - Saber o poligono regular"+
            "\n2 - Calcular perimetro de um poligono regular"+
            "\n3 - Calcular area de um poligono regular");
            opcao = scan.nextInt();

            if (opcao == 0){
                continuar = false;
            }
            else if (opcao == 1){
                System.out.println("Informe quantos angulos tem o poligono");
                angulos = scan.nextInt();
                poligono.tipo(angulos);
            }
            else if (opcao == 2){
                System.out.println("Informe quantos angulos tem o poligono");
                angulos = scan.nextInt();
                System.out.println("Informe quantos centimetros tem um lado");
                centimetros = scan.nextDouble();
                poligono.perimetro(angulos, centimetros);
            }
            else if (opcao == 3){
                System.out.println("Informe quantos angulos tem o poligono");
                angulos = scan.nextInt();
                if (angulos == 3){
                    System.out.println("Informe quantos centimetros tem a base do poligono");
                    centimetros = scan.nextDouble();
                    System.out.println("Informe a altura do poligono");
                    altura = scan.nextDouble();
                    poligono.area(angulos, centimetros, altura);
                }
                else if (angulos == 4) {
                    System.out.println("Informe quantos centimetros tem cada lado do poligono");
                    centimetros = scan.nextDouble();
                    poligono.area(angulos, centimetros, null);
                }
                else if (angulos >= 5){
                    System.out.println("Informe quantos centimetros tem a base do poligono");
                    centimetros = scan.nextDouble();
                    poligono.area(angulos, centimetros, null);
                } 
            }
            else{
                System.out.println("Opção inválida!");
                opcao = null;
            }
        }        
        scan.close();
    }
}