package in.ac.iitp.anwesha;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private View nameBox,welcomeframe,head,head_container;
    private Animation name_Box,welcome_anim,header,tray_anim,tray_anim1,img1_anim,img2_anim,moveout1,moveout2,fadedrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        ((TextView)findViewById(R.id.tv_anwesha_head)).setTypeface(AllIDS.font_Anwesha);
        //
        nameBox=findViewById(R.id.name_box);
        head_container=findViewById(R.id.head_container1);
        name_Box= AnimationUtils.loadAnimation(this, R.anim.title_open);
        nameBox.startAnimation(name_Box);
        nameBox.animate();
        //
        welcomeframe=findViewById(R.id.welcome);
        //welcome_anim=AnimationUtils.loadAnimation(this, R.anim.abc_fade_in);
        //welcome_anim.setStartOffset(2000);
        //welcome_anim.setDuration(1400);

        //welcomeframe.startAnimation(welcome_anim);
        //welcomeframe.animate();
        //
        head=findViewById(R.id.frameLayout);
        //head.setMinimumHeight(findViewById(R.id.layout_container).getHeight());
        header=AnimationUtils.loadAnimation(this, R.anim.home_options);
        head.startAnimation(header);
        head.animate();
        //

        final ImageView expandImg1=(ImageView)findViewById(R.id.ex_img1);
        final ImageView expandImg2=(ImageView)findViewById(R.id.ex_img2);
        final View tray=findViewById(R.id.link_tray);

        tray_anim=AnimationUtils.loadAnimation(this, R.anim.compress);
        tray_anim.setFillAfter(true);
        tray.startAnimation(tray_anim);
        tray.animate();

        tray_anim1=AnimationUtils.loadAnimation(this,R.anim.expand);
        tray_anim1.setFillAfter(true);
        img1_anim=AnimationUtils.loadAnimation(this, R.anim.translate_hometray);
        img1_anim.setFillAfter(true);
        expandImg1.startAnimation(img1_anim);
        expandImg1.animate();
        img2_anim=AnimationUtils.loadAnimation(this, R.anim.translate_hometray2);
        img2_anim.setFillAfter(true);
        expandImg2.startAnimation(img2_anim);
        expandImg2.animate();
        moveout1=AnimationUtils.loadAnimation(this,R.anim.moveout1);
        moveout1.setFillAfter(true);
        moveout2=AnimationUtils.loadAnimation(this,R.anim.moveout2);
        moveout2.setFillAfter(true);
        final View app_tray=findViewById(R.id.app_tray);
        final View l1=findViewById(R.id.l1);
        //final View l2=findViewById(R.id.l2);
        l1.setAlpha(0);
        fadedrop=AnimationUtils.loadAnimation(this, R.anim.fadeintranslate);
        //l2.startAnimation(fadedrop);
        //l2.animate();

        ((TextView)findViewById(R.id.id_hiuser)).setTypeface(AllIDS.font_AnweshaSub);

        app_tray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(home.this,"c",Toast.LENGTH_SHORT).show();
                app_tray.setAlpha(0);
                l1.setAlpha(1);
                app_tray.setEnabled(false);
                tray.startAnimation(tray_anim1);
                tray.animate();
                expandImg1.startAnimation(moveout1);
                expandImg1.animate();
                expandImg2.startAnimation(moveout2);
                expandImg2.animate();
            }
        });

        head_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tray.startAnimation(tray_anim);
                tray.animate();
                expandImg1.startAnimation(img1_anim);
                expandImg1.animate();
                expandImg2.startAnimation(img2_anim);
                expandImg2.animate();
                app_tray.setAlpha(1);
                l1.setAlpha(0);
                app_tray.setEnabled(true);
            }
        });

        View v=findViewById(R.id.b_home_team);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.oscillating);

        v.startAnimation(animation);

        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOpenFacebookPage();
            }
        });


    }



    public void onButtonClick(View v)
    {
        int id= v.getId();
        Intent in;
        switch (id)
        {
            case R.id.b_home_superClubs:
                MyNavigationDrawer.openEvent(this);
                break;
            case R.id.b_home_sponser:
                MyNavigationDrawer.openSponser(this);
                break;

            case R.id.b_home_team:
                MyNavigationDrawer.openTeam(this);
                break;
            case R.id.b_home_pronites:
                MyNavigationDrawer.openPronites(this);
                break;
            case R.id.b_home_galley:
                MyNavigationDrawer.openGallery(this);
                break;
            case R.id.b_home_schedule:
                MyNavigationDrawer.openSchedule(this);
                break;
            case R.id.b_home_map:
                MyNavigationDrawer.openMap(this);
                break;
            case R.id.b_home_about:
                MyNavigationDrawer.openAbout(this);
                break;


        }
    }
    void getOpenFacebookPage()
    {

        Intent in;
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            in = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/anwesha.iitpatna"));
        } catch (Exception e) {
            in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/anwesha.iitpatna"));
        }
        startActivity(in);

    }


}
