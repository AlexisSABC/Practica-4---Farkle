package farkle_game;

import java.util.ArrayList;

public class Player {
    //Atributos
    private int playerPoints;
    private ArrayList<Dice> diceHand;

    //Generar mano de dados
    public Player(){
        for(int i = 1; i <= 6; i++){
            Dice dice = new Dice();
            diceHand.add(dice);
        }
    }

    //Jugar turno
    public void playTurn(){

    }

    //Analizar resultados de tiro
    public void analyzeHand(){

    }
}