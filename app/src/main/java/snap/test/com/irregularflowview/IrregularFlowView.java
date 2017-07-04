package snap.test.com.irregularflowview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Description:不规则信息流
 * Author:    Oscar
 * Version    V1.0
 * Date:      2017/6/22
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2017/6/22         Oscar           1.0                    1.0
 * Why & What is modified:
 */
public class IrregularFlowView extends LinearLayout {
    private Context context;
    private int mMaxWidth; //最大宽度
    private int txtColor; //文字颜色
    private int txtSize; //文字大小
    private int bgLayoutDrawable = 0; //背景
    private int txtPaddingLeft = 8;
    private int txtPaddingRight = 8;
    private int txtPaddingTop = 2;
    private int txtPaddingBottom = 2;
    private int marginLeft = 0;
    private int marginRight = 10;
    private int marginTop = 10;
    private int marginBottom = 10;

    public IrregularFlowView(Context context) {
        this(context, null);
    }

    public IrregularFlowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IrregularFlowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        context = getContext();
        setLayoutParams(new LayoutParams(-1, -2));
        setGravity(Gravity.START);
        setOrientation(LinearLayout.VERTICAL);
        mMaxWidth = 1080; //屏幕宽度减去边距
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IrregularFlow);
        txtColor = typedArray.getResourceId(R.styleable.IrregularFlow_txtColor, Color.BLACK);
//        txtSize = typedArray.get
        bgLayoutDrawable = typedArray.getResourceId(R.styleable.IrregularFlow_bgLayoutDrawable, 0);

        typedArray.recycle();
    }

    public void setFlowViewData(List<String> listData){
        if (listData == null || listData.size() == 0){
            setVisibility(GONE);
            return;
        }
        int size = listData.size();
        View[] views = new View[size];
        LinearLayout.LayoutParams tvLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < size; i++) {
            String tag = listData.get(i);
            TextView tv = new TextView(context); //实例化tv
            tv.setText(tag); //设置文字
            tv.setBackgroundResource(R.color.colorAccent); //设置背景R.drawable.shape_user_tag_bg
            tv.setTextColor(Color.WHITE);  //设置文字颜色 ContextCompat.getColor(context, R.color.white)
            tv.setLayoutParams(tvLayoutParams);
            tv.setPadding(txtPaddingLeft, txtPaddingTop, txtPaddingRight, txtPaddingBottom); //设置边距
            tv.setTextSize(16);
            tv.setGravity(Gravity.CENTER);
            views[i] = tv;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        onDrawLayoutTxt(views);

    }

    public void onDrawLayoutTxt(View[] views) {
        removeAllViews();
        LinearLayout newLL = new LinearLayout(context);
        newLL.setLayoutParams(new LayoutParams(-1, -2));
        newLL.setGravity(Gravity.START);
        newLL.setOrientation(HORIZONTAL);
        int widthSoFar = 0;

        for (View view : views) {
            LinearLayout LL = new LinearLayout(context);
            LL.setOrientation(HORIZONTAL);
            LL.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER);
            LL.setLayoutParams(new AbsListView.LayoutParams(-2, -2));
            view.measure(0, 0);
            LayoutParams params = new LayoutParams(view.getMeasuredWidth(), -2);
            params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
            LL.addView(view, params);
            LL.measure(0, 0);
            widthSoFar += view.getMeasuredWidth();
            if (widthSoFar >= mMaxWidth) {
                addView(newLL);
                newLL = new LinearLayout(context);
                newLL.setLayoutParams(new LayoutParams(-1, -2));
                newLL.setOrientation(HORIZONTAL);
                newLL.setGravity(Gravity.START);
                params = new LayoutParams(LL.getMeasuredWidth(), LL.getMeasuredHeight());
                newLL.addView(LL, params);
                widthSoFar = LL.getMeasuredWidth();
            } else {
                newLL.addView(LL);
            }
        }
        addView(newLL);
    }
}
