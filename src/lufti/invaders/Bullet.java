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
			if( isInvaderBullet() ) {
				game.spawnExplosion(this);
			} else {
				game.spawnExplosion(other);
			}
			return true;
		} else {
			return false; // No collision
		}
	}
}
