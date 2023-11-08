package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SplashScreen extends BaseScreen {

        private Texture ttrSplash;
        private Stage stage;
        private Label.LabelStyle textStyle;
        private Label textLbl;
        BitmapFont font;
        Skin skin;
        FileHandle handleNivel1, handleNivel2, handleNivel3, handleDificultad;

        public SplashScreen(LastSpaceWar game) {
            super(game);

            OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
            camera.setToOrtho(true);
            StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
            font = new BitmapFont(true);
            skin = new Skin();

            //Cargamos los files
            try {
                cargarFicheros();
            } catch (IOException e) {
                e.printStackTrace();
            }

            stage = new Stage(viewport);
           // ttrSplash = new Texture("imageSplash.jpg");
          //  TextureRegion background = new TextureRegion(ttrSplash);
          //  background.flip(false, true);

            textStyle = new Label.LabelStyle(AssetManager.font, Color.GOLD);
            textLbl = new Label("LastSpaceWar", textStyle);
            Container containerTitulo = new Container(textLbl);
            containerTitulo.setTransform(true);
            containerTitulo.center();
            containerTitulo.setPosition(Settings.GAME_WIDTH / 2, 400);

            Label.LabelStyle textStyle2 = new Label.LabelStyle(AssetManager.font, null);
            Label textLbl2 = new Label("Dale al boton para continuar!", textStyle2);
            textLbl2.setFontScale(0.6f);
            Container containerTexto = new Container(textLbl2);
            containerTexto.setTransform(true);
            containerTexto.center();
            containerTexto.setPosition(Settings.GAME_WIDTH / 2, (Settings.GAME_HEIGHT/2)+200);

            TextureRegion region =  AssetManager.btnPress.get(0);
            Drawable drawable = new TextureRegionDrawable(region);
            TextureRegion region2 =  AssetManager.btnNormal.get(0);
            Drawable drawable2 = new TextureRegionDrawable(region2);
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.up = drawable2;
            textButtonStyle.down = drawable;
            textButtonStyle.font = AssetManager.font;

            TextButton buttonEmpezar = new TextButton("EMPEZAR", textButtonStyle);
            buttonEmpezar.setPosition((Settings.GAME_WIDTH/2)-350,(Settings.GAME_HEIGHT/2)+500);
            buttonEmpezar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                  jugar();
                    super.clicked(event, x, y);
                }
            });

            containerTitulo.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.5f, 1.5f, 1), Actions.scaleTo(1, 1, 1))));
            buttonEmpezar.setHeight(250);
            buttonEmpezar.setWidth(700);
            ScrollHandlerMenu scrollHandler = new ScrollHandlerMenu();

            // Afegim els actors a l'stage
            stage.addActor(scrollHandler);
            stage.addActor(buttonEmpezar);
            stage.addActor(containerTitulo);
            stage.addActor(containerTexto);
            Gdx.input.setInputProcessor(stage);

        }

        @Override
        public void render(float delta) {
            stage.draw();
            stage.act(delta);
        }

        public void jugar() {
            game.setScreen(new MainScreen(game));
            dispose();
        }

        public void cargarFicheros() throws IOException{

            handleNivel1 =Gdx.files.local("puntuacionNivel1.bin");
            handleNivel2 =Gdx.files.local("puntuacionNivel2.bin");
            handleNivel3 =Gdx.files.local("puntuacionNivel3.bin");
            handleDificultad =Gdx.files.local("dificultad.bin");
            ByteArrayOutputStream out = null;
            DataOutputStream oos = null;
            out= new ByteArrayOutputStream();
            oos = new DataOutputStream(out);

            if(!handleNivel1.exists()){
                oos.writeInt(0);
                oos.flush();
                byte[] datos = out.toByteArray();
                handleNivel1.writeBytes(datos,false);
                oos.close();
            }

            if(!handleNivel2.exists()){
                oos.writeInt(0);
                oos.flush();
                byte[] datos = out.toByteArray();
                handleNivel2.writeBytes(datos,false);
                oos.close();
            }

            if(!handleNivel3.exists()){
                oos.writeInt(0);
                oos.flush();
                byte[] datos = out.toByteArray();
                handleNivel3.writeBytes(datos,false);
                oos.close();
            }

            if(!handleDificultad.exists()){
                ByteArrayOutputStream out2 = null;
                DataOutputStream oos2 = null;
                out2= new ByteArrayOutputStream();
                oos2 = new DataOutputStream(out2);
                oos2.writeInt(10);
                oos2.flush();
                byte[] datos = out2.toByteArray();
                handleDificultad.writeBytes(datos,false);
                oos2.close();
            }
        }
    }