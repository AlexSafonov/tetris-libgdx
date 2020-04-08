package com.entropy.dobletetris;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.entropy.dobletetris.Screen.GameOverScreen;
import com.entropy.dobletetris.Screen.GameScreen;
import com.entropy.dobletetris.Screen.PauseScreen;
import com.entropy.dobletetris.Screen.StartScreen;
import com.entropy.dobletetris.Service.ScreenManager;

public class MyGdxGame extends Game {

	private Screen currentScreen;
	private ScreenManager screenManager;

	@Override
	public void create () {
		screenManager = new ScreenManager(this);
		if(currentScreen == null) currentScreen = new StartScreen(screenManager);
		setScreen(currentScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		currentScreen.dispose();
	}

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
		currentScreen = screen;
	}
}
