package lufti.invaders;

import java.util.ArrayList;
import java.util.ListIterator;
import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;
import lufti.ui.Canvas;

/**
 *
 * @author ubik
 */
public class InvaderGroup extends GameObject {

	private int direction = 1;
	private int speed = 1;
	private int turnAdvance = 8;

	private int invaderCount;
	private ArrayList<ArrayList<Invader>> columns;

	private InvaderGroup() {
		super(0, 0, 0, 0);
	}

	public static void create(InvaderGame game, SpriteSheet sprites) {
		InvaderGroup res = new InvaderGroup();

		res.columns = new ArrayList<ArrayList<Invader>>();
		// Create invaders
		for (int x = 0; x < Config.INVADER_COLS; x++) {
			ArrayList<Invader> col = new ArrayList<Invader>();
			for (int y = 0; y < Config.INVADER_ROWS.length; y++) {
				String type = Config.INVADER_ROWS[y];
				int invX = Config.INVADER_START_X + x * (Config.INVADER_SPACING_HOR + Config.MAX_INVADER_WIDTH);
				int invY = Config.INVADER_START_Y + y * (Config.INVADER_SPACING_VERT + Config.MAX_INVADER_HEIGHT);
				Invader inv = new Invader(invX, invY, sprites, type);
				inv.setCanShoot(y == Config.INVADER_ROWS.length - 1); // Only the bottom row can shoot

				game.addGameObject(inv);
				col.add(inv);
				res.invaderCount++;
			}
			res.columns.add(col);
		}

		// Add group to receive updates
		game.addGameObject(res);
	}

	@Override
	public boolean isAlive() {
		return true;
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
		// Filter dead invaders
		for (ArrayList<Invader> column : columns) {
			ListIterator<Invader> it = column.listIterator();
			while (it.hasNext()) {
				if (!it.next().isAlive()) {
					it.remove();
					invaderCount--;
				}
			}
		}

		// Make last invader be able to shoot
		for (ArrayList<Invader> column : columns) {
			if (!column.isEmpty()) {
				column.get(column.size() - 1).setCanShoot(true);
			}
		}

		speed = calculateSpeed();

		// Determine dimensions
		int left = game.getRightGameBorder();
		int right = game.getLeftGameBorder();

		for (ArrayList<Invader> column : columns) {
			for (Invader invader : column) {
				left = Math.min(left, invader.getLeftSide());
				right = Math.max(right, invader.getRightSide());
			}
		}

		int vx = direction * speed;
		int realVx = vx;
		if (right + vx > game.getRightGameBorder()) {
			realVx = game.getRightGameBorder() - right;
			turn();
		} else if (left + vx < game.getLeftGameBorder()) {
			realVx = left - game.getLeftGameBorder();
			turn();
		}

		for (ArrayList<Invader> column : columns) {
			for (Invader invader : column) {
				invader.x += realVx;
			}
		}
	}

	private void turn() {
		direction = -direction;
		for (ArrayList<Invader> column : columns) {
			for (Invader invader : column) {
				invader.y += turnAdvance;
			}
		}
	}

	@Override
	public void render(Canvas.CanvasPainter pntr, SpriteSheet sprites) {
	}

	private int calculateSpeed() {
		// TODO: make nice.
		if (invaderCount < 10) {
			return 3;
		} else if (invaderCount < 15) {
			return 2;
		}
		return 1;
	}

}
