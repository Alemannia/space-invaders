package lufti.invaders;

import java.io.IOException;
import java.util.ArrayList;
import lufti.game.AbstractGame;
import lufti.game.PlayerInput;
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
	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	private ArrayList<GameObject> newGameObjects = new ArrayList<GameObject>();

	private ParticleEmitter particleEmitter = new ParticleEmitter();

	public InvaderGame() throws IOException {
		sprites = SpriteSheetFactory.getClassic();
		
		// Create and position game objects
		ship = new Ship(Config.PLAYER_START_X, Config.PLAYER_START_Y, sprites);
		addGameObject(ship);

		InvaderGroup.create(this, sprites);

		for (int i = 0; i < Config.NUM_BUNKERS; i++) {
			int x = Config.BUNKER_START_X + i*Config.BUNKER_SPACING;
			int y = Config.BUNKER_START_Y;
			addGameObject(new Bunker(x, y, sprites));
		}
	}

	public int getRightGameBorder() {
		return Config.GAME_RIGHT;
	}

	public int getLeftGameBorder() {
		return Config.GAME_LEFT;
	}

	public int getTopGameBorder() {
		return Config.GAME_TOP;
	}

	public int getBottomGameBorder() {
		return Config.GAME_BOTTOM;
	}
	
	public int getFloor() {
		return Config.FLOOR;
	}

	public ArrayList<GameObject> findCollisions(int x, int y) {
		ArrayList<GameObject> res = new ArrayList<GameObject>();

		for (GameObject object : gameObjects) {
			if (object.contains(x, y)) {
				res.add(object);
			}
		}

		return res;
	}
	
	public ArrayList<SpriteObject> findCollisions(SpriteObject source) {
		ArrayList<SpriteObject> res = new ArrayList<SpriteObject>();
		
		for (GameObject gameObject : gameObjects) {
			if( gameObject instanceof SpriteObject) {
				SpriteObject target = (SpriteObject) gameObject;
				if(Collision.pixelPerfect(source.getSprite(),source.x,source.y,
										  target.getSprite(),target.x,target.y)) {
					res.add(target);
				}
			}
		}
		
		return res;
	}

	@Override
	public void update(PlayerInput input) {
		gameObjects.addAll(newGameObjects);
		newGameObjects.clear();

		particleEmitter.update(input, this);

		for (GameObject object : gameObjects) {
			object.update(input, this);
		}

		ArrayList<GameObject> keep = new ArrayList<GameObject>();
		for (GameObject object : gameObjects) {
			if (object.isAlive()) {
				keep.add(object);
			}
		}
		gameObjects = keep;
	}

	void spawnExplosion(int x, int y, int col) {
		particleEmitter.setColor(col).setVelocity(5f)
				.setGravity(.1f).setTTL(160)
				.setBounce(.25f, .1f);
		particleEmitter.createExplosion(x, y, 40, 25);
	}

	void spawnSmallExplosion(int x, int y, int col) {
		particleEmitter.setColor(col).setVelocity(5f)
				.setGravity(.1f).setTTL(200)
				.setBounce(.25f, .1f);
		particleEmitter.createExplosion(x, y, 10, 10);
	}

	@Override
	public void render(Canvas.CanvasPainter pntr) {
		for (GameObject object : gameObjects) {
			object.render(pntr, sprites);
		}

		particleEmitter.render(pntr, sprites);
		
		// Draw Floor
		pntr.drawLine(getLeftGameBorder(), getFloor(), getRightGameBorder(), getFloor(), 4, 0xffffffff);
	}

	public void createBullet(int x, int y, int speed, String type, boolean invaderBullet) {
		newGameObjects.add(new Bullet(x, y, speed, sprites, type, invaderBullet));
	}

	public void addGameObject(GameObject object) {
		newGameObjects.add(object);
	}
}
