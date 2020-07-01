package com.zzzyt.jade.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class InputBlocker implements InputProcessor {

	private boolean blocking;

	public InputBlocker() {
		this.blocking = false;
	}

	public void enable() {
		if (!blocking)
			Gdx.app.log("InputBlocker", "Blocking enabled.");
		this.blocking = true;
	}

	public void disable() {
		if (blocking)
			Gdx.app.log("InputBlocker", "Blocking disabled.");
		this.blocking = false;
	}

	public boolean isBlocking() {
		return blocking;
	}

	@Override
	public boolean keyDown(int keycode) {
		return blocking;
	}

	@Override
	public boolean keyUp(int keycode) {
		return blocking;
	}

	@Override
	public boolean keyTyped(char character) {
		return blocking;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return blocking;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return blocking;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return blocking;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return blocking;
	}

	@Override
	public boolean scrolled(int amount) {
		return blocking;
	}

}
