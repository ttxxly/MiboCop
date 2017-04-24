package top.ttxxly.blog.mibocop.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import top.ttxxly.blog.mibocop.R;
import top.ttxxly.blog.mibocop.Utils.MD5Utils;
import top.ttxxly.blog.mibocop.Utils.SharedPreferenceUtils;
import top.ttxxly.blog.mibocop.view.SettingItemView;

/**
 * 设置向导二页面
 */
public class Setup2Activity extends BaseSetupActivity {

    private final int REQUEST_READ_PHONE_STATE = 1;
    private SettingItemView siv_bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        siv_bind = (SettingItemView) findViewById(R.id.siv_bind);

        /**
         * 还没有点击之前就已经显示的页面
         */
        String bind_sim = SharedPreferenceUtils.getString("bind_sim", null, this);
        if (TextUtils.isEmpty(bind_sim)) {
            siv_bind.setChecked(false);
        } else {
            siv_bind.setChecked(true);
        }

        /**
         * 设置点击事件
         */
        siv_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (siv_bind.isChecked()) {
                    siv_bind.setChecked(false);
                    SharedPreferenceUtils.remove("bind_sim", getApplicationContext());
                } else {
                    siv_bind.setChecked(true);

                    //绑定 SIM 卡， 其实就是获取并保存 SIM 卡的序列号， 这个序列号对于 SIM 卡来说是唯一的
                    int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);

                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Setup2Activity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);

                    } else {
                        //TODO
                        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
                        String deviceId = tm.getSimSerialNumber();
                        if (TextUtils.isEmpty(deviceId)) {
                            Toast.makeText(getApplicationContext(), "获取不到哦23333", Toast.LENGTH_SHORT).show();
                        } else {
                            String Number = MD5Utils.encode(deviceId);
                            if (!TextUtils.isEmpty(Number)) {
                                SharedPreferenceUtils.putString("bind_sim", Number, getApplicationContext());
                                Toast.makeText(getApplicationContext(), Number, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "获取不到哦111", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                    Toast.makeText(getApplicationContext(), "获取不到1哦", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    public void ShowNextPage() {
        //跳转到设置向导三页面
        startActivity(new Intent(getApplicationContext(), Setup3Activity.class));
        finish();

        //两个activity之间的切换动画
        overridePendingTransition(R.anim.anim_next_in, R.anim.anim_next_out);
    }

    /**
     * 跳转到上一页面
     */
    public void ShowPreviousPage() {
        //跳转到设置向导一页面
        startActivity(new Intent(getApplicationContext(), Setup1Activity.class));
        finish();

        //两个activity之间的切换动画
        overridePendingTransition(R.anim.anim_last_in, R.anim.anim_last_out);
    }

    /**
     * 按钮点击下一步
     */
    public void Setup2Next(View view) {
        ShowNextPage();
    }

    /**
     * 按钮点击上一步
     */
    public void Setup2Last(View view) {
        ShowPreviousPage();
    }
}
