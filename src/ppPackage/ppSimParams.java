package ppPackage;

/**
 * The ppSimParams class holds all the simulation parameters as public static variables.
 * These parameters are used across various classes in the Pong game.
 */
public class ppSimParams {

    // Toggle button
    public static javax.swing.JToggleButton traceButton;

    // 1. Parameters defined in screen coordinates (pixels, ACM coordinates)
    public static final int WIDTH = 1280;             // Screen width in pixels
    public static final int HEIGHT = 600;             // Screen height in pixels
    public static final int OFFSET = 80;              // Additional width for UI components

    public static final double ppTableXlen = 2.74;     // Length in meters
    public static final double ppTableHgt = 1.52;      // Height in meters (ceiling)
    public static final double XwallL = 0.05;          // Left wall position in meters
    public static final double XwallR = 2.69;          // Right wall position in meters
    public static final int AgentLag = 50;             // Agent paddle lag in ticks

    // 3. Parameters defined in simulation coordinates (MKS, X-->range, Y-->height)
    public static final double g = 9.8;                // Gravitational acceleration (unused)
    public static final double k = 0.1316;             // Vt constant (unused)
    public static final double Pi = 3.1416;            // Pi (unused)
    public static final double bSize = 0.02;           // Ball radius in meters
    public static final double bMass = 0.0027;         // Ball mass in kg (unused)
    public static final double TICK = 0.005;           // Clock tick duration (sec) - reduced from 0.01
    public static final double SLEEP = 10;             // Unused
    public static final double ETHR = 0.001;           // Minimum ball energy (unused)

    public static final double Xmin = 0.0;             // Minimum value of X (pp table)
    public static final double Xmax = ppTableXlen;     // Maximum value of X
    public static final double Ymin = 0.0;             // Minimum value of Y
    public static final double Ymax = ppTableHgt;      // Maximum value of Y

    public static final int xmin = 0;                  // Minimum value of x (screen)
    public static final int xmax = WIDTH;              // Maximum value of x (screen)
    public static final int ymin = 0;                  // Minimum value of y (screen)
    public static final int ymax = HEIGHT;             // Maximum value of y (screen)

    public static final double Xs = (WIDTH + OFFSET) / (Xmax - Xmin);  // Scale factor X (pixels/meter)
    public static final double Ys = (double) HEIGHT / (Ymax - Ymin);   // Scale factor Y (pixels/meter)

    public static final double Xinit = XwallL;                         // Initial ball location (X) in meters
    public static final double Yinit = Ymax / 2;                       // Initial ball location (Y) in meters

    public static final double PD = 1;                                  // Trace point diameter (unused)

    // 4. Miscellaneous
    public static final boolean TEST = false;
    public static boolean falling = true;
    public static boolean A1 = false;
    public static final double TSCALE = 1;                         // Adjusted TSCALE to 1

    // 4. Paddle Parameters
    static final double ppPaddleH = 12 * 2.54 / 100;                  // Paddle height in meters (12 inches)
    static final double ppPaddleW = 0.75 * 2.54 / 100;                // Paddle width in meters (0.75 inches)

    static final double ppPaddleXinit = XwallR - ppPaddleW / 2;      // Initial Paddle X in meters
    static final double ppPaddleYinit = Yinit;                      // Initial Paddle Y in meters
    static final double ppPaddleXgain = 2.0;                         // Unused
    static final double ppPaddleYgain = 2.0;                         // Unused

    static final double LPaddleXinit = XwallL - ppPaddleW / 2;       // Initial Agent Paddle X in meters
    static final double LPaddleYinit = Yinit;                        // Initial Agent Paddle Y in meters
    static final double LPaddleXgain = 2.0;                           // Unused
    static final double LPaddleYgain = 2.0;                           // Unused
    static final double VoxMAX = 10.0;                                // Unused

    // 5. Parameters used by the ppSim class
    static final double YinitMAX = 0.75 * Ymax;                       // Maximum initial Y in meters
    static final double YinitMIN = 0.25 * Ymax;                       // Minimum initial Y in meters
    static final double EMIN = 1.0;                                   // Minimum energy loss coefficient
    static final double EMAX = 1.0;                                   // Maximum energy loss coefficient
    static final double VoMIN = 3.0;                                  // Minimum initial velocity (m/s) - reduced from 5.0
    static final double VoMAX = 15.0;                                 // Maximum initial velocity (m/s)
    static final double ThetaMIN = 0.0;                               // Minimum launch angle (degrees)
    static final double ThetaMAX = 20.0;                              // Maximum launch angle (degrees)
    static final long RSEED = 8976232;                                // Random seed (unused)

    // 6. Miscellaneous
    public static final boolean DEBUG = false; 
    public static final boolean MESG = true; 
    public static final int STARTDELAY = 1000;

    // Ball velocity cap
    public static final double MAX_VELOCITY = 20.0; // Maximum velocity in world units per second
}
