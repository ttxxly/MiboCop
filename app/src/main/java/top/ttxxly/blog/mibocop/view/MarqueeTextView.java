package top.ttxxly.blog.mibocop.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by ttxxly on 2017/4/3.
 */

public class MarqueeTextView extends android.support.v7.widget.AppCompatTextView {

    //new view 对象的时候，调用此构造方法
    public MarqueeTextView(Context context) {
        super(context);
        System.out.println("从内存中new 一个对象时，调用此构造方法");
    }

    //在 xml 文件中定义有属性集的时候，调用此构造方法
    public MarqueeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        System.out.println("只有属性时调用此构造方法");
    }

    //在 xml 文件中定义属性集和样式styl的时候，调用此构造方法
    public MarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        System.out.println("有属性和styl时调用此构造方法");
    }

    //强制让textview具有焦点
    @Override
    public boolean isFocused() {
        return true;
    }
}
