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
	private ArrayList<GameObject> gameObjects = new ArrayList<>();
	private ArrayList<GameObject> newGameObjects = new ArrayList<>();

	private ParticleEmitter particleEmitter = new ParticleEmitter();

	private int gameHeight, gameWidth;

	public InvaderGame() throws IOException {
		sprites = SpriteSheetFactory.getClassic();
		ship = new Ship(402, 500, sprites);

		gameHeight = 600;
		gameWidth = 800;

		addGameObject(ship);
		InvaderGroup.create(this, sprites);
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

	public ArrayList<GameObject> findCollisions(int x, int y) {
		ArrayList<GameObject> res = new ArrayList<>();

		for (GameObject object : gameObjects) {
			if (object.contains(x, y)) {
				res.add(object);
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

		ArrayList<GameObject> keep = new ArrayList<>();
		for (GameObject object : gameObjects) {
			if (object.isAlive()) {
				keep.add(object);
			}
		}
		gameObjects = keep;
	}

	void spawnExplosion(int x, int y, int col) {
		particleEmitter.setColor(col).setVelocity(5f)
				.setGravity(.1f).setTTL(200)
				.setBounce(.25f, .1f);
		particleEmitter.createExplosion(x, y, 60, 25);
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
	}

	public void createBullet(int x, int y, int speed, String type) {
		newGameObjects.add(new Bullet(x, y, speed, type));
	}

	public void addGameObject(GameObject object) {
		newGameObjects.add(object);
	}

}
