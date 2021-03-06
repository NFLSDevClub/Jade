package com.zzzyt.jade.ui.grid;

import java.util.concurrent.Callable;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.util.SE;
import com.zzzyt.jade.util.U;

public class Grid extends Group implements InputProcessor, GridComponent {

	public static boolean globalLock = false;

	public Array<GridComponent> grid;
	public int selectedX, selectedY;
	public boolean cycle;

	protected Grid parent;
	protected Callable<? extends Action> activeAction, inactiveAction;
	protected int gridX, gridY;
	protected int minX, minY, maxX, maxY;
	protected boolean enabled, active, hasSound;

	public Grid(boolean cycle) {
		this(0, 0, cycle, true, null, null);
	}

	public Grid(int gridX, int gridY, boolean cycle, boolean hasSound, Callable<? extends Action> activeAction,
			Callable<? extends Action> inactiveAction) {
		this.cycle = cycle;
		this.gridX = gridX;
		this.gridY = gridY;
		this.activeAction = activeAction;
		this.inactiveAction = inactiveAction;
		grid = new Array<GridComponent>();
		this.selectedX = 0;
		this.selectedY = 0;
		this.minX = Integer.MAX_VALUE;
		this.minY = Integer.MAX_VALUE;
		this.maxX = Integer.MIN_VALUE;
		this.maxY = Integer.MIN_VALUE;
		this.enabled = true;
		this.hasSound = hasSound;
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
		if (grid.size == 0)
			return;
		GridComponent component = grid.get(0);
		selectedX = component.getGridX();
		selectedY = component.getGridY();
		select(selectedX, selectedY);
	}

	public void selectLast() {
		if (grid.size == 0)
			return;
		GridComponent component = grid.get(grid.size - 1);
		selectedX = component.getGridX();
		selectedY = component.getGridY();
		select(selectedX, selectedY);
	}

	public void select(int nx, int ny, int dx, int dy) {
		GridComponent closest = null;
		int dist = Integer.MAX_VALUE;
		for (GridComponent component : grid) {
			if (component.isEnabled()) {
				if (dx != 0) {
					if (component.getGridY() == selectedY && distanceX(nx, component.getGridX(), dx) < dist) {
						closest = component;
						dist = distanceX(nx, component.getGridX(), dx);
					}
				} else {
					if (component.getGridX() == selectedX && distanceY(ny, component.getGridY(), dy) < dist) {
						closest = component;
						dist = distanceY(ny, component.getGridY(), dy);
					}
				}
			}
		}
		if (closest == null) {
			return;
		}
		if (closest.getGridX() != selectedX || closest.getGridY() != selectedY) {
			SE.play("select");
		}
		selectedX = closest.getGridX();
		selectedY = closest.getGridY();
		for (GridComponent component : grid) {
			if (component.isActive() && component.isEnabled()
					&& (component.getGridX() != selectedX || component.getGridY() != selectedY)) {
				component.deactivate();
			}
			if (!component.isActive() && component.isEnabled() && component.getGridX() == selectedX
					&& component.getGridY() == selectedY) {
				component.activate();
			}
		}
	}

	public void select(int nx, int ny) {
		GridComponent closest = null;
		int dist = Integer.MAX_VALUE;
		for (GridComponent component : grid) {
			if (component.isEnabled()) {
				if (distance(nx, ny, component.getGridX(), component.getGridY()) < dist) {
					closest = component;
					dist = distance(nx, ny, component.getGridX(), component.getGridY());
				}
			}
		}
		if (closest == null) {
			return;
		}
		if (closest.getGridX() != selectedX || closest.getGridY() != selectedY) {
			SE.play("select");
		}
		selectedX = closest.getGridX();
		selectedY = closest.getGridY();
		for (GridComponent component : grid) {
			if (component.isActive() && component.isEnabled()
					&& (component.getGridX() != selectedX || component.getGridY() != selectedY)) {
				component.deactivate();
			}
			if (!component.isActive() && component.isEnabled() && component.getGridX() == selectedX
					&& component.getGridY() == selectedY) {
				component.activate();
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
		if (!enabled || !active || globalLock)
			return false;
		if (U.matchKey(keycode, U.config().keyUp)) {
			select(selectedX, selectedY - 1, 0, -1);
		} else if (U.matchKey(keycode, U.config().keyDown)) {
			select(selectedX, selectedY + 1, 0, 1);
		} else if (U.matchKey(keycode, U.config().keyLeft)) {
			select(selectedX - 1, selectedY, -1, 0);
		} else if (U.matchKey(keycode, U.config().keyRight)) {
			select(selectedX + 1, selectedY, 1, 0);
		} else if (U.matchKey(keycode, U.config().keySelect)) {
			return fire();
		} else if (U.matchKey(keycode, U.config().keyCancel)) {
			exit();
		}
		return false;
	}

	/**
	 * Trigger the current button in the grid <br/>
	 * 
	 * @return Success?
	 */
	public boolean fire() {
		boolean flag = false;
		boolean flag2 = false;
		for (GridComponent button : grid) {
			if (button.isActive()) {
				button.trigger();
				flag = true;
				if (button.hasSound()) {
					flag2 = true;
				}
			}
		}
		if (flag) {
			globalLock = true;
		}
		if (flag2) {
			SE.play("ok");
		}
		return flag;
	}

	public void exit() {
		if (parent != null) {
			globalLock = true;
			if (hasSound) {
				SE.play("cancel");
			}
			parent.enable();
			disable();
			deactivate();
		}
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

	public void updateComponent() {
		for (GridComponent component : grid) {
			if (component instanceof Grid) {
				((Grid) component).updateComponent();
			} else {
				component.update();
			}
		}
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

	public Grid setGridX(int gridX) {
		this.gridX = gridX;
		return this;
	}

	public Grid setGridY(int gridY) {
		this.gridY = gridY;
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
	public void trigger() {

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

	@Override
	public boolean hasSound() {
		return hasSound;
	}
}
