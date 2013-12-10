package lufti.invaders;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;
import lufti.ui.Canvas;

/**
 *
 * @author ubik
 */
public class Ship extends SpriteObject {

	private final int bulletSpeed = -8;
	private final int shipSpeed = 4;
	private final int shipWidth = 52;

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
			game.createBullet(midX()-2, getTopSide()+8, bulletSpeed, "ProjectileA");
			reloadCounter = reloadTime;
		}
	}

	@Override
	public void render(Canvas.CanvasPainter pntr, SpriteSheet sprites) {
		if( dead ) {
			return;
		}
		
		BufferedImage shipSprite = sprites.getSprite("Ship", 0);
		Dimension shipDim = sprites.getSpriteDimension("Ship", 0);
		pntr.drawImage(shipSprite, x, y);

	}

	public void kill() {
		// dead = true;
	}
}
