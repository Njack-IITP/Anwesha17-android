package in.ac.iitp.anwesha;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Event extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener, Animation.AnimationListener, View.OnDragListener {

    final int DURATION_SEPRATION = 1300;
    private Animation animations[];
    private float radius;
    private RelativeLayout v_club_holder;
    float x_cord, y_cord, center_x = 0, center_y = 0;
    private View views[];
    private int no_of_events;
    private ImageView iv_center_glow;
    private int glowtype = 0;//0 - invisible 1 - glowing 2 -full 3 -disdlowing
    private Animation ani_glow, ani_glow_dis;
    private int last_event_drag = -1;

    private int loc_x[], loc_y[];

    private ImageView iv_center;
    private TextView tv_group_head;
    private Animation gh_in,gh_out;
    private boolean tvGroupHeadVisible=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        v_club_holder = (RelativeLayout) findViewById(R.id.fl_club_holder);
        iv_center_glow = (ImageView) findViewById(R.id.event_center_glow);
        iv_center_glow.setVisibility(View.INVISIBLE);
        iv_center = (ImageView) findViewById(R.id.event_center_back);
        tv_group_head = (TextView) findViewById(R.id.tv_group_head);
        iv_center.setOnTouchListener(this);
        iv_center.setOnDragListener(this);
        gh_in= AnimationUtils.loadAnimation(this, R.anim.group_head_in);
        gh_out=AnimationUtils.loadAnimation(this,R.anim.group_head_out);

        ani_glow = AnimationUtils.loadAnimation(this, R.anim.grow);
        ani_glow.setAnimationListener(this);
        ani_glow_dis = AnimationUtils.loadAnimation(this, R.anim.grow_disapper);
        ani_glow_dis.setAnimationListener(this);
        int EventsIDS[] = {R.id.ib_events_tech, R.id.ib_events_cult, R.id.ib_events_eco, R.id.ib_events_lit, R.id.ib_events_mang};
        int Arrows[]={R.id.event_a0,R.id.event_a1,R.id.event_a2,R.id.event_a3,R.id.event_a4};
        no_of_events = EventsIDS.length;
        views = new View[no_of_events];
        for (int i = 0; i < no_of_events; i++) {
            views[i] = findViewById(EventsIDS[i]);
            ((ImageButton)views[i]).setBackground((new BitmapDrawable(giveMeCirculatImage(((BitmapDrawable) ((ImageButton) views[i]).getBackground()).getBitmap(),0.5f,true))).getCurrent());

        }

        radius = 200+(int) (100*v_club_holder.getMeasuredWidth()*0.4);
        animations = new Animation[no_of_events];
        loc_x = new int[no_of_events];
        loc_y = new int[no_of_events];

        for (int i = 0; i < no_of_events; i++) {
            loc_x[i] = (int) (radius * Math.cos(2 * i * Math.PI / no_of_events - Math.PI / 2));
            loc_y[i] = (int) (radius * Math.sin(2 * i * Math.PI / no_of_events - Math.PI / 2));
            ImageView iv= (ImageView) findViewById(Arrows[i]);
            iv.setTranslationX(loc_x[i]/2);
            iv.setTranslationY(loc_y[i]/2);
            double angle = 360*i / no_of_events;
            iv.setRotation((float)(angle+180));
            angle +=90;
            Animation ani_oscillation;

            ani_oscillation = new TranslateAnimation(0,(float)(10*Math.cos(angle*Math.PI/180)),0,(float)(10*Math.sin(angle*Math.PI/180)));
            ani_oscillation.setRepeatCount(Animation.INFINITE);
            ani_oscillation.setInterpolator(this,android.R.anim.cycle_interpolator);
            ani_oscillation.setDuration(1000);
            iv.startAnimation(ani_oscillation);


            views[i].setTranslationX(+loc_x[i]);
            views[i].setTranslationY(+loc_y[i]);

            center_x += views[i].getX();
            center_y += views[i].getY();


            Animation animation;
            animation = new TranslateAnimation(-loc_x[i], 0, -loc_y[i], 0);
            animation.setDuration(DURATION_SEPRATION);
            animation.setFillBefore(true);
            animation.setFillAfter(false);
            animation.setInterpolator(this, android.R.anim.anticipate_overshoot_interpolator);
            animations[i] = animation;
            animations[i].setAnimationListener(this);
            views[i].startAnimation(animations[i]);



        }

        center_x /= no_of_events;
        center_y /= no_of_events;


    }

    @Override
    protected void onResume() {
        super.onResume();
        last_event_drag=-1;
        tv_group_head.setHint("Drag Your Event");
        tv_group_head.setText("Drag Your Event");
    }

    public void eventClicked(View v) {
        boolean isDone = true;
        String club="";
        switch ((String) v.getTag()) {
            case "mang":
                club = "Management";

                break;
            case "tech":
                club = "Technical";

                break;
            case "eco":
                club = "ECO";

                break;
            case "lit":
                club = "Literary";

                break;
            case "cult":
                club = "Cultural";

                break;
            default:
                isDone = false;


        }
        if (isDone)
        {
            MyNavigationDrawer.openSubEvent(this,(String)v.getTag());
            tv_group_head.setText(null);
            configureHighlighter(false);
        }

    }

    public static String getEventName(String tag) {
        if(tag==null)
            return null;
        switch (tag) {
            case "mang":
                return "Management";

            case "tech":
                return  "Techinical";

            case "eco":
                return  "ECO";

            case "lit":
                return  "Literary";

            case "cult":
                return  "Cultural";

            case "njack":
                return "NJACK";

            case "spark":
                return  "Sparkonics";

            case "scme":
                return  "SCME";

            case "thesholdNchem":
                return  "Threshold &amp; Chemical";

            case "rtdc":
                return  "RTDC";



        }
        return null;
    }

    @Override
    public void onClick(View view) {
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData dragData = new ClipData(view.getTag().toString(), mimeTypes, item);
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(dragData, shadowBuilder, null, 0);


    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENDED:

                x_cord = dragEvent.getX();
                y_cord = dragEvent.getY();

                if (glowtype == 1 || glowtype == 2)
                    if (last_event_drag >= 0)
                    {
                        eventClicked(views[last_event_drag]);
                        if (tvGroupHeadVisible) {
                            tvGroupHeadVisible = false;
                            tv_group_head.startAnimation(gh_out);
                        }

                    }


                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                x_cord = dragEvent.getX();
                y_cord = dragEvent.getY();
                if (last_event_drag >= 0)
                    configureHighlighter(true);

                break;
            case DragEvent.ACTION_DRAG_EXITED:
                x_cord = dragEvent.getX();
                y_cord = dragEvent.getY();
                if (last_event_drag >= 0) {
                    configureHighlighter(false);
                }


                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                x_cord = dragEvent.getX();
                y_cord = dragEvent.getY();


                break;

        }

        return true;
    }

    void configureHighlighter(boolean isInside) {

        if (isInside) {
            if (glowtype == 0) {
                iv_center_glow.setVisibility(View.VISIBLE);
                iv_center_glow.startAnimation(ani_glow);
                glowtype = 1;
            }
        } else {
            if (glowtype == 1 || glowtype == 2) {
                iv_center_glow.startAnimation(ani_glow_dis);
                glowtype = 3;
            }

        }


    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {


        if (animation == ani_glow) {
            glowtype = 2;
        } else if (animation == ani_glow_dis) {
            glowtype = 0;
            iv_center_glow.setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < no_of_events; i++)
            if (animation == animations[i]) {
                views[i].setOnClickListener(this);
                views[i].setOnTouchListener(this);
                break;
            }



    }

    @Override
    public void onAnimationRepeat(Animation animation) {




    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData clipData = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(clipData, shadowBuilder, view, 0);
            view.setVisibility(View.VISIBLE);

            if (view == iv_center) return true;
            last_event_drag = -1;
            for (int i = 0; i < no_of_events; i++)
                if (view == views[i]) {
                    last_event_drag = i;
                    break;
                }
            if(last_event_drag>=0)
            {
                //if(!tvGroupHeadVisible)
                {
                    tvGroupHeadVisible=true;
                    tv_group_head.setText(getEventName((String)view.getTag()));
                    tv_group_head.startAnimation(gh_in);
                }
            }


            return true;
        }
        return false;
    }

    public static Bitmap giveMeCirculatImage(Bitmap b,float scale,boolean addShadow) {
        int width = b.getWidth(), height = b.getHeight();

        Bitmap cropped_bitmap;
        if (width > height) {
            cropped_bitmap = Bitmap.createBitmap(b,
                    (width / 2) - (height / 2), 0, height, height);
        } else {
            cropped_bitmap = Bitmap.createBitmap(b, 0, (height / 2)
                    - (width / 2), width, width);
        }

        BitmapShader shader = new BitmapShader(cropped_bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        height = cropped_bitmap.getHeight();
        width = cropped_bitmap.getWidth();

        Bitmap mCanvasBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(mCanvasBitmap);

        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        canvas.scale(scale,scale);
        if(addShadow) {
       /*     Paint shadow = new Paint();
            paint.setShadowLayer(width / 2, width / 2 / 8, width / 2 / 8, Color.GREEN);
            canvas.drawCircle(width / 2, width / 2,width/2, shadow);
        */
        }

        return mCanvasBitmap;
    }

}
