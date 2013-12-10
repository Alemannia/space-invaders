package lufti.invaders;

import java.awt.image.BufferedImage;
import lufti.sprites.SpriteSheet;
import lufti.ui.Canvas;

/**
 * A superclass for game objects that are represented by a sprite.
 * @author ubik
 */
public abstract class SpriteObject extends GameObject {

	private int animCounter;
	private int tickCounter;

	private final int animLength;
	private final String type;
	private final SpriteSheet spriteSheet;

	public SpriteObject(int x, int y, SpriteSheet sprites, String type) {
		super(x, y);
		this.type = type;
		w = sprites.getSpriteDimension(type, 0).width;
		h = sprites.getSpriteDimension(type, 0).height;
		animLength = sprites.getAnimationLength(type);
		spriteSheet = sprites;
		tickCounter = 0;
	}

	public BufferedImage getSprite() {
		return spriteSheet.getSprite(type, animCounter);
	}
	
	@Override
	public void render(Canvas.CanvasPainter pntr, SpriteSheet sprites) {
		if (isAlive()) {
			pntr.drawImage(getSprite(), x, y);
		}
	}

	public void animate() {
		tickCounter++;
		if (tickCounter > Config.ANIMATION_TICK) {
			tickCounter = 0;
			animCounter = (animCounter + 1) % animLength;
		}
	}

	/**
	 * Handles being hit by a bullet.
	 * @param game the main game (to handle fx etc.)
	 * @param bullet the bullet which technically collides
	 * @return true if the collision was handled, false otherwise (i.e. no "real" collision)
	 */
	public abstract boolean handleHit(InvaderGame game, Bullet bullet);
}
