package com.zzzyt.jade.ui.screen;

public enum ScreenState {
	SHOWN, FADING_IN, FADING_OUT, HIDDEN;

	public boolean isRendered() {
		return this != HIDDEN;
	}

	public boolean isFading() {
		return this == FADING_IN || this == FADING_OUT;
	}
}
