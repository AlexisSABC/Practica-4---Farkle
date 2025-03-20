package farkle_game;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.awt.Font.PLAIN;

public class FarkleGame {
    //Atributos
    private int playersAmount;

    //Objetos graficos
    private JButton save;
    private JButton playDices;
    private JButton pass;

    private JFrame window;
    private JFrame playerWindow;

    private JLabel playerTurn;
    private JLabel preliminaryPoints;
    private JLabel winnerMessage;
    private ArrayList<JLabel> playerPoints;
    private ArrayList<JCheckBox> diceOptions;

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
        diceOptions = new ArrayList<>();

        //Agregar rutas de imagenes
        dicesPath = new ArrayList<>();
        dicesPath.add("src/Resources/face_1_point.png");
        dicesPath.add("src/Resources/face_2_points.png");
        dicesPath.add("src/Resources/face_3_points.png");
        dicesPath.add("src/Resources/face_4_points.png");
        dicesPath.add("src/Resources/face_5_points.png");
        dicesPath.add("src/Resources/face_6_points.png");

        //Crear ventana
        window = new JFrame("Practica 4 - Farkle");
    }

    //Ventana para pedir numero de jugadores
    private void playersWindow(){
        int savePlayersAmount[] = {0};

        //Definir ventana
        playerWindow = new JFrame("Practica 4 - N. de jugadores");
        playerWindow.setSize(500, 240);
        playerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerWindow.setLayout(null);

        //Preparar espacio para pocisionar objetos
        Container space = new Container();
        space = playerWindow.getContentPane();
        space.setBackground(Color.WHITE);

        //Definir etiqueta de instrucciones
        JLabel labelInstructions = new JLabel("Selecciona numero de jugadores:");
        labelInstructions.setFont(new Font ("ARIAL", Font.BOLD, 22));
        labelInstructions.setBounds((500 - labelInstructions.getPreferredSize().width) / 2, 30, labelInstructions.getPreferredSize().width, labelInstructions.getPreferredSize().height);
        labelInstructions.setVisible(true);

        //Definit lista de jugadores
        JComboBox playersList = new JComboBox<>();
        playersList.setFont(new Font ("ARIAL", Font.BOLD, 22));
        playersList.setBackground(Color.WHITE);
        playersList.setBounds((500 - 60) / 2, 70, 60, playersList.getPreferredSize().height);
        playersList.setBorder(new LineBorder(Color.BLACK, 3));
        playersList.setVisible(true);

        for(int i = 2; i <= 10; i++){
            playersList.addItem(i);
        }

        //Boton de guardar
        save = new JButton("!!Jugar!!");
        save.setBackground(Color.WHITE);
        save.setFont(new Font ("ARIAL", Font.BOLD, 20));
        save.setBounds((500 - save.getPreferredSize().width) / 2, 120, save.getPreferredSize().width, save.getPreferredSize().height);
        save.setBorder(new LineBorder(Color.BLACK, 3));
        save.setVisible(true);

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
        window.setSize(1000, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(0, 0);
        window.setLayout(null);

        //Preparar espacio para pocisionar objetos
        Container space = new Container();
        space = window.getContentPane();
        space.setBackground(Color.WHITE);

        //Etiqueta de titulo
        JLabel playerPointsTitle = new JLabel("Puntos de jugadores");
        playerPointsTitle.setVisible(true);
        playerPointsTitle.setFont(new Font ("ARIAL", Font.BOLD, 20));
        playerPointsTitle.setBounds((1000 - 700) / 4, 20, playerPointsTitle.getPreferredSize().width, playerPointsTitle.getPreferredSize().height);
        window.add(playerPointsTitle);

        //Etiquetas de jugadores
        for(int i = 0; i < this.playersAmount; i++){
            JLabel playerPointsLabel = new JLabel("+) Puntos jugador " + (i + 1) + ": 0.");
            playerPoints.add(playerPointsLabel);
        }

        for(int i = 0; i < playersAmount; i++){
            playerPoints.get(i).setBounds(16, 30 + (i * 30), 300, 60);
            playerPoints.get(i).setFont(new Font ("ARIAL", Font.BOLD, 20));
            window.add(playerPoints.get(i));
       }

        //Mostrar turno de jugador
        playerTurn = new JLabel("Turno de Jugador n");
        playerTurn.setVisible(true);
        playerTurn.setFont(new Font ("ARIAL", Font.BOLD, 25));
        playerTurn.setBounds( (330 / 3) + ((1000 - playerTurn.getPreferredSize().width) / 2), 50, playerTurn.getPreferredSize().width, playerTurn.getPreferredSize().height);
        window.add(playerTurn);

        //Mostrar dados
        showDices();

        //Mostrar opciones de seleccion de dados
        for(int i = 0; i <= 5; i++){
            JCheckBox diceOption = new JCheckBox("Dado " + (i + 1), true);
            diceOptions.add(diceOption);
        }

        for(int i = 0; i <= 5; i++){
            diceOptions.get(i).setBounds(320 + (i * 100), ((500 - diceOptions.get(i).getPreferredSize().height) / 2) - 30, 100, diceOptions.get(i).getPreferredSize().height);
            diceOptions.get(i).setBackground(Color.WHITE);
            diceOptions.get(i).setFont(new Font ("ARIAL", Font.BOLD, 18));
            window.add(diceOptions.get(i));
        }

        //Boton de tiro de dados
        playDices = new JButton("Tirar dados");
        playDices.setBackground(Color.WHITE);
        playDices.setFont(new Font ("ARIAL", Font.BOLD, 24));
        playDices.setBounds(380, ((500 - playDices.getPreferredSize().height) / 2) + 20, playDices.getPreferredSize().width, playDices.getPreferredSize().height);
        playDices.setBorder(new LineBorder(Color.BLACK, 3));
        playDices.setVisible(true);
        window.add(playDices);

        //Evento para playDices
        playDices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        //Boton de paso de turno
        pass = new JButton("Terminar turno");
        pass.setBackground(Color.WHITE);
        pass.setFont(new Font ("ARIAL", Font.BOLD, 24));
        pass.setBounds(260 + (820 / 2), ((500 - pass.getPreferredSize().height) / 2) + 20, pass.getPreferredSize().width, pass.getPreferredSize().height);
        pass.setBorder(new LineBorder(Color.BLACK, 3));
        pass.setVisible(true);
        window.add(pass);

        //Evento para pass
        pass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        //Puntos preliminares de jugador n
        preliminaryPoints = new JLabel("Puntos preliminares: 0.");
        preliminaryPoints.setVisible(true);
        preliminaryPoints.setFont(new Font ("ARIAL", Font.BOLD, 20));
        preliminaryPoints.setBounds( 320, ((500 - preliminaryPoints.getPreferredSize().height) / 2) + 60, preliminaryPoints.getPreferredSize().width, preliminaryPoints.getPreferredSize().height);
        window.add(preliminaryPoints);

        //Preparar mensaje de ganador
        winnerMessage = new JLabel("!!! JUGADOR n GANA EL JUEGO !!!");
        winnerMessage.setVisible(true);
        winnerMessage.setFont(new Font ("ARIAL", Font.BOLD, 30));
        winnerMessage.setBounds( (1000 - winnerMessage.getPreferredSize().width) / 2, 365, winnerMessage.getPreferredSize().width, winnerMessage.getPreferredSize().height);
        window.add(winnerMessage);

        //Configuraciones finales
        window.setResizable(false);
        window.setVisible(true);
    }

    //Mostrar dados
    public void showDices(){
        int generalSize = 100;

        for (int i = 0; i <= 5; i++) {
            ImageIcon originalImage = new ImageIcon(dicesPath.get(i));
            ImageIcon resizedImage = new ImageIcon(originalImage.getImage().getScaledInstance(generalSize, generalSize, Image.SCALE_SMOOTH));
            JLabel diceImage = new JLabel(resizedImage);
            diceImage.setBounds(310 + (i * generalSize + 10), (500 - generalSize) / 5, generalSize, generalSize);
            window.add(diceImage);
        }
        window.setVisible(true);
    }
}