package com.zzzyt.jade.ui;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.util.Utils;

public class Grid extends Group implements InputProcessor{

	public List<GridComponent> grid;
	public int x, y;
	public boolean cycle;

	private GridComponent current;
	private int minX, minY, maxX, maxY;

	public Grid(boolean cycle) {
		this.cycle = cycle;
		grid = new ArrayList<GridComponent>();
		this.x = 0;
		this.y = 0;
		this.minX = Integer.MAX_VALUE;
		this.minY = Integer.MAX_VALUE;
		this.maxX = Integer.MIN_VALUE;
		this.maxY = Integer.MIN_VALUE;
		this.current = null;
	}

	public GridComponent add(GridComponent component) {
		grid.add(component);
		minX = Math.min(minX, component.getGridX());
		minY = Math.min(minY, component.getGridY());
		maxX = Math.max(maxX, component.getGridX());
		maxY = Math.max(maxY, component.getGridY());
		if(component instanceof Actor) {
			addActor((Actor)component);
		}
		return component;
	}

	public void selectFirst() {
		if (grid.size() == 0)
			return;
		GridComponent button = grid.get(0);
		button.activate();
		current = button;
		x = button.getGridX();
		y = button.getGridY();
	}

	public void selectLast() {
		if (grid.size() == 0)
			return;
		GridComponent button = grid.get(grid.size() - 1);
		button.activate();
		current = button;
		x = button.getGridX();
		y = button.getGridY();
	}

	public void select(int nx, int ny) {
		GridComponent closest = null;
		int dist = Integer.MAX_VALUE;
		for (GridComponent button : grid) {
			if (distance(nx, ny, button.getGridX(), button.getGridY()) < dist) {
				closest = button;
				dist = distance(nx, ny, button.getGridX(), button.getGridY());
			}
		}
		if (current != null)
			current.deactivate();
		if (closest != null)
			closest.activate();
		current = closest;
		x = closest.getGridX();
		y = closest.getGridY();
	}

	public void updateAll() {
		for (GridComponent button : grid) {
			button.update();
		}
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
				current.trigger();
		}
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
