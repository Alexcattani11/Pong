package ppPackage;

import java.awt.Color;
import java.awt.Font; // Added import for Font
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import java.util.Random;
import static ppPackage.ppSimParams.*;

/**
 * The ppSim class is the main class that sets up and controls the pong game.
 * It manages the ball, paddles, and game flow, including user interactions.
 */
public class ppSim extends GraphicsProgram {

    ppBall myBall;          // Current game ball
    ppPaddle RPaddle;       // Right paddle (controlled by player)
    ppPaddleAgent LPaddle;  // Left paddle (controlled by the agent)
    ppTable myTable;        // The game table

    private Random rgen = new Random();

    // Scoreboard
    private ppScoreBoard scoreBoard;

    /**
     * Initializes the game, setting up paddles, the ball, and UI elements.
     */
    public void init() {
        // Set window title
        setTitle("Pong Game");

        // Calculate total window size
        int totalWidth = (int)((Xmax - Xmin) * Xs) + OFFSET;
        int totalHeight = (int)(Ymax * Ys) + 150; // Additional height for buttons

        // Set the window size
        setSize(totalWidth, totalHeight);

        // Set up the game table
        myTable = new ppTable(this);

        // Create paddles and ball
        RPaddle = new ppPaddle(ppPaddleXinit, ppPaddleYinit, Color.GREEN, myTable, this);
        LPaddle = new ppPaddleAgent(LPaddleXinit, ppPaddleYinit, Color.BLUE, myTable, this);
        myBall = newBall();

        // Set paddles in the ball
        myBall.setRightPaddle(RPaddle);
        myBall.setLeftPaddle(LPaddle);

        // Attach the ball to the agent paddle for tracking
        LPaddle.attachBall(myBall);

        // Start the paddles' threads
        RPaddle.start();
        LPaddle.start();

        // Start the ball's thread
        myBall.start();

        // Initialize and add the scoreboard
        scoreBoard = new ppScoreBoard("Player", "Agent");
        add(scoreBoard.getBoard(), NORTH); // Position at the top

        // Add control buttons
        JButton newServeButton = new JButton("New Serve");
        JButton quitButton = new JButton("Quit");
        add(newServeButton, SOUTH);
        add(quitButton, SOUTH);

        // Set font for buttons
        Font buttonFont = new Font("Verdana", Font.BOLD, 16);
        newServeButton.setFont(buttonFont);
        quitButton.setFont(buttonFont);

        // Add action listeners for buttons
        addActionListeners();

        // Add mouse listeners for player paddle control
        addMouseListeners();
    }

    /**
     * Creates and returns a new ball for the game.
     * @return A new ppBall instance.
     */
    private ppBall newBall() {
        double Xinit = XwallL + bSize;  // Ball starting position (X)
        double Yinit = YinitMIN + rgen.nextDouble() * (YinitMAX - YinitMIN);  // Random Y position
        double Vo = VoMIN;  // Fixed initial velocity
        // Random launch angle based on Y position
        double theta = Yinit > YinitMIN + (YinitMAX - YinitMIN)/2 ? ThetaMIN : ThetaMAX;
        return new ppBall(Xinit, Yinit, Vo, theta, EMIN, Color.RED, this, myTable);
    }

    /**
     * Handles mouse movement events to move the player's paddle.
     */
    public void mouseMoved(MouseEvent e) {
        if (myTable == null || RPaddle == null) return;

        GPoint Pm = myTable.S2W(new GPoint(e.getX(), e.getY()));
        RPaddle.setP(new GPoint(RPaddle.getP().getX(), Pm.getY()));  // Update paddle position
    }

    /**
     * Handles button click events (e.g., New Serve, Quit).
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println("Button clicked: " + command); // Debug statement
        try {
            if (command.equals("New Serve")) {
                System.out.println("Starting a new game..."); // Debug statement
                newGame();
            } else if (command.equals("Quit")) {
                System.out.println("Quitting the game."); // Debug statement
                System.exit(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Print the stack trace for debugging
        }
    }

    /**
     * Starts a new game by resetting the ball and paddles.
     */
    private void newGame() {
        // Stop the current ball if it's running
        if (myBall != null) {
            myBall.kill(); // Sets the running flag to false and removes the ball from the screen
        }

        // Reset paddle positions
        RPaddle.setP(new GPoint(ppPaddleXinit, ppPaddleYinit));
        LPaddle.setP(new GPoint(LPaddleXinit, ppPaddleYinit));

        // Create and start a new ball
        myBall = newBall();
        myBall.setRightPaddle(RPaddle);
        myBall.setLeftPaddle(LPaddle);
        LPaddle.attachBall(myBall);
        myBall.start();
    }

    /**
     * Overriding the run method to handle score checking.
     */
    public void run() {
        while (true) {
            checkScore();
            pause(100); // Check every 100 ms
        }
    }

    /**
     * Checks if a player has scored by determining if the ball has passed the paddles.
     */
    public void checkScore() {
        GPoint ballPosition = myBall.getP();
        if (ballPosition.getX() <= Xmin) {
            // Agent scores
            scoreBoard.incRight();
            System.out.println("Agent scores! Total Agent Score: " + scoreBoard.Scoreright.getValue());
            newGame(); // Reset the game
        } else if (ballPosition.getX() >= Xmax) {
            // Player scores
            scoreBoard.incLeft();
            System.out.println("Player scores! Total Player Score: " + scoreBoard.Scoreleft.getValue());
            newGame(); // Reset the game
        }
    }
}
