package farkle_game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FarkleGame {
    //Atributos
    private int playersAmount;
    private ArrayList<Player> players;
    private int turnPlayerID;

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
    ArrayList<JLabel> dicesImages;
    ArrayList<String> dicesPath;

    //Pedir inicialmente numero de jugadores
    public FarkleGame(){
        //Pedir numero de jugadores (y crearlos)
        players = new ArrayList<>();
        playersWindow();

        //Declarar y ArrayLists globales
        playerPoints = new ArrayList<>();
        diceOptions = new ArrayList<>();
        dicesImages = new ArrayList<>();

        //Crear dados
        dices = new ArrayList<>();
        for(int i = 0; i <= 5; i++){
            Dice dice = new Dice();
            dices.add(dice);
        }

        //Inicializar ID de jugador en 0 (Jugador 1 inicia)
        turnPlayerID = 0;

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
        labelInstructions.setBounds((500 - labelInstructions.getPreferredSize().width) / 2, (240 - labelInstructions.getPreferredSize().height) / 6, labelInstructions.getPreferredSize().width, labelInstructions.getPreferredSize().height);
        labelInstructions.setVisible(true);

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

        //Boton de guardar
        save = new JButton("!!Jugar!!");
        save.setBackground(Color.WHITE);
        save.setFont(new Font ("ARIAL", Font.BOLD, 20));
        save.setBounds((500 - save.getPreferredSize().width) / 2, ((240 - save.getPreferredSize().height) / 2) + 10, save.getPreferredSize().width, save.getPreferredSize().height);
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

        //Crear jugadores
        for(int i = 0; i < this.playersAmount; i++){
            Player player = new Player();
            players.add(player);
        }

        //Generar ventana de juego
        playerWindow.dispose();
        generateWindow();
    }

    //Construir ventana principal
    private void generateWindow(){
        //Arreglo de control para Lambdas
        int y[] = {0};

        //Crear y configurar ventana
        window.setSize(1100, 500);
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
        playerPointsTitle.setBounds((1100 - 700) / 4, 20, playerPointsTitle.getPreferredSize().width, playerPointsTitle.getPreferredSize().height);
        window.add(playerPointsTitle);

        //Etiquetas de jugadores
        for(int i = 0; i < this.playersAmount; i++){
            JLabel playerPointsLabel = new JLabel("+) Puntos jugador " + (i + 1) + ": 0.");
            playerPoints.add(playerPointsLabel);
        }

        y[0] = 0;
        playerPoints.forEach(playerPoint -> {
            playerPoint.setBounds(16, 30 + (y[0] * 30), 300, 60);
            playerPoint.setFont(new Font ("ARIAL", Font.BOLD, 20));
            window.add(playerPoint);
            y[0]++;
        });

        //Mostrar turno de jugador
        playerTurn = new JLabel("Turno de Jugador 1");
        playerTurn.setVisible(true);
        playerTurn.setFont(new Font ("ARIAL", Font.BOLD, 25));
        playerTurn.setBounds( (430 / 3) + ((1100 - playerTurn.getPreferredSize().width) / 2), (500 - playerTurn.getPreferredSize().height) / 12, playerTurn.getPreferredSize().width + 60, playerTurn.getPreferredSize().height);
        window.add(playerTurn);

        //Mostrar dados
        manageDices(false);

        //Mostrar opciones de seleccion de dados
        for(int i = 0; i <= 5; i++){
            JCheckBox diceOption = new JCheckBox("Dado " + (i + 1), true);
            diceOptions.add(diceOption);
        }

        y[0] = 0;
        diceOptions.forEach(diceOption -> {
            diceOption.setBounds(420 + (y[0] * 100), ((500 - diceOption.getPreferredSize().height) / 2) - 30, 100, diceOption.getPreferredSize().height);
            diceOption.setBackground(Color.WHITE);
            diceOption.setFont(new Font ("ARIAL", Font.BOLD, 18));
            window.add(diceOption);
            y[0]++;
        });

        //Boton de tiro de dados
        playDices = new JButton("Tirar dados");
        playDices.setBackground(Color.WHITE);
        playDices.setFont(new Font ("ARIAL", Font.BOLD, 24));
        playDices.setBounds(480, ((500 - playDices.getPreferredSize().height) / 2) + 20, playDices.getPreferredSize().width, playDices.getPreferredSize().height);
        playDices.setBorder(new LineBorder(Color.BLACK, 3));
        playDices.setVisible(true);
        window.add(playDices);

        //Evento para playDices (Segun los dados seleccionados de playDices)
        playDices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(diceOptions.get(0).isSelected()){
                    dices.get(0).playDice();
                }

                if(diceOptions.get(1).isSelected()){
                    dices.get(1).playDice();
                }

                if(diceOptions.get(2).isSelected()){
                    dices.get(2).playDice();
                }

                if(diceOptions.get(3).isSelected()){
                    dices.get(3).playDice();
                }

                if(diceOptions.get(4).isSelected()){
                    dices.get(4).playDice();
                }

                if(diceOptions.get(5).isSelected()){
                    dices.get(5).playDice();
                }

                //Borrar y mostrar dados
                manageDices(true);
                manageDices(false);

                //Controlar Jugador (Analizar dados)
                analyzeDices();
            }
        });

        //Boton de paso de turno
        pass = new JButton("Terminar turno");
        pass.setBackground(Color.WHITE);
        pass.setFont(new Font ("ARIAL", Font.BOLD, 24));
        pass.setBounds(360 + (820 / 2), ((500 - pass.getPreferredSize().height) / 2) + 20, pass.getPreferredSize().width, pass.getPreferredSize().height);
        pass.setBorder(new LineBorder(Color.BLACK, 3));
        pass.setVisible(true);
        window.add(pass);

        //Evento para pass (Pasar de turno)
        pass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skipTurn();
            }
        });

        //Puntos preliminares de jugador n
        preliminaryPoints = new JLabel("Puntos preliminares: 0.");
        preliminaryPoints.setVisible(true);
        preliminaryPoints.setFont(new Font ("ARIAL", Font.BOLD, 20));
        preliminaryPoints.setBounds( 420, ((500 - preliminaryPoints.getPreferredSize().height) / 2) + 65, preliminaryPoints.getPreferredSize().width + 100, preliminaryPoints.getPreferredSize().height);
        window.add(preliminaryPoints);

        //Preparar mensaje de ganador
        winnerMessage = new JLabel("!!! JUGADOR n GANA EL JUEGO !!!");
        winnerMessage.setVisible(false);
        winnerMessage.setFont(new Font ("ARIAL", Font.BOLD, 30));
        winnerMessage.setBounds( (1100 - winnerMessage.getPreferredSize().width) / 2, ((500 - winnerMessage.getPreferredSize().height) / 2) + 120, winnerMessage.getPreferredSize().width + 60, winnerMessage.getPreferredSize().height);
        window.add(winnerMessage);

        //Configuraciones finales
        window.setResizable(false);
        window.setVisible(true);
    }

    //Mostrar dados
    public void manageDices(boolean eraseDices){
        int generalSize = 100; //Establecer dimensiones generales de las imagenes

        if(eraseDices){
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
                window.add(diceImage);
            }
        }
        window.setVisible(true);
    }

    //Mostrar al ganador
    public void showWinner(int winnerID){
        winnerMessage.setText("!!! JUGADOR " + (turnPlayerID + 1) + " GANA EL JUEGO !!!");
        winnerMessage.setVisible(true);

        //Ocultar elementos
        playDices.setVisible(false);
        pass.setVisible(false);
        preliminaryPoints.setVisible(false);

        diceOptions.forEach(dice -> {
            dice.setVisible(false);
        });
    }

    //Pasar de turno
    public void skipTurn(){
        //Declarar al ganador o seguir jugando
        if(players.get(turnPlayerID).getPlayerPoints() >= 10000){
            showWinner(turnPlayerID);

        }else{
            //Circlar Id de Jugador
            turnPlayerID++;
            if(turnPlayerID == playersAmount){
                turnPlayerID = 0;
            }

            //Reseleccionar dados
            diceOptions.forEach(dice -> {
                dice.setSelected(true);
            });

            //Actualizar etiqueta de turno de jugador
            playerTurn.setText("Turno de Jugador " + (turnPlayerID + 1));
        }
    }

    //Control de turno
    public void analyzeDices(){
        //Codigo para analizar los puntos de los dados
    }
}