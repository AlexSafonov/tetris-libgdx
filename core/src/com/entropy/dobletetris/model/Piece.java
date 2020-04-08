package com.entropy.dobletetris.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Piece {
    private final boolean[][][] structure;
    private List<Block> blocksInPiece;

    private int currentRotationPosition; // position rotation sequence

    private Vector2 leftTopCoordinate; // we need this to keep track of x and y current structure
    private Vector2 rightLowerCoordinates;
    //This are real x and y of the shape, made from blocks.
    private float realRightX;
    private float realLeftX;
    private float realLowerY;

    private int blockSize;

    private boolean isMoving = false;
    private boolean oneLastMove = false;
    //Bounds of the move limit
    private float lowMoveBound;
    private float leftMoveBound;
    private float rightMoveBound;
    //Blocks on the board
    private List<Block> blocksOnBoard;

    public Piece( boolean[][][] shape ,String textureName, float startingPositionX, float startingPositionY, int blockSize, List<Block> blocksOnBoard, float gameFieldWidth) {
        this.structure = shape;
        this.blockSize = blockSize;
        blocksInPiece = new ArrayList<>();
        currentRotationPosition = 0;

        lowMoveBound = blockSize;
        leftMoveBound = blockSize;
        rightMoveBound = blockSize+gameFieldWidth*blockSize;

        leftTopCoordinate = new Vector2(startingPositionX,startingPositionY);
        rightLowerCoordinates = new Vector2();
        rightLowerCoordinates.y = startingPositionY;

        for(int i = 0; i< structure[currentRotationPosition].length; i++){
            rightLowerCoordinates.x = startingPositionX;
            for (int j = 0; j <structure[currentRotationPosition][0].length; j++){
                if(structure[currentRotationPosition][i][j]){
                    blocksInPiece.add(new Block(textureName,rightLowerCoordinates.x, rightLowerCoordinates.y,blockSize));
                }
                rightLowerCoordinates.x +=blockSize;
            }
            rightLowerCoordinates.y -=blockSize;
        }
        this.blocksOnBoard = blocksOnBoard;

    }


    public void startMoving(float startingPositionX, float startingPositionY){
        leftTopCoordinate.x = startingPositionX;
        leftTopCoordinate.y = startingPositionY;
        int blockIndex = 0;
        rightLowerCoordinates.y = startingPositionY;
        for(int i = 0; i< structure[currentRotationPosition].length; i++){
            rightLowerCoordinates.x = startingPositionX;

            for (int j = 0; j <structure[currentRotationPosition][0].length; j++){
                if(structure[currentRotationPosition][i][j]){
                  blocksInPiece.get(blockIndex).getSprite().setPosition(rightLowerCoordinates.x,rightLowerCoordinates.y);
                    blockIndex +=1;
                }
                rightLowerCoordinates.x +=blockSize;
            }
            rightLowerCoordinates.y -=blockSize;
        }
        setRealBounds();
        oneLastMove = true;
        isMoving = true;
    }


    private void setRealBounds(){
        //messing up variable to the value that cannot ever be true - then set them right
        realRightX = leftTopCoordinate.x;
        realLeftX = rightLowerCoordinates.x;
        realLowerY = leftTopCoordinate.y;
        for(Block block: blocksInPiece){
            if(block.getY() < realLowerY)  realLowerY = block.getY();
            if(block.getX() + blockSize > realRightX)  realRightX = block.getX() + blockSize;
            if(block.getX() < realLeftX)  realLeftX = block.getX();
        }
    }


    public void moveDown(){
        if (isMoving) {
                for (Block block : blocksInPiece) {
                        block.getSprite().setPosition(block.getSprite().getX(), block.getSprite().getY() - blockSize);
                }
                leftTopCoordinate.y -= blockSize;
                rightLowerCoordinates.y -= blockSize;
                realLowerY -= blockSize;
            } else {
            oneLastMove = false;
        }
    }

    public void moveRight() {
        if (oneLastMove && realRightX < rightMoveBound && canThisMoveRight()) {
            for (Block block : blocksInPiece) {
                block.getSprite().setPosition(block.getSprite().getX() + blockSize, block.getSprite().getY());
            }
            leftTopCoordinate.x += blockSize;
            rightLowerCoordinates.x += blockSize;
            realLeftX += blockSize;
            realRightX += blockSize;
        }
        canThisMoveDown();
    }

    public void moveLeft(){
        if (oneLastMove && realLeftX > leftMoveBound && canThisMoveLeft()) {
            for (Block block : blocksInPiece) {
                    block.getSprite().setPosition(block.getSprite().getX() - blockSize, block.getSprite().getY());
            }
            leftTopCoordinate.x -= blockSize;
            rightLowerCoordinates.x -= blockSize;
            realLeftX -= blockSize;
            realRightX -= blockSize;
        }
        canThisMoveDown();
    }

    private boolean canThisMoveLeft(){
        for (Block block: blocksInPiece){
            if( !block.canMoveLeft(blocksOnBoard)) return false;
        }
        return true;
    }

    private boolean canThisMoveRight(){
            for(Block block: blocksInPiece){
                if( !block.canMoveRight(blocksOnBoard)) return false;
            }
            return true;
    }

    private void canThisMoveDown() {
        isMoving = !(realLowerY <= lowMoveBound);

        for(Block curBlock:blocksInPiece){
            if(curBlock.canMoveDown(blocksOnBoard)){
                isMoving = false;
            }
        }
    }

    //Simulate rotation but do not render
    private boolean canRotateThis(){
        int tempRotatePosition = currentRotationPosition;
        if (tempRotatePosition == structure.length - 1) {
            tempRotatePosition = 0;
        } else {
            tempRotatePosition += 1;
        }

        float y = rightLowerCoordinates.y <= lowMoveBound ? lowMoveBound + blockSize*structure[currentRotationPosition].length: leftTopCoordinate.y;
        float x;
        for (int i = 0; i < structure[tempRotatePosition].length; i++) {
            if (leftTopCoordinate.x < leftMoveBound) {
                x = leftMoveBound;
            } else if (rightLowerCoordinates.x > rightMoveBound) {
                x = rightMoveBound - blockSize * structure[tempRotatePosition].length;
            }  else {
                x = leftTopCoordinate.x;
            }
            for (int j = 0; j < structure[0][0].length; j++) {
                if (structure[tempRotatePosition][i][j]) {
                    for(Block block: blocksOnBoard){
                        if(block.getX() == x && block.getY() == y){
                            return false;
                        }
                    }
                }
                x += blockSize;
            }
            y -= blockSize;
        }
        return true;
    }


    public void rotateFigure() {
        if (isMoving && canRotateThis() ) {
            if (currentRotationPosition == structure.length - 1) {
                currentRotationPosition = 0;
            } else {
                currentRotationPosition += 1;
            }

            int currentIndex = 0;

            float y = rightLowerCoordinates.y <= lowMoveBound ? lowMoveBound + blockSize*structure[currentRotationPosition].length: leftTopCoordinate.y;
            float x;
            for (int i = 0; i < structure[currentRotationPosition].length; i++) {
                if (leftTopCoordinate.x < leftMoveBound) {
                    x = leftMoveBound;
                } else if (rightLowerCoordinates.x > rightMoveBound) {
                    x = rightMoveBound - blockSize * structure[currentRotationPosition].length;
                }  else {
                    x = leftTopCoordinate.x;
                }

                for (int j = 0; j < structure[0][0].length; j++) {
                    if (structure[currentRotationPosition][i][j]) {
                        blocksInPiece.get(currentIndex).getSprite().setPosition(x, y);
                        currentIndex += 1;
                    }
                    x += blockSize;
                }
                y -= blockSize;
            }
            setRealBounds();
        }
    }


    public List<Block> getBlocksInPiece() {
        return blocksInPiece;
    }

    public boolean hasOneLastMove() {
        return oneLastMove;
    }

    public void render(float delta, SpriteBatch batch) {
        canThisMoveDown();
             for (Block block : blocksInPiece) {
            block.render(batch, delta);
        }

    }

    public void dispose(){
        for(Block block: blocksInPiece){
            block.dispose();
        }
    }


}
