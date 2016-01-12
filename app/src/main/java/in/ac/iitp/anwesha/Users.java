package in.ac.iitp.anwesha;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Users extends AppCompatActivity {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationDrawer(this));


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private static void showProgress(final boolean show, final View mLoginFormView, final View mProgressView, Resources resources) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private static String isCityValid(String name) {
        if (name.length() == 0)
            return "City can't be Empty";
        if (name.length() > 25)
            return "Too long for City";
        return null;
    }
    private static String isCollegeValid(String name) {
        if (name.length() == 0)
            return "College can't be Empty";
        if (name.length() > 25)
            return "Too long for College";
        return null;
    }
    private static String isContactValid(String name) {
        if (name.length() == 0)
            return "Contact can't be Empty";
        if (name.length() > 12)
            return "Too long for Contact";
        return null;
    }
    private static String isNameValid(String name) {
        if (name.length() == 0)
            return "Name can't be Empty";
        if (name.length() > 12)
            return "Too long for Name";
        return null;
    }
    private static String isAnweshaIDvalid(String id) {
        if(id.length()!=7)
            return "Invalid ID (ANWxxxx)";
        if (!id.substring(0,3).equalsIgnoreCase("ANW"))
            return "Invalid ID (ANWxxxx)";
        return null;
    }

    private static String isDOBValid(String dob) {
        String original = dob;
        dob = dob.trim();
        dob = dob.replace("-", "");
        if (dob.length() != 8)
            return "Format : YYYY-MM-DD ";
        for (char x : dob.toCharArray()) {
            if (!Character.isDigit(x))
                return "Format : YYYY-MM-DD";
        }


        return null;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Users Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://in.ac.iitp.anwesha/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Users Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://in.ac.iitp.anwesha/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class LoginFragment extends Fragment {

        private AutoCompleteTextView mEmailView;
        private EditText mPasswordView;
        private View mProgressView;
        private View mLoginFormView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_login, container, false);
            mEmailView = (AutoCompleteTextView) rootView.findViewById(R.id.email);

            mPasswordView = (EditText) rootView.findViewById(R.id.password);
            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        attemptLogin();
                        return true;
                    }
                    return false;
                }
            });

            Button mEmailSignInButton = (Button) rootView.findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin();
                }
            });

            mLoginFormView = rootView.findViewById(R.id.login_form);
            mProgressView = rootView.findViewById(R.id.login_progress);
            return rootView;
        }


        /**
         * Attempts to sign in or register the account specified by the login form.
         * If there are form errors (invalid email, missing fields, etc.), the
         * errors are presented and no actual login attempt is made.
         */
        private void attemptLogin() {

            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);

            // Store values at the time of the login attempt.
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (!isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            String result = null;
            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            } else if (( result = isAnweshaIDvalid(email))!=null) {
                mEmailView.setError(result);
                focusView = mEmailView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                showProgress(true, mLoginFormView, mProgressView, getResources());
                tryLogin(email, password);
            }
        }


        /**
         * Shows the progress UI and hides the login form.
         */


        void tryLogin(final String email, String password) {
            ArrayList<Pair<String, String>> param = new ArrayList<>();
            final String _email = email.toUpperCase();
            param.add(new Pair<String, String>("username", _email));
            param.add(new Pair<String, String>("password", password));

            MyHttpClient client = new MyHttpClient(BackgroundFetch.BASE_URL + "/login", param, true, new MyHttpClientListener() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void onFailed(Exception e) {
                    showProgress(false, mLoginFormView, mProgressView, getResources());
                }

                @Override
                public void onSuccess(Object output) {
                    String result = (String) output;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Registration")
                            .setPositiveButton("Ok",null)
                            .setMessage("Some Error Occured");


                    try {
                        JSONObject object = new JSONObject(result);
                        boolean status = object.getBoolean("status");
                        String msg = object.getString("msg");
                        dialog.setMessage(msg);
                        if(status)
                        {
                            String name = object.getString("name");
                            String key = object.getString("key");
                            AllIDS.USER_name = name;
                            AllIDS.USER_key = key;
                            AllIDS.USER_anweshaID = _email;
                            AllIDS.saveSharedPref(getContext());
                            getActivity().finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.create().show();


                    showProgress(false, mLoginFormView, mProgressView, getResources());
                }

                @Override
                public void onBackgroundSuccess(String result) {

                }
            });
        }


    }

    public static class RegistationFragment extends Fragment {

        private AutoCompleteTextView mEmailView;
        private EditText mPasswordView, mPasswordConfirm, et_name, et_dob, et_contact, et_city, et_college;
        private View mProgressView;
        private View mLoginFormView;

        private RadioButton rb_sex_male, rb_sex_female;

        private char user_sex = 'M';

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.registration, container, false);
            mEmailView = (AutoCompleteTextView) rootView.findViewById(R.id.email);

            mPasswordView = (EditText) rootView.findViewById(R.id.password);
            mPasswordConfirm = (EditText) rootView.findViewById(R.id.password_confirm);
            et_name = (EditText) rootView.findViewById(R.id.et_name);
            et_dob = (EditText) rootView.findViewById(R.id.et_dob);
            et_college = (EditText) rootView.findViewById(R.id.et_college);
            et_contact = (EditText) rootView.findViewById(R.id.et_mobile);
            et_city = (EditText) rootView.findViewById(R.id.et_city);


            Button mEmailSignInButton = (Button) rootView.findViewById(R.id.b_register);
            mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptRegister();
                }
            });

            rb_sex_female = (RadioButton) rootView.findViewById(R.id.rb_sex_female);
            rb_sex_male = (RadioButton) rootView.findViewById(R.id.rb_sex_male);
            rb_sex_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        rb_sex_male.setChecked(false);
                        user_sex = 'F';
                    }
                }
            });
            rb_sex_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        rb_sex_female.setChecked(false);
                        user_sex = 'M';
                    }
                }
            });


            mLoginFormView = rootView.findViewById(R.id.login_form);
            mProgressView = rootView.findViewById(R.id.login_progress);
            return rootView;
        }


        /**
         * Attempts to sign in or register the account specified by the login form.
         * If there are form errors (invalid email, missing fields, etc.), the
         * errors are presented and no actual login attempt is made.
         */
        private void attemptRegister() {

            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);
            mPasswordConfirm.setError(null);
            et_name.setError(null);
            et_dob.setError(null);
            et_college.setError(null);
            et_contact.setError(null);
            et_city.setError(null);

            // Store values at the time of the login attempt.
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();
            String passwordConfirm = mPasswordConfirm.getText().toString();
            String name = et_name.getText().toString();
            String dob = et_dob.getText().toString();
            String city = et_city.getText().toString();
            String college = et_college.getText().toString();
            String contact = et_contact.getText().toString();


            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (!isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
            }
            if (!password.equals(passwordConfirm)) {
                mPasswordConfirm.setError("Password Didn't Match");
                focusView = mPasswordConfirm;
            }


            String customErrorMessage = null;
            // Check for a valid email address.
            if(focusView!=null);
                else
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
            } else if (!isEmailValid(email)) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
            } else if ((customErrorMessage = isNameValid(name)) != null) {
                et_name.setError(customErrorMessage);
                focusView = et_name;
            } else if ((customErrorMessage = isDOBValid(dob)) != null) {
                et_dob.setError(customErrorMessage);
                focusView = et_dob;
            }else if ((customErrorMessage = isCityValid(city)) != null) {
                et_city.setError(customErrorMessage);
                focusView = et_city;
            }else if ((customErrorMessage = isContactValid(contact)) != null) {
                et_contact.setError(customErrorMessage);
                focusView = et_contact;
            }else if ((customErrorMessage = isCollegeValid(college)) != null) {
                et_college.setError(customErrorMessage);
                focusView = et_college;
            }

            if (focusView != null) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                showProgress(true, mLoginFormView, mProgressView, getResources());
                tryRegister(email, password,name,contact,college,dob,city);
            }
        }


        /**
         * Shows the progress UI and hides the login form.
         */


        void tryRegister(String email, String password, String name, String mobile, String college, String dob, String city) {
            ArrayList<Pair<String, String>> param = new ArrayList<>();
            param.add(new Pair<String, String>("name", name));
            param.add(new Pair<String, String>("mobile", mobile));
            param.add(new Pair<String, String>("sex", String.valueOf(user_sex)));
            param.add(new Pair<String, String>("college", college));
            param.add(new Pair<String, String>("email", email));
            param.add(new Pair<String, String>("dob", dob));
            param.add(new Pair<String, String>("city", city));

            MyHttpClient client = new MyHttpClient(BackgroundFetch.BASE_URL + "/user/register/User", param, true, new MyHttpClientListener() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void onFailed(Exception e) {
                    Toast.makeText(RegistationFragment.this.getActivity(),e.toString(),Toast.LENGTH_LONG).show();

                    showProgress(false, mLoginFormView, mProgressView, getResources());
                }

                @Override
                public void onSuccess(Object output) {
                    String result = (String) output;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Registration")
                            .setMessage("Some Error Occured!")
                            .setPositiveButton("Ok",null);
                    try {
                        JSONArray array = new JSONArray(result);
                        int status = array.getInt(0);
                        if(status==1)
                        {
                            JSONObject obj = array.getJSONObject(1);
                            et_city.setText(null);
                            et_name.setText(null);
                            et_college.setText(null);
                            et_contact.setText(null);
                            et_dob.setText(null);
                            mPasswordConfirm.setText(null);
                            mPasswordView.setText(null);
                            dialog.setMessage("Your Anwesha ID : ANW"+obj.getInt("pId"));


                        }
                        else
                        {
                            dialog.setMessage(array.getString(1));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.create().show();
                    showProgress(false, mLoginFormView, mProgressView, getResources());
                }

                @Override
                public void onBackgroundSuccess(String result) {

                }
            });
        }


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
            if (position == 0)
                return new LoginFragment();
            else return new RegistationFragment();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Login";
                case 1:
                    return "Registration";

            }
            return null;
        }
    }
}
