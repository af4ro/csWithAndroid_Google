package com.android.scarnesdice;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.android.scarnesdice.R.drawable.dice1;

public class MainActivity extends AppCompatActivity {

    private int userOverallScore = 0;
    private int userCurrentScore = 0;
    private int computerOverallScore = 0;
    private int computerCurrentScore = 0;
    private int turn = 0;                   //Player's turn
    private int[] imageArray = {dice1, R.drawable.dice2, R.drawable.dice3,
            R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Roll button click listener
        final Button rollButton = (Button) findViewById(R.id.roll_button);
        rollButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                int rolled = rollDice();
                    if(rolled>1)
                    {
                        userCurrentScore += rolled;
                        TextView t = (TextView) findViewById(R.id.score_text);
                        t.setText("Your Score: "+userOverallScore+"\nComputer Score: "+computerOverallScore
                        +"\nYour Turn Score: "+userCurrentScore);
                    }
                    else{
                        userCurrentScore = 0;
                        computerCurrentScore = 0;
                        TextView t = (TextView) findViewById(R.id.score_text);
                        t.setText("Your Score: "+userOverallScore+"\nComputer Score: "+computerOverallScore);
                        computerTurn();
                    }
            }
        });

        final Button resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userCurrentScore = 0;
                userCurrentScore = 0;
                computerCurrentScore = 0;
                computerOverallScore = 0;
                TextView t = (TextView) findViewById(R.id.score_text);
                t.setText(R.string.Score_text);
                ((ImageView) findViewById(R.id.dice_image)).setImageResource(dice1);
            }
        });

        final Button holdButton = (Button) findViewById(R.id.hold_button);
        holdButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userOverallScore += userCurrentScore;
                userCurrentScore = 0;
                ((TextView) findViewById(R.id.score_text)).setText("Your Score: "+userOverallScore+"\nComputer Score: "+computerOverallScore);
                ((ImageView) findViewById(R.id.dice_image)).setImageResource(dice1);
                computerTurn();
            }
        });
    }

    private void computerTurn()
    {
        int rolled;
        while(computerCurrentScore<20)
        {
            rolled = rollDice();
            if (rolled == 1)
            {
                computerCurrentScore = 0;
                userCurrentScore = 0;
                break;
            }
            computerCurrentScore += rolled;
        }
        computerOverallScore += computerCurrentScore;
        TextView t = (TextView) findViewById(R.id.score_text);
        t.setText("Your Score: "+userOverallScore+"\nComputer Score: "+computerOverallScore
                +"\nComputer Turn Score: "+computerCurrentScore);
        userCurrentScore = 0;
        computerCurrentScore = 0;
        ((ImageView) findViewById(R.id.dice_image)).setImageResource(dice1);
    }

    private int rollDice() {
        int rand = (int)(Math.random() * 6 + 1);
        ImageView imgView = (ImageView) findViewById(R.id.dice_image);
        imgView.setImageResource(imageArray[rand-1]);
        return rand;
    }
}
