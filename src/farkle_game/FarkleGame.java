package farkle_game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FarkleGame {
    //Atributos
    private int objectivePoints; //Guarda los puntos a alcanzar
    private int playersAmount; //Guarda la cantidad de jugadores en juego

    private ArrayList<Player> players; //Guarda los jugadores
    private int turnPlayerID; //Guarda la ID del jugado del turno actual

    private int puntosJugadorRonda; //Guarda los puntos de tiros de dados anteiores
    private int preliminarPoints; //Muestra los puntos tentativos del turno actual

    private boolean isFarkle; //Determina si el tiro de dados es un "Farkle"
    private boolean unnableHotDices; //Determina si existen "Hot Dices"
    private int hotDicesPoints; //Guarda los puntos obtenidos con Hot Dices

    //Objetos graficos
    private JButton playDices;
    private JButton pass;

    private static JFrame window;

    private JLabel playerTurn;
    private JLabel preliminaryPoints;
    private JLabel winnerMessage;
    private ArrayList<JLabel> playerPoints;

    //Dados a usar
    DicesSet dicesSet;

    //Pedir inicialmente numero de jugadores
    public FarkleGame(int playersAmount, int objectivePoints){
        //Pedir numero de jugadores (y crearlos)
        this.playersAmount = playersAmount;
        players = new ArrayList<>();

        //Establecer puntaje
        this.objectivePoints = objectivePoints;

        //Determinar "Hot Dices" en verdadero
        unnableHotDices = true;
        hotDicesPoints = 0;

        //Establecer valores por defecto
        isFarkle = false;
        preliminarPoints = 0;
        puntosJugadorRonda = 0;

        //Crea los jugadores establecidos
        for(int i = 0; i < this.playersAmount; i++){
            Player player = new Player();
            players.add(player);
        }

        //Declarar y ArrayLists globales
        playerPoints = new ArrayList<>();
        dicesSet = new DicesSet();

        //Inicializar ID de jugador en 0 (Jugador 1 inicia)
        turnPlayerID = 0;

        //Crear ventana
        window = new JFrame("Practica 4 - Farkle");
        generateWindow();
    }

    //Devolver ventana
    public static JFrame getWindow(){
        return window;
    }

    //Construir ventana principal
    private void generateWindow(){
        //Acumulador para Lambdas
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

        //Crea etiqueta de titulo
        JLabel playerPointsTitle = new JLabel("Limite de " + objectivePoints + " Puntos");
        playerPointsTitle.setVisible(true);
        playerPointsTitle.setFont(new Font ("ARIAL", Font.BOLD, 20));
        playerPointsTitle.setBounds((1100 - 700) / 6, 20, playerPointsTitle.getPreferredSize().width, playerPointsTitle.getPreferredSize().height);
        window.add(playerPointsTitle);

        //Crea etiquetas de puntos jugadores
        for(int i = 0; i < this.playersAmount; i++){
            JLabel playerPointsLabel = new JLabel("+) Puntos jugador " + (i + 1) + ": 0.");
            playerPoints.add(playerPointsLabel);
        }

        //Lambda que configura las etiquetas para los puntos de los jugadores
        y[0] = 0;
        playerPoints.forEach(playerPoint -> {
            playerPoint.setBounds(16, 30 + (y[0] * 30), 300, 60);
            playerPoint.setFont(new Font ("ARIAL", Font.BOLD, 20));
            window.add(playerPoint);
            y[0]++;
        });

        //Crea etiqueta de turno de jugador
        playerTurn = new JLabel("Turno de Jugador 1");
        playerTurn.setVisible(true);
        playerTurn.setFont(new Font ("ARIAL", Font.BOLD, 25));
        playerTurn.setBounds( (430 / 3) + ((1100 - playerTurn.getPreferredSize().width) / 2), (500 - playerTurn.getPreferredSize().height) / 12, playerTurn.getPreferredSize().width + 60, playerTurn.getPreferredSize().height);
        window.add(playerTurn);

        //Mostrar dados
        dicesSet.showDices(true);

        //Mostrar etiquetas de dados
        dicesSet.showDicesLabels();

        //Crear boton de tiro de dados
        playDices = new JButton("Tirar dados");
        playDices.setBackground(Color.WHITE);
        playDices.setFont(new Font ("ARIAL", Font.BOLD, 24));
        playDices.setBounds(480, ((500 - playDices.getPreferredSize().height) / 2) + 5, playDices.getPreferredSize().width, playDices.getPreferredSize().height);
        playDices.setBorder(new LineBorder(Color.BLACK, 3));
        playDices.setVisible(true);
        window.add(playDices);

        //Evento para playDices (Tirar los dados que se pueden tirar, es decir, los que aun no dan puntos)
        playDices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Tirar dados
                dicesSet.playDices();

                //Borrar y mostrar dados
                dicesSet.showDices(false);
                dicesSet.showDices(true);

                //Analizar dados
                if(!isFarkle){
                    dicesSet.unlockAllDices();
                    puntosJugadorRonda = preliminarPoints;
                    getPreliminarPoints();
                }
            }
        });

        //Crear boton de paso de turno
        pass = new JButton("Terminar turno");
        pass.setBackground(Color.WHITE);
        pass.setFont(new Font ("ARIAL", Font.BOLD, 24));
        pass.setBounds(360 + (820 / 2), ((500 - pass.getPreferredSize().height) / 2) + 5, pass.getPreferredSize().width, pass.getPreferredSize().height);
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

        //Etiqueta de puntos preliminares de jugador n
        preliminaryPoints = new JLabel("Puntos preliminares: 0.");
        preliminaryPoints.setVisible(true);
        preliminaryPoints.setFont(new Font ("ARIAL", Font.BOLD, 22));
        preliminaryPoints.setBounds( (430 / 3) + ((1100 - preliminaryPoints.getPreferredSize().width) / 2) - 20, ((500 - preliminaryPoints.getPreferredSize().height) / 2) + 65, preliminaryPoints.getPreferredSize().width + 200, preliminaryPoints.getPreferredSize().height);
        window.add(preliminaryPoints);

        //Etiqueta de mensaje de ganador
        winnerMessage = new JLabel("!!! JUGADOR n GANA EL JUEGO !!!");
        winnerMessage.setVisible(false);
        winnerMessage.setFont(new Font ("ARIAL", Font.BOLD, 30));
        winnerMessage.setBounds( (1100 - winnerMessage.getPreferredSize().width) / 2, ((500 - winnerMessage.getPreferredSize().height) / 2) + 120, winnerMessage.getPreferredSize().width + 60, winnerMessage.getPreferredSize().height);
        window.add(winnerMessage);

        //Configuraciones finales
        window.setResizable(false);
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

        dicesSet.lockAllDices();
    }

    //Pasar de turno
    public void skipTurn(){
        //Establecer puntos a jugador
        players.get(turnPlayerID).setPlayerPoints(preliminarPoints);
        playerPoints.get(turnPlayerID).setText("+) Puntos jugador " + (turnPlayerID + 1) + ": " + players.get(turnPlayerID).getPlayerPoints() + ".");
        isFarkle = false;

        //Reiniciar puntos preliminares
        preliminarPoints = 0;
        preliminaryPoints.setText("Puntos preliminares: 0.");

        //Declarar al ganador o seguir jugando
        if(players.get(turnPlayerID).getPlayerPoints() >= objectivePoints){
            showWinner(turnPlayerID);

        }else{

            //Circlar Id de Jugador
            turnPlayerID++;
            if(turnPlayerID == playersAmount){
                turnPlayerID = 0;
            }

            //Reseleccionar dados
            dicesSet.unlockAllDices();
            unnableHotDices = true;
            hotDicesPoints = 0;

            //Actualizar etiqueta de turno de jugador
            playerTurn.setText("Turno de Jugador " + (turnPlayerID + 1));
        }
    }

    //revisa si hay 3 iguales
    public int threeAlike(){
        int howMany=0;
        Dice diceResult = null;
        int z = 0;

        //Obtiene los dados repetidos
        for(int i = 0; i<dicesSet.getDicesSize()-2;i++){
            for(int l =i+1; l<dicesSet.getDicesSize();l++){
                Dice diceA = dicesSet.getDice(i);
                Dice diceB = dicesSet.getDice(l);
                if(diceA.getDicePoints() == diceB.getDicePoints()){
                    howMany++;
                    diceResult = diceA;
                }
            }

            if(howMany == 2){ //si 3 fueron iguales regresa la cantidad de puntos que debe
                if(diceResult.getDicePoints() == 1){

                    //Bloquear dados
                    z = 0;
                    for(int y = 0; y <= 5; y++){
                        if(dicesSet.getDicePoints(y) == diceResult.getDicePoints() && (z <= 2)){
                            dicesSet.lockDice(y);
                            z++;
                        }
                    }

                    return 1000;
                }else {
                    //Bloquear dados
                    z = 0;
                    for(int y = 0; y <= 5; y++){
                        if((dicesSet.getDicePoints(y) == diceResult.getDicePoints()) && (z <= 2)){
                            dicesSet.lockDice(y);
                            z++;
                        }
                    }

                    return diceResult.getDicePoints()*100;
                }
            }
            howMany =0; //si no tiene un trio, entonces vuelve a 0
        }
        return 0;
    }

    public boolean allMatch(){
        return dicesSet.getDices().stream().allMatch(
                c ->  dicesSet.getDicePoints(0) == (c.getDicePoints()) //revisa si todas son iguales
        );
    }

    public int onesOfFiveMoreThanThree(int oneOrFive) { //devuelve la puntuacion de los unos y cincos en juego restando los trios de unos y cinco
        int howMany=0;

        //Obtiene los dados repetidos
        for(int i =0; i<dicesSet.getDicesSize();i++) {
            Dice diceA = dicesSet.getDice(i);
            if (diceA.getDicePoints() == oneOrFive) {
                howMany++;
            }
        }

        if(howMany > 2){ //si mas de 3 fueron iguales, devuelve la puntuacion de las siguientes a esas 3
            if(oneOrFive == 1){

                //Lambda que bloquea dados
                for(int y = 0; y <= 5; y++){
                    if(dicesSet.getDicePoints(y) == 1){
                        dicesSet.lockDice(y);
                    }
                }

                return 100*(howMany-3);
            }else if(oneOrFive == 5){

                //Lambda que bloquea dados
                for(int y = 0; y <= 5; y++){
                    if(dicesSet.getDicePoints(y) == 5){
                        dicesSet.lockDice(y);
                    }
                }

                return 50*(howMany-3);
            }
        }
        return 0;
    }

    public int onesOfFive(int oneOrFive){ //este metodo devuelve los puntos de todos los 1 y 5 en los dados, solo utilizarla cuando no haya un trio en juego
        int howMany=0;

        //Obtiene los dados repetidos
        for(int i =0; i<dicesSet.getDicesSize();i++) {
            Dice diceA = dicesSet.getDice(i);
            if (diceA.getDicePoints() == oneOrFive) {
                howMany++;
            }
        }

        if(oneOrFive == 1){ //dependiendo cuantos unos o 5 haya, devuelve la cantidad de puntos

            //Bloquear dados
            for(int y = 0; y <= 5; y++) {
                if (dicesSet.getDicePoints(y) == 1) {
                    dicesSet.lockDice(y);
                }
            }

            return 100*(howMany);
        }else if(oneOrFive == 5){

            //Bloquear dados/
            for(int y = 0; y <= 5; y++){
                if(dicesSet.getDicePoints(y) == 5){
                    dicesSet.lockDice(y);
                }
            }

            return 50*(howMany);
        }
        return 0;
    }

    //revisa si hay 3 y 3 iguales, ejemplo 2, 4, 2, 2, 4, 4 hay tres dos y tres cuatros
    public int threeAndThree(){
        int points =0;
        boolean firstThree = false;
        boolean lastThree = false;
        ArrayList<Dice> dicesThree = new ArrayList<>();
        dicesThree = (ArrayList<Dice>) dicesSet.getDices().clone();

        dicesThree.sort((p1, p2) ->
                Integer.compare(p1.getDicePoints(), p2.getDicePoints())
        );
        if(dicesThree.get(0).getDicePoints() == dicesThree.get(1).getDicePoints() && dicesThree.get(0).getDicePoints() == dicesThree.get(2).getDicePoints()){
            firstThree = true;
        }
        if(dicesThree.get(3).getDicePoints() == dicesThree.get(4).getDicePoints() && dicesThree.get(3).getDicePoints() == dicesThree.get(5).getDicePoints()){
            lastThree = true;
        }
        if(firstThree && lastThree){
            Dice diceA = dicesThree.get(0);
            Dice diceB = dicesThree.get(3);
            if(diceA.getDicePoints() == 1){
                points += 1000;
            }else if(diceA.getDicePoints() == 5){
                points += 500;
            }else{
                points += diceA.getDicePoints() *100;
            }
            if(diceB.getDicePoints() == 1){
                points += 1000;
            }else if(diceB.getDicePoints() == 5){
                points += 500;
            }else{
                points += diceB.getDicePoints() *100;
            }

            dicesSet.lockAllDices();
        }
        return points; //devuelve la suma de los puntos del jugador si es el caso, sino devuelve 0
    }

    //Determinar si existen Hot Dices
    public void findHotDices(){
        dicesSet.getDices().forEach(dice -> {
            if(dice.getCanPlayDice() == true){
                unnableHotDices = false;
            }
        });
    }

    //devuelve los puntos que tiene el jugador actualmente
    public void getPreliminarPoints() {
        int points = 0;
        int threeAlikePoints = threeAlike();
        if (allMatch()) {
            points += 2 * threeAlikePoints;
        } else if (threeAndThree() != 0) {
            points += threeAndThree();
        } else {
            if (threeAlikePoints == 1000) {
                points += 1000;
                points += onesOfFiveMoreThanThree(1);
            } else {
                points += onesOfFive(1);
            }
            if (threeAlikePoints == 500) {
                points += 500;
                points += onesOfFiveMoreThanThree(5);
            } else {
                points += onesOfFive(5);
            }
            if (threeAlikePoints != 1000 && threeAlikePoints != 500) {
                points += threeAlikePoints;
            }
        }

        //Determinar si existen Hot Dices
        findHotDices();

        //Acumular puntos si existen Hot Dices
        if(unnableHotDices){
            dicesSet.unlockAllDices();
            hotDicesPoints = points + hotDicesPoints;
            preliminarPoints = hotDicesPoints;

        }else{
            preliminarPoints = hotDicesPoints + points;
        }

        preliminaryPoints.setText("Puntos preliminares: " + preliminarPoints + ".");

        //Verifica si se puede jugar o no
        determinarJugada();
    }

    //Determina si es Farkle, Hot Dices o se continua comunmente
    public void determinarJugada(){
        isFarkle = false;
        boolean isAllLocked[] = {false};

        //Determinar si los dados ya estan bloqueados
        dicesSet.getDices().forEach(dice -> {
            if(dice.getCanPlayDice()){
                isAllLocked[0] = true;
            }
        });

        if((preliminarPoints == puntosJugadorRonda) && isAllLocked[0]){ //verifica si cambio los puntos de la ronda, osea obtuvo mejor puntuacion, en caso contario es farkle y pierde todos los puntos de la ronda
            isFarkle = true; //avisa que no cambio nada de puntos, por lo cual es farkle
            dicesSet.lockAllDices();
            preliminaryPoints.setText("FARKLE - Puntos perdidos");
            preliminarPoints = 0;

        }else{
            puntosJugadorRonda=preliminarPoints; //va guardando los puntos de la ronda
        }
    }

}