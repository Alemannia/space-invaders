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

	public SpriteObject(int x, int y, SpriteSheet sprites, String type) {
		super(x, y);
		this.type = type;
		w = sprites.getSpriteDimension(type, 0).width;
		h = sprites.getSpriteDimension(type, 0).height;
		animLength = sprites.getAnimationLength(type);
		tickCounter = 0;
	}

	@Override
	public void render(Canvas.CanvasPainter pntr, SpriteSheet sprites) {
		if (isAlive()) {
			BufferedImage sprite = sprites.getSprite(type, 0);
			pntr.drawImage(sprite, x, y);
		}
	}

	public void animate() {
		tickCounter++;
		if (tickCounter > Config.ANIMATION_TICK) {
			tickCounter = 0;
			animCounter = (animCounter + 1) % animLength;
		}
	}
}
