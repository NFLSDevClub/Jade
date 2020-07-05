package com.zzzyt.jade.ui.widget;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zzzyt.jade.Jade;
import com.zzzyt.jade.util.J;

public class GameFrame extends Actor {

	private Jade jade;

	public GameFrame() {

	}

	public GameFrame(Jade jade) {
		this.jade = jade;
	}

	public void setJade(Jade jade) {
		this.jade = jade;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (jade == null)
			jade = J.getSession();
		if (jade != null)
			batch.draw(jade.getFrameTexture(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
					getScaleX(), getScaleY(), getRotation());
	}
}
