package in.ac.iitp.anwesha;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Team extends AppCompatActivity {

    ExpandableListView elv;
    ArrayList<String>  list_TeamHead;
    HashMap<String,List<String>> map_listPerson;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationDrawer(this));


        elv = (ExpandableListView) findViewById(R.id.elv_team);
        map_listPerson = new HashMap<>();
        list_TeamHead = new ArrayList<>();
        addAll();
        adapter = new Adapter();
        elv.setAdapter(adapter);

    }
    void addAll()
    {
        String temp;
        map_listPerson.put(temp="Coordinator", Arrays.asList(new String[]
                        {"Mayank Garg"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp="Sponsorship", Arrays.asList(new String[]
                        {"Ritik Mathur","Amolika Sinha","Mayank Garg"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp="Cultural", Arrays.asList(new String[]
                        {"Ballabh Inder Kishore","Rakshit Bansal"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp="Creatives and Design", Arrays.asList(new String[]
                        {"Kuntal Das","Rahul Arya","Shubham Verma","Sumit Asthana"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp="Media and PR", Arrays.asList(new String[]
                        {"Chirag Jain","Ravneet Kaur","Manu Sharma"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp="Registration Desk", Arrays.asList(new String[]
                        {"Pukhraj Jain","Arindam Banerjee","Aditya Gupta"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp="Security and Planning Committee", Arrays.asList(new String[]
                        {"Aman Singh","Sheikh Sameeruddin","Sreenath Keerty"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp="Eco Committee", Arrays.asList(new String[]
                        {"Tanuj Sharma","Alok Kumar"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp="Technical Committee", Arrays.asList(new String[]
                        {"Ashwin Goyal","Kumari Sonam","Subham Shubham"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp="Hospitality Committee", Arrays.asList(new String[]
                        {"Abhijeet Agnihotri","Dhruv Upadhyay","Mohammed Shiyas P C"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp="Literary Committee", Arrays.asList(new String[]
                        {"Aditya Jhalani","Gaurav Garg"}
        ));
        list_TeamHead.add(temp);


    }
    class Adapter extends BaseExpandableListAdapter {


        @Override
        public int getGroupCount() {
            return list_TeamHead.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return map_listPerson.get(list_TeamHead.get(i)).size();
        }

        @Override
        public Object getGroup(int i) {
            return list_TeamHead.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return map_listPerson.get(list_TeamHead.get(i)).get(i1);

        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            View v = View.inflate(Team.this,R.layout.lay_team_group,null);
            ((TextView)v.findViewById(R.id.tv_team_group)).setText((String)getGroup(i));
            return v;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            View v = View.inflate(Team.this,R.layout.lay_team_person,null);
            ((TextView)v.findViewById(R.id.tv_team_person)).setText((String)getChild(i, i1));
            return v;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }
}
