package com.example.l2_xo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class StartGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edit_text_p1 = findViewById(R.id.editText_p1);
        EditText edit_text_p2 = findViewById(R.id.editText_p2);
        Button button_start_game = findViewById(R.id.button_start_game);



        button_start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartGame.this, Game.class);

                String p1_name = edit_text_p1.getText().toString();
                String p2_name = edit_text_p2.getText().toString();

                if(p2_name.isEmpty() || p1_name.isEmpty()){
                    Toast.makeText(StartGame.this,"Insert both player names!", Toast.LENGTH_SHORT).show();
                }else{

                    intent.putExtra("p1_name", p1_name);
                    intent.putExtra("p2_name", p2_name);

                    startActivity(intent);
                }
            }
        });


    }
}