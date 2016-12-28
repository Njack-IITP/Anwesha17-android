package in.ac.iitp.anwesha;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gagan on 4/1/16.
 */
public class MyHttpClient extends AsyncTask<Void, Void, Object> {

    Context context;
    private String url;
    private boolean isPost;

    private ArrayList<Pair<String, String>> param;
    private MyHttpClientListener listener;
    private static String cookie;

    MyHttpClient(String url, ArrayList param, boolean isPost, MyHttpClientListener listener) {
        this.url = url;
        this.isPost = isPost;
        this.listener = listener;
        this.param = param;
        execute();  //Start Everything
    }

    public static void setCookie(String cookie) {
        MyHttpClient.cookie = cookie;
    }

    public static String getCookie() {
        return cookie;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null)
            listener.onPreExecute();
    }

    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private Exception exception;

    private int rc;
    private String rmsg;

    @Override
    protected String doInBackground(Void... voids) {
        HttpURLConnection httpURLConnection;
        String result = null;
        try {
            URL _url = new URL(url);
            httpURLConnection = (HttpURLConnection) _url.openConnection();
            if (isPost)
                httpURLConnection.setRequestMethod("POST");
            if (cookie != null)
                httpURLConnection.setRequestProperty("Cookie", cookie);
            if (param != null) {
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder();
                for (Pair<String, String> s : param) {
                    builder.appendQueryParameter(s.first, s.second);
                }
                String query = builder.build().getEncodedQuery();
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter br = new BufferedWriter(new OutputStreamWriter(os));
                br.write(query);
                br.flush();
                br.close();
            }
            httpURLConnection.setConnectTimeout(1000);

            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder b = new StringBuilder();
                InputStream is = httpURLConnection.getInputStream();
                int s;
                while ((s = is.read()) >= 0)
                    b.append((char) s);
                result = b.toString();
                String c = httpURLConnection.getHeaderField("set-cookie");
                rmsg = httpURLConnection.getResponseMessage();
                rc = httpURLConnection.getResponseCode();
                if (c != null)
                    cookie = c;
                listener.onBackgroundSuccess(result);

            }

        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }
        return result;

    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
//        Toast.makeText(context,"RC :"+rc+" MSG : "+rmsg,Toast.LENGTH_SHORT).show();
        if (listener != null) {
            if (result != null) {
                listener.onSuccess(result);
            } else
                listener.onFailed(exception);

        }

    }


}

interface MyHttpClientListener {
    public void onPreExecute();

    public void onFailed(Exception e);

    public void onSuccess(Object output);//Mostly String,, may be something else in special case

    public void onBackgroundSuccess(String result);

}