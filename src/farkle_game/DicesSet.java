package farkle_game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DicesSet {

    //Crear ArrayList de dados
    private ArrayList<Dice> dices;
    private ArrayList<JLabel> dicesImages;
    private ArrayList<String> dicesPath;
    private ArrayList<JLabel> dicesAvailableSign;

    //Crear los 6 dados
    public DicesSet(){
        dices = new ArrayList<>();
        for(int i = 0; i <= 5; i++){
            Dice dice = new Dice();
            dices.add(dice);
        }

        dicesImages = new ArrayList<>();
        dicesAvailableSign = new ArrayList<>();

        dicesPath = new ArrayList<>();
        dicesPath.add("src/Resources/face_1_point.png");
        dicesPath.add("src/Resources/face_2_points.png");
        dicesPath.add("src/Resources/face_3_points.png");
        dicesPath.add("src/Resources/face_4_points.png");
        dicesPath.add("src/Resources/face_5_points.png");
        dicesPath.add("src/Resources/face_6_points.png");
    }

    //Tirar todos los dados (Si estan disponibles)
    public void playDices(){
        dices.forEach(dice -> {
            if(dice.getCanPlayDice()){
                dice.playDice();
            }
        });
    }

    //Obtener los puntos de los dados
    public int getDicePoints(int diceID){
        return dices.get(diceID).getDicePoints();
    }

    //Bloquear dados
    public void lockDice(int diceID){
        dices.get(diceID).setCanPlayDice(false);
        dicesAvailableSign.get(diceID).setVisible(false);
    }

    //Bloquar todos los dados
    public void lockAllDices(){
        dices.forEach(dice -> {
            dice.setCanPlayDice(false);
        });

        dicesAvailableSign.forEach(sign -> {
            sign.setVisible(false);
        });
    }

    //Desbloquear todos los dados
    public void unlockAllDices(){
        dices.forEach(dice -> {
            dice.setCanPlayDice(true);
        });

        dicesAvailableSign.forEach(sign -> {
            sign.setVisible(true);
        });
    }

    //Regresa tamaño de ArrayList de dados
    public int getDicesSize(){
        return dices.size();
    }

    //Regresa un dado
    public Dice getDice(int diceID){
        return dices.get(diceID);
    }

    //Regresar ArrayList de dados
    public ArrayList<Dice> getDices(){
        return dices;
    }

    //Mostrar dados en ventana
    public void showDicesLabels(){
        int y[] = {0};

        //Mostrar etiquetas de disponibilidad
        for(int i = 0; i <= 5; i++){
            JLabel diceAvailableSign = new JLabel("Jugable");
            dicesAvailableSign.add(diceAvailableSign);
        }

        y[0] = 0;
        dicesAvailableSign.forEach(sign -> {
            sign.setBounds(435 + (y[0] * 100), ((500 - sign.getPreferredSize().height) / 3) + 25, 100, sign.getPreferredSize().height + 14);
            sign.setFont(new Font("ARIAL", Font.BOLD, 18));
            sign.setVisible(true);
            FarkleGame.getWindow().add(sign);
            y[0]++;
        });
    }

    //Mostrar dados
    public void showDices(boolean showDices){
        int generalSize = 100; //Establecer dimensiones generales de las imagenes

        if(!showDices){
            dicesImages.forEach(
                    dice -> dice.setVisible(false)
            );

        }else{
            for (int i = 0; i <= 5; i++) {
                ImageIcon originalImage = new ImageIcon(dicesPath.get(dices.get(i).getPathID())); //Modificar a dados de jugador
                ImageIcon resizedImage = new ImageIcon(originalImage.getImage().getScaledInstance(generalSize, generalSize, Image.SCALE_SMOOTH));
                JLabel diceImage = new JLabel(resizedImage);
                diceImage.setBounds(410 + (i * generalSize + 10), (500 - generalSize) / 5, generalSize, generalSize);
                diceImage.setVisible(true);
                dicesImages.add(diceImage);
                FarkleGame.getWindow().add(diceImage);
            }
        }
        FarkleGame.getWindow().setVisible(true);
    }
}