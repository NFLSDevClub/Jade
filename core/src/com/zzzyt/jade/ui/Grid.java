package com.zzzyt.jade.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.zzzyt.jade.util.U;

public class Grid extends Group implements InputProcessor, GridComponent {

	public List<GridComponent> grid;
	public int x, y;
	public boolean cycle;

	protected Grid parent;
	protected Runnable onTrigger;
	protected Callable<? extends Action> activeAction, inactiveAction;
	protected int gridX, gridY;
	protected int minX, minY, maxX, maxY;
	protected boolean enabled, active;

	public Grid(boolean cycle) {
		this(0, 0, cycle, null, null, null);
	}

	public Grid(int gridX, int gridY, boolean cycle, Runnable onTrigger, Callable<? extends Action> activeAction,
			Callable<? extends Action> inactiveAction) {
		this.cycle = cycle;
		this.gridX = gridX;
		this.gridY = gridY;
		this.onTrigger = onTrigger;
		this.activeAction = activeAction;
		this.inactiveAction = inactiveAction;
		grid = new ArrayList<GridComponent>();
		this.x = 0;
		this.y = 0;
		this.minX = Integer.MAX_VALUE;
		this.minY = Integer.MAX_VALUE;
		this.maxX = Integer.MIN_VALUE;
		this.maxY = Integer.MIN_VALUE;
		this.enabled = true;
		deactivate();
	}

	public GridComponent add(GridComponent component) {
		grid.add(component);
		component.setParent(this);
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
		select(x, y);
	}

	public void selectLast() {
		if (grid.size() == 0)
			return;
		GridComponent button = grid.get(grid.size() - 1);
		x = button.getGridX();
		y = button.getGridY();
		select(x, y);
	}

	public void select(int nx, int ny, int dx, int dy) {
		GridComponent closest = null;
		int dist = Integer.MAX_VALUE;
		for (GridComponent button : grid) {
			if (button.isEnabled()) {
				if (dx != 0) {
					if (distanceX(nx, button.getGridX(), dx) < dist) {
						closest = button;
						dist = distanceX(nx, button.getGridX(), dx);
					}
				} else {
					if (distanceY(ny, button.getGridY(), dy) < dist) {
						closest = button;
						dist = distanceY(ny, button.getGridY(), dy);
					}
				}
			}
		}
		if (closest == null) {
			return;
		}
		for (GridComponent button : grid) {
			if (button.isActive()) {
				button.deactivate();
			}
		}
		x = closest.getGridX();
		y = closest.getGridY();
		for (GridComponent button : grid) {
			if (button.isEnabled() && button.getGridX() == x && button.getGridY() == y) {
				button.activate();
			}
		}
	}

	public void select(int nx, int ny) {
		GridComponent closest = null;
		int dist = Integer.MAX_VALUE;
		for (GridComponent button : grid) {
			if (button.isEnabled()) {
				if (distance(nx, ny, button.getGridX(), button.getGridY()) < dist) {
					closest = button;
					dist = distance(nx, ny, button.getGridX(), button.getGridY());
				}
			}
		}
		if (closest == null) {
			return;
		}
		for (GridComponent button : grid) {
			if (button.isActive()) {
				button.deactivate();
			}
		}
		x = closest.getGridX();
		y = closest.getGridY();
		for (GridComponent button : grid) {
			if (button.isEnabled() && button.getGridX() == x && button.getGridY() == y) {
				button.activate();
			}
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

	private int distanceX(int x1, int x2, int dx) {
		if (cycle) {
			if (x1 < minX)
				x1 = maxX;
			if (x1 > maxX)
				x1 = minX;
			if (dx > 0) {
				if (x1 <= x2) {
					return x2 - x1;
				} else {
					return x2 - x1 + maxX - minX + 1;
				}
			} else {
				if (x1 >= x2) {
					return x1 - x2;
				} else {
					return x1 - x2 + maxX - minX + 1;
				}
			}
		} else {
			if (dx > 0) {
				if (x1 <= x2) {
					return x2 - x1;
				} else {
					return Integer.MAX_VALUE;
				}
			} else {
				if (x1 >= x2) {
					return x1 - x2;
				} else {
					return Integer.MAX_VALUE;
				}
			}
		}
	}

	private int distanceY(int y1, int y2, int dy) {
		if (cycle) {
			if (y1 < minY)
				y1 = maxY;
			if (y1 > maxY)
				y1 = minY;
			if (dy > 0) {
				if (y1 <= y2) {
					return y2 - y1;
				} else {
					return y2 - y1 + maxY - minY + 1;
				}
			} else {
				if (y1 >= y2) {
					return y1 - y2;
				} else {
					return y1 - y2 + maxY - minY + 1;
				}
			}
		} else {
			if (dy > 0) {
				if (y1 <= y2) {
					return y2 - y1;
				} else {
					return Integer.MAX_VALUE;
				}
			} else {
				if (y1 >= y2) {
					return y1 - y2;
				} else {
					return Integer.MAX_VALUE;
				}
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (!enabled || !active)
			return false;
		if (U.matchKey(keycode, U.getConfig().keyUp)) {
			select(x, y - 1, 0, -1);
		} else if (U.matchKey(keycode, U.getConfig().keyDown)) {
			select(x, y + 1, 0, 1);
		} else if (U.matchKey(keycode, U.getConfig().keyLeft)) {
			select(x - 1, y, -1, 0);
		} else if (U.matchKey(keycode, U.getConfig().keyRight)) {
			select(x + 1, y, 1, 0);
		} else if (U.matchKey(keycode, U.getConfig().keySelect)) {
			for (GridComponent button : grid) {
				if (button.isActive()) {
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

	@Override
	public Grid enable() {
		enabled = true;
		update();
		return this;
	}

	@Override
	public Grid disable() {
		enabled = false;
		update();
		return this;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public Grid activate() {
		active = true;
		update();
		return this;
	}

	@Override
	public Grid deactivate() {
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
		for (GridComponent button : grid) {
			button.update();
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
		if(enabled) {
			if (parent != null) {
				parent.disable();
			}
			if (onTrigger != null) {
				onTrigger.run();
			}			
		}
	}

	@Override
	public Grid setParent(Grid parent) {
		this.parent = parent;
		return this;
	}

	@Override
	public Grid getPartent() {
		return parent;
	}
}
