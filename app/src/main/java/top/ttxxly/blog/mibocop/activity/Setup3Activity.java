package top.ttxxly.blog.mibocop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import top.ttxxly.blog.mibocop.R;
import top.ttxxly.blog.mibocop.Utils.SharedPreferenceUtils;

public class Setup3Activity extends BaseSetupActivity {

    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        et_phone = (EditText) findViewById(R.id.et_phone);
        String phone = SharedPreferenceUtils.getString("phone", "", this);
        et_phone.setText(phone);

    }

    @Override
    public void ShowNextPage() {
        //在进入下一个页面的时候我们需要保存当前选择的安全号码
        String phone = et_phone.getText().toString().trim();//获取号码并去掉前后空格
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "安全号码不能为空", Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferenceUtils.putString("phone", phone, getApplicationContext());

            startActivity(new Intent(getApplicationContext(), Setup4Activity.class));
            finish();

            //两个activity之间的切换动画
            overridePendingTransition(R.anim.anim_next_in, R.anim.anim_next_out);
        }
    }

    @Override
    public void ShowPreviousPage() {
        startActivity(new Intent(getApplicationContext(), Setup2Activity.class));
        finish();

        //两个activity之间的切换动画
        overridePendingTransition(R.anim.anim_last_in, R.anim.anim_last_out);
    }

    public void Setup3Next(View view) {
        ShowNextPage();
    }

    public void Setup3Last(View view) {
        ShowPreviousPage();
    }

    /**
     * 选择联系人的点击事件
     * @param view
     */
    public void select_phone(View view) {
        //跳转到 选择联系人 页面
        startActivityForResult(new Intent(getApplicationContext(), SelectorContactActivity.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //如果在页面中不做任何操作，直接返回，也会调用方法。这个时候 data 为空, 导致空指针异常
        if (data != null)   {
            String phone = data.getStringExtra("phone");

            //对传过来的数据做处理
            phone = phone.replaceAll("&&", "").replaceAll(" ", "");
            et_phone.setText(phone);
        }
    }
}
