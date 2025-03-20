package farkle_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.awt.Font.PLAIN;

public class FarkleGame {
    //Atributos]
    private int playersAmount;
    private JButton save;

    //Objetos graficos
    private JFrame window;
    private JFrame playerWindow;
    private JLabel titlePoints;
    private ArrayList<JLabel> playerPoints;

    //ArrayList para controlar dados
    ArrayList<Dice> dices;
    ArrayList<String> dicesPath;

    //Pedir inicialmente numero de jugadores
    public FarkleGame(){
        //Pedir numero de jugadores
        playersWindow();

        //Declarar y ArrayLists objetos globales
        playerPoints = new ArrayList<>();
        dices = new ArrayList<>();

        dicesPath = new ArrayList<>();
        dicesPath.add("src/Resources/face_1_point.png");
        dicesPath.add("src/Resources/face_2_points.png");
        dicesPath.add("src/Resources/face_3_points.png");
        dicesPath.add("src/Resources/face_4_points.png");
        dicesPath.add("src/Resources/face_5_points.png");
        dicesPath.add("src/Resources/face_6_points.png");

        window = new JFrame("Practica 4 - Farkle");
    }

    //Ventana para pedir numero de jugadores
    private void playersWindow(){
        int savePlayersAmount[] = {0};

        //Definir ventana
        playerWindow = new JFrame("Practica 4 - N. de jugadores");
        playerWindow.setSize(300, 120);
        playerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerWindow.setLayout(null);

        //Definir fondo
        JPanel background = new JPanel(new FlowLayout());
        background.setBackground(Color.WHITE);
        playerWindow.setContentPane(background);

        //Definir etiqueta de instrucciones
        JLabel labelInstructions = new JLabel("Selecciona numero de jugadores:");
        labelInstructions.setFont(new Font ("ARIAL", Font.BOLD, 14));

        //Boton de guardar
        save = new JButton("!!Jugar!!");
        save.setBackground(Color.WHITE);
        save.setFont(new Font ("ARIAL", PLAIN, 14));
        save.setVisible(true);

        //Definit lista de jugadores
        JComboBox playersList = new JComboBox<>();
        playersList.setFont(new Font ("ARIAL", Font.BOLD, 14));
        playersList.setBackground(Color.WHITE);

        for(int i = 2; i <= 10; i++){
            playersList.addItem(i);
        }

        //Tomar valor de la lista
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePlayersAmount[0] = (Integer) playersList.getSelectedItem();
                setPlayersAmount(savePlayersAmount[0]);
            }
        });

        //Agregar elementos a la ventana
        playerWindow.add(labelInstructions);
        playerWindow.add(playersList);
        playerWindow.add(save);

        //Mostrar ventana
        playerWindow.setResizable(false);
        playerWindow.setVisible(true);
    }

    //Establecer numero de jugadores
    private void setPlayersAmount(int playersAmount){
        //Establecer numero de jugadores
        this.playersAmount = playersAmount;

        //Generar ventana de juego
        playerWindow.dispose();
        generateWindow();
    }

    //Construir ventana principal
    private void generateWindow(){
        //Crear y configurar ventana
        window.setSize(740, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(0, 0);
        window.setBackground(Color.WHITE);
        window.setLayout(null);

        //Etiqueta de titulo
        JLabel playerPointsTitle = new JLabel("Puntos de jugadores");
        playerPointsTitle.setVisible(true);
        playerPointsTitle.setFont(new Font ("ARIAL", Font.BOLD, 16));
        playerPointsTitle.setBounds(40, 20, playerPointsTitle.getPreferredSize().width, playerPointsTitle.getPreferredSize().height);
        window.add(playerPointsTitle);

        //Etiquetas de jugadores
        for(int i = 0; i < this.playersAmount; i++){
            JLabel playerPointsLabel = new JLabel("+) Puntos jugador " + (i + 1) + ": 0.");
            playerPoints.add(playerPointsLabel);
        }

        for(int i = 0; i < playersAmount; i++){
            playerPoints.get(i).setBounds(16, 45 + (i * 25), 300, playerPoints.get(i).getPreferredSize().height);
            playerPoints.get(i).setFont(new Font ("ARIAL", Font.BOLD, 16));
            window.add(playerPoints.get(i));
       }

        showDices();

        window.setResizable(false);
        window.setVisible(true);
    }

    //Mostrar dados
    public void showDices(){
        for (int i = 0; i <= 5; i++) {
            ImageIcon originalImage = new ImageIcon(dicesPath.get(i));
            ImageIcon resizedImage = new ImageIcon(originalImage.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));
            JLabel diceImage = new JLabel(resizedImage);
            diceImage.setBounds(250 + (i * 75), 100, 80, 80);
            window.add(diceImage);
        }
        window.setVisible(true);
    }
}