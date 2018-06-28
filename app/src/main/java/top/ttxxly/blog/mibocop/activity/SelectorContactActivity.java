package top.ttxxly.blog.mibocop.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import top.ttxxly.blog.mibocop.R;

public class SelectorContactActivity extends AppCompatActivity {

    private final int REQUEST_READ_CONTACTS = 2;//请求读取联系人的请求码
    private ArrayList<HashMap<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_contact);

        ListView lvContact = (ListView) findViewById(R.id.lv_contact);

        list = getContact();    //读取联系人，我们最好写在子线程里，这个是个耗时操作
        if (list == null) {
            Toast.makeText(this, "没有获取到哦哦 1333333 ！！！！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "没有获取到哦哦 1333333 ！！！！", Toast.LENGTH_SHORT).show();
//            lvContact.setAdapter(new SimpleAdapter(this, list, R.layout.list_item_contact,
//                    new String[]{"name", "phone"},
//                    new int[]{R.id.tv_name, R.id.tv_phone}));
        }

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> hashMap = list.get(position);
                String phone = hashMap.get("phone");

                Intent intent = new Intent();
                intent.putExtra("phone", phone);
                //requestcode, intent data
                setResult(0, intent);   //回传给上个页面

                finish();
            }
        });
    }

    /**
     * 读取联系人数据
     * 需要权限：<uses-permission android:name="android.permission.READ_CONTACTS"/>
     */
    private ArrayList<HashMap<String, String>> getContact() {
        //Android 6.0 之后危险权限需要动态配置权限
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS);//判断是否获得一个特定的权限

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //表示你没有这个权限
                ActivityCompat.requestPermissions(SelectorContactActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
            } else {
                return getHashMaps();
            }
        } else {
            //表示版本小于 Android 6.0 可以不用动态获取权限
            return getHashMaps();
        }
        return null;
    }

    private ArrayList<HashMap<String, String>> getHashMaps() {

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //向下移动光标
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();
            //取得联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String contact = cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);


            StringBuilder sb = new StringBuilder();
            while (phone.moveToNext()) { //可能一个联系人有多个电话
                String PhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //格式化手机号
                PhoneNumber = PhoneNumber.replace("-", "");
                PhoneNumber = PhoneNumber.replace(" ", "");

                sb.append(PhoneNumber + " && ");
            }

            map.put("name", contact);
            map.put("phone", sb.toString());

            phone.close();

            if (!TextUtils.isEmpty("name") && !TextUtils.isEmpty("phone")) {
                list.add(map);
            }
        }

        cursor.close();
        return list;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //根据结果码和grantResult做相应的操作
        switch (requestCode) {
            case REQUEST_READ_CONTACTS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //表示允许获取权限
                    list = getHashMaps();//特别注意这里，如果是弹出对话框，选择允许的话，一定要记得保存值。

                } else {
                    Toast.makeText(this, "READ_CONTACTS Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

}
