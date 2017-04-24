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
 *
 * 1. 新建一个类继承relativelayout（VIewGroup）
 * 2. 写一个布局文件
 * 3. 将布局添加到relativelayout（initview方法）
 * 4. 添加自定义api
 * 5. 自定义属性（1.新建values/attrs.xml文件。 2. 声明命令空间 3. 在自定义控件中配置属性 4. 在对应控件中加载属性值）
 * Created by ttxxly on 2017/4/4.
 */

public class SettingItemView extends RelativeLayout {

    private TextView tvTitle;
    private TextView tvDes;
    private CheckBox cbUpdate;
    private String namespace;
    private String descOn;
    private String descOff;

    //new view 对象的时候，调用此构造方法
    public SettingItemView(Context context) {
        super(context);
        initView();
    }
    //在 xml 文件中定义有属性集的时候，调用此构造方法
    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

//
//        int count = attrs.getAttributeCount();//获取属性集的数量，
//        // 也就是在“top.ttxxly.blog.mibocop.view.SettingItemView”中有多少个属性
//
//        for (int i=0; i<count; i++) {
//            String attributeName = attrs.getAttributeName(i);   //获取属性的名字
//            String attributeValue = attrs.getAttributeValue(i); //获取属性的值
//            System.out.println(attributeName + " : " + attributeValue);
//        }

        namespace = "http://schemas.android.com/apk/res-auto";
        String title = attrs.getAttributeValue(namespace, "title");
        descOn = attrs.getAttributeValue(namespace, "desc_on");
        descOff = attrs.getAttributeValue(namespace, "desc_off");

        setTitle(title);
    }
    //在 xml 文件中定义属性集和样式styl的时候，调用此构造方法
    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //初始化布局，将组合控件添加relativelayout中
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
     * 设置标题，才能将结果显示在页面上
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
     * true : 表示勾选
     */
    public boolean isChecked() {

        return cbUpdate.isChecked();
    }

    /**
     *
     * 设置勾选框的状态，将描述显示在页面上
     */
    public void setChecked(boolean checked) {

        cbUpdate.setChecked(checked);

        //设置对应的描述
        if (checked) {
            setDes(descOn);
        } else {
            setDes(descOff);
        }
    }
}
