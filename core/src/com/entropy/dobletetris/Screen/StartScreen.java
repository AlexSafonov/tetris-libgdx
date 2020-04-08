package com.entropy.dobletetris.Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.entropy.dobletetris.Service.ScreenManager;
import com.entropy.dobletetris.Utils.Constants;

public class StartScreen extends AbstractMenuScreen{


    public StartScreen(ScreenManager screenManager){
       super(screenManager);


    }
   @Override
    protected void createUI(){
        font.getData().setScale(2f);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.CORAL);
        Label label = new Label("TETRIS", style);
        label.setPosition(Constants.WORLD_WIDE/5f,Constants.WORLD_HEIGHT - Constants.CELL_SIZE*4);
        stage.addActor(label);

        TextButton playButton = new TextButton("Play", skin);
        playButton.setTransform(true);
        playButton.setSize(Constants.WORLD_WIDE/2,Constants.WORLD_HEIGHT/4);
        playButton.setPosition(Constants.WORLD_WIDE/4,Constants.WORLD_HEIGHT/2f-Constants.CELL_SIZE);

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.setSize(Constants.WORLD_WIDE/2,Constants.WORLD_HEIGHT/4);
        exitButton.setPosition(Constants.WORLD_WIDE/4,Constants.WORLD_HEIGHT/4-Constants.CELL_SIZE*2);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenManager.createNewGame();
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
    }


    @Override
    public void show() {
        createUI();

    }

}
