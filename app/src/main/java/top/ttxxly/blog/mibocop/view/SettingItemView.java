package top.ttxxly.blog.mibocop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import top.ttxxly.blog.mibocop.R;

/**
 * 自定义的组合控件
 * Created by ttxxly on 2017/4/4.
 */

public class SettingItemView extends RelativeLayout {

    private TextView tvTitle;
    private TextView tvDes;
    private CheckBox cbUpdate;

    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //初始化布局
    private void initView() {
        //初始化控件布局
        View child = View.inflate(getContext(), R.layout.setting_item_view, null);

        tvTitle = (TextView) child.findViewById(R.id.tv_title);
        tvDes = (TextView) child.findViewById(R.id.tv_des);
        cbUpdate = (CheckBox) child.findViewById(R.id.cb_update);


        //将布局添加到当前的Relativelayout对象
        this.addView(child);

    }

    /**
     *
     * 设置标题
     * @param title
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 设置描述
     * @param des
     */
    public void setDes(String des) {
        tvDes.setText(des);
    }

    /**
     *
     * 判断CheckBox是否勾选
     */
    public boolean isChecked() {
        return cbUpdate.isChecked();
    }

    /**
     *
     * 设置勾选框的状态
     */
    public void setChecked(boolean checked) {
        cbUpdate.setChecked(checked);
    }
}
