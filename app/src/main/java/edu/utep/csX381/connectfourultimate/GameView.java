package edu.utep.csX381.connectfourultimate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import edu.utep.csX381.connectfourultimate.R;
import edu.utep.csX381.connectfourultimate.model.Board;
import edu.utep.csX381.connectfourultimate.model.Player;

/*
    Authors: Jose Eduardo Soto & Ruth Trejo
 */
public class GameView extends View {
    private int width, height;
    private long padding, playSize, playUnitSize, playUnitPad, diskRadius, posGridGenX, posGridGenY,
            posDiskGenX, posDiskGenY;
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    //Winning sequence
    Iterable<Board.Place> winningSequence;

    public GameView(Context context) {
        super(context);
        calculateWidthAndHeight();
        player1 = new Player("Aang");
        player1.setColor(Color.rgb(255,203,135));
        player2 = new Player("Toph");
        player2.setColor(Color.rgb(135,255,235));
        currentPlayer = player1;
    }


    // Much of the graphical framework is done here. Please look below
    private void calculateWidthAndHeight() {
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    width = Math.min(getWidth(), getHeight());
                    height = Math.max(getWidth(), getHeight());
                    // Provide padding for all devices for vertical and horizontal borders
                    // All of these calculations were produced on paper.
                    padding = (long) (width * .01);
                    playSize = width - (2 * padding);
                    playUnitSize = (long) (playSize / board.numOfColumns());
                    playUnitPad = playUnitSize / padding;
                    diskRadius = (playUnitSize - (2 * playUnitPad)) / 2;
                    posGridGenX = padding;
                    posGridGenY = height - padding - playSize;
                    posDiskGenX = posGridGenX + (playUnitSize / 2);
                    posDiskGenY = posGridGenY + (playUnitSize / 2);
                }
            });
        }
    }

    /* Work in progress :(*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xCoordinate = event.getX();
        float yCoordinate = event.getY();
        int colIndex = columnIndex(xCoordinate);
        // If the user clicked on the row of disks... we drop the disk
        if (yCoordinate >= posGridGenY && yCoordinate <= (posGridGenY+playUnitSize)) {
            switch( event.getAction() ){
                case MotionEvent.ACTION_DOWN:
                    Log.d("TOUCH PRESS", String.format("DISK WAS PRESSED!!!"));
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d("TOUCH RELEASE", String.format("X Coordinate: %f \n Y Coordinate: %f", xCoordinate, yCoordinate));
                    board.dropDisc(colIndex, currentPlayer);
                    switchPlayer();
                    break;
                default:
                    break;
            }
        }

        return true;
    }
    // Simple toggle for switching players
    private void switchPlayer() {
        if (currentPlayer == player1)
            currentPlayer = player2;
        else currentPlayer = player1;
    }

    // Used for approximating what column the user wishes to drop in.
    public int columnIndex(float x){
        if(x >= 0 && x < (playUnitSize)) return 0;
        else if(x >= playUnitSize && x < (playUnitSize*2)) return 1;
        else if(x > (playUnitSize*2) && x < (playUnitSize*3)) return 2;
        else if(x > (playUnitSize*3) && x < (playUnitSize*4)) return 3;
        else if(x > (playUnitSize*4) && x < (playUnitSize*5)) return 4;
        else if(x > (playUnitSize*5) && x < (playUnitSize*6)) return 5;
        else if(x > (playUnitSize*6) && x <= width) return 6;
        else return -1;
    }

    private Paint paintLines = new Paint();
    private Paint paintDisks = new Paint();

    @Override
    public void onDraw(Canvas canvas) {
        Log.d("Draw", "onDraw() CALLED!!!!!");
        super.onDraw(canvas);

        //Drawing players and their names on game startup
        drawPlayers(canvas);
        drawPlayerNames(canvas);

        // Draw Vertical grid lines
        paintLines.setColor(Color.BLACK);
        paintLines.setStrokeWidth(14);

        // Draw Vertical lines
        for (int i = 0; i <= board.numOfColumns(); i++){
            long magnitude = i * playUnitSize;
            Log.d("Draw_PlayFormat", String.format("Vertical Line \t\t %d,%d,%d,%d",
                    magnitude + padding + posGridGenX,
                    playUnitSize + padding + posGridGenY,
                    magnitude + padding + posGridGenX,
                    board.numOfColumns() * playUnitSize + padding) + posGridGenY);
            canvas.drawLine(
                    magnitude + padding + posGridGenX,
                    playUnitSize + padding + posGridGenY, // editted out 0 to resize grid
                    magnitude + padding + posGridGenX,
                    board.numOfColumns() * playUnitSize + padding + posGridGenY,
                    paintLines);
        }
        // Draw Horizontal grid lines
        for (int i = 1; i <= board.numOfColumns(); i++) {
            long magnitude = i * playUnitSize;
            Log.d("Draw_PlayFormat", String.format("Horizontal Line \t\t %d,%d,%d,%d",
                    padding + posGridGenX,
                    magnitude+ padding + posGridGenY,
                    board.numOfColumns() * playUnitSize + padding + posGridGenX,
                    magnitude + padding + posGridGenY));
            canvas.drawLine(
                    padding + posGridGenX,
                    magnitude+ padding + posGridGenY,
                    board.numOfColumns() * playUnitSize + padding + posGridGenX,
                    magnitude + padding + posGridGenY,
                    paintLines);
        }

        /*padding, playSize, playUnitSize, playUnitPad, diskRadius, posGridGenX, posGridGenY,
    posDiskGenX, posDiskGenY*/
        Log.d("Draw_PlayFormat", String.format("width \t\t %d", width));
        Log.d("Draw_PlayFormat", String.format("height \t\t %d", height));
        Log.d("Draw_PlayFormat", String.format("padding \t\t %d", padding));
        Log.d("Draw_PlayFormat", String.format("playSize \t\t %d", playSize));
        Log.d("Draw_PlayFormat", String.format("playUnitSize \t\t %d", playUnitSize));
        Log.d("Draw_PlayFormat", String.format("playUnitPad \t\t %d", playUnitPad));
        Log.d("Draw_PlayFormat", String.format("diskRadius \t\t %d", diskRadius));
        Log.d("Draw_PlayFormat", String.format("posGridGenX \t\t %d", posGridGenX));
        Log.d("Draw_PlayFormat", String.format("posGridGenY \t\t %d", posDiskGenY));
        Log.d("Draw_PlayFormat", String.format("posDiskGenX \t\t %d", posDiskGenX));
        Log.d("Draw_PlayFormat", String.format("posDiskGenY \t\t %d", posDiskGenY));

        // Draw Circles with color
        paintDisks.setStrokeWidth(1);
        paintDisks.setColor(Color.BLUE);
        /*
            The idea is to go use the Board object to listen for ChangeListener. We must also
            implement and define the function diskDropped() to draw the new circle here.
        */

        // New Top Row of Circles draw and change color according to who's turn it is
        for (int i = 0; i < board.numOfColumns(); i++) {
            Log.d("Draw_Circle_TOP", String.format("Circle \t\t %d, %d",
                    posGridGenX + (long)(playUnitSize/2) + playUnitPad + (i * playUnitSize),
                    posGridGenY + (long)(playUnitSize/2) + playUnitPad));
            canvas.drawCircle(
                    posGridGenX + (long)(playUnitSize/2) + playUnitPad + (i * playUnitSize),
                    posGridGenY + (long)(playUnitSize/2) + playUnitPad,
                    diskRadius, currentPlayer.getPaint());
        }//DRAW TOP CIRCLES LOOP END

        // Draw the rest of the circles that should be made when falling onto the columns
        for (int i = 0; i < board.numOfColumns(); i++) {
            for (int j = 0; j < board.numOfRows(); j++) {
                // The circles are placeholders for possible positions of disks
                Player player = board.playerAt(i,j);
                if (player != null){
                    Log.d("Draw_Circle", String.format("Circle \t\t %d, %d",
                            posGridGenX + (long)(playUnitSize/2) + playUnitPad + (i * playUnitSize),
                            posGridGenY + (long)(playUnitSize/2) + playUnitPad + ((j+1) * playUnitSize)));
                    canvas.drawCircle(
                            posGridGenX + (long)(playUnitSize/2) + playUnitPad + (i * playUnitSize),
                            posGridGenY + (long)(playUnitSize/2) + playUnitPad + ((j+1) * playUnitSize),
                            diskRadius, player.getPaint());

                }

            }
        }// draw circles when dropping in columns LOOP END

        /* If a given player has won, draw the strokes for the winning sequence */
        if(board.isWonBy(currentPlayer)) {
            winningSequence = board.winningRow();
            paintDisks.setColor(Color.GREEN);
            for (Board.Place coordinates : winningSequence) {
                int i = coordinates.getX();
                int j = coordinates.getY();

                // The circles are placeholders for possible winning sequence of disks
                Player player = board.playerAt(i, j);
                if (player != null) {
                    /* Debugging comment ...*/
                    Log.d("Draw_Circle", String.format("Circle \t\t %d, %d",
                            posGridGenX + (long) (playUnitSize / 2) + playUnitPad + (i * playUnitSize),
                            posGridGenY + (long) (playUnitSize / 2) + playUnitPad + ((j + 1) * playUnitSize)));
                    canvas.drawCircle(
                            posGridGenX + (long) (playUnitSize / 2) + playUnitPad + (i * playUnitSize),
                            posGridGenY + (long) (playUnitSize / 2) + playUnitPad + ((j + 1) * playUnitSize),
                            diskRadius, paintDisks);
                    drawWinner(canvas, currentPlayer.name()); //draw the name of the winner on the screen
                }//end if statement
            }//end for loop for winning sequence
        }//winning sequence if statement

    } //end onDraw method

    /* drawPlayers draws the image icons of the current players */
    public void drawPlayers(Canvas canvas){
        //Draw Aang and Toph Players
        Paint aangPlayer = new Paint();
        Bitmap drawAangPlayer = BitmapFactory.decodeResource(getResources(), R.drawable.aang_player);
        canvas.drawBitmap(drawAangPlayer, 30, 50, aangPlayer);

        Paint tophPlayer = new Paint();
        Bitmap drawTophPlayer = BitmapFactory.decodeResource(getResources(), R.drawable.toph_player);
        canvas.drawBitmap(drawTophPlayer, 1000, 50, tophPlayer);
    }

    /* drawWinner draws the name of the winning player! */
    public void drawWinner(Canvas canvas, String name){
        Paint winner = new Paint();
        winner.setTextSize(100);
        winner.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.format("%s WON!", name.toUpperCase()), 750, 800, winner);
    }

    /* drawPlayerNames draws the name of the current players */
    public void drawPlayerNames(Canvas canvas){
        //Draw names of players
        Paint aangName = new Paint();
        aangName.setTextSize(70);
        aangName.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Aang", 500, 450, aangName);

        Paint tophName = new Paint();
        tophName.setTextSize(70);
        tophName.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Toph", 950, 450, tophName);
    }

    /* Set board calls redraw to redraw the board */
    public void setBoard(Board board) {
        // This function will also supply the ChangeListener
        this.board = board;
        this.board.setChangeListener(new Board.ChangeListener() {
            @Override
            public void discDropped(int x, int y, Player player) {
                Log.d("Set_Board", "diskDropped() called!!");
                // Switch Players
                redraw();
            }
        });
    }

    // annonymous method ChangeListener needed to call invalidate.
    private void redraw() {
        Log.d("Redraw", "redraw() called!!");
        this.invalidate();
    }
} //end GameViewClass