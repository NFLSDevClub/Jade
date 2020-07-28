package com.zzzyt.jade.ui.grid;

import java.util.concurrent.Callable;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.zzzyt.jade.util.SE;
import com.zzzyt.jade.util.U;

/**
 * Noisy Grid is a grid with SE supported
 * @author XGN
 *
 */
public class NoisyGrid extends Grid {

	public NoisyGrid(boolean cycle) {
		super(cycle);
	}
	
	public NoisyGrid(int gridX, int gridY, boolean cycle, Callable<? extends Action> activeAction,
			Callable<? extends Action> inactiveAction) {
		super(gridX, gridY, cycle, activeAction, inactiveAction);
	}

	@Override
	public boolean keyDown(int keycode) {
		boolean val=super.keyDown(keycode);
		if (U.matchKey(keycode, U.config().keyUp)) {
			SE.play("select");
		} else if (U.matchKey(keycode, U.config().keyDown)) {
			SE.play("select");
		} else if (U.matchKey(keycode, U.config().keyLeft)) {
			SE.play("select");
		} else if (U.matchKey(keycode, U.config().keyRight)) {
			SE.play("select");
		} else if (U.matchKey(keycode, U.config().keySelect)) {
			SE.play("ok");
		} else if (U.matchKey(keycode, U.config().keyCancel)) {
			SE.play("cancel");
		}
		return val;
	}
	
	@Override
	public boolean fire() {
		boolean success=super.fire();
		if(success) {
			SE.play("ok");
		}else {
			SE.play("invalid");
		}
		
		return success;
	}
}
