package com.example.obstacleracetask1;

import java.util.Arrays;
import java.util.Random;

public class GameManger {
    public static final int MAX_LIVES = 3,COLUMNS = 3,ROWS = 5;
    public boolean[] lifes;
    public boolean[][] activeIos;
    private int lives = MAX_LIVES, AndroidIndex;
    boolean isHit,finish;

    public GameManger() {
        AndroidIndex =1;
        activeIos =new boolean[ROWS][COLUMNS];
        initLives();
    }
    public void initLives(){
        lifes=new boolean[MAX_LIVES];
        Arrays.fill(lifes, true);
    }
    public void reduceLives() {
        lives--;
    }

    public  int getROWS() {
        return ROWS;
    }

    public  int getCOLUMNS() {
        return COLUMNS;
    }


    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(COLUMNS);
    }
    public boolean isActive(int row,int col) {
        return activeIos[row][col];
    }
    public void updateGame() {
        for (int i = getROWS()-1; i >=0;i--){
            for (int j = 0; j <getCOLUMNS();j++){
                if( isActive(i,j) && i == getROWS()-1) {
                    activeIos[i][j] = false;

                    if (j == AndroidIndex) {
                        isHit = true;
                        if(lives==0){
                            lives = MAX_LIVES; //update lives when over
                        }
                        reduceLives();
                        lifes[lives] = false;
                        if(!lifes[0]) { initLives();} // Update hearts

                        //if (lives == 0)
                           // finish = true;

                    }
                }
                else if(i != getROWS()-1){
                    activeIos[i+1][j]= activeIos[i][j];

                }
            }
        }
    }
    public void updateNewIos(){
        int col =getRandom();
        for(int i = 0;i<getCOLUMNS();i++){
            activeIos[0][i]= col == i;
        }
    }
    public void update(){
        updateGame();
        updateNewIos();
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public boolean[] getLifes() {
        return lifes;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setAndroidIndex(int androidIndex) {
        this.AndroidIndex = androidIndex;
    }
}
