package top.ttxxly.blog.mibocop.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import top.ttxxly.blog.mibocop.Utils.SharedPreferenceUtils;

/**
 * 开启 重启 广播接收者， 接受系统广播，重启判断 SIM卡是否变更
 * 需要权限： <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
 * 需要注册：
 *          <receiver android:name=".receiver.BootCompleteReceiver">
 *              <intent-filter>
 *                  <action android:name="android.intent.action.BOOT_COMPLETED" />
 *              </intent-filter>
 *           </receiver>
 *
 * @author ttxxly
 * @date 2017年11月1日10:55:44
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction().toString();
        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            // u can start your service here
            Toast.makeText(context, "boot completed action has got", Toast.LENGTH_LONG).show();
            return;
        }

        System.out.println("开机启动11111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        boolean protect = SharedPreferenceUtils.getBoolean("protect", false, context);
        if (!protect) {
            return ;
        }
        //判断 SIM 卡是否变更
        String savedSim = SharedPreferenceUtils.getString("bind_sim", null, context);

        if (!TextUtils.isEmpty(savedSim)) {
            //获取当前 SIM 卡 进行比对

            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getSimSerialNumber();

            if (savedSim.equals(deviceId)) {
                Log.i("intent", "手机安全放心使用");
            }else {
                Log.i("intent", "手机处于危险中，需要发送报警短信");
            }
            /**
             * 如果SIM 卡变更的话就要发送报警短信
             * 1.获取安全手机号码
             * 2.使用 SMSmanager 发送短信
             */
            String phone = SharedPreferenceUtils.getString("phone", "", context);

            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(phone, null, "Sim Card changed!!!", null, null);//发送报警短信
        }
    }
}
