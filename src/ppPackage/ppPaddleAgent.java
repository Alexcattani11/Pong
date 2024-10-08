package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

/**
 * The ppPaddleAgent class represents the paddle controlled by the computer (agent).
 * It tracks the ball and adjusts its position to intercept the ball.
 */
public class ppPaddleAgent extends ppPaddle {

    // Instance variables
    private ppBall myBall;  // Reference to the ball
    private int ballSkip = 0; // Counter to introduce a lag for the agent's response

    /**
     * Constructor for the ppPaddleAgent class.
     * @param X The initial X position of the agent paddle.
     * @param Y The initial Y position of the agent paddle.
     * @param myColor The color of the agent paddle.
     * @param myTable Reference to the game table.
     * @param GProgram Reference to the GraphicsProgram for display.
     */
    public ppPaddleAgent(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
        super(X, Y, myColor, myTable, GProgram);
    }

    /**
     * The run method tracks the ball and adjusts the paddle's position accordingly.
     * It introduces a time lag to make the game fairer for the player.
     */
    public void run() {
        while (true) {
            // Move the paddle to intercept the ball
            Paddle2Intercept();

            GProgram.pause((int)(TICK * 1000));  // Pause for a time step (e.g., 5 ms)
        }
    }

    /**
     * Adjusts the paddle's position to intercept the ball.
     */
    public void Paddle2Intercept() {
        if (ballSkip++ >= AgentLag) {  // Delay based on the agent's lag
            double targetY = myBall.getP().getY();
            setP(new GPoint(getP().getX(), targetY));
            ballSkip = 0;
        }
    }

    /**
     * Attaches a reference to the ball, allowing the agent to track it.
     * @param myBall The ball to be tracked by the agent paddle.
     */
    public void attachBall(ppBall myBall) {
        this.myBall = myBall;
    }
}
