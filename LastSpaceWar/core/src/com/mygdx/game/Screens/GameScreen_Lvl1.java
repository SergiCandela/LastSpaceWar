package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.LastSpaceWar;
import com.mygdx.game.Heplers.AssetManager;
import com.mygdx.game.Heplers.InputHandler;
import com.mygdx.game.Settings.Settings;
import com.mygdx.game.Objects.Bullet;
import com.mygdx.game.Objects.ScrollHandler;
import com.mygdx.game.Objects.Spacecraft;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen_Lvl1 extends BaseScreen {

    public enum GameState {
        READY, RUNNING, GAMEOVER
    }
    private GameState currentState;
    private Stage stage;
    private ScrollHandler scrollHandler;
    private SpriteBatch batch;
    private Spacecraft spacecraft;
    private float explosionTime = 0;
    int num, puntuacio;
    private ArrayList<Bullet> listBullets;
    private GlyphLayout textLayout;
    private Label textLbl;
    FileHandle handle;
    private Label.LabelStyle textStyle2;

    public GameScreen_Lvl1(Batch prevBatch, Viewport prevViewport, LastSpaceWar game) {
        super(game);

        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(true);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        stage = new Stage(viewport);
        //stage.setDebugAll(true);
        textLayout = new GlyphLayout();
        batch = new SpriteBatch();
        scrollHandler = new ScrollHandler(stage);
        listBullets = new ArrayList<Bullet>();
        num=0;
        puntuacio=0;

        //Creem la nau
        spacecraft = new Spacecraft(Settings.SPACECRAFT_STARTX, Settings.SPACECRAFT_STARTY,
                Settings.SPACECRAFT_WIDTH, Settings.SPACECRAFT_HEIGHT);
        spacecraft.setName("spacecraft");

        //Label de la puntuacio
        textStyle2 = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label(Integer.toString(puntuacio), textStyle2);
        Container container = new Container(textLbl);
        container.setTransform(true);
        container.center();
        container.setPosition(Settings.GAME_WIDTH -150, 75);

        currentState = GameState.RUNNING;
        stage.addActor(scrollHandler);
        stage.addActor(spacecraft);
        stage.addActor(container);
        Gdx.input.setInputProcessor(new InputHandler(this));
    }

    @Override
    public void render(float delta) {
        stage.draw();
        // Depenent de l'estat del joc farem unes accions o unes altres
        switch (currentState) {
            case GAMEOVER:
                try {
                    updateGameOver(delta);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case READY:
                updateReady();
                break;
        }
    }
    private void updateReady() {
        batch.begin();
        stage.addActor(textLbl);
        batch.end();
    }

    private void updateRunning(float delta) {
        stage.act(delta);

        //Per a qe no pugi la puntuacio molt sumem un quan sigui true i el mateix amb les bales
        if(num%7==0){
            textLbl.setText(puntuacio++);
            int bulletX = (int) ((getJet().getX())+70);
            int bulletY = (int) ((Settings.GAME_HEIGHT-getJet().getY()));
            getLlistaBales().
                    add(new Bullet(bulletX, bulletY));
        }

        batch.begin();
        Iterator itBala = listBullets.iterator();
        while (itBala.hasNext()) {
            Bullet bala = (Bullet) itBala.next();
            if (bala.isActive()) {
                bala.draw(batch);
                bala.act(delta);
            } else itBala.remove();
        }

        if (scrollHandler.collides(spacecraft)) {
            // Si hi ha hagut col·lisió: posem l'estat a GameOver
            stage.getRoot().findActor("spacecraft").remove();
            currentState = GameState.GAMEOVER;
        }

        if(scrollHandler.out()){
            // Si ha pasat una nau al final de la pantalla: posem l'estat a GameOver
            stage.getRoot().findActor("spacecraft").remove();
            currentState = GameState.GAMEOVER;
        }

        Iterator itBala2 = listBullets.iterator();
        while (itBala2.hasNext()) {
            Bullet bala = (Bullet) itBala2.next();
            if (scrollHandler.collides2(bala,batch)) {
                puntuacio +=50;
            }
        }

        num++;
        batch.end();
    }

    private void updateGameOver(float delta) throws IOException {
        stage.act(delta);
        handle = Gdx.files.local("puntuacionNivel1.bin");
        ByteArrayOutputStream out = null;
        DataOutputStream oos = null;
        out= new ByteArrayOutputStream();
        oos = new DataOutputStream(out);
        String record="Intentalo\n de nuevo!";
        ByteArrayInputStream in = new ByteArrayInputStream(handle.readBytes());
        DataInputStream ooi = new DataInputStream(in);
        if(puntuacio >ooi.readInt()){
            oos.writeInt(puntuacio);
            oos.flush();
            byte[] datos = out.toByteArray();
            handle.writeBytes(datos,false);
            oos.close();
            record="Nuevo Record!";
       }

        batch.begin();
        batch.draw((TextureRegion) AssetManager.explosionAnim.getKeyFrame(explosionTime, false), (getJet().getX())+70, getJet().getY() + getJet().getHeight() / 2 , 200, 200);
        batch.end();
        explosionTime += delta;
        game.setScreen(new GameOverScreen(game, puntuacio,record));
    }

    @Override
    public void dispose () {
    }
    public void reset() {
    }

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }
    public Stage getStage() {
        return stage;
    }
    public GameState getCurrentState() {
        return currentState;
    }
    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }
    public Spacecraft getJet () {
        return spacecraft;
    }
    public ArrayList<Bullet> getLlistaBales () {
        return listBullets;
    }
}