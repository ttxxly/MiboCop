package top.ttxxly.blog.mibocop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import top.ttxxly.blog.mibocop.R;
import top.ttxxly.blog.mibocop.Utils.SharedPreferenceUtils;

/**
 * Created by ttxxly on 2017/4/13.
 * 手机防盗经过设置向导后最终的页面
 */

public class LostAndFoundActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否是第一次进入
        boolean configed = SharedPreferenceUtils.getBoolean("configed", false, this);

        if(!configed) {
            //进入设置向导页面
            startActivity(new Intent(getApplicationContext(), Setup1Activity.class));
        }else{
            setContentView(R.layout.activity_lost_and_found);

            TextView tv_safe_phone = (TextView) findViewById(R.id.tv_safe_phone);
            ImageView iv_lock = (ImageView) findViewById(R.id.iv_lock);

            String phone = SharedPreferenceUtils.getString("phone", "", this);
            //设置电话号码
            tv_safe_phone.setText(phone);

            boolean protect = SharedPreferenceUtils.getBoolean("protect", false, this);
            if (protect) {
                //更新锁的状态
                iv_lock.setImageResource(R.drawable.lock);
            }else {
                iv_lock.setImageResource(R.drawable.unlock);
            }
        }
    }

    /**
     * 重新进入设置向导
     * @param view
     */
    public void reSetup(View view) {
        startActivity(new Intent(getApplicationContext(), Setup1Activity.class));
        finish();
    }
}
