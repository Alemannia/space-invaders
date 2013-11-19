package lufti.invaders;

import java.awt.image.BufferedImage;
import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;
import lufti.ui.Canvas;

/**
 *
 * @author ubik
 */
public class Invader extends GameObject {

	private String type;
	private int animCounter;
	private final int ANIM_TICK = 10;
	private int tickCounter = 0;
	private boolean dead = false;

	public Invader(int x, int y, SpriteSheet sprites, String type) {
		super(x, y);
		w = sprites.getSpriteDimension(type, 0).width;
		h = sprites.getSpriteDimension(type, 0).height;
		this.type = type;
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
		tickCounter++;
		if (tickCounter > ANIM_TICK) {
			tickCounter = 0;
			animCounter++;
		}
	}
	
	@Override
	public boolean isAlive() {
		return !dead;
	}

	@Override
	public void render(Canvas.CanvasPainter pntr, SpriteSheet sprites) {
		animCounter = animCounter % sprites.getAnimationLength(type);
	
		BufferedImage spr = sprites.getSprite(type, animCounter);
		pntr.drawImage(spr, x, y);
	}

	public void kill() {
		dead = true;
	}
}
