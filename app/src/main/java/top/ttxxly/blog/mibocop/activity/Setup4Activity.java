package top.ttxxly.blog.mibocop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import top.ttxxly.blog.mibocop.R;
import top.ttxxly.blog.mibocop.Utils.SharedPreferenceUtils;

/**
 * 设置向导4
 */
public class Setup4Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        final CheckBox cb_protect = (CheckBox) findViewById(R.id.cb_protect);

        boolean protect = SharedPreferenceUtils.getBoolean("protect", false, this);
        if (protect) {
            cb_protect.setText("防盗保护已开启");
            cb_protect.setChecked(true);
        }else {
            cb_protect.setText("您没有开启防盗保护");
            cb_protect.setChecked(false);
        }
        cb_protect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //判断勾选框的情况
                if (isChecked) {
                    cb_protect.setText("防盗保护已开启");

                    SharedPreferenceUtils.putBoolean("protect", true, getApplicationContext());
                }else {
                    cb_protect.setText("您没有开启防盗保护");

                    SharedPreferenceUtils.putBoolean("protect", false, getApplicationContext());
                }
            }
        });
    }

    @Override
    public void ShowNextPage() {
        SharedPreferenceUtils.putBoolean("configed", true, this);   //通过configed来判断是否已经设置过向导了。
        startActivity(new Intent(getApplicationContext(), LostAndFoundActivity.class));
        finish();

        //两个activity之间的切换动画
        overridePendingTransition(R.anim.anim_next_in, R.anim.anim_next_out);
    }

    @Override
    public void ShowPreviousPage() {
        startActivity(new Intent(getApplicationContext(), Setup3Activity.class));
        finish();

        //两个activity之间的切换动画
        overridePendingTransition(R.anim.anim_last_in, R.anim.anim_last_out);
    }

    public void Setup4Next(View view) {
        ShowNextPage();
    }


    public void Setup4Last(View view) {
        ShowPreviousPage();
    }
}
