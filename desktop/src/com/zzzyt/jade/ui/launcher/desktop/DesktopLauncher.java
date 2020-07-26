package com.zzzyt.jade.ui.launcher.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zzzyt.jade.demo.ui.JadeDemo;
import com.zzzyt.jade.util.U;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = U.config().startupWindowWidth;
		config.height = U.config().startupWindowHeight;
		config.foregroundFPS = U.config().fps;
		config.backgroundFPS = U.config().fps;
		config.resizable = U.config().allowResize;
		config.vSyncEnabled = U.config().vsyncEnabled;
		config.samples = 3;
		new LwjglApplication(new JadeDemo(), config);
	}
}
