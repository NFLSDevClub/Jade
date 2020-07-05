package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.Gdx;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.Jade;
import com.zzzyt.jade.demo.player.PlayerMarisa;
import com.zzzyt.jade.demo.player.PlayerReimu;
import com.zzzyt.jade.ui.widget.GameFrame;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.G;
import com.zzzyt.jade.util.Game;
import com.zzzyt.jade.util.M;

public class GameScreen extends BasicScreen {

	public Jade jade;

	private GameFrame frame;

	private float tmpf;

	public GameScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/Idea12.ogg", "bg/game.png");

		this.frame = new GameFrame();
		frame.setBounds(Config.offsetX, Config.offsetY, Config.w, Config.h);
		st.addActor(frame);

		this.jade = new Jade();

		if ("reimu".equals(G.get("_player"))) {
			jade.setPlayer(new PlayerReimu());
		} else if ("marisa".equals(G.get("_player"))) {
			jade.setPlayer(new PlayerMarisa());
		}

		frame.setJade(jade);

		tmpf = 72;
	}

	@Override
	public void render(float delta) {
		if (Jade.session.frame % diffToChance((String) G.get("_difficulty")) == 0) {
			tmpf += M.sin(Jade.session.frame) * 6;
			for (int i = 0; i < 360; i += 72) {
				B.as(0, -100, i + tmpf, 2, "DS_MISSILE_RED", 0);
				B.as(0, -100, i - tmpf, 2, "DS_MISSILE_BLUE", 0);
			}
		}

		jade.update();
		jade.draw();

		// Important!!!! Or viewport will become stretched.
		Gdx.gl20.glViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(),
				viewport.getScreenHeight());
		st.act();
		st.draw();
		jade.postRender();
	}

	private int diffToChance(String str) {
		switch (str) {
		case "easy":
			return 16;
		case "normal":
			return 10;
		case "hard":
			return 5;
		case "lunatic":
			return 2;
		default:
			return 0;
		}
	}

	@Override
	protected void onQuit() {

	}

	@Override
	public void hide() {
		Game.removeProcessor(input);
		state = ScreenState.HIDDEN;
		if (jade != null)
			jade.dispose();
		if (st != null)
			st.dispose();
	}

	@Override
	public String getName() {
		return "game";
	}

}
