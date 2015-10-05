package in.ac.iitp.anwesha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeScreen extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 0;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 0;

    private View ib_proceed;


    private TextView tv_anwesha,tv_anwesha_desc;

    private Animation ani_fadein,ani_fadein2,ani_fadeout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome_screen);
        ib_proceed = findViewById(R.id.proceed);
        ib_proceed.setOnClickListener(this);


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.




        tv_anwesha= (TextView) findViewById(R.id.tv_anwesha_text);
        tv_anwesha.setTypeface(AllIDS.font_Anwesha);
        tv_anwesha_desc = (TextView) findViewById(R.id.tv_anwesha_desc);
        tv_anwesha_desc.setTypeface(AllIDS.font_AnweshaSub);
        ani_fadein= AnimationUtils.loadAnimation(this, R.anim.fadein);
        ani_fadein.setFillAfter(true);
        ani_fadein.setStartOffset(300);
        ani_fadein2 = AnimationUtils.loadAnimation(this,R.anim.fadein);
        ani_fadein2.setStartOffset(1200);
        findViewById(R.id.logo).startAnimation(ani_fadein2);
        ani_fadein2.setFillAfter(true);
        //ani_fadeout = AnimationUtils.loadAnimation(this,R.anim.fadeout);
        //total 1700 ms
        //ani_fadeout.setStartOffset(1700+1000);


        tv_anwesha.startAnimation(ani_fadein);
        tv_anwesha_desc.startAnimation(ani_fadein2);
        ani_fadein2.setAnimationListener(this);

        tv_anwesha.animate();
        tv_anwesha_desc.animate();
        hide();


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };


    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
    }


    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };

    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation == ani_fadein2)
        {
            ib_proceed.setVisibility(View.VISIBLE);
            tv_anwesha.clearAnimation();
            ib_proceed.startAnimation(ani_fadein);

            //     tv_anwesha.startAnimation(ani_fadeout);
        //    tv_anwesha_desc.startAnimation(ani_fadeout);
       //     ani_fadeout.setAnimationListener(this);

        }
        else
        {
//            ib_proceed.setVisibility(View.VISIBLE);
//            ib_proceed.startAnimation(ani_fadein);

        }
    }


    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(this, Home.class);
        startActivity(in);

        finish();

    }
}
