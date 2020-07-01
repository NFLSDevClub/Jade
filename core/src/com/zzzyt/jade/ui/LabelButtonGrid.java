package com.zzzyt.jade.ui;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.ui.widget.LabelButton;
import com.zzzyt.jade.util.Utils;

public class LabelButtonGrid extends InputAdapter {

	public List<LabelButton> buttons;
	public int x, y;
	public boolean cycle;

	private LabelButton current;
	private int minX, minY, maxX, maxY;

	public LabelButtonGrid(boolean cycle) {
		this.cycle = cycle;
		buttons = new ArrayList<LabelButton>();
		this.x = 0;
		this.y = 0;
		this.minX = Integer.MAX_VALUE;
		this.minY = Integer.MAX_VALUE;
		this.maxX = Integer.MIN_VALUE;
		this.maxY = Integer.MIN_VALUE;
		this.current = null;
	}

	public LabelButton add(LabelButton button) {
		buttons.add(button);
		minX = Math.min(minX, button.gridX);
		minY = Math.min(minY, button.gridY);
		maxX = Math.max(maxX, button.gridX);
		maxY = Math.max(maxY, button.gridY);
		return button;
	}

	public LabelButton add(LabelButton button, int gridX, int gridY) {
		button.gridX = gridX;
		button.gridY = gridY;
		buttons.add(button);
		minX = Math.min(minX, button.gridX);
		minY = Math.min(minY, button.gridY);
		maxX = Math.max(maxX, button.gridX);
		maxY = Math.max(maxY, button.gridY);
		return button;
	}

	public void selectFirst() {
		if (buttons.size() == 0)
			return;
		LabelButton button = buttons.get(0);
		button.setActive();
		current = button;
		x = button.gridX;
		y = button.gridY;
	}

	public void selectLast() {
		if (buttons.size() == 0)
			return;
		LabelButton button = buttons.get(buttons.size() - 1);
		button.setActive();
		current = button;
		x = button.gridX;
		y = button.gridY;
	}

	public void select(int nx, int ny) {
		LabelButton closest = null;
		int dist = Integer.MAX_VALUE;
		for (LabelButton button : buttons) {
			if (distance(nx, ny, button.gridX, button.gridY) < dist) {
				closest = button;
				dist = distance(nx, ny, button.gridX, button.gridY);
			}
		}
		if (current != null)
			current.setInactive();
		if (closest != null)
			closest.setActive();
		current = closest;
		x = closest.gridX;
		y = closest.gridY;
	}

	public void updateAll() {
		for (LabelButton button : buttons) {
			button.update();
		}
	}

	public void addToStage(Stage st) {
		for (LabelButton button : buttons) {
			st.addActor(button);
		}
	}

	public void addToGroup(Group g) {
		for (LabelButton button : buttons) {
			g.addActor(button);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (Utils.matchKey(keycode, Config.keyUp)) {
			select(x, y - 1);
		} else if (Utils.matchKey(keycode, Config.keyDown)) {
			select(x, y + 1);
		} else if (Utils.matchKey(keycode, Config.keyLeft)) {
			select(x - 1, y);
		} else if (Utils.matchKey(keycode, Config.keyRight)) {
			select(x + 1, y);
		} else if (Utils.matchKey(keycode, Config.keySelect)) {
			if (current != null)
				current.run();
		}
		return false;
	}

	private int distance(int x1, int y1, int x2, int y2) {
		if (cycle) {
			if (x1 < minX)
				x1 = maxX;
			if (x1 > maxX)
				x1 = minX;
			if (y1 < minY)
				y1 = maxY;
			if (y1 > maxY)
				y1 = minY;
		}
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
}
