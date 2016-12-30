package in.ac.iitp.anwesha;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Pronites extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    static ImageView i1;
    static ImageView i2;
    static ImageView i3;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_nites);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

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

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
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

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

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
            View rootView;
            Random rand = new Random();
            int i;
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                rootView = inflater.inflate(R.layout.fragment_pro_nites, container, false);
                ((TextView) rootView.findViewById(R.id.textView2)).setTypeface(AllIDS.font_AnweshaSub);
                i = (rand.nextInt(100)) % 2;
                if (i == 0) {
                    ((ImageView) rootView.findViewById(R.id.img1)).setBackgroundResource(R.drawable.anwesha);
                } else {
                    ((ImageView) rootView.findViewById(R.id.img1)).setBackgroundResource(R.drawable.anwesha5);
                }

            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                rootView = inflater.inflate(R.layout.fragment_pro_nites_2, container, false);
                ((TextView) rootView.findViewById(R.id.textView4)).setTypeface(AllIDS.font_AnweshaSub);
                i = (rand.nextInt(100)) % 2;
                if (i == 0) {
                    ((ImageView) rootView.findViewById(R.id.img2)).setBackgroundResource(R.drawable.anwesha2);
                } else {
                    ((ImageView) rootView.findViewById(R.id.img2)).setBackgroundResource(R.drawable.anwesha3);
                }


            } else {
                rootView = inflater.inflate(R.layout.fragment_pro_nites_3, container, false);
                ((TextView) rootView.findViewById(R.id.textView3)).setTypeface(AllIDS.font_AnweshaSub);
                i = (rand.nextInt(100)) % 2;
                if (i == 0) {
                    ((ImageView) rootView.findViewById(R.id.img3)).setBackgroundResource(R.drawable.anwesha4);
                } else {
                    ((ImageView) rootView.findViewById(R.id.img3)).setBackgroundResource(R.drawable.anwesha1);
                }

            }
            return rootView;
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent in = new Intent(this,Home.class);
            startActivity(in);
        }
    }
}