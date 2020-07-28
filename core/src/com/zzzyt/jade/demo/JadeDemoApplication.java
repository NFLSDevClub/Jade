package com.zzzyt.jade.demo;

import com.zzzyt.jade.demo.ui.screen.DifficultySelectScreen;
import com.zzzyt.jade.demo.ui.screen.GameScreen;
import com.zzzyt.jade.demo.ui.screen.MusicRoomScreen;
import com.zzzyt.jade.demo.ui.screen.PlayerSelectScreen;
import com.zzzyt.jade.demo.ui.screen.SettingsScreen;
import com.zzzyt.jade.demo.ui.screen.SpellSelectScreen;
import com.zzzyt.jade.demo.ui.screen.StageSelectScreen;
import com.zzzyt.jade.demo.ui.screen.StartScreen;
import com.zzzyt.jade.music.BackgroundMusic;
import com.zzzyt.jade.ui.JadeApplication;
import com.zzzyt.jade.ui.screen.BlankScreen;
import com.zzzyt.jade.util.A;
import com.zzzyt.jade.util.B;
import com.zzzyt.jade.util.BGM;
import com.zzzyt.jade.util.U;

public class JadeDemoApplication extends JadeApplication {

	@Override
	public void onStart() {
		BGM.register(new BackgroundMusic("mus/Idea12.ogg", 0, 12));
		BGM.register(new BackgroundMusic("mus/E.0109.ogg", 10, 50));
		BGM.register(new BackgroundMusic("mus/Yet Another Tetris (Piano ver.).ogg", 0, Float.MAX_VALUE));
		A.load(U.config().UIFont);
		A.load("font/LBRITE.ttf");
		A.load("font/LBRITEI.ttf");
		A.load("bg/blank.png");
		A.load("bg/start.png");
		A.load("default_shot.shot");
		A.finishLoading();

		B.setSheet(U.config().defaultShotSheet);
		
		screens.add(new BlankScreen());
		screens.add(new StartScreen());
		screens.add(new GameScreen());
		screens.add(new DifficultySelectScreen());
		screens.add(new PlayerSelectScreen());
		screens.add(new StageSelectScreen());
		screens.add(new SpellSelectScreen());
		screens.add(new MusicRoomScreen());
		screens.add(new SettingsScreen());

		U.switchScreen("blank");
		U.switchScreen("start", 0.5f);
	}
	
}
