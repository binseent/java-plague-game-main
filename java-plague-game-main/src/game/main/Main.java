package game.main;

import game.component.GamePanel;
import game.obj.sound.Sound;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class Main extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private GamePanel panelGame;
    private Sound sound;

    public Main() {
        init();
        sound = new Sound();
    }

    private void init() {
        setTitle("Java 2D Game");
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a card panel with CardLayout
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        

        // Create a game menu panel
        JPanel menuPanel = createMenuPanel();
        cardPanel.add(menuPanel, "menu");

        // Create the settings panel
        JPanel settingsPanel = createSettingsPanel();
        cardPanel.add(settingsPanel, "settings");

        // Create the game panel
        panelGame = new GamePanel();
        cardPanel.add(panelGame, "game");

        // Set the background color of the content pane
        getContentPane().setBackground(Color.BLACK);

        // Add the card panel to the frame
        add(cardPanel);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                showMenu(); // Show the menu when the window opens
                sound.startBackgroundMusic(); // Start the background music
            }
        });
    }
    
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(getClass().getResource("/game/image/menu_background.png"));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };

        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridwidth = 2; // Span two columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Create menu components
        JLabel titleLabel = new JLabel("PLAGUE LEGENDS");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));

        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setPreferredSize(new Dimension(400, 100)); // Set preferred button size here
        startButton.addActionListener(e -> {
            showGame();
            sound.soundShoot(); // Play shoot sound effect
        });

        JButton optionsButton = new JButton("Options");
        optionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsButton.setPreferredSize(new Dimension(400, 100)); // Set preferred button size here
        optionsButton.addActionListener(e -> showSettings());

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setPreferredSize(new Dimension(400, 100)); // Set preferred button size here
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        // Add components to the menu panel with appropriate constraints
        menuPanel.add(titleLabel, gbc);
        menuPanel.add(startButton, gbc);
        menuPanel.add(optionsButton, gbc);
        menuPanel.add(exitButton, gbc);

        return menuPanel;
    }

    private JPanel createSettingsPanel() {
        JPanel settingsPanel = new JPanel(new BorderLayout());

        // Create settings components
        JLabel titleLabel = new JLabel("Game Settings");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton returnButton = new JButton("Return");
        returnButton.setPreferredSize(new Dimension(150, 30));
        returnButton.addActionListener(e -> {
            showMenu();
            sound.soundHit(); // Play hit sound effect
        });

        // Create the credits text
        JTextArea creditsTextArea = new JTextArea();
        creditsTextArea.setText("HELLO TO MY DEAR PLAYER\n\n" +
                                "I EXPRESS DEEP GRATITUDE FOR PLAYING THIS GAME\n" +
                                "THANK YOU SO MUCH\n\n" +
                                "AND MOREOVER I WISH YOU THE BEST\n\n" +
                                "BTW TRY TO HIT 100 KILLS\n" +
                                "AND SEE WHAT HAPPENS\n\n" +
                                "THE CONTROLS ARE:\n" +
                                "W FOR SPEED\n" +
                                "A FOR LEFT MANEUVER\n" +
                                "D FOR RIGHT MANEUVER\n" +
                                "J OR LEFT MOUSE CLICK FOR LIGHT BULLETS *PS I MADE IT FOR PEOPLE WHO WANTS TO GO HARDCORE*\n" +
                                "K OR RIGHT MOUSE CLICK FOR NEWBIES LIKE YOURSELF HAHAHAHAHAHHAHAHAHA\n\n" +
                                "WELCOME TO PLAGUE LEGENDS ");
        creditsTextArea.setLineWrap(true);
        creditsTextArea.setWrapStyleWord(true);
        creditsTextArea.setFont(new Font("Arial", Font.BOLD, 16));
        creditsTextArea.setForeground(Color.WHITE);
        creditsTextArea.setBackground(Color.BLACK);
        creditsTextArea.setEditable(false);
        creditsTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        // Create a scroll pane for the credits text
        JScrollPane creditsScrollPane = new JScrollPane(creditsTextArea);
        creditsScrollPane.setPreferredSize(new Dimension(400, 200));

        // Add components to the settings panel
        settingsPanel.add(titleLabel, BorderLayout.NORTH);
        settingsPanel.add(creditsScrollPane, BorderLayout.CENTER);
        settingsPanel.add(returnButton, BorderLayout.SOUTH);

        return settingsPanel;
    }

    private void showMenu() {
        cardLayout.show(cardPanel, "menu"); // Show the menu panel
    }

    private void showSettings() {
        cardLayout.show(cardPanel, "settings"); // Show the settings panel
    }

    private void showGame() {
        // Stop the background music
        sound.stopBackgroundMusic();

        // Show the game panel
        cardLayout.show(cardPanel, "game");
        panelGame.start(); // Start the game

        // Resume playing the background music
        sound.startBackgroundMusic();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }
}
