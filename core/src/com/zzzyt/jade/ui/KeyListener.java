package com.zzzyt.jade.ui;

import com.badlogic.gdx.InputAdapter;
import com.zzzyt.jade.util.U;

public class KeyListener extends InputAdapter {

	private Runnable runnable;
	private int[] keycodes;

	public KeyListener(int[] keycodes, Runnable runnable) {
		this.runnable = runnable;
		this.keycodes = keycodes;
	}

	public boolean keyDown(int keycode) {
		if (U.matchKey(keycode, keycodes)) {
			runnable.run();
		}
		return false;
	}
}
