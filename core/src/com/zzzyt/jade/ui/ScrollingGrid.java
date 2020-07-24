package com.zzzyt.jade.ui;

import java.util.concurrent.Callable;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ScrollingGrid extends Grid {

	protected Rectangle region;
	protected float offsetX, offsetY;

	public ScrollingGrid(boolean cycle, Rectangle region) {
		super(cycle);
		this.region = region;
		setCullingArea(new Rectangle(region));
		this.offsetX = 0;
		this.offsetY = 0;
	}

	public ScrollingGrid(int gridX, int gridY, boolean cycle, Rectangle region, Callable<? extends Action> activeAction,
			Callable<? extends Action> inactiveAction) {
		super(gridX, gridY, cycle, activeAction, inactiveAction);
		this.region = region;
		setCullingArea(new Rectangle(region));
		this.offsetX = 0;
		this.offsetY = 0;
	}

	@Override
	public void act(float delta) {
		for (GridComponent component : grid) {
			if (component.isActive() && component instanceof Actor) {
				Actor actor = (Actor) component;
				float dx = calculateDelta(actor.getX() + offsetX, actor.getWidth(), region.getX(), region.getWidth());
				float dy = calculateDelta(actor.getY() + offsetY, actor.getHeight(), region.getY(), region.getHeight());
				if (dx == 0 && dy == 0)
					continue;
				offsetX -= dx;
				offsetY -= dy;
				break;
			}
		}
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Rectangle cullingArea = getCullingArea();
		float tmpX1 = getX(), tmpY1 = getY();
		float tmpX2 = cullingArea.getX(), tmpY2 = cullingArea.getY();
		setPosition(tmpX1 + offsetX, tmpY1 + offsetY);
		cullingArea.setPosition(tmpX2 - offsetX, tmpY2 - offsetY);
		super.draw(batch, parentAlpha);
		setPosition(tmpX1, tmpY1);
		cullingArea.setPosition(tmpX2, tmpY2);
	}

	private float calculateDelta(float x, float width, float x2, float width2) {
		if (x < x2) {
			return x - x2;
		}
		if (x + width > x2 + width2) {
			return x + width - x2 - width2;
		}
		return 0;
	}

}
