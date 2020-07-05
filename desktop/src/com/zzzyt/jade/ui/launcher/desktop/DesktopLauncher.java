package com.zzzyt.jade.ui.launcher.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zzzyt.jade.Config;
import com.zzzyt.jade.demo.JadeDemo;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Config.windowWidth;
		config.height = Config.windowHeight;
		config.foregroundFPS = Config.fps;
		config.backgroundFPS = Config.fps;
		config.resizable = false;
		config.vSyncEnabled = Config.vsyncEnabled;
		config.samples = 3;
		new LwjglApplication(new JadeDemo(), config);
	}
}
