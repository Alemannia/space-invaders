package lufti.invaders;

import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;

/**
 *
 * @author ubik
 */
public class Ship extends SpriteObject {

	private final int bulletSpeed = -8;
	private final int shipSpeed = 4;

	private final int reloadTime = 4;
	private int reloadCounter = 0;
	
	
	private boolean dead = false;

	public Ship(int x, int y, SpriteSheet sprites) {
		super(x, y, sprites, "Ship");
		this.y -= h; // Initial coords are for the bottom of the ship
	}
	
	@Override
	public boolean isAlive() {
		return !dead;
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
		animate();
		
		if (reloadCounter > 0) {
			reloadCounter--;
		}

		if (input.containsCommand(PlayerInput.Command.RIGHT)) {
			x += shipSpeed;
			if (x > game.getRightGameBorder() - w) {
				x = game.getRightGameBorder() - w;
			}
		}
		if (input.containsCommand(PlayerInput.Command.LEFT)) {
			x -= shipSpeed;
			if (x < game.getLeftGameBorder()) {
				x = game.getLeftGameBorder();
			}
		}

		int projHeight = 16; // ;sprites.getSpriteDimension("ProjectileA", 0).height;
		if (input.containsCommand(PlayerInput.Command.SHOOT) && reloadCounter <= 0) {
			game.createBullet(midX()-2, getTopSide()+8, bulletSpeed, "ProjectileA", false);
			reloadCounter = reloadTime;
		}
	}

	public void kill() {
		// dead = true;
	}

	@Override
	public boolean handleHit(InvaderGame game, Bullet bullet) {
		if( !bullet.isInvaderBullet() ) {
			return false; // Not a collision
		}
		
		game.spawnExplosion(midX(), midY(), 0xff00ff00);
		kill();
		return true;
	}
}
