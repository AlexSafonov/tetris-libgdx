package com.entropy.dobletetris.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

import java.util.List;

public class Block {
    private final Sprite sprite;
    private int size ;

    private int howManyRowsToGoDown;

    private Animation<TextureRegion> animation;
    private TextureAtlas blocksAtlas;

    private float stateTime;

    private boolean markedForDeletion;
    private boolean isAnimated;


    public Block(String textureName, float x, float y, int blockSize){
        this.sprite = new Sprite(new Texture(textureName));
        size = blockSize;
        sprite.setSize(size, size);
        sprite.setPosition(x,y);

        howManyRowsToGoDown = 0;

        blocksAtlas = new TextureAtlas("blocks.atlas");

        animation = new Animation<>(0.1f, blocksAtlas.getRegions());
        stateTime = 0f;

    }

    public Sprite getSprite() {
        return sprite;
    }

    public void render(SpriteBatch batch, float delta) {
        if (!isAnimated)   {this.sprite.draw(batch);

        } else{
            stateTime += delta;
            batch.draw(animation.getKeyFrame(stateTime, true), sprite.getX(), sprite.getY(), size, size);
                   }
    }


    // Just a shorthand
    public float getX(){
        return sprite.getBoundingRectangle().x;
    }
    public float getY(){
        return sprite.getBoundingRectangle().y;
    }

    public boolean canMoveDown(List<Block> blockList){
        for (Block block: blockList){
            if(this.getY() - block.getY() == size && this.getX() == block.getX() ) {
                return true;
            }
        }
        return false;
    }

    public boolean canMoveLeft(List<Block> blockList){

        for (Block block: blockList) {
            if (this.getX() - block.getX() == size && this.getY() == block.getY()) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveRight(List<Block> blockList) {
        for (Block block : blockList) {
            if (this.getX() + size == block.getX() && this.getY() == block.getY()) {
                return false;
            }
        }
        return true;
    }
    public void animate(){
       isAnimated = true;

    }

    public void dispose(){
        sprite.getTexture().dispose();
        blocksAtlas.dispose();
    }

    public void plusOneRowDown(){
        howManyRowsToGoDown +=1;
    }
    public int getHowManyRowsToGoDown() {
        return howManyRowsToGoDown;
    }

    public void setHowManyRowsToGoDown(int howManyRowsToGoDown) {
        this.howManyRowsToGoDown = howManyRowsToGoDown;
    }

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    public void setMarkedForDeletion(boolean markedForDeletion) {
        this.markedForDeletion = markedForDeletion;
    }
}
