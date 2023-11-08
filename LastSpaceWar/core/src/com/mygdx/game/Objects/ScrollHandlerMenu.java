package com.mygdx.game.Objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.Settings.Settings;

public class ScrollHandlerMenu extends Group {

    // Fons de pantalla
    Background bg, bg_back;

    public ScrollHandlerMenu() {

        // Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH , Settings.GAME_HEIGHT*2, Settings.BG_SPEED);
        bg_back = new Background(0, bg.getTailY(), Settings.GAME_WIDTH , Settings.GAME_HEIGHT*2 , Settings.BG_SPEED);

        addActor(bg);
        addActor(bg_back);


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Si algun element està fora de la pantalla, fem un reset de l'element.
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailY());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailY());

        }
    }
}