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
public class Ship extends GameObject {

	private int shipSpeed = 8;
	private int shipWidth = 52;

	public Ship(int x, int y) {
		super(x, y);
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
		if (input.containsCommand(PlayerInput.Command.RIGHT)) {
			x += shipSpeed;
			if (x > game.getRightGameBorder() - shipWidth / 2) {
				x = game.getRightGameBorder() - shipWidth / 2;
			}
		}
		if (input.containsCommand(PlayerInput.Command.LEFT)) {
			x -= shipSpeed;
			if (x < game.getLeftGameBorder() + shipWidth / 2) {
				x = game.getLeftGameBorder() + shipWidth / 2;
			}
		}
		
		int projHeight = 16; // ;sprites.getSpriteDimension("ProjectileA", 0).height;
		if (input.containsCommand(PlayerInput.Command.SHOOT)) {
			game.createBullet(x-2, y-projHeight);
		}
	}

	@Override
	public void render(Canvas.CanvasPainter pntr, SpriteSheet sprites) {
		BufferedImage shipSprite = sprites.getSprite("Ship", 0);
		Dimension shipDim = sprites.getSpriteDimension("Ship", 0);
		pntr.drawImage(shipSprite, x - shipSprite.getWidth() / 2,
				y - shipSprite.getHeight() / 2);

	}
}
