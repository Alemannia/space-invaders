package lufti.invaders;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;
import lufti.ui.Canvas;

/**
 *
 * @author ubik
 */
public class Bullet extends GameObject {

	private int speed;
	private String type;
	private boolean dead = false;

	public Bullet(int x, int y, int speed, String type) {
		super(x, y);
		this.speed = speed;
		this.type = type;
	}

	@Override
	public void render(Canvas.CanvasPainter pntr, SpriteSheet sprites) {
		BufferedImage proj = sprites.getSprite(type, 0);
		pntr.drawImage(proj, x, y);
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
		y += speed;
		if (y < game.getTopGameBorder() - 50 || y > game.getBottomGameBorder() + 50) {
			dead = true;
		}

		if (dead) {
			return;
		}

		ArrayList<GameObject> collisions = game.findCollisions(x, y);

		Invader hitInvader = null;
		for (GameObject object : collisions) {
			if (object instanceof Invader) {
				hitInvader = (Invader) object;
			}
		}

		if (hitInvader != null) {
			hitInvader.kill();
			kill();
			game.spawnExplosion(hitInvader.midX(), hitInvader.midY());
		}
	}

	public void kill() {
		dead = true;
	}

	@Override
	public boolean isAlive() {
		return !dead;
	}
}
