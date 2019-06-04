package com.entropy.dobletetris.Control;

import com.badlogic.gdx.*;

public class KeyboardInput extends InputAdapter {

    private boolean up, down, left, right;


    @Override
    public boolean keyDown( int keycode) {

        switch (keycode) // switch code base on the variable keycode
        {
            case Input.Keys.LEFT:  	// if keycode is the same as Keys.LEFT a.k.a 21
                left = true;	// do this
                return true;	// we have reacted to a keypress so stop checking for more
            case Input.Keys.RIGHT: 	// if keycode is the same as Keys.LEFT a.k.a 22
                right = true;	// do this
                return true;	// we have reacted to a keypress so stop checking for more
            case Input.Keys.UP: 		// if keycode is the same as Keys.LEFT a.k.a 19
                up = true;		// do this
                return true;	// we have reacted to a keypress so stop checking for more
            case Input.Keys.DOWN: 	// if keycode is the same as Keys.LEFT a.k.a 20
                down = true;	// do this
                return true;	// we have reacted to a keypress so stop checking for more
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) // switch code base on the variable keycode
        {
            case Input.Keys.LEFT:  	// if keycode is the same as Keys.LEFT a.k.a 21
                left = false;	// do this
                keyProcessed = true;	// we have reacted to a keypress
                break;
            case Input.Keys.RIGHT: 	// if keycode is the same as Keys.LEFT a.k.a 22
                right = false;	// do this
                keyProcessed = true;	// we have reacted to a keypress
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

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
