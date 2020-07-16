package edu.utep.csX381.connectfourultimate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import edu.utep.csX381.connectfourultimate.model.Board;
import edu.utep.csX381.connectfourultimate.model.Player;
import edu.utep.csX381.connectfourultimate.model.Board;

public class MainActivity extends AppCompatActivity {

    private Board board;
    private GameView gameView;
    public boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        board = new Board();
        gameView = new GameView(this);
        gameView.setBoard(board);
        setContentView(gameView);
        
        Player player = new Player("Me");
        gameView.invalidate();
    }
}