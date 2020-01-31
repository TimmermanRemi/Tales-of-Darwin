package fr.iutlens.mmi.jumper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void start(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        this.startActivity(intent);
    }

    if(hero.perdu){//bouton affiche rejouer

    }else{ //bouton affiche jouer

    }


}
