package ppPackage;

import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.graphics.GLine;
import acm.program.GraphicsProgram;

/**
 * The ppTable class manages the graphical representation of the game table.
 * It handles drawing the table and converting between world and screen coordinates.
 */
public class ppTable {

    GraphicsProgram GProgram;  // Reference to the GraphicsProgram
    Color ppSurface;
    GRect gPlane; // Ground line
    GRect field;  // Playing field

    /**
     * Constructor for ppTable.
     * Sets up the table display.
     * @param GProgram Reference to the GraphicsProgram.
     */
    public ppTable(GraphicsProgram GProgram) {
        this.GProgram = GProgram;
        ppSurface = new Color(255, 255, 204); // Very pale yellow
        newScreen(); // Generate display
    }

    /**
     * Converts world coordinates to screen coordinates.
     * @param P The point in world coordinates.
     * @return The corresponding point in screen coordinates.
     */
    public GPoint W2S(GPoint P) {
        return new GPoint((P.getX() - Xmin) * Xs + OFFSET / 2, ymax - (P.getY() - Ymin) * Ys);
    }

    /**
     * Converts screen coordinates to world coordinates.
     * @param P The point in screen coordinates.
     * @return The corresponding point in world coordinates.
     */
    public GPoint S2W(GPoint P) {
        double X = (P.getX() - OFFSET / 2) / Xs + Xmin;
        double Y = (ymax - P.getY()) / Ys + Ymin;
        return new GPoint(X, Y);
    }

    /**
     * Clears the playing field and redraws ground line and field.
     */
    public void newScreen() {
        // Remove existing ground line and field if they exist
        if (gPlane != null) {
            GProgram.remove(gPlane);
        }
        if (field != null) {
            GProgram.remove(field);
        }

        // Redraw ground line
        gPlane = new GRect(0, HEIGHT * Ys, WIDTH * Xs + OFFSET, 3);
        gPlane.setColor(Color.BLACK);
        gPlane.setFilled(true);
        GProgram.add(gPlane);

        // Redraw playing field background
        field = new GRect((XwallL - ppPaddleW) * Xs + OFFSET / 2, 0, 
                          (XwallR - XwallL + 2 * ppPaddleW) * Xs, HEIGHT * Ys);
        field.setFilled(true);
        field.setColor(ppSurface);
        GProgram.add(field);

        // Optional: Add dashed center line
        double centerX = (WIDTH * Xs + OFFSET) / 2;
        int dashLength = 10;
        int gapLength = 10;
        for (int y = 0; y < HEIGHT * Ys; y += dashLength + gapLength) {
            GLine dash = new GLine(centerX, y, centerX, y + dashLength);
            dash.setColor(Color.WHITE);
            // Cannot set stroke width in ACM's GLine, so skip or use multiple lines for thickness
            GProgram.add(dash);
        }
    }
}
