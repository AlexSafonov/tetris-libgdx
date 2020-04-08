package com.entropy.dobletetris.Control;

import com.badlogic.gdx.*;

public class KeyboardInput extends InputAdapter {

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;


    @Override
    public boolean keyDown( int keycode) {

        switch (keycode) // switch code base on the variable keycode
        {
            case Input.Keys.LEFT:
                left = true;
                return true;
            case Input.Keys.RIGHT:
                right = true;
                return true;
            case Input.Keys.UP:
                up = true;
                return true;
            case Input.Keys.DOWN:
                down = true;
                return true;
            case Input.Keys.ESCAPE:
                pause = true;
                return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode)
        {
            case Input.Keys.LEFT:
                left = false;
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                right = false;
                keyProcessed = true;
                break;
            case Input.Keys.UP:
                up = false;
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                down = false;
                keyProcessed = true;
                break;
            case Input.Keys.ESCAPE:
                pause = false;
                keyProcessed = true;
                break;
        }
        return keyProcessed;	//  return our peyProcessed flag
    }

    @Override
    public boolean keyTyped( char character) {
    /*    if(Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)){
            piece.rotateFigure();
        }*/
        return false;

    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }


    public boolean isRight() {
        return right;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    private boolean pause;
}
