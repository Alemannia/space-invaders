package lufti.invaders;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;

/**
 *
 * @author ubik
 */
public class Bunker extends SpriteObject {

	private BufferedImage bunkerSprite;

	public Bunker(int x, int y, SpriteSheet sprites) {
		super(x, y, sprites, "Bunker");
		this.y -= h; // Initial position denotes sprite bottom

		// Copy sprite
		BufferedImage template = sprites.getSprite("Bunker", 0);
		bunkerSprite = new BufferedImage(template.getWidth(), template.getHeight(), template.getType());
		bunkerSprite.getGraphics().drawImage(template, 0, 0, null);
	}

	@Override
	public BufferedImage getSprite() {
		return bunkerSprite;
	}

	@Override
	public boolean isAlive() {
		return true; // TODO: check if pixels are left
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
	}

	@Override
	public boolean handleHit(InvaderGame game, Bullet bullet) {
		// Make collision points transparent
		int w = bunkerSprite.getWidth();
		int h = bunkerSprite.getHeight();

		ArrayList<Point> collisions = Collision.pixelPerfectAll(bunkerSprite, x, y, bullet.getSprite(), bullet.x, bullet.y);
		for (Point point : makeFuzzy(collisions)) {
			for (int x = point.x; x < point.x + 4; x++) {
				for (int y = point.y; y < point.y + 4; y++) {
					if (x >= 0 && x < w && y >= 0 && y < h) {
						bunkerSprite.setRGB(x, y, 0x00000000);
					}
				}
			}
		}
		return true;
	}

	private ArrayList<Point> makeFuzzy(ArrayList<Point> points) {
		int numPoints = 1;
		int radius = 8;
		Random rnd = new Random();

		ArrayList<Point> res = new ArrayList<Point>();
		for (Point point : points) {
			res.add(point);
			for (int i = 0; i < numPoints; i++) {
				double angle = rnd.nextDouble() * 2 * Math.PI;
				double dist = rnd.nextDouble() * radius;
				int x = (int) (point.x + Math.cos(angle) * dist);
				int y = (int) (point.y + Math.sin(angle) * dist);

				// Clip to 4-grid
				x = x / 4 * 4;
				y = y / 4 * 4;

				// Add meta-pixel
				res.add(new Point(x, y));
			}
		}
		return res;
	}
}
