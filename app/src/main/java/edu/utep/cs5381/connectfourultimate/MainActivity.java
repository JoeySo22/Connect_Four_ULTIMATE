package edu.utep.cs5381.connectfourultimate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.utep.cs5381.connectfourultimate.model.Board;
import edu.utep.cs5381.connectfourultimate.model.Player;

/*
    Authors: Jose Eduardo Soto and Ruth Trejo.
 */

public class MainActivity extends AppCompatActivity {
    private Board board;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        board = new Board();
        gameView = new GameView(this);
        gameView.setBoard(board);
        setContentView(gameView);
        Player player = new Player("Me");
        board.dropDisc(0, player);
        board.dropDisc(2, player);
        gameView.invalidate();
    }
}