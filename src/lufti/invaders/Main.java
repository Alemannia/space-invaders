package lufti.invaders;

import java.awt.Color;
import java.io.IOException;
import lufti.game.AbstractGame;
import lufti.ui.SimpleWindow;

/**
 * 
 * @author ubik
 */
public class Main {
	public static void main(String[] args) throws IOException {
		SimpleWindow window = SimpleWindow.create(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, 60, Color.BLACK);
		InvaderGame game = new InvaderGame();
		
		AbstractGame.attach(game, window, 60);
	}
}
	