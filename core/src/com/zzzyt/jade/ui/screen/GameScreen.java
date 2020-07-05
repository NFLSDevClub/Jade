package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.Gdx;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.Jade;
import com.zzzyt.jade.demo.difficulty.DifficultyAll;
import com.zzzyt.jade.demo.player.PlayerMarisa;
import com.zzzyt.jade.demo.player.PlayerReimu;
import com.zzzyt.jade.ui.widget.GameFrame;
import com.zzzyt.jade.util.G;
import com.zzzyt.jade.util.Game;

public class GameScreen extends BasicScreen {

	public Jade jade;

	private GameFrame frame;

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

		jade.setDifficulty(new DifficultyAll((String) G.get("_difficulty")));
	}

	@Override
	public void render(float delta) {
		jade.update();
		jade.draw();

		// Important!!!! Or viewport will become stretched.
		Gdx.gl20.glViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(),
				viewport.getScreenHeight());
		st.act();
		st.draw();
		jade.postRender();
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
