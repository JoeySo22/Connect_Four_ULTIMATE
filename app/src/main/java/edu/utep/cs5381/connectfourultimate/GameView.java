package edu.utep.cs5381.connectfourultimate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import edu.utep.cs5381.connectfourultimate.model.Board;
import edu.utep.cs5381.connectfourultimate.model.Player;

/*
    Authors: Jose Eduardo Soto & Ruth Trejo
 */

public class GameView extends View {
    private int width, height;
    private long padding, playSize, playUnitSize, playUnitPad, diskRadius, posGridGenX, posGridGenY,
    posDiskGenX, posDiskGenY;
    private Board board;

    public GameView(Context context) {
        super(context);
        calculateWidthAndHeight();
    }

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
                    padding = (long)(width * .01);
                    playSize = width - (2*padding);
                    playUnitSize = (long) (playSize / board.numOfColumns());
                    playUnitPad = playUnitSize / padding;
                    diskRadius = (playUnitSize - (2 * playUnitPad)) / 2;
                    posGridGenX = padding;
                    posGridGenY = height - padding - playSize;
                    posDiskGenX = posGridGenX + (playUnitSize/2);
                    posDiskGenY = posGridGenY + (playUnitSize/2);
                }
            });
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_BUTTON_PRESS
        }
    }

    private Paint paintLines = new Paint();
    private Paint paintDisks = new Paint();

    @Override
    public void onDraw(Canvas canvas){
        Log.d("Draw", "onDraw() CALLED!!!!!");
        super.onDraw(canvas);
        // Draw Vertical grid lines
        paintLines.setColor(Color.BLACK);
        paintLines.setStrokeWidth(14);

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

        // Draw Circles
        paintDisks.setStrokeWidth(1);
        paintDisks.setColor(Color.BLUE);
        /*
            The idea is to go use the Board object to listen for ChangeListener. We must also
            implement and define the function diskDropped() to draw the new circle here.
        */

        // New Top Row of Circles draw and change color according to who's turn it is
        for (int i = 0; i <= board.numOfColumns(); i++) {
            Log.d("Draw_Circle_TOP", String.format("Circle \t\t %d, %d",
                    posGridGenX + (long)(playUnitSize/2) + playUnitPad + (i * playUnitSize),
                    posGridGenY + (long)(playUnitSize/2) + playUnitPad));
            canvas.drawCircle(
                    posGridGenX + (long)(playUnitSize/2) + playUnitPad + (i * playUnitSize),
                    posGridGenY + (long)(playUnitSize/2) + playUnitPad,
                    diskRadius, paintDisks);
        }

        // Draw the rest of the circles that should be made
        for (int i = 0; i < board.numOfColumns(); i++) {
            for (int j = 1; j < board.numOfRows(); j++) {
                // The circles are placeholders for possible positions of disks
                Player player = board.playerAt(i,j);
                if (player != null){
                    Log.d("Draw_Circle", String.format("Circle \t\t %d, %d",
                            posGridGenX + (long)(playUnitSize/2) + playUnitPad + (i * playUnitSize),
                            posGridGenY + (long)(playUnitSize/2) + playUnitPad + ((j+1) * playUnitSize)));
                    canvas.drawCircle(
                            posGridGenX + (long)(playUnitSize/2) + playUnitPad + (i * playUnitSize),
                            posGridGenY + (long)(playUnitSize/2) + playUnitPad + ((j+1) * playUnitSize),
                            diskRadius, paintDisks);
                }

            }
        }
    }

    public void setBoard(Board board) {
        // This function will also supply the ChangeListener
        this.board = board;
        this.board.setChangeListener(new Board.ChangeListener() {
            @Override
            public void discDropped(int x, int y, Player player) {
                // Need to alert View of arguments and redraw when changed.

            }
        });
    }
}
