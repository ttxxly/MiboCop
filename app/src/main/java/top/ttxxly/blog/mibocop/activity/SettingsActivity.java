package top.ttxxly.blog.mibocop.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import top.ttxxly.blog.mibocop.R;
import top.ttxxly.blog.mibocop.view.SettingItemView;


/**
 *
 * 设置页面
 */
public class SettingsActivity extends AppCompatActivity {

    private SettingItemView sivUpdate;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sivUpdate = (SettingItemView) findViewById(R.id.siv_update);

        sp = getSharedPreferences("config", MODE_PRIVATE);

        //设置标题和描述
        sivUpdate.setTitle("自动更新设置");
        boolean autoUpdate = sp.getBoolean("auto_update", true);
        if(autoUpdate) {
            sivUpdate.setDes("自动更新已开启");
            sivUpdate.setChecked(true);
        }else {
            sivUpdate.setChecked(false);
            sivUpdate.setDes("自动更新已关闭");
        }

        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断勾选框是否选中
                if (sivUpdate.isChecked()) {
                    sivUpdate.setChecked(false);
                    sivUpdate.setDes("自动更新已关闭");
                    sp.edit().putBoolean("auto_update", false).commit();
                }else {
                    sivUpdate.setChecked(true);
                    sivUpdate.setDes("自动更新已开启");
                    sp.edit().putBoolean("auto_update", true).commit();
                }
            }
        });
    }
}
