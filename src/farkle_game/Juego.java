package farkle_game;

import javax.swing.*;
import java.awt.*;

public class Juego {
    private static JFrame window;

    public static void main(String[] args){

        //Crear ventana d juego
        window = new JFrame("Practica 4 - Farkle");
        generateWindow();
    }

    //Construir ventana
    private static void generateWindow(){
        //Crear y configurar ventana
        window.setSize(1000, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(0, 0);

        //Cambiar color de fondo
        JPanel background = new JPanel();
        background.setBackground(Color.WHITE);
        window.setContentPane(background);

        window.setResizable(false);
        window.setVisible(true);
    }

    //Mostrar imagen en ventana (????????)
    private static void showImages(String path, int x, int y){
        //Obtener imagen de ruta
        ImageIcon image = new ImageIcon(path);

        //Redimensionar la imagen
        Image resizedImage = image.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);

        //Crear una nueva imagen con la imagen redimensionada
        ImageIcon newResizedImage = new ImageIcon(resizedImage);

        //Crear un JLabel para mostrar la nueva imagen
        JLabel imageLabel = new JLabel(newResizedImage);

        //Repocisionar imagen
        imageLabel.setBounds(x, y, 80, 80);

        //Agregar el JLabel a la ventana
        window.add(imageLabel);

        // Hacer visible la ventana
        window.setVisible(true);
    }
}