package lufti.invaders;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import lufti.game.AbstractGame;
import lufti.game.PlayerInput;
import lufti.game.PlayerInput.Command;
import lufti.sprites.SpriteSheet;
import lufti.sprites.SpriteSheetFactory;
import lufti.ui.Canvas;

/**
 * The invader game.
 *
 * @author ubik
 */
public class InvaderGame extends AbstractGame {

	private SpriteSheet sprites;
	private Ship ship;
	private ArrayList<Bullet> bullets = new ArrayList<>();
	private ArrayList<Invader> invaders = new ArrayList<>();
	private ParticleEmitter particleEmitter = new ParticleEmitter();

	private int gameHeight, gameWidth;

	public InvaderGame() throws IOException {
		sprites = SpriteSheetFactory.getClassic();
		ship = new Ship(402, 500, sprites);
		gameHeight = 600;
		gameWidth = 800;

		// Create invaders
		int rows = 5;
		int cols = 11;
		int offX = 110, offY = 50;
		int dx = 14 * 4;
		int dy = 14 * 4;
		String[] types = new String[]{"InvaderC", "InvaderA", "InvaderA", "InvaderB", "InvaderB"};
		int[] offsets = new int[]{8, 4, 4, 0, 0};
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				String type = types[y];
				invaders.add(new Invader(offX + x * dx + offsets[y], offY + y * dy, sprites, type));
			}
		}
	}

	public int getRightGameBorder() {
		return gameWidth - 40;
	}

	public int getLeftGameBorder() {
		return 40;
	}

	public int getTopGameBorder() {
		return 0;
	}

	public int getBottomGameBorder() {
		return gameHeight;
	}

	@Override
	public void update(PlayerInput input) {
		ship.update(input, this);

		particleEmitter.update(input, this);

		for (Invader invader : invaders) {
			invader.update(input, this);
		}

		ArrayList<Bullet> keep = new ArrayList<>();
		for (Bullet bullet : bullets) {
			bullet.update(input, this);
			
			Invader hitInvader = null;
			for (Invader invader : invaders) {
				if (invader.contains(bullet.x, bullet.y)) {
					hitInvader = invader;
					break;
				}
			}

			if (hitInvader != null) {
				hitInvader.kill();
				spawnExplosion(hitInvader.midX(), hitInvader.midY());
				continue;
			}

			keep.add(bullet);
		}
		bullets = keep;
		
		ArrayList<Invader> aliveInvaders = new ArrayList<>();
		for (Invader invader : invaders) {
			if(invader.isAlive()) {
				aliveInvaders.add(invader);
			}
		}
		invaders = aliveInvaders;
	}

	private void spawnExplosion(int x, int y) {
		particleEmitter.setColor(0xffffffff).setVelocity(5f)
				.setGravity(.1f).setTTL(200)
				.setBounce(.25f, .1f);
		particleEmitter.createExplosion(x, y, 60, 25);
	}

	@Override
	public void render(Canvas.CanvasPainter pntr) {
		ship.render(pntr, sprites);

		for (Bullet bullet : bullets) {
			bullet.render(pntr, sprites);
		}

		for (Invader invader : invaders) {
			invader.render(pntr, sprites);
		}

		particleEmitter.render(pntr, sprites);
	}

	public void createBullet(int x, int y, int speed) {
		bullets.add(new Bullet(x, y, speed));
	}

}
