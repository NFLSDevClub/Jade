package com.zzzyt.jade.ui.widget;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.Game;

public class FPSDisplay extends Actor {

	private BitmapFont font;

	public FPSDisplay() {
		this.font = A.get("font/debug.fnt");
		setPosition(Config.windowWidth - 72, 15);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (Game.game.fpsCounter.hasEnoughData()) {
			font.draw(batch, String.format("%.2f fps", 1 / Game.game.fpsCounter.getMean()), getX(), getY());
		} else {
			font.draw(batch, "----- fps", getX(), getY());
		}
	}
}
