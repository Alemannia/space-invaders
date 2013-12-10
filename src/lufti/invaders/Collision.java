package lufti.invaders;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import lufti.game.AbstractGame;
import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;
import lufti.sprites.SpriteSheetFactory;
import lufti.ui.Canvas;
import lufti.ui.SimpleWindow;

/**
 *
 * @author ubik
 */
public class Collision {

	public static boolean pixelPerfect(BufferedImage sprA, int ax, int ay,
			BufferedImage sprB, int bx, int by) {
		Rectangle rectA = getRect(sprA, ax, ay);
		Rectangle rectB = getRect(sprB, bx, by);

		Rectangle inter = rectA.intersection(rectB);
		if (inter.isEmpty()) {
			return false;
		}

		// Test intersection
		for (int x = inter.x; x < inter.x + inter.width; x++) {
			for (int y = inter.y; y < inter.y + inter.height; y++) {
				int pixelA = sprA.getRGB(x - ax, y - ay);
				if (((pixelA >> 24) & 0xff) == 0) {
					continue;
				}

				int pixelB = sprB.getRGB(x - bx, y - by);
				if (((pixelB >> 24) & 0xff) == 0) {
					continue;
				}

				return true;
			}
		}

		return false;
	}

	/**
	 * Tests two bitmaps for pixel-wise intersection and returns a list
	 * of non-transparent pixels present in both. The returned coordinates 
	 * are in the coordinate system of the first image.
	 * @param sprA
	 * @param ax
	 * @param ay
	 * @param sprB
	 * @param bx
	 * @param by
	 * @return 
	 */
	public static ArrayList<Point> pixelPerfectAll(BufferedImage sprA, int ax, int ay,
			BufferedImage sprB, int bx, int by) {
		Rectangle rectA = getRect(sprA, ax, ay);
		Rectangle rectB = getRect(sprB, bx, by);

		Rectangle inter = rectA.intersection(rectB);
		ArrayList<Point> res = new ArrayList<Point>();
		
		if (inter.isEmpty()) {
			return res;
		}

		// Find collisions
		for (int x = inter.x; x < inter.x + inter.width; x++) {
			for (int y = inter.y; y < inter.y + inter.height; y++) {
				int pixelA = sprA.getRGB(x - ax, y - ay);
				if (((pixelA >> 24) & 0xff) == 0) {
					continue;
				}

				int pixelB = sprB.getRGB(x - bx, y - by);
				if (((pixelB >> 24) & 0xff) == 0) {
					continue;
				}

				res.add( new Point(x-ax, y-ay));
			}
		}

		return res;
	}

	private static Rectangle getRect(BufferedImage spr, int x, int y) {
		return new Rectangle(x, y, spr.getWidth(), spr.getHeight());
	}

	public static void main(String[] args) throws IOException {
		SimpleWindow window = SimpleWindow.create(600, 600, 60, Color.BLACK);
		AbstractGame game = new CollisionTest();

		AbstractGame.attach(game, window, 60);
	}

	private static class CollisionTest extends AbstractGame {

		private final SpriteSheet sprites;
		private BufferedImage spriteA;
		private BufferedImage spriteB;

		private int ax, ay, bx, by;

		public CollisionTest() throws IOException {
			this.sprites = SpriteSheetFactory.getClassic();
			spriteA = sprites.getSprite("InvaderA", 0);
			spriteB = sprites.getSprite("InvaderB", 0);
			ax = 300;
			ay = 300;
			bx = 200;
			by = 300;
		}

		@Override
		public void update(PlayerInput input) {
			if (input.containsCommand(PlayerInput.Command.LEFT)) {
				ax--;
			}
			if (input.containsCommand(PlayerInput.Command.RIGHT)) {
				ax++;
			}
			if (input.containsCommand(PlayerInput.Command.UP)) {
				ay--;
			}
			if (input.containsCommand(PlayerInput.Command.DOWN)) {
				ay++;
			}
		}

		@Override
		public void render(Canvas.CanvasPainter pntr) {
			pntr.drawImage(spriteA, ax, ay);
			pntr.drawImage(spriteB, bx, by);

			if (pixelPerfect(spriteA, ax, ay, spriteB, bx, by)) {
				pntr.drawImageBoundary(spriteA, ax, ay);
				pntr.drawImageBoundary(spriteB, bx, by);
			}

		}

	}
}
