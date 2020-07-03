package com.zzzyt.jade;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.zzzyt.jade.entity.Bullet;
import com.zzzyt.jade.entity.RoundBullet;
import com.zzzyt.jade.entity.BasicPlayer;
import com.zzzyt.jade.util.Game;
import com.zzzyt.jade.util.Utils;

public class Jade implements Disposable {

	public static Jade session;

	private transient FrameBuffer fbo;
	private transient TextureRegion fboRegion;
	private transient SpriteBatch batch;
	private transient OrthographicCamera cam;
	private transient Logger logger;

	public Pool<RoundBullet> roundBulletPool;
	
	public Array<Bullet> bullets;
	public Array<Bullet> candidates;
	public BasicPlayer player;

	private boolean terminating;
	private int candidateCount;
	private int bulletCount, blankCount;

	public Jade() {
		this.logger = new Logger("Jade",Config.logLevel);

		logger.info("Creating Jade session...");

		this.fbo = new FrameBuffer(Format.RGBA8888, Config.w, Config.h, false);
		this.fboRegion = new TextureRegion(fbo.getColorBufferTexture());

		this.cam = new OrthographicCamera(fbo.getWidth(), fbo.getHeight());
		cam.position.set(Config.w / 2 - Config.originX, Config.h / 2 - Config.originY, 0);
		cam.update();

		fboRegion.flip(false, true);
		this.batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);

		this.roundBulletPool=new Pool<RoundBullet>() {
			@Override
			protected RoundBullet newObject() {
				return new RoundBullet();
			}
		};
		
		this.bullets = new Array<Bullet>(false, 1024);
		this.candidates = new Array<Bullet>(false, 256);

		if (Jade.session != null) {
			logger.error("[WARN] There's another existing Jade session!");
		}
		Jade.session = this;

		this.terminating = false;
	}

	public void draw() {
		fbo.begin();
		Utils.glClear();
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

	public TextureRegion getFrame() {
		return fboRegion;
	}

	public RoundBullet newRoundBullet(TextureRegion region, int tag, float radius) {
		RoundBullet tmp=roundBulletPool.obtain();
		tmp.sprite=new Sprite(region);
		tmp.tag=tag;
		tmp.radius=radius;
		return tmp;
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
		if(bullet instanceof RoundBullet) {
			roundBulletPool.free((RoundBullet) bullet);
		}
		return bullet;
	}

	public void update() {
		player.update();
		Bullet tmp;
		for (int i = 0; i < candidateCount; i++) {
			if (terminating)
				break;
			tmp = candidates.get(i);
			if (tmp.collide(player)) {
				tmp.onHit();
			}
		}
	}

	public void postRender() {
		for (int i = 0; i < bullets.size; i++) {
			if (terminating)
				break;
			if (bullets.get(i) != null)
				bullets.get(i).update();
		}

		if ((bulletCount <= Config.cleanupBulletCount && blankCount >= Config.cleanupBlankCount)
				|| (bullets.size >= 1048576)) {
			clanupBulletArray();
		}

		candidateCount = 0;
		Bullet tmp;
		float dst = Utils.sqr(player.radius + Config.safeDistance);
		for (int i = 0; i < bullets.size; i++) {
			if (terminating)
				break;
			if (bullets.get(i) == null)
				continue;
			tmp = bullets.get(i);
			if (tmp.dist2(player.x, player.y) <= Utils.sqr(tmp.getBoundingRadius()) + dst) {
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

	public BasicPlayer setPlayer(BasicPlayer player) {
		this.player = player;
		return player;
	}

	public void terminate() {
		logger.info("Terminating Jade session.");
		terminating = true;
	}

	public void onHit() {
		terminate();
		Game.switchScreen("start");
	}

	public static boolean outOfWorld(float x, float y, float rx, float ry) {
		if (x + rx < -Config.originX - Config.deleteDistance)
			return true;
		if (x - rx > Config.w + Config.deleteDistance - Config.originX)
			return true;
		if (y + ry < -Config.originY - Config.deleteDistance)
			return true;
		if (y - ry > Config.h + Config.deleteDistance - Config.originY)
			return true;
		return false;
	}

	public static boolean outOfFrame(float x, float y, float rx, float ry) {
		if (x + rx < -Config.originX)
			return true;
		if (x - rx > Config.w - Config.originX)
			return true;
		if (y + ry < -Config.originY)
			return true;
		if (y - ry > Config.h - Config.originY)
			return true;
		return false;
	}

	public static Rectangle getWorld() {
		return new Rectangle(-Config.originX, -Config.originY, Config.w, Config.h);
	}

	@Override
	public void dispose() {
		terminate();
		logger.info("Disposing Jade session.");
		fbo.dispose();
		Jade.session = null;
	}
}
