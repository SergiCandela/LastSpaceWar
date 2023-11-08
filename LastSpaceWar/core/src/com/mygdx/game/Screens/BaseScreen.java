package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.LastSpaceWar;

public abstract class BaseScreen implements Screen {

    public LastSpaceWar game;
    public BaseScreen(LastSpaceWar game) {
        this.game=game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}