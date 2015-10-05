package in.ac.iitp.anwesha;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Gallery extends AppCompatActivity implements AdapterView.OnItemClickListener {


    GridView gv;
    ArrayList<File> list;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        file = Environment.getExternalStorageDirectory();
        Toast.makeText(getApplicationContext(), file.getAbsolutePath().toString().replace("emulated/0", "removable/sdcard1/Anwesha/Gallery"), Toast.LENGTH_LONG).show();
        list = imageReader(new File(file.getAbsolutePath().toString().replace("emulated/0", "removable/sdcard1/Anwesha/Gallery")));
        gv = (GridView) findViewById(R.id.gridView);
        gv.setAdapter(new GridAdapter());
        gv.setOnItemClickListener(this);
    }

    public ArrayList<File> imageReader(File root) {
        ArrayList<File> a = new ArrayList<File>();

        File[] files = root.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().endsWith(".jpg")
                    || files[i].getName().endsWith(".jpeg")
                    || files[i].getName().endsWith(".png")) {
                a.add(files[i]);
            }
        }
        return a;
    }

    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub
            arg1 = getLayoutInflater().inflate(R.layout.activity_gallery_item, null);
            ImageView iv = (ImageView) arg1.findViewById(R.id.thumbImage);
            if(getItem(arg0)!=null){
                iv.setImageURI(Uri.parse(getItem(arg0).toString()));
            }
            return arg1;
        }


    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

}
