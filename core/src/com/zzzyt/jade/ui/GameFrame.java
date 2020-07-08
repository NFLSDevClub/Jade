package com.zzzyt.jade.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zzzyt.jade.game.Jade;
import com.zzzyt.jade.util.J;

public class GameFrame extends Actor {

	private static final Color tempColor1 = new Color(), tempColor2 = new Color();

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
		if (jade != null) {
			tempColor1.set(batch.getColor());
			tempColor2.set(getColor());
			tempColor2.a *= parentAlpha;
			batch.setColor(tempColor2);
			batch.draw(jade.getFrameTexture(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
					getScaleX(), getScaleY(), getRotation());
			batch.setColor(tempColor1);
		}
	}
}
