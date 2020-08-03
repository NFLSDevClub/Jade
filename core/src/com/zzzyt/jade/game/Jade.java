package com.zzzyt.jade.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectMap;
import com.zzzyt.jade.game.entity.Bullet;
import com.zzzyt.jade.game.entity.Enemy;
import com.zzzyt.jade.util.U;

public class Jade implements Disposable {

	public static Jade session;

	private int frame;

	private transient FrameBuffer fbo;
	private transient TextureRegion fboRegion;
	private transient SpriteBatch batch;
	private transient OrthographicCamera cam;
	private transient Logger logger;

	public Array<Task> tasks;
	public Array<Drawable> nDrawables, pDrawables;
	public ObjectMap<Integer, Array<Operator>> operators;
	public EntityArray<Enemy> enemies;
	public EntityArray<Bullet> bullets;
	public Player player;
	public BossScene bossScene;
	
	private boolean running;
	private boolean paused;

	public Jade() {
		this.frame = 0;

		this.logger = new Logger("Jade", U.config().logLevel);

		logger.info("Creating Jade session...");

		this.fbo = new FrameBuffer(Format.RGBA8888, U.config().w, U.config().h, false);
		this.fboRegion = new TextureRegion(fbo.getColorBufferTexture());

		this.cam = new OrthographicCamera(fbo.getWidth(), fbo.getHeight());
		cam.position.set(U.config().w / 2 - U.config().originX, U.config().h / 2 - U.config().originY, 0);
		cam.update();

		fboRegion.flip(false, true);
		this.batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);

		this.operators = new ObjectMap<Integer, Array<Operator>>();
		this.bullets = new EntityArray<>();
		this.enemies = new EntityArray<>();
		this.tasks = new Array<Task>(true, 16);
		this.nDrawables = new Array<Drawable>(true, 16);
		this.pDrawables = new Array<Drawable>(true, 16);

		if (Jade.session != null) {
			logger.error("[WARN] There's another existing Jade session!");
		}
		Jade.session = this;

		this.running = true;
		this.paused = false;
	}

	public void draw() {
		if (running && !paused) {
			fbo.begin();
			U.glClear();
			batch.begin();
			for (int i = 0; i < nDrawables.size; i++) {
				if (nDrawables.get(i) != null) {
					nDrawables.get(i).draw(batch);
				}
			}
			
			enemies.draw(batch);
			bullets.draw(batch);
			
			for (int i = 0; i < pDrawables.size; i++) {
				if (pDrawables.get(i) != null) {
					pDrawables.get(i).draw(batch);
				}
			}
			batch.end();
			fbo.end();
		}
	}

	public void update() {
		if (tasks.size == 0) {
			terminate();
			return;
		}
		frame++;
		for (int i = 0; i < tasks.size; i++) {
			if (tasks.get(i) != null) {
				tasks.get(i).update(frame);
				if (tasks.get(i).isFinished()) {
					tasks.set(i, null);
				}
			}
		}
		U.cleanupArray(tasks);

		for (int i = 0; i < nDrawables.size; i++) {
			if (nDrawables.get(i) != null) {
				nDrawables.get(i).update(frame);
			}
		}
		
		enemies.update(frame);
		bullets.update(frame);
		
		for (int i = 0; i < pDrawables.size; i++) {
			if (pDrawables.get(i) != null) {
				pDrawables.get(i).update(frame);
			}
		}

		if ((bullets.count <= U.config().cleanupBulletCount && bullets.blankCount >= U.config().cleanupBlankCount)
				|| (bullets.size() >= 1048576)) {
			logger.info("Cleaning up blanks in bullet array: bulletCount=" + bullets.count + " blankCount=" + bullets.blankCount);
			bullets.cleanUp();
		}
		
		if ((enemies.count <= U.config().cleanupBulletCount && enemies.blankCount >= U.config().cleanupBlankCount)
				|| (enemies.size() >= 1048576)) {
			logger.info("Cleaning up blanks in enemy array: enemyCount=" + enemies.count + " blankCount=" + enemies.blankCount);
			enemies.cleanUp();
		}
		
		//updating bossScene
		if(bossScene!=null) {
			bossScene.update(frame);
			if(bossScene.isFinished()) {
				bossScene=null;
			}
		}
	}

	public FrameBuffer getFrameBuffer() {
		return fbo;
	}

	public TextureRegion getFrameTexture() {
		return fboRegion;
	}

	public Jade add(Bullet bullet) {
		bullets.add(bullet);
		return this;
	}

	public Jade remove(Bullet bullet) {
		bullets.remove(bullet);
		return this;
	}

	public Jade add(Enemy enemy) {
		enemies.add(enemy);
		return this;
	}
	
	public Jade remove(Enemy enemy) {
		enemies.remove(enemy);
		return this;
	}
	
	public Jade addOperator(Operator operator) {
		int tag = operator.getTag();
		Array<Operator> tmp = operators.get(tag);
		if (tmp == null) {
			tmp = new Array<Operator>(false, 8);
			tmp.add(operator);
			operators.put(tag, tmp);
		} else {
			tmp.add(operator);
		}
		return this;
	}

	public Jade removeOperator(Operator operator) {
		Array<Operator> tmp = operators.get(operator.getTag());
		if (tmp != null) {
			tmp.removeValue(operator, true);
		}
		return this;
	}

	public Array<Operator> getOperators(int tag) {
		return operators.get(tag);
	}

	public void terminate() {
		logger.info("Terminating Jade session...");
		running = false;
	}

	public void pause() {
		logger.info("Pausing Jade session...");
		paused = true;
	}

	public void resume() {
		logger.info("Resuming Jade seesion...");
		paused = false;
	}
	
	/**
	 * When player is hit
	 */
	public void onHit() {
		if(!U.config().invulnerable) {
			player.onHit();
		}
	}

	public int frame() {
		return frame;
	}

	public Jade addTask(Task task) {
		tasks.add(task);
		return this;
	}

	public Jade removeTask(Task task) {
		int index = tasks.indexOf(task, true);
		tasks.set(index, null);
		return this;
	}

	public Array<Task> getTasks() {
		return tasks;
	}

	public Task getTask(int index) {
		return tasks.get(index);
	}

	public Jade setPlayer(Player player) {
		if (this.player != null) {
			removeDrawable(player);
		}
		this.player = player;
		addDrawable(player);
		return this;
	}

	public Player getPlayer() {
		return player;
	}

	public Logger getLogger() {
		return logger;
	}

	public EntityArray<Bullet> getBullets() {
		return bullets;
	}

	public EntityArray<Enemy> getEnemies() {
		return enemies;
	}

	
	public int getBulletCount() {
		return bullets.count;
	}

	public Jade addDrawable(Drawable drawable) {
		if (drawable.getZIndex() < 0) {
			nDrawables.insert(Drawable.binarySearch(nDrawables, drawable.getZIndex()), drawable);
		} else if (drawable.getZIndex() > 0) {
			pDrawables.insert(Drawable.binarySearch(pDrawables, drawable.getZIndex()), drawable);
		}
		return this;
	}

	public Jade removeDrawable(Drawable drawable) {
		if (drawable.getZIndex() < 0) {
			nDrawables.removeValue(drawable, true);
		} else if (drawable.getZIndex() > 0) {
			pDrawables.removeValue(drawable, true);
		}
		return this;
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

	public boolean isPaused() {
		return paused;
	}


}
