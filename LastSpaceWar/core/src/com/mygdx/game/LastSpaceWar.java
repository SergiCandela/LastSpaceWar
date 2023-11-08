package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Heplers.AssetManager;
import com.mygdx.game.Screens.SplashScreen;

public class LastSpaceWar extends Game {
	
	@Override
	public void create () {
		AssetManager.load();
			setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
		AssetManager.dispose();
	}
}