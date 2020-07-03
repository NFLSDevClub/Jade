package com.zzzyt.jade.ui.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.ui.GridComponent;
import com.zzzyt.jade.util.A;

public class GridLabel extends Label implements GridComponent{

	public Runnable runnable;
	public boolean active;
	
	private int gridX, gridY;
	private float staticX, staticY;
	private LabelStyle activeStyle, inactiveStyle;
	private Action activeAction, inactiveAction;

	public GridLabel(CharSequence text, LabelStyle activeStyle) {
		super(text, activeStyle);
		this.activeStyle = activeStyle;
	}

	public GridLabel(CharSequence text, int fontSize, float x, float y, float width, float height, int gridX,
			int gridY, Runnable runnable) {
		super(text, new LabelStyle(A.getFont(Config.UIFont, 24, 2, Color.BLACK), Config.UIFontColor));
		this.staticX = x;
		this.staticY = y;
		this.gridX = gridX;
		this.gridY = gridY;
		this.runnable = runnable;
		this.active = false;
		this.activeStyle = new LabelStyle(A.getFont(Config.UIFont, 24, 2, Color.BLACK), Config.UIFontColor);
		this.inactiveStyle = new LabelStyle(A.getFont(Config.UIFont, 24, 2, Color.BLACK), Config.UIFontColor);
		this.activeAction = Actions.parallel(
				Actions.forever(Actions.sequence(Actions.alpha(0.6f, 0.1f, Interpolation.fade),
						Actions.alpha(1f, 0.1f, Interpolation.fade))),
				Actions.sequence(Actions.moveBy(-10, 0, 0.1f, Interpolation.sine),
						Actions.moveBy(10, 0, 0.1f, Interpolation.sine)));
		this.inactiveAction = Actions.parallel(Actions.alpha(1f),
				Actions.moveTo(staticX, staticY, 0.1f, Interpolation.sine));
		setBounds(x, y, width, height);
	}

	@Override
	public void trigger() {
		runnable.run();
	}

	@Override
	public void update() {
		setPosition(staticX, staticY);
		if (active) {
			if (activeStyle != null)
				setStyle(activeStyle);
			setColor(activeStyle.fontColor);
			getActions().clear();
			if (activeAction != null)
				addAction(activeAction);
			activeAction.restart();
		} else {
			if (inactiveStyle != null)
				setColor(inactiveStyle.fontColor);
			setStyle(inactiveStyle);
			getActions().clear();
			if (inactiveAction != null)
				addAction(inactiveAction);
			inactiveAction.restart();
		}
	}

	@Override
	public GridLabel activate() {
		active = true;
		update();
		return this;
	}

	@Override
	public GridLabel deactivate() {
		active = false;
		update();
		return this;
	}

	public GridLabel setActiveStyle(LabelStyle style) {
		activeStyle = style;
		update();
		return this;
	}

	public GridLabel setInactiveStyle(LabelStyle style) {
		inactiveStyle = style;
		update();
		return this;
	}

	public GridLabel setActiveAction(Action action) {
		activeAction = action;
		update();
		return this;
	}

	public GridLabel setInactiveAction(Action action) {
		inactiveAction = action;
		update();
		return this;
	}

	public Label setAction(Runnable action) {
		this.runnable = action;
		return this;
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
	public boolean isActive() {
		return active;
	}

}
