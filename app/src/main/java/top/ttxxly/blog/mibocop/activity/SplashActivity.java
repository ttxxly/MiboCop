package top.ttxxly.blog.mibocop.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import top.ttxxly.blog.mibocop.R;
import top.ttxxly.blog.mibocop.Utils.StreamUtils;

/**
 * 闪屏页面
 * 展示logo， 公司品牌
 *
 * @author ttxxly
 *         检查版本更新
 *         项目初始化
 *         校验合法性（检查是否有网络，检查是否登录）
 */
public class SplashActivity extends AppCompatActivity {

    private static final int CODE_UPDATE_DIALOG = 1;
    private static final int CODE_ENTER_HOME = 2;
    private static final int CODE_URL_ERROR = 3;
    private static final int CODE_NETWORK_ERROR = 4;
    private static final int CODE_JSON_ERROR = 5;

    private TextView tv_version;
    //服务器返回的最新版本信息
    private String mVersionName;    //为了区分局部变量和成员变量我们在前面加一个小写的m,表示成员
    private int mVersonCode;
    private String mVersionDescrible;
    private String mUrl;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)   {
                case CODE_UPDATE_DIALOG:
                    showUpdateDialog();
                    break;
                case CODE_ENTER_HOME:
                    enterHome();
                    break;
                case CODE_URL_ERROR:
                    Toast.makeText(getApplicationContext(), "网络链接错误", Toast.LENGTH_SHORT).show();
                    break;
                case CODE_NETWORK_ERROR:
                    Toast.makeText(getApplicationContext(), "小主，没有网哦", Toast.LENGTH_SHORT).show();
                    break;
                case CODE_JSON_ERROR:
                    Toast.makeText(getApplicationContext(), "数据解析错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText("版本名：" + getVersionName());

        checkVersion();
    }

    /**
     * 检查版本
     *
     * @author ttxxly
     */

    private void checkVersion() {
        new Thread() {

            private long startTime;

            @Override
            public void run() {
                Message msg = Message.obtain();
                long startTime=0, endTime=0;

                try {
                    startTime = System.currentTimeMillis();
                    //10.0.2.2是预留IP, 供模拟器访问PC的本地服务器如Tomcat
                    HttpURLConnection conn = (HttpURLConnection) new URL(
                            "http://10.0.2.2:8080/update.json").openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(2000);
                    conn.setConnectTimeout(2000);

                    //conn.connect();


                    if (conn.getResponseCode() == 200) {
                        InputStream in = conn.getInputStream();
                        String s = StreamUtils.Stream2String(in);

//                        System.out.println(s);
                        JSONObject jo = new JSONObject(s);
                        mVersionName = jo.getString("versionName");
                        mVersonCode = jo.getInt("versionCode");
                        mVersionDescrible = jo.getString("versionDescrible");
                        mUrl = jo.getString("url");

                        if (getVersonCode() < mVersonCode) {
//                            System.out.println("版本有更新");
//                            showUpdateDialog();
                            msg.what = CODE_UPDATE_DIALOG;
                        } else {
//                            System.out.println("版本没有更新");
                            msg.what = CODE_ENTER_HOME;
                        }
                    } else {
                        msg.what = CODE_NETWORK_ERROR;
                    }
                } catch (IOException e) {
                    //网络异常
                    e.printStackTrace();
                    msg.what = CODE_NETWORK_ERROR;
                } catch (JSONException e) {
                    //json数据解析失败
                    e.printStackTrace();
                    msg.what = CODE_JSON_ERROR;
                } finally {
                    endTime = System.currentTimeMillis();

                    //真正访问网络使用的时间
                    long usedTime = endTime - startTime ;
                    try {
                        if(usedTime < 2000) {
                            Thread.sleep(2000 - usedTime);     //休眠再发消息
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 升级弹窗
     */

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发现新版本: " + mVersionName);
        builder.setMessage(mVersionDescrible);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        builder.show();
    }

    /**
     * 获取版本名称
     */
    private String getVersionName() {
        PackageManager pm = getPackageManager();    //获取包管理器

        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
//            int versionCode = packageInfo.versionCode;  //版本号
//            String versionName = packageInfo.versionName;   //版本名称

//            System.out.println("versionCode:" + versionCode);
//            System.out.println("versionName:" + versionName);

            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //包名未找到异常
            e.printStackTrace();
        }

        //版本名称没有找到的情况，我们就返回1.0
        return "1.0";
    }

    /**
     * 获取版本号
     */

    private int getVersonCode() {
        PackageManager pm = getPackageManager();    //获取包管理器

        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
//            int versionCode = packageInfo.versionCode;  //版本号
//            String versionName = packageInfo.versionName;   //版本名称

//            System.out.println("versionCode:" + versionCode);
//            System.out.println("versionName:" + versionName);

            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            //包名未找到异常
            e.printStackTrace();
        }

        //版本号没有找到的情况，我们就返回-1
        return -1;
    }

    /**
     *
     * 检查版本更新后跳转到主页面
     */
    private void enterHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
