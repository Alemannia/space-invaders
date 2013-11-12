package lufti.invaders;

import java.awt.image.BufferedImage;
import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;
import lufti.ui.Canvas;

/**
 *
 * @author ubik
 */
public class Bullet extends GameObject {

	public Bullet(int x, int y) {
		super(x, y);
	}

	@Override
	public void render(Canvas.CanvasPainter pntr, SpriteSheet sprites) {
		BufferedImage proj = sprites.getSprite("ProjectileA", 0);
		pntr.drawImage(proj, x, y);
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
		// TODO!!
	}
}
