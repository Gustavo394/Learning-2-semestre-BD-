package A_Atividade;

import java.util.Scanner;

public class Nubank{
    
    public static void main (String[] args) {

        Double saque = null;
        Double deposito = null;
        int opcao;
        Boolean continuar = true;

        Scanner scan = new Scanner(System.in);

        Conta conta = new Conta();

        while (continuar == true){
            System.out.println("Bem vindo\nSelecione a opção desejada\n0 - Sair\n1 - Sacar\n2 - Depositar\n3 - Calcular rendimento");
            opcao = scan.nextInt();
            if (opcao == 0){
                continuar = false;
            }
            else if (opcao == 1){
                System.out.println("Informe o valor a ser sacado: ");
                saque = scan.nextDouble();
                conta.sacar(saque);
            }
            else if (opcao == 2){
                System.out.println("Informe o valor a ser depositado: ");
                deposito = scan.nextDouble();
                conta.depositar(deposito);
            }
            else if (opcao == 3){
                conta.rendimento();
            }
            else {
                System.out.println("Opção inválida!");
            }            
        }
        scan.close();
    }
}