package B_Atividade;

import java.util.Scanner;
import java.lang.Math;

public class Poligono {
    String tipo;
    Double perimetro = 0.0;
    Double area;

    Scanner scan = new Scanner(System.in);

    public void tipo(Integer angulos){
        if (angulos == 3){
            tipo = "triangulo";
        }
        else if (angulos == 4){
            tipo = "quadrado";            
        }
        else if (angulos == 5){
            tipo = "pentagono";            
        }
        else if (angulos == 6){
            tipo = "hexagono";            
        }
        else if (angulos == 7){
            tipo = "heptagono";            
        }
        else if (angulos == 8){
            tipo = "octogono";            
        }
        else if (angulos == 9){
            tipo = "eneagono";            
        }
        else if (angulos == 10){
            tipo = "decagono";            
        }
        else{
            tipo = "não identificado";
        }
        System.out.println("O polígono informado é um: " + tipo);
    }
    public void perimetro(Integer angulos, Double centimetros){
        perimetro = centimetros * centimetros;
        ///for (int i = 1; i <= angulos; i++){
        ///    System.out.println("Informe quantos centimetros tem o angulo " + i + ": ");
        ///    perimetro += scan.nextDouble();
        ///}
        System.out.println("O perimetro do polígono é: " + perimetro);
    }
    public void area(Integer angulos, Double centimetros, Double altura){

        if (angulos == 3){
            area = (centimetros * altura) / 2;
            System.out.println("A área desse polígono é: " + area);
        }
        else if (angulos == 4){
            area = centimetros * centimetros;
            System.out.println("A área desse polígono é: " + area);
        }
        else if (angulos >= 5){
            area = (((centimetros * centimetros) * angulos) / (4*Math.tan(Math.PI/angulos)));
            System.out.println("A área desse polígono é: " + area);
        }
    }
}