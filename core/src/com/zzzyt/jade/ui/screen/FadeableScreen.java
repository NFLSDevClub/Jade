package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.Screen;

public interface FadeableScreen extends Screen {

	public boolean fadeOut(float duration);

	public boolean fadeIn(float duration);

	public ScreenState getState();
	
	public void setState(ScreenState state);
	
	public String getName();

}
