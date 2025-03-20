package farkle_game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Dice {
    //Atributos
    private int dicePoints;
    private int x;
    private int y;
    private String imagePath;
    private JLabel dice;

    //Inicializar dado
    public Dice(){
        dicePoints = 6;
    }

    //Tirar Dado
    public int playDice(){
        Random generator = new Random();
        int points = generator.nextInt(6) + 1;

        this.dicePoints = points;

        return points;
    }

    //Mostrar dado en Ventana
    public int getPathID(){
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
}