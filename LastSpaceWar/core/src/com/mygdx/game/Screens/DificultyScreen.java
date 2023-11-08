package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DificultyScreen extends BaseScreen {

    private Stage stage;
    private ScrollHandlerMenu scrollHandler;
    BitmapFont font;
    Skin skin;
    private Label.LabelStyle textStyle2;
    private Label textLbl;
    FileHandle handle;


    public DificultyScreen(LastSpaceWar game) {
        super(game);

        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        // Creem l'stage i assginem el viewport
        stage = new Stage(viewport);
        handle = Gdx.files.local("dificultad.bin");
        font = new BitmapFont(true);
        skin = new Skin();
        scrollHandler = new ScrollHandlerMenu();

        // Afegim els actors a l'stage
        TextureRegion region =  AssetManager.btnPress.get(0);
        Drawable drawablePress = new TextureRegionDrawable(region);
        TextureRegion region2 =  AssetManager.btnNormal.get(0);
        Drawable drawableNormal = new TextureRegionDrawable(region2);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = drawableNormal;
        textButtonStyle.down = drawablePress;
        textButtonStyle.font = AssetManager.font;

        TextButton buttonFacil = new TextButton("FACIL", textButtonStyle);
        buttonFacil.setPosition((Settings.GAME_WIDTH/2)-300,(Settings.GAME_HEIGHT/3)-100);
        buttonFacil.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dificulty(6);
                super.clicked(event, x, y);
            }
        });

        TextButton buttonNormal = new TextButton("NORMAL", textButtonStyle);
        buttonNormal.setPosition((Settings.GAME_WIDTH/2)-300,(Settings.GAME_HEIGHT/2)-95);
        buttonNormal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dificulty(10);
                super.clicked(event, x, y);
            }
        });

        TextButton buttonDificil = new TextButton("DIFICIL", textButtonStyle);
        buttonDificil.setPosition((Settings.GAME_WIDTH/2)-300,(Settings.GAME_HEIGHT/2)+200);
        buttonDificil.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dificulty(15);
                super.clicked(event, x, y);
            }
        });

        TextButton buttonAtras = new TextButton("ATRAS", textButtonStyle);
        buttonAtras.setPosition((Settings.GAME_WIDTH/2)-200,(Settings.GAME_HEIGHT/2)+500);
        buttonAtras.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                salir();
                super.clicked(event, x, y);
            }
        });

        textStyle2 = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("LastSpaceWar", textStyle2);
        Container container = new Container(textLbl);
        container.setTransform(true);
        container.center();
        container.setSize(10,10);
        float f = (float) ((float)Settings.GAME_HEIGHT / 2.5);
        container.setPosition(Settings.GAME_WIDTH / 2, 250);

        // Afegim les accions de escalar: primer es fa gran i després torna a l'estat original ininterrompudament
        container.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.3f, 1.3f, 1), Actions.scaleTo(1, 1, 1))));

        buttonFacil.setHeight(250);
        buttonFacil.setWidth(600);
        buttonNormal.setHeight(250);
        buttonNormal.setWidth(600);
        buttonDificil.setHeight(250);
        buttonDificil.setWidth(600);
        buttonAtras.setHeight(250);
        buttonAtras.setWidth(400);

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(buttonFacil);
        stage.addActor(buttonNormal);
        stage.addActor(buttonDificil);
        stage.addActor(buttonAtras);
        stage.addActor(container);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
    }

    public void salir() {
        game.setScreen(new OptionScreen(game));
    }

    public void dificulty(int num) {
        ByteArrayOutputStream out = null;
        DataOutputStream oos = null;
        out= new ByteArrayOutputStream();
        oos = new DataOutputStream(out);
        ByteArrayInputStream in = new ByteArrayInputStream(handle.readBytes());
        DataInputStream ooi = new DataInputStream(in);
        try {
            oos.writeInt(num);
            oos.flush();
            byte[] datos = out.toByteArray();
            handle.writeBytes(datos,false);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}