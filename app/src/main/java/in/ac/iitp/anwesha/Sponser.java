package in.ac.iitp.anwesha;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Sponser extends AppCompatActivity
       {


    private ListView ll_others;
           private int OtherIDs[]={R.drawable.s_ebay,R.drawable.s_io,R.drawable.s_sbi,R.drawable.s_sp};//,R.drawable.s_webx99,R.drawable.s_spykar,R.drawable.s_ebay,R.drawable.s_cc};
           private String OtherText[]={"Hospitality Partner",null,null,null,null,"Strategic Sponsors",null,null};//,"Gift","Syle","Online Shopping","Beverage Partner"};
          private int columns;
    private int MAX_WIDTH;
           CAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationDrawer(this));




        ll_others = (ListView) findViewById(R.id.grid_other_sponser);
        adapter = new CAdapter(this);
        ll_others.setAdapter(adapter);
        MAX_WIDTH = ll_others.getWidth();
        columns = Integer.parseInt((String)ll_others.getTag());
        for(int i=0;i<OtherIDs.length;i++) {
          /*  ArrayList<Integer> d_row = new ArrayList<Integer>();
            ArrayList<String> d_rowS = new ArrayList<String>();
            for(int j=0;j<columns && i<OtherIDs.length;i++,j++)
            {
                d_row.add(OtherIDs[i]);
                d_rowS.add(OtherText[i]);
            }

            append(d_row,d_rowS);
        */
         //   adapter.add(OtherIDs[i]);
        }

        }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





/*
    void append(ArrayList<Integer> drow,ArrayList<String> drowS) {
        LinearLayout ll_row = (LinearLayout) getLayoutInflater().inflate(R.layout.lay_sponser_row,null);
        ImageView iv[]=new ImageView[4];
        iv[0] = (ImageView) ll_row.findViewById(R.id.iv_s1);
        iv[1] = (ImageView) ll_row.findViewById(R.id.iv_s2);
        iv[2] = (ImageView) ll_row.findViewById(R.id.iv_s3);
        iv[3] = (ImageView) ll_row.findViewById(R.id.iv_s4);
        TextView tv[]=new TextView[4];
        tv[0] = (TextView) ll_row.findViewById(R.id.tv_s1);
        tv[1] = (TextView) ll_row.findViewById(R.id.tv_s2);
        tv[2] = (TextView) ll_row.findViewById(R.id.tv_s3);
        tv[3] = (TextView) ll_row.findViewById(R.id.tv_s4);
        for(int i=0;i<4;i++)
            iv[i].setVisibility(View.GONE);
        for(int i=0;i<4;i++)
            tv[i].setVisibility(View.GONE);

        for(int i=0;i<drow.size();i++)
        if(drow.get((i))!=0) {
            //iv[i].measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            //iv[i].setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),drow.get(i)),iv[i].getMeasuredWidth(),iv[i].getMeasuredHeight(),false));//Resource(drow.get((i)));

            iv[i].setImageResource(drow.get(i));
            iv[i].setVisibility(View.VISIBLE);
            if (drowS.get((i)) != null) {
                tv[i].setText(drowS.get((i)));
                tv[i].setVisibility(View.VISIBLE);
            }
        }


    }*/

           class CAdapter extends ArrayAdapter<Integer>
           {
               ArrayList<Integer> l = new ArrayList<>();
                int resource = R.layout.lay_sponser_row;
               public CAdapter(Context context) {
                   super(context,  R.layout.lay_sponser_row);
               }

               @Override
               public void add(Integer object) {
                  super.add(object);
                        l.add(object);
               }

               @Override
               public int getCount() {
                   return l.size();
               }

               @Override
               public Integer getItem(int position) {
                   return l.get(position);
               }

               @Override
               public View getView(int position, View convertView, ViewGroup parent) {
                   View view = getLayoutInflater().inflate(resource,null);
                   ((ImageView)view.findViewById(R.id.iv_s1)).setImageResource(getItem(position));
                   return view;
               }
           }

}
