package com.zzzyt.jade.ui.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.zzzyt.jade.demo.JadeDemoApplication;
import com.zzzyt.jade.util.U;

public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(U.config().startupWindowWidth, U.config().startupWindowHeight);
		config.useVsync(U.config().vsyncEnabled);
		config.setResizable(U.config().allowResize);
		config.setTitle(U.config().windowTitle);
		config.setWindowIcon("icon/icon_16x.png", "icon/icon_32x.png", "icon/icon_48x.png", "icon/icon_128x.png");
		JadeDemoApplication game = new JadeDemoApplication();
		game.setSync(new DesktopSync());
		new Lwjgl3Application(game, config);
	}
}
