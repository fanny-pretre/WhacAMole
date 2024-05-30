
// IMPORT STATEMENTS 
import java.awt.*;
import java.awt.event.*;
// Random library qui permettra de placer la taupe et la plante dans des endroits random
import java.util.Random;

import javax.swing.*;


// CLASS DECLARATION
public class WhacAMole {


    // ATTRIBUTS ET FIELDS


    // Déterminer la taille du board
    int boardWidth = 600; 
    int boardHeight = 650; 

    // Window
    JFrame frame = new JFrame("Mario: Whac A Mole"); 
    // Section de texte 
    JLabel textLabel = new JLabel();
    // Zone de texte
    JPanel textPanel = new JPanel();
    //Zone de jeu 
    JPanel boardPanel = new JPanel(); 
    // 9 boutons 
    JButton [] board = new JButton[9]; 
    

    // Images
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    // Boutons plante et taupe
    JButton currentMoleTile;
    JButton currentPlantTile; 

    // Timer et random
    Random random= new Random();
    Timer setMoleTimer; 
    Timer setPlantTimer; 

    // Pour le score
    int score;



    // CONSTRUCTOR AVEC LES PROPRIETES
    WhacAMole () {
        // STRUCTURE DE LA FENETRE 
        // Pour déterminer la taille du board en fonction de ce qui a été déclaré plus haut
        frame.setSize(boardWidth, boardHeight);
        // Ouvre la window au centre
        frame.setLocationRelativeTo(null);
        // Permet de gérer la taille de la fenêtre
        frame.setResizable(true);
        // Pour fermer le programme
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Fait un layout carré classique
        frame.setLayout(new BorderLayout());

        // STRUCTURE DU TEXTE
        // Pour choisir la police et la taille
        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        //Pour centrer le texte plutôt que de l'avoir à gauche
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        //Affichage du texte
        textLabel.setText("Score : 0"); 
        //Ajoute de l'opacité au texte
        textLabel.setOpaque(true);
        
        // STRUCTURE DE LA ZONE DE TEXTE
        //Fait un layout carré classique
        textPanel.setLayout(new BorderLayout());
        //Ajoute le texte à la zone de texte
        textPanel.add(textLabel); 
        //Ajoute la zone de texte à la fenêtre en haut
        frame.add(textPanel, BorderLayout.NORTH); 

        //STRUCTURE DE LA ZONE DE JEU
        //Structure la zone de jeu en grille de 3 par 3
        boardPanel.setLayout(new GridLayout(3, 3));
        //Ajoute le boardPanel au frame 
        frame.add(boardPanel); 

        // On récupère l'image, on la scale et on va créer une nouvelle icône à partir de cette scaled image
     Image plantImg = new ImageIcon(getClass().getResource("./piranha.png")).getImage(); 
      plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

      Image moleImg = new ImageIcon(getClass().getResource("./monty.png")).getImage(); 
      moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

//STRUCTURE DU SCORE 
score = 0;

        //STRUCTURE DES BOUTONS
        // Ajoute les boutons au boardPanel
        for (int i=0; i <9; i++) {
            JButton tile = new JButton();
            board[i] = tile; 
            boardPanel.add(tile);
            //retire les contours autour de l'image
            tile.setFocusable(false);
            //tile.setBackground(Color.pink);
            //tile.setIcon(moleIcon);
            //Ecouteur d'évènement sur les boutons pour mettre à jour le score
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //On localise le bouton qui a été cliqué
                   JButton tile = (JButton) e.getSource();
                   //On ajoute les points si le joueur a cliqué sur la bonne case
                   if (tile == currentMoleTile) {
                        score += 10; 
                        textLabel.setText("Score: " + Integer.toString(score));
                   }
                   // On display game over s'il a cliqué sur la plante, on arrête de faire bouger les plantes et on ne peut plus cliquer
                    else if (tile == currentPlantTile) {
                        textLabel.setText("Game Over: " + Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        // Passe le fond en gris
                        for (int i = 0; i < 9; i++) {
                            board[i].setEnabled(false);
                        }
                    }
                }
            });
        }

        //set le timer pour le déplacement de la taupe
        setMoleTimer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentMoleTile != null) {
                    // Si le bouton contient quelque chose alors il faut le déplacer
                    currentMoleTile.setIcon(null);
                    currentMoleTile = null;
                }

                // choisir un placement aléatoire pour le nouvel espace de la taupe
                int num = random.nextInt(9); 
                JButton tile = board[num];

                //Si le carreau est occupé par la plante alors on passe le carreau
                if (currentPlantTile == tile) return; 

                currentMoleTile = tile;
                currentMoleTile.setIcon(moleIcon);
            }
        });

        //set le timer pour le déplacement de la plante
        setPlantTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentPlantTile != null) {
                    // Si le bouton contient quelque chose alors il faut le déplacer
                    currentPlantTile.setIcon(null);
                    currentPlantTile = null;
                }

                // choisir un placement aléatoire pour le nouvel espace de la plante
                int num = random.nextInt(9); 
                JButton tile = board[num];

                 //Si le carreau est occupé par la taupe alors on passe le carreau
                 if (currentMoleTile == tile) return; 

                currentPlantTile = tile;
                currentPlantTile.setIcon(plantIcon);
            }
        });

        //Lance le timer pour la taupe
        setMoleTimer.start(); 

        //Lance le timer pour la plante
        setPlantTimer.start(); 

        //Ouvre la fenêtre mais pas avant de s'être assuré que tout le reste est ok. 
        frame.setVisible(true);
    }
}
