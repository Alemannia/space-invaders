package lufti.invaders;

import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;
import lufti.ui.Canvas;

/**
 *
 * @author ubik
 */
public abstract class GameObject {
	protected int x, y;

	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public abstract void update(PlayerInput input, InvaderGame game);
	
	public abstract void render(Canvas.CanvasPainter pntr, SpriteSheet sprites);
}
