package com.zzzyt.jade.ui.launcher.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zzzyt.jade.ui.JadeDemo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=640;
		config.height=480;
		config.foregroundFPS=60;
		config.backgroundFPS=60;
		config.resizable=false;
		config.vSyncEnabled=false;
		config.samples=3;
		new LwjglApplication(new JadeDemo(), config);
	}
}
