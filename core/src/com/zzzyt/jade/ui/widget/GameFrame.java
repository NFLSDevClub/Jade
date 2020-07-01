package com.zzzyt.jade.ui.widget;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zzzyt.jade.Jade;

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
			jade = Jade.session;
		if (jade != null)
			batch.draw(jade.getFrame(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
					getScaleX(), getScaleY(), getRotation());
	}
}
