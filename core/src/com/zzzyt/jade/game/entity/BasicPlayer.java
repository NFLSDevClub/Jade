package com.zzzyt.jade.game.entity;

import com.zzzyt.jade.game.Player;
import com.zzzyt.jade.util.J;
import com.zzzyt.jade.util.M;
import com.zzzyt.jade.util.SE;
import com.zzzyt.jade.util.U;
import com.zzzyt.jade.util.WAS;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;

public class BasicPlayer extends Player {

	public transient WAS was;
	public transient Sprite hitbox;
	public float radius, speedHigh, speedLow;
	public float x, y;

	private int dx, dy;

	/**
	 * allow to trigger {@link #onShot()} {@link #onBomb()}?
	 */
	public boolean canBomb, canShot;

	/**
	 * Normal player state
	 */
	public static final int PLOK=0;
	/**
	 * Waiting for deadbombing state
	 */
	public static final int PLDB=1;
	/**
	 * Waiting for respawn state
	 */
	public static final int PLRB=2;
	
	/**
	 * The duration of death-bombing <br/>
	 * In frames
	 */
	public int deathbombWindow;
	/**
	 * Player state: PLOK, PLDB, PLRB <br/>
	 */
	public int state;
	/**
	 * The number of frames from death to reborn <br/>
	 */
	public int rebirthFrame;
	
	/**
	 * invincibility frames
	 */
	public int invFrame;
	
	/**
	 * Frame counter for reborn and deathbombing
	 */
	private int internalFrameCounter;
	
	private static Logger logger = new Logger("BasicPlayer", U.config().logLevel);

	public BasicPlayer() {

	}

	public BasicPlayer(Array<? extends TextureRegion> left, Array<? extends TextureRegion> center,
			Array<? extends TextureRegion> right, Array<? extends TextureRegion> toLeft,
			Array<? extends TextureRegion> toRight, Sprite hitbox, int frameLength, int transitionFrameLength,
			float radius, float speedHigh, float speedLow,int deathbombWindow, int rebirthFrame) {
		
		was=new WAS(left, center, right, toLeft, toRight, frameLength, transitionFrameLength);
		this.hitbox = hitbox;
		this.radius = radius;
		this.speedHigh = speedHigh;
		this.speedLow = speedLow;
		this.x = U.screenToWorldX(U.config().w / 2);
		this.y = U.screenToWorldY(32);
		this.canBomb = this.canShot = true;
		this.deathbombWindow=deathbombWindow;
		this.rebirthFrame=rebirthFrame;
	}

	public BasicPlayer(TextureAtlas atlas, String regionName, int frameLength, int transitionFrameLength, float radius,
			float speedHigh, float speedLow,int deathbombWindow, int rebirthFrame) {
		
		was=new WAS(atlas.findRegions(regionName + "_left"),
				atlas.findRegions(regionName + "_center"),
				atlas.findRegions(regionName + "_right"),
				atlas.findRegions(regionName + "_toLeft"),
				atlas.findRegions(regionName + "_toRight"),
				frameLength,
				transitionFrameLength);
		
		this.hitbox = new Sprite(atlas.findRegion(regionName + "_hitbox"));
		hitbox.setAlpha(0);
		this.radius = radius;
		this.speedHigh = speedHigh;
		this.speedLow = speedLow;
		this.x = U.screenToWorldX(U.config().w / 2);
		this.y = U.screenToWorldY(32);
		this.canBomb = this.canShot = true;

		this.deathbombWindow=deathbombWindow;
		this.rebirthFrame=rebirthFrame;
	}

	public BasicPlayer(TextureRegion region, float radius, float speedHigh, float speedLow,int deathbombWindow,int rebirthFrame) {
		Array<TextureRegion> tmp = new Array<TextureRegion>();
		tmp.add(region);
		
		was=new WAS(tmp,tmp,tmp,tmp,tmp,1,1);
		this.radius = radius;
		this.speedHigh = speedHigh;
		this.speedLow = speedLow;
		this.x = U.screenToWorldX(U.config().w / 2);
		this.y = U.screenToWorldY(32);
		this.canBomb = this.canShot = true;
		

		this.deathbombWindow=deathbombWindow;
		this.rebirthFrame=rebirthFrame;
	}

	public void draw(Batch batch) {
		
		if(state==PLOK || state==PLDB) {
			if(invFrame%2==0 || state==PLDB) {
				TextureRegion tmp = was.getTexture();
				batch.draw(tmp, x - tmp.getRegionWidth() / 2, y - tmp.getRegionHeight() / 2);
			}
		}
		
		if (U.checkKey(U.config().keySlow) || state!=PLOK) {
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

	}

	@Override
	public void onBomb() {

	}

	@Override
	public void onRebirthStart() {
		
	}
	
	@Override
	public void onRebirthEnd() {
		this.x = U.screenToWorldX(U.config().w / 2);
		this.y = U.screenToWorldY(32);
	}
	
	@Override
	public void onHit() {
		if(state==PLOK && invFrame==0) {
			state=PLDB;
			internalFrameCounter=deathbombWindow;
			SE.play("die");
		}else {
			//no hitting 
		}
	}
	
	@Override
	public void update(int t) {
		
		if(invFrame>0) {
			invFrame--;
		}
		
		
		if(state==PLOK) {
			updateOK(t);

			was.update(t,dx);
		}else if(state==PLDB) {
			//deathbombing
			if(J.isKeyPressed(U.config().keyBomb)) {
				logger.debug("Deathbomb Succeed");
				onBomb();
				state=PLOK;
				
				addInvincibilityFrame(120);
			}else{
				logger.debug("Deathbomb Fail");
				internalFrameCounter--;
				if(internalFrameCounter<=0) {
					state=PLRB;
					internalFrameCounter=rebirthFrame;
					
					onRebirthStart();
				}
			}

			was.update(t,dx);
		}else if(state==PLRB) {
			
			internalFrameCounter--;
			if(internalFrameCounter<=0) {
				state=PLOK;
				
				onRebirthEnd();
				addInvincibilityFrame(180);
			}
		}
	}

	/**
	 * Gives player invincibility frame
	 * @param i
	 */
	public void addInvincibilityFrame(int i) {
		invFrame+=i;
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
		if (canBomb && J.isKeyPressed(U.config().keyBomb)) {
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
		x = M.clamp(x, radius - U.config().originX, U.config().w - radius - U.config().originX);
		y = M.clamp(y, radius - U.config().originY, U.config().h - radius - U.config().originY);

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
	public float getRadius() {
		return radius;
	}

	@Override
	public int getZIndex() {
		return -1024;
	}
}
