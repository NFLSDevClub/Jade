package com.zzzyt.jade.ui.grid;

import java.util.concurrent.Callable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GridImage extends Image implements GridComponent {

	protected Grid parent;
	protected float staticX, staticY;
	protected int gridX, gridY;
	protected Runnable runnable;
	protected boolean active, enabled;
	protected Callable<? extends Action> activeAction, inactiveAction;

	public GridImage(TextureRegion texture, float x, float y, int gridX, int gridY, Runnable runnable) {
		this(texture,x,y,gridX,gridY,null,null,runnable);
		setActiveAction(() -> Actions.sequence(
				Actions.moveTo(staticX, staticY), Actions.color(Color.WHITE),
				Actions.sequence(
						Actions.moveTo(staticX - 10, staticY, 0.1f, Interpolation.sine),
						Actions.moveTo(staticX, staticY, 0.1f, Interpolation.sine))));
		setInactiveAction(() -> Actions.forever(
				Actions.color(Color.GRAY)));
	}

	public GridImage(TextureRegion texture, float x, float y, int gridX, int gridY,
			Callable<? extends Action> activeAction, Callable<? extends Action> inactiveAction, Runnable runnable) {
		super(texture);
		setPosition(x, y);
		this.staticX = x;
		this.staticY = y;
		this.gridX = gridX;
		this.gridY = gridY;
		this.runnable = runnable;
		this.activeAction = activeAction;
		this.inactiveAction = inactiveAction;
		this.enabled = true;
		deactivate();
	}

	@Override
	public GridComponent activate() {
		active = true;
		update();
		return this;
	}

	@Override
	public GridComponent deactivate() {
		active = false;
		update();
		return this;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void update() {
		if (enabled) {
			setColor(Color.WHITE);
		} else {
			setColor(Color.GRAY);
		}
		if (enabled && active) {
			clearActions();
			if (activeAction != null) {
				try {
					addAction(activeAction.call());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			clearActions();
			if (inactiveAction != null) {
				try {
					addAction(inactiveAction.call());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public int getGridX() {
		return gridX;
	}

	@Override
	public int getGridY() {
		return gridY;
	}

	@Override
	public void trigger() {
		if (enabled && runnable != null) {
			runnable.run();
		}
	}

	public GridImage setActiveAction(Callable<? extends Action> activeAction) {
		this.activeAction = activeAction;
		update();
		return this;
	}

	public GridImage setInactiveAction(Callable<? extends Action> inactiveAction) {
		this.inactiveAction = inactiveAction;
		update();
		return this;
	}

	@Override
	public GridImage enable() {
		this.enabled = true;
		update();
		return null;
	}

	@Override
	public GridImage disable() {
		this.enabled = false;
		update();
		return this;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public GridImage setParent(Grid parent) {
		this.parent = parent;
		return this;
	}

	@Override
	public Grid getPartent() {
		return parent;
	}

}