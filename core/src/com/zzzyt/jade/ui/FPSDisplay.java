package com.zzzyt.jade.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.U;

public class FPSDisplay extends Actor {

	private BitmapFont font;
	private int counter;
	private String text;

	public FPSDisplay() {
		this.font = A.get("font/debug.fnt");
		this.counter = 0;
		this.text = "----- fps";
		setBounds(U.config().screenWidth - 100, 0, 100, 16);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		counter++;
		if (counter >= 4) {
			if (U.game.fpsCounter.hasEnoughData()) {
				text = String.format("%.2f fps", 1 / U.game.fpsCounter.getMean());
			}
			counter = 0;
		}
		font.draw(batch, text, getX(), getY() + getHeight());
	}
}
