package farkle_game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitWindow {
    //Declarar objetos y atributos
    private JFrame playerWindow;
    private JButton save;

    private int playersAmount;
    private int objectivePoints;

    //Invicar ventana
    public InitWindow(){
        playersWindow();
    }

    //Ventana para pedir numero de jugadores
    private void playersWindow(){
        int savePlayersAmount[] = {0};
        int saveSelectedPoints[] = {0};

        //Definir ventana
        playerWindow = new JFrame("Practica 4 - N. de jugadores");
        playerWindow.setSize(500, 350);
        playerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerWindow.setLayout(null);

        //Preparar espacio para pocisionar objetos
        Container space = new Container();
        space = playerWindow.getContentPane();
        space.setBackground(Color.WHITE);

        //Definir etiqueta de instrucciones de jugadores
        JLabel playerInstructions = new JLabel("Selecciona numero de jugadores:");
        playerInstructions.setFont(new Font ("ARIAL", Font.BOLD, 22));
        playerInstructions.setBounds((500 - playerInstructions.getPreferredSize().width) / 2, (240 - playerInstructions.getPreferredSize().height) / 6, playerInstructions.getPreferredSize().width, playerInstructions.getPreferredSize().height);
        playerInstructions.setVisible(true);

        //Definit lista de jugadores
        JComboBox playersList = new JComboBox<>();
        playersList.setFont(new Font ("ARIAL", Font.BOLD, 22));
        playersList.setBackground(Color.WHITE);
        playersList.setBounds((500 - 60) / 2, (240 - playersList.getPreferredSize().height) / 3, 60, playersList.getPreferredSize().height);
        playersList.setBorder(new LineBorder(Color.BLACK, 3));
        playersList.setVisible(true);

        for(int i = 2; i <= 10; i++){
            playersList.addItem(i);
        }

        //Definir etiqueta de instrucciones de puntos
        JLabel pointsInstructions = new JLabel("Selecciona puntaje a alcanzar:");
        pointsInstructions.setFont(new Font ("ARIAL", Font.BOLD, 22));
        pointsInstructions.setBounds((500 - pointsInstructions.getPreferredSize().width) / 2, ((240 - pointsInstructions.getPreferredSize().height) / 2) + 30, pointsInstructions.getPreferredSize().width, pointsInstructions.getPreferredSize().height);
        pointsInstructions.setVisible(true);

        //Definit lista de puntajes
        JComboBox pointsList = new JComboBox<>();
        pointsList.setFont(new Font ("ARIAL", Font.BOLD, 22));
        pointsList.setBackground(Color.WHITE);
        pointsList.setBounds((500 - 100) / 2, ((240 - pointsList.getPreferredSize().height) / 2) + 70, 100, pointsList.getPreferredSize().height);
        pointsList.setBorder(new LineBorder(Color.BLACK, 3));
        pointsList.setVisible(true);

        for(int i = 1; i <= 10; i++){
            pointsList.addItem(i * 1000);
        }

        //Boton de guardar
        save = new JButton("!!Jugar!!");
        save.setBackground(Color.WHITE);
        save.setFont(new Font ("ARIAL", Font.BOLD, 20));
        save.setBounds((500 - save.getPreferredSize().width) / 2, ((240 - save.getPreferredSize().height) / 2) + 130, save.getPreferredSize().width, save.getPreferredSize().height);
        save.setBorder(new LineBorder(Color.BLACK, 3));
        save.setVisible(true);

        //Tomar valor de la lista
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePlayersAmount[0] = (Integer) playersList.getSelectedItem();
                saveSelectedPoints[0] = (Integer) pointsList.getSelectedItem();
                setPlayersPoints(savePlayersAmount[0], saveSelectedPoints[0]);
            }
        });

        //Agregar elementos a la ventana
        playerWindow.add(playerInstructions);
        playerWindow.add(playersList);
        playerWindow.add(pointsInstructions);
        playerWindow.add(pointsList);
        playerWindow.add(save);

        //Mostrar ventana
        playerWindow.setResizable(false);
        playerWindow.setVisible(true);
    }

    //Establecer numero de jugadores
    private void setPlayersPoints(int playersAmount, int objectivePoints){
        //Establecer numero de jugadores
        this.playersAmount = playersAmount;
        this.objectivePoints = objectivePoints;

        //Cerrar ventana
        playerWindow.dispose();

        //Iniciar juego
        FarkleGame game = new FarkleGame(this.playersAmount, this.objectivePoints);
    }
}