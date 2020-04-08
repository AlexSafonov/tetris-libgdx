package com.entropy.dobletetris.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.entropy.dobletetris.Service.ScreenManager;
import com.entropy.dobletetris.Utils.Constants;

public abstract class AbstractMenuScreen extends ScreenAdapter {

    protected SpriteBatch batch;
    protected Viewport viewport;
    protected Camera camera;
    protected Skin skin;
    protected TextureAtlas skinAtlas;
    protected Stage stage;
    protected BitmapFont font;

    protected ScreenManager screenManager;

    public AbstractMenuScreen(ScreenManager screenManager){
        this.screenManager = screenManager;
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.WORLD_WIDE, Constants.WORLD_HEIGHT);
        camera.position.set(Constants.WORLD_WIDE / 2, Constants.WORLD_HEIGHT / 2, 0);

        viewport = new FitViewport(Constants.WORLD_WIDE, Constants.WORLD_HEIGHT, camera);
        skin = new Skin(Gdx.files.internal("skin/shadeui/uiskin.json"));
        skinAtlas = new TextureAtlas("skin/shadeui/uiskin.atlas");
        //  skin.addRegions(skinAtlas);
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"), Gdx.files.internal("font/font.png"), false);
        skin.add("ArcadeFont",font);
        skin.getFont("font-button").getData().setScale(3f,3f);

        stage = new Stage();
        stage.setViewport(viewport);
        stage.getViewport().apply();
        stage.getCamera().update();
        Gdx.input.setInputProcessor(stage);


    }

    protected void createUI(){

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
