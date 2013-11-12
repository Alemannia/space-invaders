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

	private int reloadTime = 4;
	private int reloadCounter = 0;

	public Ship(int x, int y, SpriteSheet sprites) {
		super(x, y);
		w = sprites.getSpriteDimension("Ship", 0).width;
		h = sprites.getSpriteDimension("Ship", 0).height;
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
		if (reloadCounter > 0) {
			reloadCounter--;
		}

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
		if (input.containsCommand(PlayerInput.Command.SHOOT) && reloadCounter <= 0) {
			game.createBullet(x - 2, y - projHeight);
			reloadCounter = reloadTime;
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
