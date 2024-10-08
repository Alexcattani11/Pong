Pong Game in Java
Welcome to the Pong Game, a classic table tennis simulation developed in Java using the ACM Graphics Library. This guide provides quick instructions on setting up, compiling, and running the game. This was developed during a university course.

📋 Prerequisites
Java Development Kit (JDK)
Version: Java SE 8 or higher
Download: Oracle JDK or OpenJDK
ACM Java Library
Library File: acm.jar
Download: ACM Java Libraries

Download acm.jar:

Place acm.jar inside a lib/ directory in your project folder.

Compilation Command:

For macOS/Linux:
javac -cp "lib/acm.jar" -d bin src/ppPackage/*.java

For Windows:
javac -cp "lib\acm.jar" -d bin src\ppPackage\*.java

Run Command:

For macOS/Linux:
java -cp "bin:lib/acm.jar" ppPackage.ppSim

For Windows:
java -cp "bin;lib\acm.jar" ppPackage.ppSim

🎮 Game Controls
Player Paddle (Right Paddle):

Move: Control vertically using the mouse.
Agent Paddle (Left Paddle):

Automatic: Controlled by the computer.
Buttons:

New Serve: Resets the game.
Quit: Exits the game.
🛠 Troubleshooting
Compilation Errors:

Ensure all variables in ppSimParams.java are defined before use.
Verify correct static imports in all classes:
java
Copy code
import static ppPackage.ppSimParams.*;
Agent Paddle Not Moving:

Ensure paddle threads are properly managed and not duplicated.
Confirm old balls are removed using the kill() method:
java
Copy code
public void kill() {
    running = false;
    this.interrupt();
    GProgram.remove(myBall);
}
Graphical Issues:

Check scaling factors (Xs, Ys) and window size configurations.

Enjoy your game! 🏓🎉
