package game.component;

import javax.swing.JOptionPane;

public class WinCondition {
    private int virusCount;
    private boolean isPaused;

    public WinCondition() {
        virusCount = 0;
        isPaused = false;
    }

    public void incrementVirusCount() {
        if (!isPaused) {
            virusCount++;
            if (virusCount >= 100) {
                displayWinMessage();
                pauseGame();
            }
        }
    }

    private void displayWinMessage() {
        JOptionPane.showMessageDialog(null, "CONGRATULATIONS HUNTER, YOU HAVE UNLOCKED ENDLESS MODE", "GAME MASTER", JOptionPane.INFORMATION_MESSAGE);
    }

    private void pauseGame() {
        isPaused = true;
        // Code to pause the game goes here
    }

    public void resumeGame() {
        isPaused = false;
        // Code to resume the game goes here
    }
}
