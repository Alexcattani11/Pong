package ppPackage;

import java.awt.Color;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import static ppPackage.ppSimParams.*;

/**
 * The ppBall class simulates a ping pong ball in the game.
 * It handles movement, collision detection with the paddles,
 * and interaction with the game table.
 */
public class ppBall extends Thread {

    // Instance variables
    private double Xinit;     // Initial X position
    private double Yinit;     // Initial Y position
    private double Vo;        // Initial velocity
    private double theta;     // Launch angle (degrees)
    private double loss;      // Energy loss coefficient
    private Color color;      // Ball color
    private GraphicsProgram GProgram;  // Reference to GraphicsProgram
    private GOval myBall;     // Graphical representation of the ball
    private ppTable myTable;  // Reference to the game table
    private ppPaddle RPaddle; // Right paddle
    private ppPaddleAgent LPaddle; // Left paddle
    private boolean running;  // Control flag

    // Position and velocity
    double X, Y;           // Current position
    double Vx, Vy;         // Velocity components
    double ScrX, ScrY;     // Screen coordinates

    // Velocity increment constants
    private static final double VELOCITY_INCREMENT = 0.5; // 0.5% per collision

    /**
     * Constructor for ppBall.
     */
    public ppBall(double Xinit, double Yinit, double Vo, double theta, double loss, 
                  Color color, GraphicsProgram GProgram, ppTable myTable) {
        this.Xinit = Xinit;
        this.Yinit = Yinit;
        this.Vo = Vo;
        this.theta = theta;
        this.loss = loss;
        this.color = color;
        this.GProgram = GProgram;
        this.myTable = myTable;

        // Initialize position
        this.X = Xinit;
        this.Y = Yinit;

        // Convert world to screen coordinates
        GPoint p = myTable.W2S(new GPoint(Xinit, Yinit));
        ScrX = p.getX();
        ScrY = p.getY();

        // Create and add the ball to the screen
        myBall = new GOval(ScrX - bSize * Xs, ScrY - bSize * Ys, 2 * bSize * Xs, 2 * bSize * Ys);
        myBall.setColor(this.color);
        myBall.setFilled(true);
        GProgram.add(myBall);

        // Pause before starting
        GProgram.pause(1000);
    }

    /**
     * The run method contains the main simulation loop for the ball.
     */
    public void run() {
        running = true;

        // Initialize velocity components
        Vx = Vo * Math.cos(Math.toRadians(theta));
        Vy = Vo * Math.sin(Math.toRadians(theta));

        // Simulation loop
        while (running) {
            // Store previous position
            double prevX = X;
            double prevY = Y;

            // Update position
            X += Vx * TICK;
            Y += Vy * TICK;

            // Handle collision with walls
            if (Y >= Ymax - bSize || Y <= Ymin + bSize) {
                Vy = -Vy * loss; // Invert Vy
                Y = Math.max(Ymin + bSize, Math.min(Y, Ymax - bSize)); // Adjust position
                System.out.println("Collision with wall at Y = " + Y);
            }

            // Handle collision with paddles
            boolean collidedWithRPaddle = RPaddle.contact(X, Y);
            boolean collidedWithLPaddle = LPaddle.contact(X, Y);

            if (collidedWithRPaddle || collidedWithLPaddle) {
                Vx = -Vx * loss; // Invert Vx and apply energy loss

                // Calculate impact position to adjust Vy based on where the ball hits the paddle
                double impactY;
                if (collidedWithRPaddle) {
                    impactY = Y - RPaddle.getP().getY();
                } else {
                    impactY = Y - LPaddle.getP().getY();
                }

                // Normalize impactY based on paddle height
                double normalizedImpact = impactY / (ppPaddleH);

                // Adjust Vy to make the bounce angle more dynamic
                Vy += normalizedImpact * 1.0; // Adjust the factor as needed

                // Increment velocity for speed progression
                Vx *= (1 + VELOCITY_INCREMENT / 100);
                Vy *= (1 + VELOCITY_INCREMENT / 100);

                // Cap the velocity
                Vx = Math.signum(Vx) * Math.min(Math.abs(Vx), MAX_VELOCITY);
                Vy = Math.signum(Vy) * Math.min(Math.abs(Vy), MAX_VELOCITY);

                // Adjust position to prevent multiple collisions
                if (collidedWithRPaddle) {
                    X = RPaddle.getP().getX() - ppPaddleW - bSize;
                    System.out.println("Collision with right paddle. New Vx: " + Vx);
                }
                if (collidedWithLPaddle) {
                    X = LPaddle.getP().getX() + ppPaddleW + bSize;
                    System.out.println("Collision with left paddle. New Vx: " + Vx);
                }
            }

            // Update ball position on the screen
            updateBallPosition();

            // Pause for the tick duration
            GProgram.pause((int)(TICK * 1000));
        }
    }

    /**
     * Updates the ball's graphical position.
     */
    private void updateBallPosition() {
        GPoint p = myTable.W2S(new GPoint(X, Y));
        myBall.setLocation(p.getX() - bSize * Xs, p.getY() - bSize * Ys);
    }

    /**
     * Sets the right paddle.
     */
    public void setRightPaddle(ppPaddle RPaddle) {
        this.RPaddle = RPaddle;
    }

    /**
     * Sets the left paddle.
     */
    public void setLeftPaddle(ppPaddle LPaddle) {
        this.LPaddle = (ppPaddleAgent) LPaddle;
    }

    /**
     * Gets the ball's current position.
     */
    public synchronized GPoint getP() {
        return new GPoint(X, Y);
    }

    /**
     * Terminates the ball's thread and removes it from the screen.
     */
    public void kill() {
        running = false;
        this.interrupt();
        GProgram.remove(myBall); // Remove ball from screen
    }
}
