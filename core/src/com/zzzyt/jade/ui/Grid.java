package com.zzzyt.jade.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.util.U;

public class Grid extends Group implements InputProcessor {

	public List<GridComponent> grid;
	public int x, y;
	public boolean cycle;

	private int minX, minY, maxX, maxY;
	private boolean enabled;

	public Grid(boolean cycle) {
		this.cycle = cycle;
		grid = new ArrayList<GridComponent>();
		this.x = 0;
		this.y = 0;
		this.minX = Integer.MAX_VALUE;
		this.minY = Integer.MAX_VALUE;
		this.maxX = Integer.MIN_VALUE;
		this.maxY = Integer.MIN_VALUE;
		this.enabled = true;
	}

	public GridComponent add(GridComponent component) {
		grid.add(component);
		minX = Math.min(minX, component.getGridX());
		minY = Math.min(minY, component.getGridY());
		maxX = Math.max(maxX, component.getGridX());
		maxY = Math.max(maxY, component.getGridY());
		if (component instanceof Actor) {
			addActor((Actor) component);
		}
		return component;
	}

	public void selectFirst() {
		if (grid.size() == 0)
			return;
		GridComponent button = grid.get(0);
		x = button.getGridX();
		y = button.getGridY();
		select(x,y);
	}

	public void selectLast() {
		if (grid.size() == 0)
			return;
		GridComponent button = grid.get(grid.size() - 1);
		x = button.getGridX();
		y = button.getGridY();
		select(x,y);
	}

	public void select(int nx, int ny) {
		GridComponent closest = null;
		int dist = Integer.MAX_VALUE;
		for (GridComponent button : grid) {
			if (button.isActive()) {
				button.deactivate();
			}
			if (distance(nx, ny, button.getGridX(), button.getGridY()) < dist) {
				closest = button;
				dist = distance(nx, ny, button.getGridX(), button.getGridY());
			}
		}
		if (closest == null) {
			return;
		}
		x = closest.getGridX();
		y = closest.getGridY();
		for (GridComponent button : grid) {
			if (button.getGridX() == x && button.getGridY() == y) {
				button.activate();
			}
		}
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
		if (!enabled)
			return false;
		if (U.matchKey(keycode, Config.keyUp)) {
			select(x, y - 1);
		} else if (U.matchKey(keycode, Config.keyDown)) {
			select(x, y + 1);
		} else if (U.matchKey(keycode, Config.keyLeft)) {
			select(x - 1, y);
		} else if (U.matchKey(keycode, Config.keyRight)) {
			select(x + 1, y);
		} else if (U.matchKey(keycode, Config.keySelect)) {
			for (GridComponent button : grid) {
				if(button.isActive()) {
					button.trigger();					
				}
			}
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

	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}

	public boolean isEnabled() {
		return enabled;
	}
}
