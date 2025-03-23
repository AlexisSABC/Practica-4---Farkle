package farkle_game;

import java.util.Random;

public class Dice {
    //Atributos
    private int dicePoints; //Guarda los puntos del dado
    private boolean canPlayDice; //Determina si el dado se puede o no jugar si el dado pertenece a un dado con puntuacion

    //Inicializar dado
    public Dice(){
        dicePoints = 6;
        canPlayDice = true;
    }

    public void setDicePoints(int dicePoints) {
        this.dicePoints = dicePoints;
    }

    //Tirar Dado
    public void playDice(){
        Random generator = new Random();
        this.dicePoints = generator.nextInt(6) + 1;
    }

    //Mostrar dado en Ventana
    public int getImagePathID(){
        int imagePathID = 0;
        switch(dicePoints){
            case 1:
                imagePathID = 0;
                break;

            case 2:
                imagePathID = 1;
                break;

            case 3:
                imagePathID = 2;
                break;

            case 4:
                imagePathID = 3;
                break;

            case 5:
                imagePathID = 4;
                break;

            case 6:
                imagePathID = 5;
                break;

            default:
                //Hacer nada
                break;
        }
        return imagePathID;
    }

    //Getter de puntos de dado
    public int getDicePoints(){
        return dicePoints;
    }

    //Getter para canPlayDice
    public boolean getCanPlayDice(){
        return canPlayDice;
    }

    //Setter para canPlayDice
    public void setCanPlayDice(boolean canPlayDice){
        this.canPlayDice = canPlayDice;
    }
}