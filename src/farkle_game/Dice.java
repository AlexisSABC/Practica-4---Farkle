package farkle_game;

import java.util.Random;

public class Dice {
    //Atributos
    private int dicePoints;
    private int x;
    private int y;

    //Tirar Dado
    public int playDice(){
        Random generator = new Random();
        int points = generator.nextInt(6) + 1;

        showDice(points);

        return points;
    }

    //Mostrar dado en Ventana
    private void showDice(int points){
        switch(points){
            case 1:

                break;

            case 2:
                break;

            case 3:
                break;

            case 4:
                break;

            case 5:
                break;

            case 6:
                break;

            default:
                //Hacer nada
                break;
        }
    }

    //Borrar dado de ventana
    public void eraseDice(){

    }

    //Getter de puntos de dado
    public int getDicePoints(){
        return dicePoints;
    }

    //Getter de pocision en x
    public int getX(){
        return x;
    }

    //Getter de pocision en y
    public int getY(){
        return y;
    }

    //Setter de pocision en x
    public void setX(int x){
        this.x = x;
    }

    //Setter de pocision en x
    public void setY(int y){
        this.y = y;
    }
}
