package top.ttxxly.blog.mibocop.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 对 SharedPreference 的封装
 * Created by ttxxly on 2017/4/4.
 */

public class SharedPreferenceUtils {

    //保存
    public static void putBoolean(String key, boolean value, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    //获取
    public static boolean getBoolean(String key, boolean defvalue, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);

        return sp.getBoolean(key, defvalue);
    }

    //保存
    public static void putString(String key, String value, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    //获取
    public static String getString(String key, String defvalue, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);

        return sp.getString(key, defvalue);
    }

    //保存
    public static void putInt(String key, int value, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    //获取
    public static int getInt(String key, int defvalue, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);

        return sp.getInt(key, defvalue);
    }

    //删除 key
    public static void remove(String key, Context ctx){
        SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);

        sp.edit().remove(key).commit();
    }
}
