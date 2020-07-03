package com.zzzyt.jade.ui.widget;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zzzyt.jade.ui.GridComponent;

public class GridImage extends Image implements GridComponent {

	private int gridX, gridY;
	private Runnable runnable;
	private boolean active;

	public GridImage(Texture texture, float x, float y, int gridX, int gridY, Runnable runnable) {
		super(texture);
		setPosition(x, y);
		this.gridX = gridX;
		this.gridY = gridY;
		this.runnable = runnable;
		deactivate();
	}

	@Override
	public GridComponent activate() {
		active = true;
		clearActions();
		addAction(Actions.sequence(Actions.moveBy(-10, 0, 0.1f, Interpolation.sine),
				Actions.moveBy(10, 0, 0.1f, Interpolation.sine)));
		setColor(1, 1, 1, 1f);
		return this;
	}

	@Override
	public GridComponent deactivate() {
		active = false;
		setColor(0.5f, 0.5f, 0.5f, 1f);
		return this;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void update() {
		if (active) {
			activate();
		} else {
			deactivate();
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
		runnable.run();
	}

}