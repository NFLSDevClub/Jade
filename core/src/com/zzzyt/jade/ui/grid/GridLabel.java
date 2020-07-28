package com.zzzyt.jade.ui.grid;

import java.util.concurrent.Callable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.U;

public class GridLabel extends Label implements GridComponent {

	public boolean active;
	protected Grid parent;
	protected int gridX, gridY;
	protected float staticX, staticY;
	protected LabelStyle activeStyle, inactiveStyle;
	protected Callable<? extends Action> activeAction, inactiveAction;

	public GridLabel(CharSequence text, LabelStyle style) {
		super(text, style);
		this.activeStyle = style;
	}

	public GridLabel(CharSequence text, int fontSize, float x, float y, float width, float height, int gridX, int gridY,
			Callable<? extends Action> activeAction, Callable<? extends Action> inactiveAction, LabelStyle activeStyle,
			LabelStyle inactiveStyle) {
		super(text, new LabelStyle(A.getFont(U.config().UIFont, fontSize, 2, Color.BLACK), U.config().UIFontColor));
		this.staticX = x;
		this.staticY = y;
		this.gridX = gridX;
		this.gridY = gridY;
		this.active = false;
		this.activeStyle = activeStyle;
		this.inactiveStyle = inactiveStyle;
		this.activeAction = activeAction;
		this.inactiveAction = inactiveAction;
		setBounds(x, y, width, height);
	}

	public GridLabel(CharSequence text, int fontSize, float x, float y, float width, float height, int gridX,
			int gridY) {
		this(text, fontSize, x, y, width, height, gridX, gridY, null, null,
				new LabelStyle(A.getFont(U.config().UIFont, fontSize, 2, Color.BLACK), U.config().UIFontColor),
				new LabelStyle(A.getFont(U.config().UIFont, fontSize, 2, Color.BLACK), U.config().UIFontColor));
	}

	@Override
	public GridComponent setParent(Grid parent) {
		return null;
	}

	@Override
	public Grid getPartent() {
		return parent;
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
	public GridComponent enable() {
		return this;
	}

	@Override
	public GridComponent disable() {
		return this;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void update() {
		if (active) {
			if (activeStyle != null)
				setStyle(activeStyle);
			getActions().clear();
			if (activeAction != null) {
				try {
					addAction(activeAction.call());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if (inactiveStyle != null)
				setStyle(inactiveStyle);
			getActions().clear();
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

	}

	@Override
	public boolean hasSound() {
		return false;
	}

}
