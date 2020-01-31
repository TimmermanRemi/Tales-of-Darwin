package fr.iutlens.mmi.jumper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import fr.iutlens.mmi.jumper.utils.AccelerationProxy;

/***
 * Crédits image : https://pixabay.com/fr/lapin-dessin-animé-jeu-élément-1582176/
 *
 *
 *
 */
public class MainActivity extends AppCompatActivity {

//    private AccelerationProxy proxy;
    private GameView game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // On récupère la vue du jeu
        game = findViewById(R.id.gameView);

        // On configure le jeu pour recevoir les changements d'orientation
   //     proxy = new AccelerationProxy(this, game);
    }


    @Override
    protected void onResume() {
        super.onResume();
     //   proxy.resume(); // On relance l'accéléromètre
    }

    @Override
    protected void onPause() {
        super.onPause();
       // proxy.pause(); // On mets en pause l'accéléromètre
    }

    public void button_r(View view) {
        game.mv_right();
    }

    public void button_l(View view) {
        game.mv_left();
    }
}
