package com.zzzyt.jade.game.entity;

import com.zzzyt.jade.game.Entity;
import com.zzzyt.jade.game.Player;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.M;
import com.zzzyt.jade.util.SE;
import com.zzzyt.jade.util.U;
import com.zzzyt.jade.util.Collision;
import com.zzzyt.jade.util.Collision.CollisionData;
import com.zzzyt.jade.util.FlyingAnimation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;

public class BasicPlayer extends Player {

	public transient FlyingAnimation was;
	public transient Sprite hitbox;
	public float speedHigh, speedLow;
	public CollisionData collision, collisionItem, collisionGraze;
	public float x, y;

	private int dx, dy;

	/**
	 * The duration of death-bombing <br/>
	 * In frames
	 */
	public int deathbombWindow;

	/**
	 * The number of frames from death to reborn <br/>
	 */
	public int respawnFrame;

	/**
	 * invincibility frames
	 */
	public int invFrame;

	private static Logger logger = new Logger("BasicPlayer", U.config().logLevel);

	public BasicPlayer() {

	}

	public static final int ITEM_MUL = 15;
	public static final int GRAZE_MUL = 10;

	public BasicPlayer(Array<? extends TextureRegion> left, Array<? extends TextureRegion> center,
			Array<? extends TextureRegion> right, Array<? extends TextureRegion> toLeft,
			Array<? extends TextureRegion> toRight, Sprite hitbox, int frameLength, int transitionFrameLength,
			float radius, float speedHigh, float speedLow, int deathbombWindow, int rebirthFrame) {

		was = new FlyingAnimation(left, center, right, toLeft, toRight, frameLength, transitionFrameLength);
		this.hitbox = hitbox;
		this.collision = new Collision.Circle(radius);
		this.collisionItem = new Collision.Circle(radius * ITEM_MUL);
		this.collisionGraze = new Collision.Circle(radius * GRAZE_MUL);
		this.speedHigh = speedHigh;
		this.speedLow = speedLow;
		this.x = U.screenToWorldX(U.config().w / 2);
		this.y = U.screenToWorldY(32);
		this.canBomb = this.canShot = true;
		this.state = PlayerState.Normal;
		this.deathbombWindow = deathbombWindow;
		this.respawnFrame = rebirthFrame;
	}

	public BasicPlayer(TextureAtlas atlas, String regionName, int frameLength, int transitionFrameLength, float radius,
			float speedHigh, float speedLow, int deathbombWindow, int rebirthFrame) {

		was = new FlyingAnimation(atlas.findRegions(regionName + "_left"), atlas.findRegions(regionName + "_center"),
				atlas.findRegions(regionName + "_right"), atlas.findRegions(regionName + "_toLeft"),
				atlas.findRegions(regionName + "_toRight"), frameLength, transitionFrameLength);

		this.hitbox = new Sprite(atlas.findRegion(regionName + "_hitbox"));
		hitbox.setAlpha(0);
		this.collision = new Collision.Circle(radius);
		this.collisionItem = new Collision.Circle(radius * ITEM_MUL);
		this.collisionGraze = new Collision.Circle(radius * GRAZE_MUL);
		this.speedHigh = speedHigh;
		this.speedLow = speedLow;
		this.x = U.screenToWorldX(U.config().w / 2);
		this.y = U.screenToWorldY(32);
		this.canBomb = this.canShot = true;
		this.state = PlayerState.Normal;
		this.deathbombWindow = deathbombWindow;
		this.respawnFrame = rebirthFrame;
	}

	public BasicPlayer(TextureRegion region, float radius, float speedHigh, float speedLow, int deathbombWindow,
			int rebirthFrame) {
		Array<TextureRegion> tmp = new Array<TextureRegion>();
		tmp.add(region);

		was = new FlyingAnimation(tmp, tmp, tmp, tmp, tmp, 1, 1);
		this.collision = new Collision.Circle(radius);
		this.collisionItem = new Collision.Circle(radius * ITEM_MUL);
		this.collisionGraze = new Collision.Circle(radius * GRAZE_MUL);
		this.speedHigh = speedHigh;
		this.speedLow = speedLow;
		this.x = U.screenToWorldX(U.config().w / 2);
		this.y = U.screenToWorldY(32);
		this.canBomb = this.canShot = true;
		this.state = PlayerState.Normal;
		this.deathbombWindow = deathbombWindow;
		this.respawnFrame = rebirthFrame;
	}

	public void draw(Batch batch) {

		if (state == PlayerState.Normal || state == PlayerState.DeathBombing) {
			if (invFrame % 2 == 0 || state == PlayerState.DeathBombing) {
				TextureRegion tmp = was.getTexture();
				batch.draw(tmp, x - tmp.getRegionWidth() / 2, y - tmp.getRegionHeight() / 2);
			}
		}

		if (U.checkKey(U.config().keySlow) || state != PlayerState.Normal) {
			U.addAlpha(hitbox, 0.1f);
		} else {
			U.addAlpha(hitbox, -0.1f);
		}
		if (hitbox.getColor().a > 0) {
			hitbox.draw(batch);
			hitbox.setRotation(-hitbox.getRotation());
			hitbox.draw(batch);
			hitbox.setRotation(-hitbox.getRotation());
		}
	}

	@Override
	public BasicPlayer setX(float x) {
		this.x = x;
		return this;
	}

	@Override
	public BasicPlayer setY(float y) {
		this.y = y;
		return this;
	}

	@Override
	public BasicPlayer setXY(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public void onShot() {
		J.getSession().event.onPlayerShoot(this);
	}

	@Override
	public void onBomb() {
		J.getSession().event.onBomb(this);

		if (J.getSession().bossScene != null) {
			J.getSession().bossScene.getCurrentSpellcard().failBonus = true;
		}
	}

	@Override
	public void onGraze(EnemyBullet eb) {
		J.getSession().event.onGraze(this, eb);
		SE.play("graze");
	}

	@Override
	public void onRebirthStart() {
		J.getSession().event.onRebirthStart(this);
		J.getSession().bossScene.getCurrentSpellcard().failBonus = true;
	}

	@Override
	public void onRebirthEnd() {
		this.x = U.screenToWorldX(U.config().w / 2);
		this.y = U.screenToWorldY(32);
		J.getSession().event.onRebirthEnd(this);
	}

	/**
	 * Frame counter for reborn and deathbombing
	 */
	private int internalFrameCounter;

	@Override
	public void onHit() {
		if (state == PlayerState.Normal && invFrame == 0) {
			state = PlayerState.DeathBombing;
			internalFrameCounter = deathbombWindow;
			SE.play("pldead");
			J.getSession().event.onPlayerHit(this);
		} else {
			// no hitting
		}
	}

	@Override
	public void update(int t) {

		if (invFrame > 0) {
			invFrame--;
		}

		if (state == PlayerState.Normal) {
			updateOK(t);

			was.update(t, dx);
		} else if (state == PlayerState.DeathBombing) {
			// deathbombing
			if (J.isKeyJustPressed(U.config().keyBomb) && canBomb) {
				logger.debug("Deathbomb Succeed");
				onBomb();
				state = PlayerState.Normal;

				J.getSession().event.onPlayerDeathbomb(this);
				addInvincibilityFrame(120);
			} else {
				logger.debug("Deathbomb Fail");
				internalFrameCounter--;
				if (internalFrameCounter <= 0) {
					state = PlayerState.Respwaning;
					internalFrameCounter = respawnFrame;

					onRebirthStart();
				}
			}

			was.update(t, dx);
		} else if (state == PlayerState.Respwaning) {

			internalFrameCounter--;
			if (internalFrameCounter <= 0) {
				state = PlayerState.Normal;

				onRebirthEnd();
				addInvincibilityFrame(180);
			}
		}
	}

	/**
	 * Gives player invincibility frame
	 * 
	 * @param i
	 */
	public void addInvincibilityFrame(int i) {
		invFrame += i;
	}

	public void updateOK(int t) {
		dx = 0;
		dy = 0;
		float speed = speedHigh;
		if (J.isKeyPressed(U.config().keySlow)) {
			speed = speedLow;
		}
		if (J.isKeyPressed(U.config().keyUp)) {
			dy++;
		}
		if (J.isKeyPressed(U.config().keyDown)) {
			dy--;
		}
		if (J.isKeyPressed(U.config().keyLeft)) {
			dx--;
		}
		if (J.isKeyPressed(U.config().keyRight)) {
			dx++;
		}
		if (canShot && J.isKeyPressed(U.config().keyShot)) {
			onShot();
		}
		if (canBomb && J.isKeyJustPressed(U.config().keyBomb)) {
			onBomb();
		}

		if (Math.abs(dx) > 0 && Math.abs(dy) == 0) {
			x += speed * dx;
		} else if (Math.abs(dx) == 0 && Math.abs(dy) > 0) {
			y += speed * dy;
		} else if (Math.abs(dx) > 0 && Math.abs(dy) > 0) {
			x += speed * dx / M.SQRT2;
			y += speed * dy / M.SQRT2;
		}
		x = M.clamp(x, collision.getBoundingWidth() / 2 - U.config().originX,
				U.config().w - collision.getBoundingWidth() / 2 - U.config().originX);
		y = M.clamp(y, collision.getBoundingHeight() / 2 - U.config().originY,
				U.config().h - collision.getBoundingHeight() / 2 - U.config().originY);

		hitbox.setPosition(x - hitbox.getWidth() / 2, y - hitbox.getHeight() / 2);
		hitbox.setRotation(M.normalizeAngle(hitbox.getRotation() + 4f));

	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public int getZIndex() {
		return -1024;
	}

	@Override
	public CollisionData getCollisionData(Entity other) {
		if (other instanceof Item) {
			return collisionItem;
		}
		return collision;
	}

	@Override
	public CollisionData getGrazeCollisionData() {
		return collisionGraze;
	}
}
