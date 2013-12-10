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
public class Bullet extends SpriteObject {

	private int speed;
	private boolean dead = false;
	private final boolean invaderBullet; // Denotes that this was fired by an invader

	public Bullet(int x, int y, int speed, SpriteSheet sprites, String type, boolean invaderBullet) {
		super(x, y, sprites, type);
		this.speed = speed;
		this.invaderBullet = invaderBullet;
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
		animate();
		
		// Move
		y += speed;
		if (y < game.getTopGameBorder() - 50 || y > game.getBottomGameBorder() + 50) {
			dead = true;
		}

		if (dead) {
			return;
		}

		// Find collisions
		ArrayList<SpriteObject> collisions = game.findCollisions(this);
		
		for (SpriteObject spriteObject : collisions) {
			if( spriteObject.handleHit(game, this) ){
				kill();
				break;
			}
		}

		/*
		if (speed > 0) {
			Ship hitShip = null;
			for (SpriteObject object : collisions) {
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
			for (SpriteObject object : collisions) {
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
			for (SpriteObject object : collisions) {
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
		}*/
	}
	
	public boolean isInvaderBullet() {
		return invaderBullet;
	}

	public void kill() {
		dead = true;
	}

	@Override
	public boolean isAlive() {
		return !dead;
	}

	@Override
	public boolean handleHit(InvaderGame game, Bullet other) {
		if( isInvaderBullet() != other.isInvaderBullet() ) {
			kill();
			game.spawnExplosion(other.midX(), other.midY(), 0xffffffff);
			return true;
		} else {
			return false; // No collision
		}
	}
}
