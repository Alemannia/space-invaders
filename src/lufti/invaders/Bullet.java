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

	public Bullet(int x, int y, int speed, SpriteSheet sprites, String type) {
		super(x, y);
		w = sprites.getSpriteDimension(type, 0).width;
		h = sprites.getSpriteDimension(type, 0).height;

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

		if (speed > 0) {
			Ship hitShip = null;
			for (GameObject object : collisions) {
				if (object instanceof Ship) {
					hitShip = (Ship) object;
				}
			}

			if (hitShip != null) {
				hitShip.kill();
				kill();
				game.spawnExplosion(hitShip.midX(), hitShip.midY(), 0xff00ff00);
			}
		} else {
			Invader hitInvader = null;
			for (GameObject object : collisions) {
				if (object instanceof Invader) {
					hitInvader = (Invader) object;
				}
			}

			if (hitInvader != null) {
				hitInvader.kill();
				kill();
				game.spawnExplosion(hitInvader.midX(), hitInvader.midY(), 0xffffffff);
				return;
			}

			Bullet hitBullet = null;
			for (GameObject object : collisions) {
				if (object instanceof Bullet && object != this) {
					hitBullet = (Bullet) object;
				}
			}

			if (hitBullet != null) {
				hitBullet.kill();
				kill();
				game.spawnExplosion(hitBullet.midX(), hitBullet.midY(), 0xffffffff);
				return;
			}
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
