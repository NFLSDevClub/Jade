package com.zzzyt.jade.ui;

import com.badlogic.gdx.InputAdapter;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.util.Utils;

public class QuitListener extends InputAdapter {

	private Runnable runnable;

	public QuitListener(Runnable runnable) {
		this.runnable = runnable;
	}

	public boolean keyDown(int keycode) {
		if (Utils.matchKey(keycode, Config.keyCancel)) {
			runnable.run();
		}
		return false;
	}
}
