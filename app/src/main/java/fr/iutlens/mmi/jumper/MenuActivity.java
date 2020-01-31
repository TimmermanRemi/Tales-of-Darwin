package fr.iutlens.mmi.jumper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button button = findViewById(R.id.button);
        TextView txt = findViewById(R.id.textView);


        if(getIntent().getBooleanExtra("perdu",false)){//bouton affiche rejouer
            int score = getIntent().getIntExtra("score", 0);

             button.setText("rejouer");
             txt.setText("Vous avez perdu avec un score de " +score+ " m");
        }else{ //bouton affiche jouer
            button.setText("jouer");
        }
    }

    public void start(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        this.startActivity(intent);
    }



}
