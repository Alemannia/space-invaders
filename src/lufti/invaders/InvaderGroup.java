package lufti.invaders;

import java.util.ArrayList;
import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;
import lufti.ui.Canvas;

/**
 *
 * @author ubik
 */
public class InvaderGroup extends GameObject {

	private ArrayList<Invader> invaders = new ArrayList<>();
	private int direction = 1;
	private int speed = 1;
	private int turnAdvance = 8;

	private InvaderGroup() {
		super(0, 0, 0, 0);
	}

	public static void create(InvaderGame game, SpriteSheet sprites) {
		InvaderGroup res = new InvaderGroup();
		
		// Create invaders
		for (int x = 0; x < Config.INVADER_COLS; x++) {
			for (int y = 0; y < Config.INVADER_ROWS.length; y++) {
				String type = Config.INVADER_ROWS[y];
				Invader inv = new Invader(Config.INVADER_START_X + x*(Config.INVADER_SPACING_HOR+Config.MAX_INVADER_WIDTH),
									Config.INVADER_START_Y + y*(Config.INVADER_SPACING_VERT+Config.MAX_INVADER_HEIGHT), sprites, type);
				System.out.println(inv);
				game.addGameObject(inv);
				res.invaders.add(inv);
			}
		}
		
		// Add group to receive updates
		game.addGameObject(res);
	}

	@Override
	public boolean isAlive() {
		return !invaders.isEmpty();
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {
		// Filter dead invaders
		ArrayList<Invader> keep = new ArrayList<>();
		for (Invader invader : invaders) {
			if(invader.isAlive()) {
				keep.add(invader);
			}
		}
		invaders = keep;
		
		speed = calculateSpeed();
		
		// Determine dimensions
		int left = game.getRightGameBorder();
		int right = game.getLeftGameBorder();
		
		for (Invader invader : invaders) {
			left = Math.min(left, invader.getLeftSide());
			right = Math.max(right, invader.getRightSide());
		}

		int vx = direction*speed;
		int realVx = vx;
		if( right+vx > game.getRightGameBorder() ) {
			realVx = game.getRightGameBorder()-right;
			turn();
		} else if (left+vx < game.getLeftGameBorder() ) {
			realVx = left-game.getLeftGameBorder();
			turn();
		}

		for (Invader invader : invaders) {
			invader.x += realVx;
		}
	}
	
	private void turn() {
		direction = -direction;
		for (Invader invader : invaders) {
			invader.y += turnAdvance;
		}
	}

	@Override
	public void render(Canvas.CanvasPainter pntr, SpriteSheet sprites) {
	}

	private int calculateSpeed() {
		// TODO: make nice.
		if( invaders.size() < 10) {
			return 3;
		} else if (invaders.size() < 15) {
			return 2;
		}
		return 1;
	}

}
