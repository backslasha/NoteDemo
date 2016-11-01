package mouse.com.test.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import mouse.com.test.R;
import mouse.com.test.widgets.DraggableLayout;

public class MyAdapter extends BaseAdapter {
    private String TAG = "haibiao";
    private LayoutInflater mInflater;
    private String[] titles;
    private Context mContext;
    private DraggableLayout mOpenningLayout;

    public MyAdapter(String[] titles, Context mContext) {
        this.titles = titles;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final DraggableLayout layout = (DraggableLayout) mInflater.inflate(R.layout.item, null);

        TextView textView = (TextView) layout.findViewById(R.id.id_tv_content);
        LinearLayout menuLayout = (LinearLayout) layout.findViewById(R.id.id_ll_menu);
        Button menuButton = (Button) menuLayout.findViewById(R.id.id_btn_in_menu3);

        textView.setText(titles[position]);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "" + titles[position], Toast.LENGTH_SHORT).show();
            }
        });

        layout.setOnFrontViewClickListener(new DraggableLayout.OnFrontViewClickListener() {
            @Override
            public void onFrontViewClick(DraggableLayout draggableLayout) {
                Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        layout.setOnDraggingStatusListner(new DraggableLayout.OnDraggingStatusListener() {
            @Override
            public void onDragging(DraggableLayout draggableLayout) {
                Log.d(TAG, "onDragging: ");
                mOpenningLayout = layout;
            }

            @Override
            public void onClose(DraggableLayout draggableLayout) {
                Log.d(TAG, "onClose: ");
                mOpenningLayout = null;
            }

            @Override
            public void onOpen(DraggableLayout draggableLayout) {
                Log.d(TAG, "onOpen: ");
            }

            @Override
            public void onStartClose(DraggableLayout draggableLayout) {
                Log.d(TAG, "onStartClose: ");
            }

            @Override
            public void onStartOpen(DraggableLayout draggableLayout) {
                Log.d(TAG, "onStartOpen: ");
            }

        });
        return layout;
    }

    public boolean hasOpenMenu() {
        return mOpenningLayout != null;
    }

    public DraggableLayout getTheOpenningMenu() {
        return mOpenningLayout;
    }
}
