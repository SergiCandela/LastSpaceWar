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
import java.io.DataInputStream;
import java.io.IOException;

public class RecordScreen extends BaseScreen {

    private Stage stage;
    private ScrollHandlerMenu scrollHandler;
    BitmapFont font;
    Skin skin;
    private Label.LabelStyle textStyle2;
    private Label textLbl;

    public RecordScreen(LastSpaceWar game) throws IOException {
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

        FileHandle filePuntuacionLvl1 = Gdx.files.local("puntuacionNivel1.bin");
        FileHandle filePuntuacionLvl2 = Gdx.files.local("puntuacionNivel2.bin");
        FileHandle filepuntuacionLvl3 = Gdx.files.local("puntuacionNivel3.bin");

        ByteArrayInputStream in = new ByteArrayInputStream(filePuntuacionLvl1.readBytes());
        DataInputStream ooi = new DataInputStream(in);
        String g = Integer.toString(ooi.readInt());
        ByteArrayInputStream in2 = new ByteArrayInputStream(filePuntuacionLvl2.readBytes());
        DataInputStream ooi2 = new DataInputStream(in2);
        String g2 = Integer.toString(ooi2.readInt());
        ByteArrayInputStream in3 = new ByteArrayInputStream(filepuntuacionLvl3.readBytes());
        DataInputStream ooi3 = new DataInputStream(in3);
        String g3 = Integer.toString(ooi3.readInt());

        TextButton buttonAtras = new TextButton("ATRAS", textButtonStyle);
        buttonAtras.setPosition((Settings.GAME_WIDTH/2)-200,(Settings.GAME_HEIGHT/2)+500);
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

        textStyle2 = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("Nivel 1  ----", textStyle2);
        Container containerNivel1 = new Container(textLbl);
        containerNivel1.setTransform(true);
        containerNivel1.setPosition((Settings.GAME_WIDTH/2)-150,(Settings.GAME_HEIGHT/3));


        textStyle2 = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label(g, textStyle2);
        Container containerPuntuacion1 = new Container(textLbl);
        containerPuntuacion1.setTransform(true);
        containerPuntuacion1.setPosition((Settings.GAME_WIDTH/2+300),(Settings.GAME_HEIGHT/3));

        textStyle2 = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("Nivel 2  ----", textStyle2);
        Container containerNivel2 = new Container(textLbl);
        containerNivel2.setTransform(true);
        containerNivel2.setPosition((Settings.GAME_WIDTH/2)-150,(Settings.GAME_HEIGHT/3)+200);


        textStyle2 = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label(g2, textStyle2);
        Container containerPuntuacion2 = new Container(textLbl);
        containerPuntuacion2.setTransform(true);
        containerPuntuacion2.setPosition((Settings.GAME_WIDTH/2+300),(Settings.GAME_HEIGHT/3)+200);

        textStyle2 = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("Nivel 3  ----", textStyle2);
        Container containerNivel3 = new Container(textLbl);
        containerNivel3.setTransform(true);
        containerNivel3.setPosition((Settings.GAME_WIDTH/2)-150,(Settings.GAME_HEIGHT/3)+400);


        textStyle2 = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label(g3, textStyle2);
        Container containerPuntuacion3 = new Container(textLbl);
        containerPuntuacion3.setTransform(true);
        containerPuntuacion3.setPosition((Settings.GAME_WIDTH/2+300),(Settings.GAME_HEIGHT/3)+400);

        textStyle2 = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("Mejores\nPuntuaciones", textStyle2);
        Container containerTitulo = new Container(textLbl);
        containerTitulo.setTransform(true);
        containerTitulo.center();
        containerTitulo.setSize(10,10);
        containerTitulo.setPosition(Settings.GAME_WIDTH / 2, 250);
        containerTitulo.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.3f, 1.3f, 1), Actions.scaleTo(1, 1, 1))));

        buttonAtras.setHeight(250);
        buttonAtras.setWidth(400);

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(containerNivel1);
        stage.addActor(containerPuntuacion1);
        stage.addActor(containerNivel2);
        stage.addActor(containerPuntuacion2);
        stage.addActor(containerNivel3);
        stage.addActor(containerPuntuacion3);
        stage.addActor(buttonAtras);
        stage.addActor(containerTitulo);
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
}