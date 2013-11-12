package lufti.invaders;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import lufti.game.AbstractGame;
import lufti.game.PlayerInput;
import lufti.game.PlayerInput.Command;
import lufti.sprites.SpriteSheet;
import lufti.sprites.SpriteSheetFactory;
import lufti.ui.Canvas;

/**
 * The invader game.
 *
 * @author ubik
 */
public class InvaderGame extends AbstractGame {

	private SpriteSheet sprites;
	private Ship ship;
	private ArrayList<Bullet> bullets = new ArrayList<>();

	private int gameHeight, gameWidth;

	public InvaderGame() throws IOException {
		sprites = SpriteSheetFactory.getClassic();
		ship = new Ship(400, 500);
		gameHeight = 600;
		gameWidth = 800;
	}

	public int getRightGameBorder() {
		return gameWidth - 100;
	}

	public int getLeftGameBorder() {
		return 100;
	}

	public int getTopGameBorder() {
		return 0;
	}

	public int getBottomGameBorder() {
		return gameHeight;
	}

	@Override
	public void update(PlayerInput input) {
		ship.update(input, this);
		
		

		ArrayList<Bullet> keep = new ArrayList<>();
		for (Bullet bullet : bullets) {
			bullet.y -= 8;
			if (bullet.y > getTopGameBorder()-50) {
				keep.add(bullet);
			}
		}
		
		bullets = keep;
	}

	@Override
	public void render(Canvas.CanvasPainter pntr) {
		ship.render(pntr, sprites);
		
		for (Bullet bullet : bullets) {
			bullet.render(pntr, sprites);
		}
	}

	public void createBullet(int x, int y) {
		bullets.add(new Bullet(x,y));
	}

}
