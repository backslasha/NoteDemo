package mouse.com.cloudnote.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import mouse.com.cloudnote.R;
import mouse.com.cloudnote.adapters.MyAdapter;
import mouse.com.cloudnote.widgets.DraggableLayout;

public class MainActivity extends AppCompatActivity {
    String TAG = "haibiao";
    //private ViewPager viewPager;

    private MyAdapter adapter;
    private ListView listView;
    private static final String[] titles = {"item11111111111111", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9", "item10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.id_lv_notes);
        adapter = new MyAdapter(titles, this);


        listView.setAdapter(adapter);
    }



//    private void initViewPager() {
//        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View decoView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decoView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            getSupportActionBar().hide();
//        }
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new notesFragment());
//
//        viewPager.setAdapter(new FragPagerAdapter(getSupportFragmentManager(), fragments));
//    }
}
