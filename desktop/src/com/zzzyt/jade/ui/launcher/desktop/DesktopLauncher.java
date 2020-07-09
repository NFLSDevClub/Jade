package com.zzzyt.jade.ui.launcher.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zzzyt.jade.demo.JadeDemo;
import com.zzzyt.jade.util.U;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = U.getConfig().windowWidth;
		config.height = U.getConfig().windowHeight;
		config.foregroundFPS = U.getConfig().fps;
		config.backgroundFPS = U.getConfig().fps;
		config.resizable = U.getConfig().allowResize;
		config.vSyncEnabled = U.getConfig().vsyncEnabled;
		config.samples = 3;
		new LwjglApplication(new JadeDemo(), config);
	}
}
