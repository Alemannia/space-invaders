package lufti.invaders;

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
	private int x, y;
	private ArrayList<Point> bullets = new ArrayList<>();

	public InvaderGame() throws IOException {
		sprites = SpriteSheetFactory.getClassic();
		x = 50;
		y = 500;
	}

	@Override
	public void update(PlayerInput input) {
		if (input.containsCommand(Command.RIGHT)) {
			x += 8;
			if (x > 750) {
				x = 750;
			}
		}
		if (input.containsCommand(Command.LEFT)) {
			x -= 8;
			if (x < 50) {
				x = 50;
			}
		}

		int projHeight = sprites.getSpriteDimension("ProjectileA", 0).height;
		if (input.containsCommand(Command.SHOOT)) {
			bullets.add(new Point(x - 2, y - projHeight));
		}

		ArrayList<Point> keep = new ArrayList<>();
		for (Point point : bullets) {
			point.y -= 8;
			if (point.y > -50) {
				keep.add(point);
			}
		}
		bullets = keep;
	}

	@Override
	public void render(Canvas.CanvasPainter pntr) {
		BufferedImage ship = sprites.getSprite("Ship", 0);
		int width = sprites.getSpriteDimension("Ship", 0).width;
		pntr.drawImage(ship, x - width / 2, y);

		BufferedImage proj = sprites.getSprite("ProjectileA", 0);
		for (Point point : bullets) {
			pntr.drawImage(proj, point.x, point.y);
		}
	}

}
