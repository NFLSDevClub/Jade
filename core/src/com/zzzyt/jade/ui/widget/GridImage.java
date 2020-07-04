package com.zzzyt.jade.ui.widget;

import java.util.concurrent.Callable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zzzyt.jade.ui.GridComponent;

public class GridImage extends Image implements GridComponent {

	protected float staticX, staticY;
	protected int gridX, gridY;
	protected Runnable runnable;
	protected boolean active;
	protected Callable<? extends Action> activeAction, inactiveAction;

	public GridImage(TextureRegion texture, float x, float y, int gridX, int gridY, Runnable runnable) {
		super(texture);
		setPosition(x, y);
		this.staticX = x;
		this.staticY = y;
		this.gridX = gridX;
		this.gridY = gridY;
		this.runnable = runnable;
		this.activeAction = () -> Actions.sequence(Actions.moveTo(staticX, staticY), Actions.color(Color.WHITE),
				Actions.sequence(Actions.moveTo(staticX - 10, staticY, 0.1f, Interpolation.sine),
						Actions.moveTo(staticX, staticY, 0.1f, Interpolation.sine)));
		this.inactiveAction = () -> Actions.forever(Actions.color(Color.GRAY));
		deactivate();
	}

	public GridImage(TextureRegion texture, float x, float y, int gridX, int gridY, Callable<? extends Action> activeAction,
			Callable<? extends Action> inactiveAction, Runnable runnable) {
		super(texture);
		setPosition(x, y);
		this.staticX = x;
		this.staticY = y;
		this.gridX = gridX;
		this.gridY = gridY;
		this.runnable = runnable;
		this.activeAction = activeAction;
		this.inactiveAction = inactiveAction;
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
		if (active) {
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
		if (runnable != null) {
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

}