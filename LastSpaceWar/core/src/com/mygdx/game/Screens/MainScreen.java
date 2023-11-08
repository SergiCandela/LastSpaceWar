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

public class MainScreen extends BaseScreen {

    private Stage stage;
    private ScrollHandlerMenu scrollHandler;
    BitmapFont font;
    Skin skin;
    private Label.LabelStyle textStyle2;
    private Label textLbl;

    public MainScreen(LastSpaceWar game) {
        super(game);

        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        // Creem l'stage i assginem el viewport
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

        TextButton buttonJugar = new TextButton("JUGAR", textButtonStyle);
        buttonJugar.setPosition((Settings.GAME_WIDTH/2)-300,(Settings.GAME_HEIGHT/3)-100);
        buttonJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                niveles();
                super.clicked(event, x, y);
            }
        });

        TextButton buttonRecord = new TextButton("RECORD", textButtonStyle);
        buttonRecord.setPosition((Settings.GAME_WIDTH/2)-300,(Settings.GAME_HEIGHT/2)-95);
        buttonRecord.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    record();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.clicked(event, x, y);
            }
        });

        TextButton buttonOpciones = new TextButton("OPCIONES", textButtonStyle);
        buttonOpciones.setPosition((Settings.GAME_WIDTH/2)-300,(Settings.GAME_HEIGHT/2)+200);
        buttonOpciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                  opciones();
                super.clicked(event, x, y);
            }
        });

        TextButton buttonSalir = new TextButton("SALIR", textButtonStyle);
        buttonSalir.setPosition((Settings.GAME_WIDTH/2)-200,(Settings.GAME_HEIGHT/2)+500);
        buttonSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
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

        // Afegim les accions de escalar: primer es fa gran i després torna a l'estat original ininterrompudament
        container.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.3f, 1.3f, 1), Actions.scaleTo(1, 1, 1))));

        //Posicions dels botons
        buttonJugar.setHeight(250);
        buttonJugar.setWidth(600);
        buttonRecord.setHeight(250);
        buttonRecord.setWidth(600);
        buttonOpciones.setHeight(250);
        buttonOpciones.setWidth(600);
        buttonSalir.setHeight(250);
        buttonSalir.setWidth(400);

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(buttonJugar);
        stage.addActor(buttonRecord);
        stage.addActor(buttonOpciones);
        stage.addActor(buttonSalir);
        stage.addActor(container);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
    }

    public void opciones() {
        game.setScreen(new OptionScreen(game));
    }
    public void niveles() {
        game.setScreen(new LevelScreen(game));
    }

    public void record() throws IOException {
        game.setScreen(new RecordScreen(game));
    }
}