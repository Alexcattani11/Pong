package ppPackage;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import acm.gui.IntField;
import acm.gui.TableLayout;
import static ppPackage.ppSimParams.*;

/**
 * The score board class is responsible for creating a panel
 * containing labels and scores for 2 opponents and methods
 * for incrementing and clearing scores.
 *
 */
public class ppScoreBoard {
    // Instance Variables
    JPanel myPanel;        // Panel for scoreboard
    JLabel TFleft;         // Labels for opponents
    JLabel TFright;
    IntField Scoreleft;    // Labels for scores
    IntField Scoreright;

    // Constructor - create a panel to hold names and scores. Use a modern font.
    public ppScoreBoard(String agentName, String playerName) {
        myPanel = new JPanel();
        TFleft = new JLabel(agentName);
        TFright = new JLabel(playerName);
        Font modernFont = new Font("Arial", Font.BOLD, 24);
        TFleft.setFont(modernFont);
        TFright.setFont(modernFont);
        Scoreleft = new IntField(0);
        Scoreright = new IntField(0);
        Scoreleft.setFont(modernFont);
        Scoreright.setFont(modernFont);
        myPanel.setLayout(new TableLayout(1,4));
        myPanel.add(TFleft,"fill=BOTH");
        myPanel.add(Scoreleft,"fill=BOTH");
        myPanel.add(TFright,"fill=BOTH");
        myPanel.add(Scoreright,"fill=BOTH");
    }

    // Increment score on left (Player)
    public void incLeft() {
        Scoreleft.setValue(Scoreleft.getValue() + 1);
    }

    // Increment score on right (Agent)
    public void incRight() {
        Scoreright.setValue(Scoreright.getValue() + 1);
    }

    // Clear both scores to 0
    public void clear() {
        Scoreleft.setValue(0);
        Scoreright.setValue(0);
    }

    // Return reference to scoreboard object
    public Component getBoard() {
        return myPanel;
    }
}
