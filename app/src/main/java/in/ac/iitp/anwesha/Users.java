package in.ac.iitp.anwesha;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class Users extends AppCompatActivity {

    private GoogleApiClient client;

    private static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return !password.isEmpty();
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
        if (name.length() > 25)
            return "Too long for Name";
        return null;
    }

    private static String isAnweshaIDvalid(String id) {
        if (id.length() != 7)
            return "Invalid ID (ANWxxxx)";
        if (!id.substring(0, 3).equalsIgnoreCase("ANW"))
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        if (getPreferences().getInt("loginflag", 0) != 0) {
            intent = new Intent(this, WelcomeScreen.class);
            Log.v("chirag", "chirag");
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_users);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new LoginFragment(), "login").commit();

        }
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private SharedPreferences getPreferences() {
        SharedPreferences sharedPref = getApplication().getSharedPreferences("login", MODE_PRIVATE);
        return sharedPref;
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

        private TextInputLayout mEmailView;
        private TextInputLayout mPasswordView;
        private View mProgressView;
        private View mLoginFormView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_login, container, false);
            mEmailView = (TextInputLayout) rootView.findViewById(R.id.email);

            mPasswordView = (TextInputLayout) rootView.findViewById(R.id.password);

            TextView signup = (TextView) rootView.findViewById(R.id.sign_up);
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, new RegistationFragment(), "signup").commit();

                }
            });
            Button mEmailSignInButton = (Button) rootView.findViewById(R.id.log_in);
            mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin();
                }
            });

            TextView skip = (TextView) rootView.findViewById(R.id.skip);
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Home.class);
                    intent.putExtra("loginflag",2);
                    intent.putExtra("id","Anwesha 2017");
                    intent.putExtra("name","Think.Dream.Live");
                    getActivity().startActivity(intent);
                }
            });
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
            String email = mEmailView.getEditText().getText().toString();
            String password = mPasswordView.getEditText().getText().toString();

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
            } else if ((result = isAnweshaIDvalid(email)) != null) {
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
                tryLogin(email, password);
            }
        }


        /**
         * Shows the progress UI and hides the login form.
         */


        void tryLogin(final String email, final String password) {
            ArrayList<Pair<String, String>> param = new ArrayList<>();
            final String _email = email.toUpperCase();
            param.add(new Pair<String, String>("username", _email));
            param.add(new Pair<String, String>("password", password));

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Logging in...");
            progressDialog.show();

            MyHttpClient client = new MyHttpClient(BackgroundFetch.BASE_URL + "/login", param, true, new MyHttpClientListener() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void onFailed(Exception e) {
                    progressDialog.dismiss();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Login")
                            .setPositiveButton("Ok", null)
                            .setMessage("Lost Connection!");
                    dialog.create().show();
                }

                @Override
                public void onSuccess(Object output) {
                    progressDialog.dismiss();
                    String result = (String) output;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Login")
                            .setPositiveButton("Ok", null)
                            .setMessage("Some Error Occured");

                    try {
                        JSONObject object = new JSONObject(result);
                        boolean status = object.getBoolean("status");
                        String msg = object.getString("msg");
                        dialog.setMessage(msg);
                        if (status) {
                            String name = object.getString("name");
                            String key = object.getString("key");
                            AllIDS.USER_name = name;
                            AllIDS.USER_key = key;
                            AllIDS.USER_anweshaID = _email;
                            AllIDS.USER_anweshapass = password;
                            Intent intent;
                            intent = new Intent(getActivity(), Home.class);
                            intent.putExtra("loginflag",1);
                            intent.putExtra("id",_email);
                            intent.putExtra("name",name);
                            startActivity(intent);
                            getActivity().finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.create().show();

                }

                @Override
                public void onBackgroundSuccess(String result) {

                }
            });

        }


    }

    public static class RegistationFragment extends Fragment {

        private AutoCompleteTextView mEmailView;
        private EditText et_name, et_dob, et_contact, et_city, et_college;
        private View mProgressView;
        private View mLoginFormView;
        private DatePicker datePicker;
        private Calendar calendar;

        private RadioButton rb_sex_male, rb_sex_female;

        private char user_sex = 'M';

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.registration, container, false);
            mEmailView = (AutoCompleteTextView) rootView.findViewById(R.id.email);

            et_name = (EditText) rootView.findViewById(R.id.et_name);
            et_dob = (EditText) rootView.findViewById(R.id.et_dob);
            et_college = (EditText) rootView.findViewById(R.id.et_college);
            et_contact = (EditText) rootView.findViewById(R.id.et_mobile);
            et_city = (EditText) rootView.findViewById(R.id.et_city);


            final ImageView datepicker = (ImageView) rootView.findViewById(R.id.datepicker);
            datepicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectdate();
                }
            });
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

            return rootView;
        }

        public void selectdate() {

            Calendar newCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    monthOfYear++;
                    if (monthOfYear == 11 || monthOfYear == 12) {
                        if (dayOfMonth < 10)
                            et_dob.setText(year + "-" + monthOfYear + "-" + "0" + dayOfMonth);
                        else
                            et_dob.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    } else {
                        if (dayOfMonth < 10)
                            et_dob.setText(year + "-" + "0" + monthOfYear + "-" + "0" + dayOfMonth);
                        else
                            et_dob.setText(year + "-" + "0" + monthOfYear + "-" + dayOfMonth);
                    }
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();

        }

        /**
         * Attempts to sign in or register the account specified by the login form.
         * If there are form errors (invalid email, missing fields, etc.), the
         * errors are presented and no actual login attempt is made.
         */
        private void attemptRegister() {

            // Reset errors.
            mEmailView.setError(null);
            et_name.setError(null);
            et_dob.setError(null);
            et_college.setError(null);
            et_contact.setError(null);
            et_city.setError(null);

            // Store values at the time of the login attempt.
            String email = mEmailView.getText().toString();
            String name = et_name.getText().toString();
            String dob = et_dob.getText().toString();
            String city = et_city.getText().toString();
            String college = et_college.getText().toString();
            String contact = et_contact.getText().toString();


            View focusView = null;

            String customErrorMessage = null;
            // Check for a valid email address.
            if (focusView != null) ;
            else if (TextUtils.isEmpty(email)) {
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
            } else if ((customErrorMessage = isCityValid(city)) != null) {
                et_city.setError(customErrorMessage);
                focusView = et_city;
            } else if ((customErrorMessage = isContactValid(contact)) != null) {
                et_contact.setError(customErrorMessage);
                focusView = et_contact;
            } else if ((customErrorMessage = isCollegeValid(college)) != null) {
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
                tryRegister(email, name, contact, college, dob, city);
            }
        }


        /**
         * Shows the progress UI and hides the login form.
         */


        void tryRegister(String email, String name, String mobile, String college, String dob, String city) {
            ArrayList<Pair<String, String>> param = new ArrayList<>();
            param.add(new Pair<String, String>("name", name));
            param.add(new Pair<String, String>("mobile", mobile));
            param.add(new Pair<String, String>("sex", String.valueOf(user_sex)));
            param.add(new Pair<String, String>("college", college));
            param.add(new Pair<String, String>("email", email));
            param.add(new Pair<String, String>("dob", dob));
            param.add(new Pair<String, String>("city", city));

            final Intent intent;
            intent = new Intent(getActivity(), Home.class);
            intent.putExtra("name",name);

            AllIDS.USER_name = name;

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Registering...");
            progressDialog.show();

            MyHttpClient client = new MyHttpClient(BackgroundFetch.BASE_URL + "/user/register/User", param, true, new MyHttpClientListener() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void onFailed(Exception e) {
                    progressDialog.dismiss();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                            .setTitle("Registration")
                            .setPositiveButton("Ok", null)
                            .setMessage("Lost Connection!");
                    dialog.create().show();

                }

                @Override
                public void onSuccess(Object output) {
                    progressDialog.dismiss();
                    String result = (String) output;

                    try {
                        JSONArray array = new JSONArray(result);
                        int status = array.getInt(0);
                        if (status == 1) {
                            JSONObject obj = array.getJSONObject(1);
                            et_city.setText(null);
                            et_name.setText(null);
                            et_college.setText(null);
                            et_contact.setText(null);
                            et_dob.setText(null);
                            final int anwid = obj.getInt("pId");

                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                                    .setTitle("Registration Successful")
                                    .setMessage("Your Anwesha ID : ANW" + anwid + "\n" + "You will receive a confirmation e-mail soon")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AllIDS.USER_anweshaID = "ANW" + anwid;

                                            intent.putExtra("loginflag",1);
                                            intent.putExtra("id","ANW"+anwid);

                                            startActivity(intent);
                                            getActivity().finish();
                                        }
                                    });
                            dialog.create().show();

                        } else {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                                    .setTitle("Registration Unsuccessful")
                                    .setMessage(array.getString(1))
                                    .setPositiveButton("Ok", null);
                            dialog.create().show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onBackgroundSuccess(String result) {

                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = fragmentManager.findFragmentByTag("login");
        if (fragment != null && fragment.isVisible()) {
            this.finishAffinity();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new LoginFragment(), "login").commit();
        }
    }

}
