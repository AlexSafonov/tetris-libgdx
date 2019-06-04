package com.entropy.dobletetris.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Cell[][] cells;
    private int cellSize;

    private Sprite cellSprite1;
    private Sprite cellSprite2;
    private Sprite background;

    private BitmapFont font;

    private int score;
    private int level;
    private int lines;

    //this is for deleteRows()
    private float sumOfCoordinatesByX;

    private List<Block> blocksOnBoard;


    public Board(int cellSize, float worldWide, float worldHeight, int gameFieldWidth, int gameFieldHeight) {
        this.cellSize = cellSize;

        font = new BitmapFont(Gdx.files.internal("font/font.fnt"), Gdx.files.internal("font/font.png"), false);

        // font.setColor(Color.WHITE);

        this.cells = new Cell[gameFieldHeight][gameFieldWidth];
        sumOfCoordinatesByX = gameFieldWidth * (gameFieldWidth + 1) / 2 * cellSize; // S = n*(n+1) / 2 and * cell size

        float gridYCoordinateStart = cellSize;
        float gridXCoordinateStart;

        score = 0;
        level = 0;

        for (int i = 0; i < cells.length; i++) {
            gridXCoordinateStart = cellSize;
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell(gridXCoordinateStart, gridYCoordinateStart);
                gridXCoordinateStart += cellSize;
            }
            gridYCoordinateStart += cellSize;
        }
        cellSprite1 = new Sprite(new Texture("001.jpg"));
        cellSprite1.setSize(cellSize, cellSize);
        cellSprite2 = new Sprite(new Texture("002.jpg"));
        cellSprite2.setSize(cellSize, cellSize);
        background = new Sprite(new Texture("boardBackGround.jpg"));
        background.setSize(worldWide, worldHeight);

        blocksOnBoard = new ArrayList<>();

    }

    //sure we can iterate over cells again. But why we should do it?
    public void checkCell() {
        for (Cell[] cell : cells) {
            for (int j = 0; j < cells[0].length; j++) {
                blockLoop:
                // this is not unnecessary.
                for (Block block : blocksOnBoard) {
                    if (block.getX() == cell[j].getCoordinates().x && block.getY() == cell[j].getCoordinates().y) {
                        cell[j].setFull(true);
                        break blockLoop; // found block that was looking for
                    } else {
                        cell[j].setFull(false);
                    }
                }
            }
        }
    }

    public void prepareBlocksToDelete() {
        int howManyLinesWasDeleted = 0;
        for (Cell[] cell : cells) {
            float sumX = 0;
            // we need only Y i e which row to delete ;
            float effectivelyFinalY = cell[0].getCoordinates().y;

            for (Cell value : cell) {
                if (value.isFull()) {
                    sumX += value.getCoordinates().x;
                }
            }
            if (sumX == sumOfCoordinatesByX) {
                for (Block block : blocksOnBoard) {
                    if (block.getY() == effectivelyFinalY) {
                        block.animate();
                        block.setMarkedForDeletion(true);
                    }
                }
                for (Block block : blocksOnBoard) {
                    if (block.getY() > effectivelyFinalY)
                        block.plusOneRowDown();
                }
                howManyLinesWasDeleted += 1;
                lines += 1;
            }
        }
        //classic tetris rules
        if (howManyLinesWasDeleted == 1) score += 40;
        if (howManyLinesWasDeleted == 2) score += 100;
        if (howManyLinesWasDeleted == 3) score += 300;
        if (howManyLinesWasDeleted == 4) score += 1200;
    }

    public void deleteBlocks() {
        blocksOnBoard.removeIf(Block::isMarkedForDeletion);
        for (Block block : blocksOnBoard) {
            block.getSprite().setY(block.getY() - cellSize * block.getHowManyRowsToGoDown());
            block.setHowManyRowsToGoDown(0);
        }
    }

    private void drawBoard(SpriteBatch batch) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (i % 2 == 0) { // if row even draw in every even cell sprite №1 else do opposite
                    if (j % 2 == 0) {
                        cellSprite1.setPosition(cells[i][j].getCoordinates().x, cells[i][j].getCoordinates().y);
                        cellSprite1.draw(batch);
                    } else {
                        cellSprite2.setPosition(cells[i][j].getCoordinates().x, cells[i][j].getCoordinates().y);
                        cellSprite2.draw(batch);
                    }
                }
                if (i % 2 != 0) {
                    if (j % 2 != 0) {
                        cellSprite1.setPosition(cells[i][j].getCoordinates().x, cells[i][j].getCoordinates().y);
                        cellSprite1.draw(batch);
                    } else {
                        cellSprite2.setPosition(cells[i][j].getCoordinates().x, cells[i][j].getCoordinates().y);
                        cellSprite2.draw(batch);
                    }
                }
            }
        }
    }

    private void drawBlocksOnBoard(SpriteBatch batch, float delta) {
        blocksOnBoard.forEach(block -> block.render(batch, delta));
    }


    public void render(SpriteBatch batch, float delta) {
        background.draw(batch);


        drawBoard(batch);
        drawBlocksOnBoard(batch, delta);

        font.draw(batch, "NEXT", 480, 740);
        font.draw(batch, "SCORE", 480, 450);
        font.draw(batch, String.valueOf(score), 480, 400);
        font.draw(batch, "LINES", 480, 300);
        font.draw(batch, String.valueOf(lines), 480, 250);
        font.draw(batch, "Level", 480, 150);
        font.draw(batch, String.valueOf(level), 480, 100);

    }

    public void dispose() {
        cellSprite1.getTexture().dispose();
        cellSprite2.getTexture().dispose();
        background.getTexture().dispose();
        font.dispose();
    }

    public List<Block> getBlocksOnBoard() {
        return blocksOnBoard;
    }

    public int getLines() {
        return lines;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
