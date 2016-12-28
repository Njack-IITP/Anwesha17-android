package in.ac.iitp.anwesha;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Team extends AppCompatActivity {

   /* ExpandableListView elv;
    ArrayList<String> list_TeamHead;
    HashMap<String, List<String>> map_listPerson;
    Adapter adapter;
*/
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


       /* elv = (ExpandableListView) findViewById(R.id.elv_team);
        map_listPerson = new HashMap<>();
        list_TeamHead = new ArrayList<>();
        addAll();
        adapter = new Adapter();
        elv.setAdapter(adapter);
*/
    }

  /*  void addAll() {
        String temp;
        map_listPerson.put(temp = "Coordinator", Arrays.asList(new String[]
                {"Mayank Garg", "08292340330"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp = "Sponsorship", Arrays.asList(new String[]
                {"Ritik Mathur", "07597065284", "Amolika Sinha", "09472472533", "Mayank Garg", "08292340330"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp = "Cultural", Arrays.asList(new String[]
                {"Ballabh Inder Kishore", "08292310299", "Rakshit Bansal", "07277634406"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp = "Creatives and Design", Arrays.asList(new String[]
                {"Kuntal Das", "09504424461", "Rahul Arya", "08292347413", "Shubham Verma", "07762882652", "Sumit Asthana", "081277224282"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp = "Media and PR", Arrays.asList(new String[]
                {"Chirag Jain", "08292344734", "Ravneet Kaur", "08292347037", "Manu Sharma", "08292340331"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp = "Registration Desk", Arrays.asList(new String[]
                {"Pukhraj Jain", "09650364301", "Arindam Banerjee", "09472472543", "Aditya Gupta", "08292339046"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp = "Security and Planning Committee", Arrays.asList(new String[]
                {"Aman Singh", "08292347037", "Sheikh Sameeruddin", "09472472454", "Srinath Keerty", "08292337686"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp = "Eco Committee", Arrays.asList(new String[]
                {"Tanuj Sharma", "09631073195", "Alok Kumar", "09709348974"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp = "Technical Committee", Arrays.asList(new String[]
                {"Ashwin Goyal", "08292340508", "Kumari Sonam", "09631073195", "Subham Shubham", "09013443130"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp = "Hospitality Committee", Arrays.asList(new String[]
                {"Abhijeet Agnihotri", "08292346573", "Dhruv Upadhyay", "08292348408", "Mohammed Shiyas P C", "09631073195"}
        ));
        list_TeamHead.add(temp);
        map_listPerson.put(temp = "Literary Committee", Arrays.asList(new String[]
                {"Aditya Jhalani", "08292346960", "Kumar Gaurav", "09534089367"}
        ));
        list_TeamHead.add(temp);


    }

    class Adapter extends BaseExpandableListAdapter implements View.OnClickListener {


        @Override
        public int getGroupCount() {
            return list_TeamHead.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return map_listPerson.get(list_TeamHead.get(i)).size() / 2;
        }

        @Override
        public Object getGroup(int i) {
            return list_TeamHead.get(i);
        }

        @Override
        public Pair<String, String> getChild(int i, int i1) {
            return new Pair<String, String>(map_listPerson.get(list_TeamHead.get(i)).get(2 * i1), map_listPerson.get(list_TeamHead.get(i)).get(2 * i1 + 1));

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
            View v = View.inflate(Team.this, R.layout.lay_team_group, null);
            ((TextView) v.findViewById(R.id.tv_team_group)).setText((String) getGroup(i));
            return v;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            View v = View.inflate(Team.this, R.layout.lay_team_person, null);
            Pair<String, String> child = getChild(i, i1);
            ((TextView) v.findViewById(R.id.tv_team_person)).setText(child.first);
            View call = v.findViewById(R.id.call);
            call.setTag(child.second);
            call.setOnClickListener(this);
            return v;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + view.getTag()));
            if (ActivityCompat.checkSelfPermission(Team.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Team.this, "No Permission Given", Toast.LENGTH_SHORT).show();
                return;
            }
            Team.this.startActivity(intent);
        }
    }*/
}
