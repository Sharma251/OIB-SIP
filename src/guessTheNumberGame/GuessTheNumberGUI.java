package guessTheNumberGame;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GuessTheNumberGUI extends JFrame implements ActionListener {
    private Random random = new Random();
    private int numberToGuess;
    private int attemptsLeft;
    private int round;
    private int totalScore;

    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private JLabel roundLabel;
    private JLabel scoreLabel;
    private JButton guessButton;

    public GuessTheNumberGUI() {
        setTitle("🎯 Guess the Number Game");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        roundLabel = new JLabel("Round 1 of 3", SwingConstants.CENTER);
        messageLabel = new JLabel("Guess a number between 1 and 100", SwingConstants.CENTER);
        attemptsLabel = new JLabel("Attempts left: 7", SwingConstants.CENTER);
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);

        guessField = new JTextField();
        guessButton = new JButton("Guess");

        add(roundLabel);
        add(messageLabel);
        add(guessField);
        add(guessButton);
        add(attemptsLabel);
        add(scoreLabel);

        guessButton.addActionListener(this);

        startNewGame();
        setVisible(true);
    }

    private void startNewGame() {
        totalScore = 0;
        round = 1;
        roundLabel.setText("Round " + round + " of 3");
        scoreLabel.setText("Score: " + totalScore);
        startNewRound();
    }

    private void startNewRound() {
        numberToGuess = random.nextInt(100) + 1;
        attemptsLeft = 7;
        messageLabel.setText("Guess a number between 1 and 100");
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        guessField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (attemptsLeft > 0) {
            try {
                int userGuess = Integer.parseInt(guessField.getText());
                attemptsLeft--;

                if (userGuess == numberToGuess) {
                    int points = (attemptsLeft + 1) * 10;
                    totalScore += points;
                    messageLabel.setText("🎉 Correct! You earned " + points + " points.");
                    scoreLabel.setText("Score: " + totalScore);

                    if (round < 3) {
                        round++;
                        roundLabel.setText("Round " + round + " of 3");
                        startNewRound();
                    } else {
                        endGame();
                    }
                } else if (userGuess < numberToGuess) {
                    messageLabel.setText("📉 Too low! Try again.");
                } else {
                    messageLabel.setText("📈 Too high! Try again.");
                }

                attemptsLabel.setText("Attempts left: " + attemptsLeft);

                if (attemptsLeft == 0 && userGuess != numberToGuess) {
                    messageLabel.setText("❌ Out of attempts! Number was: " + numberToGuess);

                    if (round < 3) {
                        round++;
                        roundLabel.setText("Round " + round + " of 3");
                        startNewRound();
                    } else {
                        endGame();
                    }
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("⚠️ Please enter a valid number!");
            }
        }
    }

    private void endGame() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "🏆 Game Over! Your final score: " + totalScore + "\nDo you want to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame(); // restart
        } else {
            System.exit(0); // exit
        }
    }

    public static void main(String[] args) {
        new GuessTheNumberGUI();
    }
}
