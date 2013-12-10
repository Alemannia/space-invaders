package lufti.invaders;

import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;

/**
 *
 * @author ubik
 */
public class Bunker extends SpriteObject {

	public Bunker(int x, int y, SpriteSheet sprites) {
		super(x, y, sprites, "Bunker");
		this.y -= h; // Initial position denotes sprite bottom
	}

	@Override
	public boolean isAlive() {
		return true; // TODO: check if pixels are left
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
	}

	@Override
	public boolean handleHit(InvaderGame game, Bullet bullet) {
		// TODO: remove pixels
		return true;
	}
}
