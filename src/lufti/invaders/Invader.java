package lufti.invaders;

import java.awt.image.BufferedImage;
import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;
import lufti.ui.Canvas;

/**
 *
 * @author ubik
 */
public class Invader extends SpriteObject {

	private double shootProb = 0.001;
	private boolean dead = false;
	private boolean canShoot = true;

	public Invader(int x, int y, SpriteSheet sprites, String type) {
		super(x, y, sprites, type);
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
		animate();
		
		if(canShoot && Math.random() < shootProb) {
			game.createBullet(midX(), getBottomSide()-16, 4, "ProjectileB", true);
		}
	}
	
	@Override
	public boolean isAlive() {
		return !dead;
	}
	
	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}
	
	public void kill() {
		dead = true;
	}

	@Override
	public boolean handleHit(InvaderGame game, Bullet bullet) {
		if( bullet.isInvaderBullet() ) {
			return false;
		}
		
		game.spawnExplosion(bullet.midX(), bullet.midY(), 0xffffffff);
		kill();
		return true;
	}
}
