package in.ac.iitp.anwesha2k17;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 0;
    private static final int UI_ANIMATION_DELAY = 0;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {

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
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    private View ib_proceed;

    private TextView tv_anwesha, tv_anwesha_desc;
    private Animation ani_fadein, ani_fadein2, ani_fadeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome_screen);
        ib_proceed = findViewById(R.id.proceed);
        ib_proceed.setOnClickListener(this);
        tv_anwesha = (TextView) findViewById(R.id.tv_anwesha_text);
        tv_anwesha.setTypeface(AllIDS.font_Anwesha);
        tv_anwesha_desc = (TextView) findViewById(R.id.tv_anwesha_desc);
        tv_anwesha_desc.setTypeface(AllIDS.font_AnweshaSub);
        ani_fadein = AnimationUtils.loadAnimation(this, R.anim.fadein);
        ani_fadein.setFillAfter(true);
        ani_fadein.setStartOffset(300);
        ani_fadein2 = AnimationUtils.loadAnimation(this, R.anim.fadein);
        ani_fadein2.setStartOffset(1200);
        findViewById(R.id.logo).startAnimation(ani_fadein2);
        ani_fadein2.setFillAfter(true);
        ani_fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);
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

        delayedHide(100);
    }

    private void hide() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        mHideHandler.removeCallbacks(mShowPart2Runnable);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == ani_fadein2) {
            ib_proceed.setVisibility(View.INVISIBLE);
            tv_anwesha.clearAnimation();
            ani_fadein.setDuration(1000);
            ib_proceed.startAnimation(ani_fadein);
            ani_fadein.setAnimationListener(this);

        } else {
            onClick(null);

        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    private SharedPreferences getPreferences() {
        SharedPreferences sharedPref = getApplication().getSharedPreferences("login", MODE_PRIVATE);
        return sharedPref;
    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(this, Home.class);
        in.putExtra("loginflag",getPreferences().getInt("loginflag", 0));
        in.putExtra("id",getPreferences().getString("id", "Anwesha 2017"));
        in.putExtra("name",getPreferences().getString("name", "Think.Dream.Live"));
        startActivity(in);
        finish();
    }
}
