package com.zzzyt.jade.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.entity.Bullet;
import com.zzzyt.jade.entity.Player;
import com.zzzyt.jade.entity.RoundBullet;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.Game;
import com.zzzyt.jade.util.M;
import com.zzzyt.jade.util.U;

public class Jade implements Disposable {

	public static Jade session;

	private int frame;

	private transient FrameBuffer fbo;
	private transient TextureRegion fboRegion;
	private transient SpriteBatch batch;
	private transient OrthographicCamera cam;
	private transient Logger logger;

	public Difficulty difficulty;

	public Array<Bullet> bullets;
	public Array<Bullet> candidates;
	public Player player;

	private boolean running;
	private int candidateCount;
	private int bulletCount, blankCount;

	public Jade() {
		this.frame = 0;

		this.logger = new Logger("Jade", Config.logLevel);

		logger.info("Creating Jade session...");

		this.fbo = new FrameBuffer(Format.RGBA8888, Config.w, Config.h, false);
		this.fboRegion = new TextureRegion(fbo.getColorBufferTexture());

		this.cam = new OrthographicCamera(fbo.getWidth(), fbo.getHeight());
		cam.position.set(Config.w / 2 - Config.originX, Config.h / 2 - Config.originY, 0);
		cam.update();

		fboRegion.flip(false, true);
		this.batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);

		this.bullets = new Array<Bullet>(false, 1024);
		this.candidates = new Array<Bullet>(false, 256);

		if (Jade.session != null) {
			logger.error("[WARN] There's another existing Jade session!");
		}
		Jade.session = this;

		this.running = true;
	}

	public void draw() {
		fbo.begin();
		U.glClear();
		batch.begin();
		player.draw(batch);
		for (int i = 0; i < bullets.size; i++) {
			if (bullets.get(i) == null)
				continue;
			bullets.get(i).draw(batch);
		}
		batch.end();
		fbo.end();
	}

	public TextureRegion getFrameTexture() {
		return fboRegion;
	}

	public Bullet add(Bullet bullet) {
		bulletCount++;
		bullet.id = bullets.size;
		bullets.add(bullet);
		return bullet;
	}

	public Bullet remove(Bullet bullet) {
		if (bullet.id != bullets.size - 1)
			blankCount++;
		bulletCount--;
		bullets.set(bullet.id, null);
		if (bullet.getClass() == RoundBullet.class) {
			B.freeRoundBullet((RoundBullet) bullet);
		}
		return bullet;
	}

	public void update() {
		if (!running)
			return;
		frame++;
		player.update(frame);
		Bullet tmp;
		for (int i = 0; i < candidateCount; i++) {
			tmp = candidates.get(i);
			if (tmp.collide(player)) {
				tmp.onHit();
			}
		}
	}

	public void postRender() {
		if (!running) {
			Game.switchScreen("start");
			return;
		}
		if (difficulty.isFinished()) {
			terminate();
			Game.switchScreen("start");
			return;
		}
		difficulty.update(frame);
		for (int i = 0; i < bullets.size; i++) {
			if (bullets.get(i) != null) {
				bullets.get(i).update(frame);
			}
		}

		if ((bulletCount <= Config.cleanupBulletCount && blankCount >= Config.cleanupBlankCount)
				|| (bullets.size >= 1048576)) {
			clanupBulletArray();
		}

		candidateCount = 0;
		Bullet tmp;
		float dst = M.sqr(player.getRadius() + Config.safeDistance);
		for (int i = 0; i < bullets.size; i++) {
			if (bullets.get(i) == null)
				continue;
			tmp = bullets.get(i);
			if (tmp.dist2(player.getX(), player.getY()) <= M.sqr(tmp.getBoundingRadius()) + dst) {
				if (candidates.size > candidateCount) {
					candidates.set(candidateCount, tmp);
				} else {
					candidates.add(tmp);
				}
				candidateCount++;
			}
		}
	}

	private void clanupBulletArray() {
		logger.info("Cleaning up blanks in bullet array: bulletCount=" + bulletCount + " blankCount=" + blankCount);
		int j = 0;
		for (int i = 0; i < bullets.size; i++) {
			if (bullets.get(i) != null) {
				bullets.set(j, bullets.get(i));
				bullets.get(j).id = j;
				j++;
			}
		}
		bullets.truncate(j);
		blankCount = 0;
	}

	public void terminate() {
		logger.info("Terminating Jade session...");
		running = false;
	}

	private void pause() {
		logger.info("Pausing Jade session...");
		running = false;
	}

	public void onHit() {
		if(!Config.invulnerable) {
			pause();			
		}
	}

	public int frame() {
		return frame;
	}

	public Jade setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
		return this;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public Jade setPlayer(Player player) {
		this.player = player;
		return this;
	}

	public Player getPlayer() {
		return player;
	}

	public Logger getLogger() {
		return logger;
	}

	public Array<Bullet> getBullets() {
		return bullets;
	}
	
	public int getBulletCount() {
		return bulletCount;
	}
	
	@Override
	public void dispose() {
		terminate();
		logger.info("Disposing Jade session.");
		fbo.dispose();
		Jade.session = null;
	}

	public boolean isRunning() {
		return running;
	}

}
