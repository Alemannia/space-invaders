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

	private int speed;
	private boolean dead = false;

	public Bullet(int x, int y, int speed) {
		super(x, y);
		this.speed = speed;
	}

	@Override
	public void render(Canvas.CanvasPainter pntr, SpriteSheet sprites) {
		BufferedImage proj = sprites.getSprite("ProjectileA", 0);
		pntr.drawImage(proj, x, y);
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
		y += speed;
		if (y < game.getTopGameBorder() - 50 || y > game.getBottomGameBorder() + 50) {
			dead = true;
		}

	}

	@Override
	public boolean isAlive() {
		return !dead;
	}
}
