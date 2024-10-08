package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

/**
 * The ppPaddle class represents a paddle in the pong game.
 * It handles paddle movement and collision detection with the ball.
 */
public class ppPaddle extends Thread {

    // Paddle's position and velocity variables
    protected double X, Y;        // Paddle position (X, Y)
    protected double Vx, Vy;      // Paddle velocity components (X, Y)
    protected double lastX, lastY; // Previous position (for velocity calculation)
    protected GRect myPaddle;     // Graphical representation of the paddle
    protected GraphicsProgram GProgram; // Graphics program (for display)
    protected ppTable myTable;    // Reference to the game table
    protected boolean running = true; // Control flag

    /**
     * Constructor for the ppPaddle class.
     * @param X The initial X position of the paddle.
     * @param Y The initial Y position of the paddle.
     * @param myColor The color of the paddle.
     * @param myTable Reference to the game table.
     * @param GProgram Reference to the GraphicsProgram for display.
     */
    public ppPaddle(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
        this.X = X;
        this.Y = Y;
        this.GProgram = GProgram;
        this.myTable = myTable;
        lastX = X;
        lastY = Y;

        // Create the paddle's graphical representation
        GPoint p = myTable.W2S(new GPoint(X - ppPaddleW / 2, Y + ppPaddleH / 2));
        double ScrX = p.getX();
        double ScrY = p.getY();
        myPaddle = new GRect(ScrX, ScrY, ppPaddleW * Xs, ppPaddleH * Ys);
        myPaddle.setFilled(true);
        myPaddle.setColor(myColor);
        GProgram.add(myPaddle);
    }

    /**
     * The run method continuously updates the paddle's velocity.
     */
    public void run() {
        while (running) {
            try {
                // Calculate velocity based on position change
                Vx = (X - lastX) / TICK;
                Vy = (Y - lastY) / TICK;
                lastX = X;
                lastY = Y;
                GProgram.pause((int)(TICK * 1000));  // Pause for a time step (e.g., 5 ms)
            } catch (Exception e) {
                break;
            }
        }
    }

    /**
     * Determines whether the ball is in contact with the paddle.
     * @param Sx The X-coordinate of the ball's contact surface.
     * @param Sy The Y-coordinate of the ball's contact surface.
     * @return true if the ball is in contact with the paddle, false otherwise.
     */
    public boolean contact(double Sx, double Sy) {
        // Define a horizontal threshold for collision (e.g., within paddle width + ball size)
        double horizontalThreshold = ppPaddleW / 2 + bSize;

        // Check if the ball's X is near the paddle's X and Y is within vertical range
        return (Math.abs(Sx - this.X) <= horizontalThreshold) &&
               (Sy >= (this.Y - ppPaddleH / 2) && Sy <= (this.Y + ppPaddleH / 2));
    }    

    /**
     * Returns the paddle's velocity as a GPoint object.
     * @return GPoint representing the paddle's velocity.
     */
    public GPoint getV() {
        return new GPoint(Vx, Vy);
    }

    /**
     * Sets the new position of the paddle.
     * @param P The new position of the paddle.
     */
    public synchronized void setP(GPoint P) {
        double newY = P.getY();

        // Clamp the Y position within the table boundaries
        if (newY < Ymin + ppPaddleH / 2) {
            newY = Ymin + ppPaddleH / 2;
        } else if (newY > Ymax - ppPaddleH / 2) {
            newY = Ymax - ppPaddleH / 2;
        }

        this.X = P.getX();
        this.Y = newY;

        GPoint p = myTable.W2S(new GPoint(this.X - ppPaddleW / 2, this.Y + ppPaddleH / 2));
        myPaddle.setLocation(p.getX(), p.getY());
    }      

    /**
     * Returns the current position of the paddle.
     * @return GPoint representing the paddle's current position.
     */
    public GPoint getP() {
        return new GPoint(X, Y);
    }

    /**
     * Returns the sign of the paddle's vertical velocity (Vy).
     * @return -1.0 if moving upwards, 1.0 if moving downwards.
     */
    public double getSgnVy() {
        return (Vy < 0) ? -1.0 : 1.0;
    }

    /**
     * Terminates the paddle's thread and removes it from the screen.
     */
    public void kill() {
        running = false;
        this.interrupt();
        GProgram.remove(myPaddle); // Remove paddle from screen
    }
}
