package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.ui.widget.LabelButton;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.G;
import com.zzzyt.jade.util.Game;

public class PlayerSelectScreen extends BasicScreen {

	private Grid grid;

	public PlayerSelectScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/Idea29.ogg",A.get("bg/select_bg.png"));
		
		this.grid = new Grid(true);
		st.addActor(grid);
		
		grid.add(new LabelButton("Hakurei Reimu", 24, 330, 290, 200, 30, 0, 0, () -> {
			G.put("_selectedPlayer", "reimu");
			Game.switchScreen("game");
		}));
		grid.add(new LabelButton("Kirisame Marisa", 24, 330, 260, 200, 30, 0, 1, () -> {
			G.put("_selectedPlayer", "marisa");
			Game.switchScreen("game");
		}));
		grid.selectFirst();
		grid.updateAll();
		input.addProcessor(grid);
	}

	@Override
	protected void onFadeIn(float duration) {
		grid.clearActions();
		grid.setPosition(100, 0);
		grid.addAction(Actions.moveTo(0, 0, duration, Interpolation.sineOut));
	}
	
	@Override
	protected void onFadeOut(float duration) {
		grid.clearActions();
		grid.setPosition(0, 0);
		grid.addAction(Actions.moveTo(100, 0, duration, Interpolation.sineOut));
	}
	
	@Override
	protected void onQuit() {
		Game.switchScreen("start", 0.5f);
	}
	
	@Override
	public String getName() {
		return "playerSelect";
	}

}
