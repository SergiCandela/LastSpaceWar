package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.LastSpaceWar;
import com.mygdx.game.Heplers.AssetManager;
import com.mygdx.game.Settings.Settings;
import com.mygdx.game.Objects.ScrollHandlerMenu;
import java.io.IOException;

public class OptionScreen  extends BaseScreen {

    private Stage stage;
    private ScrollHandlerMenu scrollHandler;
    BitmapFont font;
    Skin skin;
    private Label.LabelStyle textStyle2;
    private Label textLbl;

    public OptionScreen(LastSpaceWar game) {
        super(game);

        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        stage = new Stage(viewport);
        font = new BitmapFont(true);
        skin = new Skin();
        scrollHandler = new ScrollHandlerMenu();

        TextureRegion region =  AssetManager.btnPress.get(0);
        Drawable drawable = new TextureRegionDrawable(region);
        TextureRegion region2 =  AssetManager.btnNormal.get(0);
        Drawable drawable2 = new TextureRegionDrawable(region2);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = drawable2;
        textButtonStyle.down = drawable;
        textButtonStyle.font = AssetManager.font;

        TextButton buttonDificultad = new TextButton("DIFICULTAD", textButtonStyle);
        buttonDificultad.setPosition((Settings.GAME_WIDTH/2)-300,(Settings.GAME_HEIGHT/3)-100);
        buttonDificultad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 level();
                super.clicked(event, x, y);
            }
        });

        TextButton buttonAtras = new TextButton("ATRAS", textButtonStyle);
        buttonAtras.setPosition((Settings.GAME_WIDTH/2)-200,(Settings.GAME_HEIGHT/2)-95);
        buttonAtras.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    salir();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.clicked(event, x, y);
            }
        });

        textStyle2 = new Label.LabelStyle(AssetManager.font, Color.GOLD);
        textLbl = new Label("LastSpaceWar", textStyle2);
        Container container = new Container(textLbl);
        container.setTransform(true);
        container.center();
        container.setSize(10,10);
        container.setPosition(Settings.GAME_WIDTH / 2, 250);
        container.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.3f, 1.3f, 1), Actions.scaleTo(1, 1, 1))));

        buttonDificultad.setHeight(250);
        buttonDificultad.setWidth(600);
        buttonAtras.setHeight(250);
        buttonAtras.setWidth(400);

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(buttonDificultad);
        stage.addActor(buttonAtras);
        stage.addActor(container);
        Gdx.input.setInputProcessor(stage);

    }
    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
    }
    public void salir() throws IOException {
        game.setScreen(new MainScreen(game));
}
    public void level() {
        game.setScreen(new DificultyScreen(game));
    }

}