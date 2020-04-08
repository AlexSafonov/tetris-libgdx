package com.entropy.dobletetris.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.entropy.dobletetris.Control.KeyboardInput;
import com.entropy.dobletetris.Service.PieceFactory;
import com.entropy.dobletetris.Service.ScreenManager;
import com.entropy.dobletetris.model.Block;
import com.entropy.dobletetris.model.Board;
import com.entropy.dobletetris.Utils.Constants;
import com.entropy.dobletetris.model.Piece;

public class GameScreen extends ScreenAdapter {
    private KeyboardInput controller = new KeyboardInput();

    private SpriteBatch batch;
    private Camera camera;
    private Viewport viewport;

    private Board board;
    private Piece currentPiece;
    private Piece futurePiece;

    private float levelSpeed; // global level speed
    private float currentDropSpeed; // store current drop speed of a piece
    private float timer;

    private float strafeSpeed;
    private float strafeSpeedTimer = strafeSpeed;

    private boolean gameOver;
    private boolean paused;

    private PieceFactory pieceFactory;


    private final Vector2 START_POSITION = new Vector2(Constants.CELL_SIZE * 4f, Constants.CELL_SIZE * 20f);
    private final Vector2 SHOW_POSITION = new Vector2(Constants.CELL_SIZE * 12.4f, Constants.CELL_SIZE * 15.7f);

    private ScreenManager screenManager;


    public GameScreen(ScreenManager screenManager){
        this.screenManager = screenManager;
        Gdx.input.setInputProcessor(controller);

        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.WORLD_WIDE, Constants.WORLD_WIDE);
        camera.position.set(Constants.WORLD_WIDE / 2, Constants.WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(Constants.WORLD_WIDE, Constants.WORLD_HEIGHT, camera);

        pieceFactory = new PieceFactory();

        board = new Board(Constants.CELL_SIZE, Constants.WORLD_WIDE, Constants.WORLD_HEIGHT, Constants.GAME_FIELD_WIDTH, Constants.GAME_FIELD_HEIGHT);

        futurePiece = pieceFactory.createRandomPiece(SHOW_POSITION.x, SHOW_POSITION.y,  Constants.CELL_SIZE, board.getBlocksOnBoard(), Constants.GAME_FIELD_WIDTH);
        currentPiece =  pieceFactory.createRandomPiece(START_POSITION.x, START_POSITION.y, Constants.CELL_SIZE, board.getBlocksOnBoard(), Constants.GAME_FIELD_WIDTH);
        currentPiece.startMoving(START_POSITION.x, START_POSITION.y);

        levelSpeed = 1f;
        currentDropSpeed = levelSpeed;
        timer = currentDropSpeed;
        strafeSpeed = 0.08f;

    }


    @Override
    public void show() {
        camera.update();

    }

    // should not call pause or resume yourself, those are methods called by the framework in specific situations -- from framework docs
    //So we make our  custom
    public void pauseGame(){
        if(controller.isPause()){
            paused = true;
            controller.setPause(false);
            screenManager.pauseGame();
        }
    }

    @Override
    public void resume(){
        super.resume();
        Gdx.input.setInputProcessor(controller);
        timer = currentDropSpeed;
        paused = false;
    }


    private void addBlocksToBoard(){
        board.getBlocksOnBoard().addAll(currentPiece.getBlocksInPiece());
    }

    private void checkGameOver (){
        for(Block block: board.getBlocksOnBoard()){
            if(block.getY() >= START_POSITION.y-Constants.CELL_SIZE ){
                gameOver = true;
                screenManager.gameOver();
            }


        }
    }

    private void getNewPiece(){
        currentDropSpeed = levelSpeed;
        currentPiece = futurePiece;
        currentPiece.startMoving(START_POSITION.x, START_POSITION.y);
        futurePiece = pieceFactory.createRandomPiece(SHOW_POSITION.x, SHOW_POSITION.y, Constants.CELL_SIZE, board.getBlocksOnBoard(), Constants.GAME_FIELD_WIDTH);

    }
    private void drop(){
        currentDropSpeed = 0.005f;
        timer = currentDropSpeed;
    }

    private void increaseSpeed(){
        if(board.getLines()>board.getLevel()*10+10){
            board.setLevel(board.getLevel()+1);
            if(levelSpeed > 0.1f) {
                levelSpeed = levelSpeed - 0.045f;
            }
        }
    }

    private void moveCurrentPiece(float delta){
        strafeSpeedTimer -= delta;
        if(strafeSpeedTimer <=0) {
            strafeSpeedTimer = strafeSpeed;
            if (controller.isLeft()) {
                currentPiece.moveLeft();
            }
            if (controller.isRight()) {
                currentPiece.moveRight();
            }
            if (controller.isUp()) {
                currentPiece.rotateFigure();
                controller.setUp(false);
            }
            if (controller.isDown()) {
                drop();
                controller.setDown(false);
            }
        }
    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        board.render(batch, delta);
        currentPiece.render(delta,batch);
        futurePiece.render(delta,batch);

        pauseGame();
        checkGameOver();

        if(!gameOver && !paused) {
            timer -= delta;
            moveCurrentPiece(delta);
            if (timer <= 0) {
                timer = currentDropSpeed;
                board.deleteBlocks();
                currentPiece.moveDown();
                if (!currentPiece.hasOneLastMove()) {
                    addBlocksToBoard();
                    getNewPiece();
                    board.checkCell();
                    board.prepareBlocksToDelete();
                    increaseSpeed();
                    timer = currentDropSpeed;
                }
            }
        }
        batch.end();
    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        currentPiece.dispose();
        for(Block block: board.getBlocksOnBoard()){
            block.dispose();
        }
        board.dispose();
    }

}
