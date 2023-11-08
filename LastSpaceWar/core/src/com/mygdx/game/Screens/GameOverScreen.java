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

public class GameOverScreen extends BaseScreen {

    private Stage stage;
    private ScrollHandlerMenu scrollHandler;
    BitmapFont font;
    Skin skin;
    private Label.LabelStyle textStyle2;
    private Label textLbl;

    public GameOverScreen(LastSpaceWar game, int puntuacion,String record) {
        super(game);

        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        stage = new Stage(viewport);
        font = new BitmapFont(true);
        skin = new Skin();

        TextureRegion region =  AssetManager.btnPress.get(0);
        Drawable seleccionado = new TextureRegionDrawable(region);
        TextureRegion region2 =  AssetManager.btnNormal.get(0);
        Drawable noSeleccionado = new TextureRegionDrawable(region2);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = noSeleccionado;
        textButtonStyle.down = seleccionado;
        textButtonStyle.font = AssetManager.font;

        TextButton buttonSalir = new TextButton("SALIR", textButtonStyle);
        buttonSalir.setPosition((Settings.GAME_WIDTH/2)-500,(Settings.GAME_HEIGHT/2)+500);
        buttonSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                salir();
                super.clicked(event, x, y);
            }
        });

        TextButton buttonJugar = new TextButton("JUGAR", textButtonStyle);
        buttonJugar.setPosition((Settings.GAME_WIDTH/2)+100,(Settings.GAME_HEIGHT/2)+500);
        buttonJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    jugar();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.clicked(event, x, y);
            }
        });

        textStyle2 = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("Puntuacion: "+puntuacion, textStyle2);
        Container container2 = new Container(textLbl);
        container2.setTransform(true);
        container2.center();
        container2.setPosition(Settings.GAME_WIDTH / 2, 600);

        textStyle2 = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label(record, textStyle2);
        Container container3 = new Container(textLbl);
        container3.setTransform(true);
        container3.center();
        container3.setSize(10,10);
        container3.setPosition((Settings.GAME_WIDTH/2),(Settings.GAME_HEIGHT/3)+400);

        textStyle2 = new Label.LabelStyle(AssetManager.font, Color.RED);
        textLbl = new Label("Has muerto!", textStyle2);
        Container container = new Container(textLbl);
        container.setTransform(true);
        container.center();
        container.setSize(10,10);
        container.setPosition(Settings.GAME_WIDTH / 2, 250);
        container.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(
                Actions.scaleTo(1.3f, 1.3f, 1), Actions.scaleTo(1, 1, 1))));


        buttonSalir.setHeight(250);
        buttonSalir.setWidth(400);
        buttonJugar.setHeight(250);
        buttonJugar.setWidth(400);
        scrollHandler = new ScrollHandlerMenu();

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(container2);
        stage.addActor(container3);
        stage.addActor(buttonSalir);
        stage.addActor(buttonJugar);
        stage.addActor(container);
        Gdx.input.setInputProcessor(stage);

    }
    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
    }

    public void salir() {
        game.setScreen(new MainScreen(game));
    }

    public void jugar() throws IOException {
        game.setScreen(new GameScreen_Lvl1(stage.getBatch(), stage.getViewport(), game));
    }
}