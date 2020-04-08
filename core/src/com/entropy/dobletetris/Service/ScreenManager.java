package com.entropy.dobletetris.Service;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.entropy.dobletetris.Screen.GameOverScreen;
import com.entropy.dobletetris.Screen.GameScreen;
import com.entropy.dobletetris.Screen.PauseScreen;
import com.entropy.dobletetris.Screen.StartScreen;

public class ScreenManager {

     private Game game;
     private Screen currentGame;

     public ScreenManager(Game game){
         this.game = game;

     }
/*      go back to main menu?
      public void startMenu(){
          game.setScreen(new StartScreen(this));
      }*/

      public void createNewGame(){
          game.setScreen(new GameScreen(this));
          currentGame = game.getScreen();
      }

      public void pauseGame(){
          currentGame = game.getScreen();
          game.setScreen(new PauseScreen(this));
      }

      public void resumeGame(){
          currentGame.resume();
          game.setScreen(currentGame);

      }

      public void gameOver(){
          game.setScreen(new GameOverScreen(this));
      }





}
