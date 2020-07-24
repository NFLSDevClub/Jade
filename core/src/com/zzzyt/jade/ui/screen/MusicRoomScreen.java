package com.zzzyt.jade.ui.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.zzzyt.jade.music.BackgroundMusic;
import com.zzzyt.jade.ui.GridButton;
import com.zzzyt.jade.ui.ScrollingGrid;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.BGM;
import com.zzzyt.jade.util.U;

public class MusicRoomScreen extends BasicScreen {

	private ScrollingGrid grid;

	private Label comment;

	private Array<BackgroundMusic> bgms;
	private Array<String> names;
	private Array<String> comments;

	public MusicRoomScreen() {
		super();
	}

	@Override
	public void show() {
		init("mus/E.0109.ogg", "bg/select.png");

		this.comment = new Label("",
				new LabelStyle(A.getFont(U.config().UIFont, 18, 2, Color.BLACK), U.config().UIFontColor));
		comment.setBounds(320, 32, 288, 416);
		comment.setWrap(true);
		comment.setAlignment(Align.topLeft);
		st.addActor(comment);

		this.bgms = new Array<BackgroundMusic>();
		this.comments = new Array<String>();
		this.names = new Array<String>();
		bgms.add(BGM.getBGM("mus/E.0109.ogg"));
		names.add("E.0109");
		comments.add("E.0109\nby Zzzyt.\nTitle theme.");

		bgms.add(BGM.getBGM("mus/Idea12.ogg"));
		names.add("Desire Drive");
		comments.add("Desire Drive\nby Team Shanghai Alice, arranged by Zzzyt\nStage 1 theme.");

		bgms.add(BGM.getBGM("mus/Yet Another Tetris (Piano ver.).ogg"));
		names.add("Yet Another Tetris");
		comments.add("Yet Another Tetris\nArranged by Zzzyt\nStage Extra theme.");

		this.grid = new ScrollingGrid(true, new Rectangle(32, 32, 256, 416));
		st.addActor(grid);
		for (int i = 0; i < bgms.size; i++) {
			final int tmpint = i;
			grid.add(new GridButton((i + 1) + ". " + names.get(i), 18, 40, 420 - i * 20, 20, 40, 0, i, () -> {
				setComment(tmpint);
				BGM.play(bgms.get(tmpint).getName());
			}));
		}
		setComment(0);

		grid.selectFirst();
		grid.activate();
		grid.updateComponent();
		grid.update();
		input.addProcessor(grid);
	}

	@Override
	protected void onFadeIn(float duration) {
		grid.clearActions();
		grid.setPosition(-200, 0);
		grid.addAction(Actions.moveTo(0, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onFadeOut(float duration) {
		grid.clearActions();
		grid.setPosition(0, 0);
		grid.addAction(Actions.moveTo(-200, 0, duration, Interpolation.sineOut));
	}

	@Override
	protected void onQuit() {
		U.switchScreen("start", 0.5f);
	}

	@Override
	public String getName() {
		return "musicRoom";
	}

	private void setComment(int id) {
		comment.setText(comments.get(id));
	}

}