package com.entropy.dobletetris.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.entropy.dobletetris.model.Constants;
import javafx.scene.control.ToggleButton;

public class StartScreen extends ScreenAdapter {


    private SpriteBatch batch;
    private Viewport viewport;
    private Camera camera;
    private Skin skin;
    private TextureAtlas skinAtlas;
    private Stage stage;
    private BitmapFont font;

    public StartScreen(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.WORLD_WIDE, Constants.WORLD_HEIGHT);
        camera.position.set(Constants.WORLD_WIDE / 2, Constants.WORLD_HEIGHT / 2, 0);

        viewport = new FitViewport(Constants.WORLD_WIDE, Constants.WORLD_HEIGHT, camera);
        skin = new Skin(Gdx.files.internal("skin/shadeui/uiskin.json"));
        skinAtlas = new TextureAtlas("skin/shadeui/uiskin.atlas");
      //  skin.addRegions(skinAtlas);
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"), Gdx.files.internal("font/font.png"), false);
        skin.add("ArcadeFont",font);

        stage = new Stage();
        stage.setViewport(viewport);
        stage.getViewport().apply();
        stage.getCamera().update();


    }

    @Override
    public void show() {
            /* mainTable.setFillParent(true);
        mainTable.setTransform(true);*/
        //Set alignment of contents in the table.
       // mainTable.top();
        //Lable textArea = new TextArea( "TETRIS" , skin );
        font.getData().setScale(2f);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.CORAL);
        Label label = new Label("TETRIS", style);
        label.setPosition(Constants.WORLD_WIDE/4,Constants.WORLD_HEIGHT);
        stage.addActor(label);
        //Create buttons
      //  TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);
       // buttonStyle.font = new BitmapFont();

        TextButton playButton = new TextButton("Play", skin);
        playButton.setTransform(true);
        playButton.setSize(Constants.WORLD_WIDE/2,Constants.WORLD_HEIGHT/4);
        playButton.setPosition(Constants.WORLD_WIDE/4,Constants.WORLD_HEIGHT/2f);
      //  TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.setSize(Constants.WORLD_WIDE/2,Constants.WORLD_HEIGHT/4);
        exitButton.setPosition(Constants.WORLD_WIDE/4,Constants.WORLD_HEIGHT/4);

        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(playButton);
        stage.addActor(exitButton);
      //  mainTable.setSize(1000,600);
      //  mainTable.row().fillX();

        //Add buttons to table
       // mainTable.add(playButton);
  //      mainTable.add(optionsButton);
     //   mainTable.row();
     //   mainTable.add(exitButton);
     //   mainTable.setPosition(Constants.WORLD_HEIGHT/2, Constants.WORLD_WIDE/2);





    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       // stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getCamera().update();
        batch.begin();
        font.draw(batch,"TETRIS", Constants.WORLD_WIDE/4, 0);
        stage.getBatch().begin();
        stage.getBatch().end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().viewportWidth = Constants.WORLD_WIDE;
        stage.getCamera().viewportHeight = Constants.WORLD_HEIGHT * height / width;
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2, stage.getCamera().viewportHeight / 2, 0);
        stage.getCamera().update();
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
         skin.dispose();
        skinAtlas.dispose();
    }
}
