package com.mygdx.game.Heplers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Objects.Spacecraft;
import com.mygdx.game.Screens.GameScreen_Lvl1;

public class InputHandler implements InputProcessor {

    // Enter per a la gesitó del moviment d'arrastrar
    int previousY = 0;
    int previousX = 0;

    // Objectes necessaris
    private Spacecraft spacecraft;
    private GameScreen_Lvl1 screen;
    private Vector2 stageCoord;
    private Stage stage;

    public InputHandler(GameScreen_Lvl1 screen) {

        // Obtenim tots els elements necessaris
        this.screen = screen;
        spacecraft = screen.getSpacecraft();
        stage = screen.getStage();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        switch (screen.getCurrentState()) {
            case READY:
                screen.setCurrentState(GameScreen_Lvl1.GameState.RUNNING);
                break;
            case RUNNING:
                previousY = screenY;
                previousX = screenX;
                stageCoord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                break;
            case GAMEOVER:
                screen.reset();
                break;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        // Quan deixem anar el dit acabem un moviment
        // i posem la nau en l'estat normal
        spacecraft.goStraight();
        return true;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Controlem per a quin lloc va el dit
        if (Math.abs(previousY - screenY) > 2){
            if (previousY < screenY) {
                spacecraft.goDown();
            } else {
                spacecraft.goUp();
            }
        }
        if (Math.abs(previousX - screenX) > 2){
            if (previousX < screenX) {
                spacecraft.goRight();
         } else {
            spacecraft.goLeft();
         }
        }
        // Guardem la posició de la Y i X
        previousY = screenY;
        previousX = screenX;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}