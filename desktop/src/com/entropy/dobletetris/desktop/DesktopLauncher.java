package com.entropy.dobletetris.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.entropy.dobletetris.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Psycho Tetris";
		config.height = 880;
		config.width = 720;

		new LwjglApplication(new MyGdxGame(), config);
	}
}
