package lufti.invaders;

import java.util.ArrayList;
import java.util.Random;
import lufti.game.PlayerInput;
import lufti.sprites.SpriteSheet;
import lufti.ui.Canvas;

/**
 *
 * @author ubik
 */
public class ParticleEmitter extends GameObject {

	// Variables for configuration
	private int rgb = 0xffffffff;
	private float vel = 0f, velVar = 0f;
	private float bounce = .25f, bounceVar = 0f;
	private float accelX = 0f, accelY = 0f;
	private int ttl = 120;

	private Random random = new Random();
	private ArrayList<Particle> particles = new ArrayList<Particle>();

	public ParticleEmitter() {
		super(0, 0);
	}

	public ParticleEmitter setColor(int argb) {
		this.rgb = argb;
		return this;
	}

	public ParticleEmitter setVelocity(float vel) {
		return setVelocity(vel, 0f);
	}

	public ParticleEmitter setVelocity(float vel, float variance) {
		this.vel = vel;
		this.velVar = variance;
		return this;
	}
	
	public ParticleEmitter setBounce(float bounce) {
		return setBounce(bounce, 0f);
	}
	
	public ParticleEmitter setBounce(float bounce, float variance) {
		this.bounce = bounce;
		this.bounceVar = variance;
		return this;
	}

	public ParticleEmitter setGravity(float grav) {
		this.accelX = 0f;
		this.accelY = grav;
		return this;
	}

	public ParticleEmitter setTTL(int ttl) {
		this.ttl = ttl;
		return this;
	}

	public void createExplosion(int cx, int cy, int n, float radius) {
		float PI2 = (float) (2 * Math.PI);
		for (int i = 0; i < n; i++) {
			float angle = random.nextFloat() * PI2;
			float r = random.nextFloat() * radius;
			float dx = (float) (Math.cos(angle));
			float dy = (float) (Math.sin(angle));

			Particle p = createParticle(dx, dy);
			p.x = cx + dx * r;
			p.y = cy + dy * r;
			p.oldX = p.x;
			p.oldY = p.y;
		}
	}

	private Particle createParticle(float dx, float dy) {
		float v = vel + random.nextFloat() * velVar;
		float bnc = bounce + random.nextFloat() * bounceVar;

		Particle p = new Particle();
		p.ttl = ttl;
		p.ax = accelX;
		p.ay = accelY;
		p.rgb = rgb;
		p.vx = v * dx;
		p.vy = v * dy;
		p.bounce = bnc;
		
		particles.add(p);
		return p;
	}

	@Override
	public void update(PlayerInput input, InvaderGame game) {

		int left = game.getLeftGameBorder();
		int right = game.getRightGameBorder();
		int top = game.getTopGameBorder();
		int bottom = game.getFloor();

		for (Particle p : particles) {
			p.oldX = p.x;
			p.oldY = p.y;
			
			p.x += p.vx;
			p.y += p.vy;
			p.vx += p.ax;
			p.vy += p.ay;

			if (p.x > right) {
				p.x = right;
				p.vx = -p.vx * p.bounce;
			} else if (p.x < left) {
				p.x = left;
				p.vx = -p.vx * p.bounce;
			}

			if (p.y > bottom) {
				p.y = bottom;
				p.vy = -p.vy * p.bounce;
			} else if (p.y < top) {
				p.y = top;
				p.vy = -p.vy * p.bounce;
			}

			p.ttl--;
		}

		ArrayList<Particle> remaining = new ArrayList<Particle>();
		for (Particle p : particles) {
			if (p.ttl <= 0) { // || p.x < left || p.x > right || p.y < top || p.y > bottom) {
				continue;
			}
			remaining.add(p);
		}
		particles = remaining;
	}

	@Override
	public void render(Canvas.CanvasPainter pntr, SpriteSheet sprites) {
		for (Particle p : particles) {
			int ox = (int) (p.oldX + .5f);
			int oy = (int) (p.oldY + .5f);
			int x = (int) (p.x + .5f);
			int y = (int) (p.y + .5f);
			
			pntr.drawLine(ox, oy, x, y, 4, p.rgb);
		}
	}

	@Override
	public boolean isAlive() {
		return true;
	}

	private class Particle {
		int ttl;
		int rgb;
		float oldX, oldY;
		float x, y;
		float vx, vy;
		float ax, ay;
		float bounce;
	}
}
