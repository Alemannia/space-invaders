package lufti.invaders;

import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;
import lufti.ui.Canvas;

/**
 * Original game measures:
 *		width 224px
 *		height ca. 170px
 *		invader spacing horizontal: 16px from left side to left side
 *		invader spacing vertical: 8px
 *		invader start position: ca 180px from window bottom
 *		player spacing from window bottom: 24px
 *		line spacing form window bottom: 17px
 *		bunker spacing from window bottom: 40px
 * @author ubik
 */
public abstract class GameObject {

	protected int x, y;
	protected int w, h;

	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
		w = h = 0;
	}

	public GameObject(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int midX() {
		return x+w/2;
	}
	
	public int midY() {
		return y+h/2;
	}
	
	public int getLeftSide() {
		return x;
	}
	
	public int getRightSide() {
		return x+w;
	}
	
	public int getTopSide() {
		return y;
	}
	
	public int getBottomSide() {
		return y+h;
	}
	
	public boolean contains(int px, int py) {
		if (px < x || px >= x + w || py < y || py >= y + h) {
			return false;
		}
		return true;
	}
	
	public abstract boolean isAlive();

	public abstract void update(PlayerInput input, InvaderGame game);

	public abstract void render(Canvas.CanvasPainter pntr, SpriteSheet sprites);

	@Override
	public String toString() {
		return "GameObject{" + "x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + '}';
	}
}
