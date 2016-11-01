package mouse.com.cloudnote.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import mouse.com.cloudnote.adapters.MyAdapter;
import mouse.com.cloudnote.R;

public class notesFragment extends Fragment {
    private static final String[] titles = {"item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9", "item10"};
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.frag_notes, container, false);
        initListView(parentView);
        return parentView;
    }

    private void initListView(View parentView) {
        listView = (ListView) parentView.findViewById(R.id.id_lv_notes);
        MyAdapter adapter = new MyAdapter(titles, getActivity());
        listView.setAdapter(adapter);
        listView.requestDisallowInterceptTouchEvent(true);
    }
}
