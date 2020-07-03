package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.zzzyt.jade.ui.Grid;
import com.zzzyt.jade.ui.GridComponent;
import com.zzzyt.jade.ui.widget.GridImage;
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
		init("mus/Idea29.ogg", A.get("bg/select_bg.png"));

		this.grid = new Grid(true);
		st.addActor(grid);

		grid.add(new GridImage(A.get("diff/reimu_description.png"), 120, 100, 0, 0, ()->{
			G.put("_player", "reimu");
			Game.switchScreen("game");
		}) {
			@Override
			public GridComponent activate() {
				active = true;
				clearActions();
				addAction(Actions.moveTo(120, 100, 0.2f, Interpolation.sine));
				setColor(1, 1, 1, 1f);
				return this;
			}

			@Override
			public GridComponent deactivate() {
				active = false;
				clearActions();
				addAction(Actions.moveTo(-300, 100, 0.2f, Interpolation.sine));
				setColor(0.5f, 0.5f, 0.5f, 1f);
				return this;
			}
		});
		grid.add(new GridImage(A.get("diff/reimu_portrait.png"), 240, 100, 0, 0, null) {
			@Override
			public GridComponent activate() {
				active = true;
				clearActions();
				addAction(Actions.moveTo(240, 100, 0.2f, Interpolation.sine));
				setColor(1, 1, 1, 1f);
				return this;
			}

			@Override
			public GridComponent deactivate() {
				active = false;
				clearActions();
				addAction(Actions.moveTo(80, 100, 0.2f, Interpolation.sine));
				setColor(0.5f, 0.5f, 0.5f, 1f);
				return this;
			}
		});
		
		grid.add(new GridImage(A.get("diff/marisa_description.png"), 660, 100, 1, 0, null) {
			@Override
			public GridComponent activate() {
				active = true;
				clearActions();
				addAction(Actions.moveTo(340, 100, 0.2f, Interpolation.sine));
				setColor(1, 1, 1, 1f);
				return this;
			}

			@Override
			public GridComponent deactivate() {
				active = false;
				clearActions();
				addAction(Actions.moveTo(660, 100, 0.2f, Interpolation.sine));
				setColor(0.5f, 0.5f, 0.5f, 1f);
				return this;
			}
		});
		grid.add(new GridImage(A.get("diff/marisa_portrait.png"), 360, 100, 1, 0, ()->{
			G.put("_player", "marisa");
			Game.switchScreen("game");
		}) {
			@Override
			public GridComponent activate() {
				active = true;
				clearActions();
				addAction(Actions.moveTo(200, 100, 0.2f, Interpolation.sine));
				setColor(1, 1, 1, 1f);
				return this;
			}

			@Override
			public GridComponent deactivate() {
				active = false;
				clearActions();
				addAction(Actions.moveTo(360, 100, 0.2f, Interpolation.sine));
				setColor(0.5f, 0.5f, 0.5f, 1f);
				return this;
			}
		});

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
		Game.switchScreen("difficultySelect", 0.5f);
	}

	@Override
	public String getName() {
		return "playerSelect";
	}

}
