package com.zzzyt.jade.ui.screen;

public enum ScreenState {
	SHOWN, FADING_IN, FADING_OUT, HIDDEN;

	public boolean isRendered() {
		if (this == HIDDEN)
			return false;
		return true;
	}

	public boolean isFading() {
		if (this == FADING_IN || this == FADING_OUT)
			return true;
		return false;
	}
}
