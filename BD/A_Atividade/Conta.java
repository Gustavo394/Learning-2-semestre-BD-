package A_Atividade;

public class Conta {

    String titular;
    Integer numero;
    String agencia;
    Double saldo = 0.0;
    String data_abertura;
    
    public void sacar(double saque) {
        if (saldo < saque){
            System.out.println("Saldo insuficiente");
            System.out.println("Saldo atual: " + saldo);
        }
        else{
            saldo -= saque;
            System.out.println("Valor sacado: " + saque + "\nSaldo atual da conta " + saldo);
        }
    }

    public void depositar(double deposito){
        saldo += deposito;
        System.out.println("Valor depositado: " + deposito + "\nSaldo atual da conta " + saldo);
    }

    public void rendimento(){
        Double rendimento = saldo * 0.1;
        System.out.println("Rendimento mensal: " + rendimento);
    }
}