package com.example.l2_xo;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class Game extends AppCompatActivity {
    private ImageButton changeBckButton;
    private Toolbar toolbar;
    private TextView p1;
    private TextView p2;
    private TextView player1Score;
    private TextView player2Score;
    private TextView playerStatus;
    private Button resetGame;
    private Button[] buttons = new Button[9];
    private int playerOneScoreCount, playerTwoScoreCount, roundCount;
    boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};
    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8},//rows
            {0,3,6}, {1,4,7}, {2,5,8},//columns
            {0,4,8}, {2,4,6}//diagonals
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        changeBckButton = findViewById(R.id.change_bckg_color);
        changeBckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showColorPickerDialog();

            }
        });

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);


        p1 = findViewById(R.id.tv_p1_name);
        p2 = findViewById(R.id.tv_p2_name);
        player1Score = findViewById(R.id.p1_score);
        player2Score = findViewById(R.id.p2_score);
        playerStatus = findViewById(R.id.playerStatus);
        resetGame = findViewById(R.id.reset_game_btn);

        Intent intent = getIntent();

        if (intent != null){

        String p1_name = intent.getStringExtra("p1_name");
        String p2_name = intent.getStringExtra("p2_name");
        p1.setText(p1_name);
        p2.setText(p2_name);

        for(int i=0; i<buttons.length; i++){
            String buttonID = "btn_"+i;
            int resourceID=getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!((Button)view).getText().toString().equals("")){
                        return;
                    }
                    String buttonID = view.getResources().getResourceEntryName(view.getId());
                    int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));
                    if(activePlayer){
                        ((Button)view).setText("X");
                        ((Button)view).setTextColor(Color.parseColor("#FF5733"));
                        gameState[gameStatePointer] = 0;

                    }else {
                        ((Button)view).setText("O");
                        ((Button)view).setTextColor(Color.parseColor("#2ECC71"));
                        gameState[gameStatePointer] = 1;
                    }
                    roundCount++;

                    if(checkWinner()){
                        if(activePlayer){
                            playerOneScoreCount++;
                            updatePlayerScore();
                            Toast.makeText(Game.this, "Player 1 won!", Toast.LENGTH_SHORT).show();
                            playAgain();
                        }else{
                            playerTwoScoreCount++;
                            updatePlayerScore();
                            Toast.makeText(Game.this, "Player 2 won!", Toast.LENGTH_SHORT).show();
                            playAgain();
                        }

                    }else if(roundCount == 9){
                        playAgain();
                        Toast.makeText(Game.this, "Draw!", Toast.LENGTH_SHORT).show();
                    }else{
                        activePlayer = !activePlayer;
                    }

                    if(playerOneScoreCount > playerTwoScoreCount){
                        playerStatus.setText("P1 is winning!");
                    }else if (playerTwoScoreCount > playerOneScoreCount){
                        playerStatus.setText("P2 is winning!");
                    }else{
                        playerStatus.setText("");
                    }

                    resetGame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            playAgain();
                            playerOneScoreCount = 0;
                            playerTwoScoreCount = 0;
                            playerStatus.setText("");
                            updatePlayerScore();
                        }
                    });
                }
            });
            }

        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer=true;

        }


    }

    public boolean checkWinner(){
        boolean winnerResult = false;
        for(int [] winningPosition : winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]]&&
                    gameState[winningPosition[0]]!=2){
                winnerResult = true;
            }
        }
        return winnerResult;
    }
    public void updatePlayerScore(){
        player1Score.setText(Integer.toString(playerOneScoreCount));
        player2Score.setText(Integer.toString(playerTwoScoreCount));
    }
    public void playAgain(){
        roundCount = 0;
        activePlayer = true;

        for(int i=0; i<buttons.length;i++){
            gameState[i]=2;
            buttons[i].setText("");

        }
    }

    private void showColorPickerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        builder.setTitle("Pick a color");
        final CharSequence[] colors = {"Red", "Green", "Blue", "Yellow", "Black", "Custom"};
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (which == colors.length-1){
                    setActivityBackgroundColor(Color.WHITE);
                } else {
                    int color = Color.BLACK;
                    switch(which){
                        case 0:
                            color = Color.RED;
                            break;
                        case 1:
                            color = Color.GREEN;
                            break;
                        case 2:
                            color = Color.BLUE;
                            break;
                        case 3:
                            color = Color.YELLOW;
                            break;
                    }
                    setActivityBackgroundColor(color);
                }
            }
        });
        builder.show();
    }

    private void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

}