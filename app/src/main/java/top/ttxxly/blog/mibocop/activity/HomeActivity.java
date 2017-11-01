package top.ttxxly.blog.mibocop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import top.ttxxly.blog.mibocop.R;
import top.ttxxly.blog.mibocop.Utils.MD5Utils;
import top.ttxxly.blog.mibocop.Utils.SharedPreferenceUtils;

/**
 * 主页面
 *
 * @author ttxxly
 */
public class HomeActivity extends AppCompatActivity {

    private GridView gvHome;

    private String[] mHomeNames = new String[]{"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计",
            "手机杀毒", "缓存清理", "高级工具", "设置中心"};

    private int[] mImageIds = new int[]{R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable.home_apps
            , R.drawable.home_taskmanager, R.drawable.home_netmanager, R.drawable.home_trojan
            , R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable.home_settings,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gvHome = (GridView) findViewById(R.id.gv_home);
        gvHome.setAdapter(new HomeAdapter());

        gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //手机防盗
                        ShowSafeDialog();
                        break;
                    case 8:
                        //设置中心
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 手机防盗弹窗
     */
    private void ShowSafeDialog() {
        String pwd = SharedPreferenceUtils.getString("passwd", null, this);
        if (!TextUtils.isEmpty(pwd)) {
            //输入密码弹窗
            showInputPwdDialog(pwd);
        } else {
            //设置密码弹窗
            ShowSetPwdDialog();
        }

    }

    /**
     * 输入密码弹窗
     */
    private void showInputPwdDialog(String pwd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_inputpwd, null);//给dialog设置特定布局
        //dialog.setView(view);
        dialog.setView(view, 0, 0, 0, 0);   //兼容Android 2.x 版本， 去掉上下左右边距

        //这里一定要记住 不是 findViewById  是   view.findViewById()
        Button btnOK = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        final EditText etPwd = (EditText) view.findViewById(R.id.et_pwd);


        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pwd = etPwd.getText().toString().trim();//trim去掉末尾的空格

                //判断密码是否正确
                if (!TextUtils.isEmpty(Pwd)) {
                    //如果两个相等
                    String save_pwd = SharedPreferenceUtils.getString("passwd", null, getApplicationContext());
                    if (MD5Utils.encode(Pwd).equals(save_pwd)) {
                        //密码正确
                        dialog.dismiss();   //会话框消失

                        //跳转到手机防盗设置界面
                        startActivity(new Intent(getApplicationContext(), LostAndFoundActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 设置密码弹窗
     */
    private void ShowSetPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_setpwd, null);//给dialog设置特定布局
        //dialog.setView(view);
        dialog.setView(view, 0, 0, 0, 0);   //兼容Android 2.x 版本， 去掉上下左右边距

        //这里一定要记住 不是 findViewById  是   view.findViewById()
        Button btnOK = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        final EditText etPwd = (EditText) view.findViewById(R.id.et_pwd);
        final EditText etConPwd = (EditText) view.findViewById(R.id.et_con_pwd);


        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pwd = etPwd.getText().toString().trim();//trim去掉末尾的空格
                String ConPwd = etConPwd.getText().toString().trim();//trim去掉末尾的空格

                //判断密码的合法性
                if (!TextUtils.isEmpty(Pwd) && !TextUtils.isEmpty(ConPwd)) {
                    //如果两个相等
                    if (Pwd.equals(ConPwd)) {
                        //保存密码
                        SharedPreferenceUtils.putString("passwd", MD5Utils.encode(Pwd), getApplicationContext());

                        dialog.dismiss();   //会话框消失

                        //跳转到手机防盗设置页面
                        startActivity(new Intent(getApplicationContext(), LostAndFoundActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mHomeNames.length;   //条目的数量
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.list_item_home, null);   //获取视图对象

            //获取相应的布局
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            //根据位置来设置对应的名称
            tvName.setText(mHomeNames[position]);
            //根据位置来设置相应的图标
            ivIcon.setImageResource(mImageIds[position]);

            return view;
        }
    }
}
