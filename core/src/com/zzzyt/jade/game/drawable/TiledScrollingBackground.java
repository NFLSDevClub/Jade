package com.zzzyt.jade.game.drawable;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.zzzyt.jade.game.Drawable;
import com.zzzyt.jade.util.M;
import com.zzzyt.jade.util.U;

public class TiledScrollingBackground implements Drawable {

	private TiledDrawable tile;
	private float delta, scrollSpeed;
	private int zIndex;
	private float x, y;
	private float width;

	public TiledScrollingBackground(TextureRegion region, float scrollSpeed) {
		this(region, scrollSpeed, 0, -2048, 0, U.config().w);
	}

	public TiledScrollingBackground(TextureRegion region, float scrollSpeed, float transitionTime, int zIndex) {
		this(region, scrollSpeed, transitionTime, zIndex, 0, U.config().w);
	}

	public TiledScrollingBackground(TextureRegion region, float scrollSpeed, float transitionTime, int zIndex, float x,
			float width) {
		this.tile = new TiledDrawable(region);
		if (transitionTime <= 0) {
			this.delta = 0;
		} else {
			tile.getColor().a = 0;
			this.delta = 1 / transitionTime;
		}
		tile.setMinHeight(U.config().h);
		this.scrollSpeed = scrollSpeed;
		this.zIndex = zIndex;
		this.x = x;
		this.y = 0;
		this.width = width;
	}

	@Override
	public void draw(Batch batch) {
		tile.draw(batch, x - U.config().originX, y - U.config().originY - tile.getRegion().getRegionHeight(), width,
				U.config().h + tile.getRegion().getRegionHeight());
	}

	@Override
	public int getZIndex() {
		return zIndex;
	}

	public TiledScrollingBackground setScrollSpeed(float scroolSpeed) {
		this.scrollSpeed = scroolSpeed;
		return this;
	}

	@Override
	public void update(int t) {
		tile.getColor().a = MathUtils.clamp(tile.getColor().a + delta, 0, 1);
		y = M.safeMod(y - scrollSpeed, tile.getRegion().getRegionHeight());
	}

	public TiledScrollingBackground setAlpha(float alpha) {
		tile.getColor().a = alpha;
		return this;
	}

}
