package mouse.com.cloudnote.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class DraggableLayout extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View mBackView; //item的侧边布局
    private View mFrontView;//当前显示的item布局
    private int mWidth; //屏幕的宽度,mFrontView的宽度
    private int mHeight; //mFrontView的高度
    private int mRange;//mFrontView侧拉时向左移动的最大距离,即mBackView的宽度
    private boolean isSliding = false;

    private Status mStatus = Status.CLOSE;

    private OnDraggingStatusListener mDraggingStatusListner;

    public void setOnFrontViewClickListener(OnFrontViewClickListener mOnFrontViewClickListener) {
        this.mOnFrontViewClickListener = mOnFrontViewClickListener;
    }

    private OnFrontViewClickListener mOnFrontViewClickListener;

    public interface OnFrontViewClickListener {
        void onFrontViewClick(DraggableLayout draggableLayout);
    }

    public interface OnDraggingStatusListener {
        //正在拖动的状态
        void onDragging(DraggableLayout draggableLayout);

        //关闭的状态
        void onClose(DraggableLayout draggableLayout);

        //打开着的状态
        void onOpen(DraggableLayout draggableLayout);

        //要关闭的状态
        void onStartClose(DraggableLayout draggableLayout);

        //要拉开的状态
        void onStartOpen(DraggableLayout draggableLayout);
    }

    public enum Status {
        CLOSE, OPEN, DRAGGING;
    }

    public void setOnDraggingStatusListner(OnDraggingStatusListener DraggingStatusListner) {
        this.mDraggingStatusListner = DraggingStatusListner;
    }

    public DraggableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (child == mFrontView) {
                    if (left > 0) {
                        left = 0;
                    } else if (left < -mRange) {
                        left = -mRange;
                    }
                }

                if (child == mBackView) {
                    if (left > mWidth) {
                        left = mWidth;
                    } else if (left < mWidth - mRange) {
                        left = mWidth - mRange;
                    }
                }
                return left;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                if (changedView == mFrontView) {
                    mBackView.offsetLeftAndRight(dx);
                } else if (changedView == mBackView) {
                    mFrontView.offsetLeftAndRight(dx);
                }
                requestDisallowInterceptTouchEvent(true);
                dispatchSwipeEvent();
                invalidate();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (xvel < 0) {//如果有向左的速度，打开抽屉
                    open();
                    //如果没有速度，侧拉距离大于一半则打开抽屉
                } else if (xvel == 0 && (mWidth - mBackView.getLeft() > 0.5f * mRange && mStatus != Status.OPEN)) {
                    open();
                    //如果没有速度，设置了前景View监听，当前关闭状态，捕获的是前景View，那么执行前景View的监听事件
                } else if (xvel == 0 && mOnFrontViewClickListener != null && mStatus == Status.CLOSE && releasedChild == mFrontView) {
                    mOnFrontViewClickListener.onFrontViewClick(DraggableLayout.this);
                } else {
                    close();
                }
            }
        });

    }

    public DraggableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void dispatchSwipeEvent() {
        if (mDraggingStatusListner != null) {
            mDraggingStatusListner.onDragging(this);
            isSliding = true;
        }

        if (mDraggingStatusListner != null && getCurStatus() == Status.CLOSE) {
            mDraggingStatusListner.onClose(this);
            isSliding = false;
        } else if (mDraggingStatusListner != null && getCurStatus() == Status.OPEN) {
            mDraggingStatusListner.onOpen(this);
            isSliding = false;
        }

        Status preStatus = mStatus;
        mStatus = getCurStatus();
        if (mDraggingStatusListner != null && getCurStatus() == Status.DRAGGING) {
            if (preStatus == Status.CLOSE) {
                mDraggingStatusListner.onStartOpen(this);
            } else {
                mDraggingStatusListner.onStartClose(this);
            }
        }
    }

    public DraggableLayout(Context context) {
        this(context, null);
    }

    //获取子控件的引用
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFrontView = getChildAt(0);
        mBackView = getChildAt(1);

    }

    //在这里获取子控件的相关宽高信息
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mFrontView.getMeasuredWidth();
        mHeight = mFrontView.getMeasuredHeight();
        mRange = mBackView.getMeasuredWidth();
    }

    //把frameLayout中两个重叠的布局放好位置
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutChild(false);//默认抽屉关闭状态布局
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        //只要坐标数值还在发生改变，“computeScroll→invalidate→ondraw→computeScroll”循环不结束
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    //打开抽屉方向的startScroll()
    public void open() {
        if (mViewDragHelper.smoothSlideViewTo(mFrontView, -mRange, 0)) {
            ViewCompat.postInvalidateOnAnimation(this); //进入computeScroll循环
        }
    }

    //关闭抽屉方向的startScroll()
    public void close() {
        //smoothSlideViewTo使坐标数值变化，调用postInvalidateOnAnimation才是使View移动，之后再computeScroll根据不断刷新的坐标数值刷新View的位置产生“滚动”
        if (mViewDragHelper.smoothSlideViewTo(mFrontView, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this); //进入computeScroll循环
        }
    }

    //判断是否正滑动
    public boolean isSliding() {
        return isSliding;
    }

    //根据抽屉是否打开放置两个子View的位置
    private void layoutChild(boolean isOpen) {
        Rect frontRect = computeFrontViewRect(isOpen);
        mFrontView.layout(frontRect.left, frontRect.top, frontRect.right, frontRect.bottom);

        int left = frontRect.right;
        mBackView.layout(left, 0, left + mRange, mHeight);

    }

    //根据抽屉是否打开返回FrontView的Rect信息
    private Rect computeFrontViewRect(boolean isOpen) {
        int left = isOpen ? -mRange : 0;
        return new Rect(left, 0, left + mWidth, mHeight);
    }

    //获取抽屉当前的状态
    private Status getCurStatus() {
        if (mBackView.getLeft() == mWidth) {
            return Status.CLOSE;
        } else if (mBackView.getLeft() == mWidth - mRange) {
            return Status.OPEN;
        } else {
            return Status.DRAGGING;
        }
    }
}
