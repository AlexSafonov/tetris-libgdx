package com.entropy.dobletetris;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.entropy.dobletetris.Screen.GameScreen;
import com.entropy.dobletetris.Screen.StartScreen;

public class MyGdxGame extends Game {

	private Screen currentScreen;

	@Override
	public void create () {
		if(currentScreen == null) currentScreen = new StartScreen();
		setScreen(currentScreen);
	}


	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
	}
}
