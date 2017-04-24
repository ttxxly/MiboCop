package top.ttxxly.blog.mibocop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import top.ttxxly.blog.mibocop.R;

/**
 * 设置向导一
 */
public class Setup1Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    @Override
    public void ShowNextPage() {
        //跳转到设置向导二页面
        startActivity(new Intent(getApplicationContext(), Setup2Activity.class));
        finish();

        //两个activity之间的切换动画
        overridePendingTransition(R.anim.anim_next_in, R.anim.anim_next_out);
    }

    @Override
    public void ShowPreviousPage() {

    }

    /**
     *
     * 设置界面下一步的按钮点击事件
     * @param view
     */
    public void Setup1Next(View view) {
        ShowNextPage();
    }

}
