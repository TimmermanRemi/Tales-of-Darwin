package fr.iutlens.mmi.jumper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

import fr.iutlens.mmi.jumper.utils.AccelerationProxy;
import fr.iutlens.mmi.jumper.utils.RefreshHandler;
import fr.iutlens.mmi.jumper.utils.SpriteSheet;
import fr.iutlens.mmi.jumper.utils.TimerAction;

public class GameView extends View implements TimerAction, AccelerationProxy.AccelerationListener, View.OnTouchListener {
    public static final float SPEED = 0.1f;
    private RefreshHandler timer;
    private Level level;
    private float current_pos;
    private float score;
    private Hero hero;
    private double prep;
    private float speed;
    private float alpha;
    private float alpha_target;

    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    /**
     * Initialisation de la vue
     *
     * Tous les constructeurs (au-dessus) renvoient ici.
     *
     * @param attrs
     * @param defStyle
     */
    private void init(AttributeSet attrs, int defStyle) {

        // Chargement des feuilles de sprites
        SpriteSheet.register(R.drawable.decor_running,3,4,this.getContext());
        level = new Level(R.drawable.decor_running,null);
        SpriteSheet.register(R.drawable.running_rabbit,3,3,this.getContext());
        hero = new Hero(R.drawable.running_rabbit,SPEED);



        // Gestion du rafraichissement de la vue. La méthode update (juste en dessous)
        // sera appelée toutes les 30 ms
        timer = new RefreshHandler(this);
        current_pos =0;
        score = 0;
        alpha = 0.5f;
        timer.scheduleRefresh(30);
        mv_right();

        // Un clic sur la vue lance (ou relance) l'animation
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                hero.jump(1.5f);
//
//            }
//        });

        this.setOnTouchListener(this);

    }

    /**
     * Mise à jour (faite toutes les 30 ms)
     */
    @Override
    public void update() {
        if (this.isShown() && !hero.perdu) { // Si la vue est visible et que le héros n'a pas perdu
            timer.scheduleRefresh(30); // programme le prochain rafraichissement
            score += speed;
            current_pos += speed;
            if (current_pos>level.getLength()) current_pos = 0; //permet de boucler le niveau
            if (current_pos<0) speed = 0;
            hero.update(level.getFloor(current_pos+1),level.getSlope(current_pos+1), speed, level.getTuile(current_pos+1));

            if(hero.perdu){ //si l'héros a perdu, lance l'activité menu
                Intent intent = new Intent(getContext(),MenuActivity.class);
                intent.putExtra("perdu", true);
                intent.putExtra("score", (int)(score));
                getContext().startActivity(intent);
            }

            if (speed>=0) alpha_target=0.3f;
            else alpha_target = 0.6f;


            if (Math.abs((alpha_target-alpha))< 0.05f) alpha = alpha_target;
            else if (alpha< alpha_target) alpha += 0.05f;
            else if (alpha > alpha_target) alpha -= 0.05f;


            invalidate(); // demande à rafraichir la vue
        }
    }

    /**
     * Méthode appelée (automatiquement) pour afficher la vue
     * C'est là que l'on dessine le décor et les sprites
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // On met une couleur de fond
        canvas.drawColor(0xff000000);

        // On choisit la transformation à appliquer à la vue i.e. la position
        // de la "camera"
        setCamera(canvas);

        // Dessin des différents éléments
        level.paint(canvas,current_pos);

        float x = 1;
        float y = hero.getY();
        hero.paint(canvas,level.getX(x),level.getY(y),speed);


    }

    private void setCamera(Canvas canvas) {

        float scale = getWidth()/level.getWidth();

        // La suite de transfomations est à interpréter "à l'envers"

        int x = (int) (getWidth() * alpha);


        canvas.translate(x,getHeight()/2);

        // On mets à l'échelle calculée au dessus
        canvas.scale(scale, scale);

        // On centre sur la position actuelle de la voiture (qui se retrouve en 0,0 )
        canvas.translate(0,-level.getY(hero.getY()));

    }


    @Override
    public void onAcceleration(float accelDelta, double dt) {
//        Log.d("onAcceleration", accelDelta+" "+dt);
        if (accelDelta>0.5f){
            hero.jump((float) Math.abs(accelDelta));
        }
/*        if (accelDelta<0)
            prep += -accelDelta;
            if (prep > hero.MAX_STRENGTH) {
                hero.jump((float) prep);
                prep = 0;
            }
        else {
            if (prep > 0.1) {
                hero.jump((float) prep);
            }
            prep = 0;
        }*/
    }

    public void mv_right() {
        if(speed == -SPEED) speed = 0;
        else speed = SPEED;
    }

    public void mv_left() {
        if(current_pos>0){
            if(speed == SPEED) speed = 0;
            else speed = -SPEED;
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction()== MotionEvent.ACTION_DOWN){
            hero.jump(2.5f);
            return true;
        }


        return false;
    }
}
