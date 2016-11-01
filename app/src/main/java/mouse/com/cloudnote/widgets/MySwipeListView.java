package mouse.com.cloudnote.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import mouse.com.test.MyAdapter;

public class MySwipeListView extends ListView {

    public MySwipeListView(Context context) {
        this(context, null);
    }

    public MySwipeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySwipeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //如果有拉开的抽屉：1.点击的区域是拉开的抽屉，则不拦截点击事件，
        // 2.点击的区域不是拉开的抽屉，则关闭拉开的抽屉，并拦截消化此点击事件
        if (((MyAdapter) getAdapter()).hasOpenMenu()) {
            if (ev.getY() >= ((MyAdapter) getAdapter()).getTheOpenningMenu().getTop() &&
                    ev.getY() <= ((MyAdapter) getAdapter()).getTheOpenningMenu().getBottom())
                return false;
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (((MyAdapter) getAdapter()).hasOpenMenu()) {
                ((MyAdapter) getAdapter()).getTheOpenningMenu().close();
                return true;
            }
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (((MyAdapter) getAdapter()).hasOpenMenu()) {
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }
}
