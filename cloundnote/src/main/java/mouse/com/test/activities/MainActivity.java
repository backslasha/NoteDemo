package mouse.com.test.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mouse.com.test.R;
import mouse.com.test.adapters.MyAdapter;
import mouse.com.test.widgets.MySwipeListView;

public class MainActivity extends AppCompatActivity {
    private static final String[] titles = {"item11111111111111", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9", "item10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MySwipeListView listView = (MySwipeListView) findViewById(R.id.id_lv_notes);
        MyAdapter adapter = new MyAdapter(titles, this);
        listView.setAdapter(adapter);
    }

}
