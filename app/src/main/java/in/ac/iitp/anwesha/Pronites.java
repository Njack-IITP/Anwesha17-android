package in.ac.iitp.anwesha;

import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Pronites extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    static ImageView i1;
    static ImageView i2;
    static ImageView i3;
/*    TextView tv1,tv2,tv3;
    Typeface font;*/
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_nites);
       /* i1=(ImageView)findViewById(R.id.img1);
        i2=(ImageView)findViewById(R.id.img2);
        i3=(ImageView)findViewById(R.id.img3);*/
     /*   tv1=(TextView)findViewById(R.id.textView2);
        tv2=(TextView)findViewById(R.id.textView3);
        tv3=(TextView)findViewById(R.id.textView4);
        font=Typeface.createFromAsset(getAssets(), "fonts/caviardreams.ttf");
        tv1.setTypeface(font);
        tv2.setTypeface(font);
        tv3.setTypeface(font); */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(mViewPager);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationDrawer(this));

        //This was giving null pointer exception !!

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });      */

    }




    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Day 1";
                case 1:
                    return "Day 2";
                case 2:
                    return "Day 3";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;// = inflater.inflate(R.layout.fragment_pro_nites, container, false);
            Random rand=new Random();
            int i;
            if( getArguments().getInt(ARG_SECTION_NUMBER)==1){
                rootView = inflater.inflate(R.layout.fragment_pro_nites, container, false);
                ((TextView)rootView.findViewById(R.id.textView2)).setTypeface(AllIDS.font_AnweshaSub);
                i=(rand.nextInt(100))%2;
                if(i==0) {
                    ((ImageView)rootView.findViewById(R.id.img1)).setBackgroundResource(R.drawable.anwesha);
                }
                else {
                    ((ImageView)rootView.findViewById(R.id.img1)).setBackgroundResource(R.drawable.anwesha5);
                }

            }
            else if(getArguments().getInt(ARG_SECTION_NUMBER)==2)
            {
                rootView = inflater.inflate(R.layout.fragment_pro_nites_2, container, false);
                ((TextView)rootView.findViewById(R.id.textView4)).setTypeface(AllIDS.font_AnweshaSub);
                i=(rand.nextInt(100))%2;
                if(i==0) {
                    ((ImageView)rootView.findViewById(R.id.img2)).setBackgroundResource(R.drawable.anwesha2);
                }
                else {
                    ((ImageView)rootView.findViewById(R.id.img2)).setBackgroundResource(R.drawable.anwesha3);
                }


            }else{
                rootView = inflater.inflate(R.layout.fragment_pro_nites_3, container, false);
                ((TextView)rootView.findViewById(R.id.textView3)).setTypeface(AllIDS.font_AnweshaSub);
                i=(rand.nextInt(100))%2;
                if(i==0) {
                    ((ImageView)rootView.findViewById(R.id.img3)).setBackgroundResource(R.drawable.anwesha4);
                }
                else {
                    ((ImageView)rootView.findViewById(R.id.img3)).setBackgroundResource(R.drawable.anwesha1);
                }

            }
            return rootView;
        }
    }
}