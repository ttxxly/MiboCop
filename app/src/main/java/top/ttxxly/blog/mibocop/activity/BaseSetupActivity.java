package top.ttxxly.blog.mibocop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 设置向导的基础类
 */
public abstract class BaseSetupActivity extends AppCompatActivity {

    private GestureDetector mDetector;//手势识别器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //手势识别器
        //快速滑动 动作
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            /**
             *
             * 快速滑动 动作
             * @param e1 起点坐标
             * @param e2 终点坐标
             * @param velocityX 水平滑动速度
             * @param velocityY 竖直滑动速度
             * @return
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                //判断 滑动的方向
//                e1.getX();  //相对父控件的 x 坐标
//                e1.getRawX();    //屏幕的绝对坐标

                if (e2.getRawX() > e1.getRawX()) {
                    //向右滑动，下一页
                    //跳转到设置向导三页面
//                    startActivity(new Intent(getApplicationContext(), Setup3Activity.class));
//                    finish();
//
//                    //两个activity之间的切换动画
//                    overridePendingTransition(R.anim.anim_next_in, R.anim.anim_next_out);
                    ShowNextPage();
                    return true;
                }

                if (e2.getRawX() < e1.getRawX()) {
                    //向左滑动，上一页
                    //跳转到设置向导一页面
//                    startActivity(new Intent(getApplicationContext(), Setup1Activity.class));
//                    finish();
//
//                    //两个activity之间的切换动画
//                    overridePendingTransition(R.anim.anim_last_in, R.anim.anim_last_out);
                    ShowPreviousPage();
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    /**
     * 当前页面触摸时走此方法
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mDetector.onTouchEvent(event);  //将事件委托给手势识别器处理
        return super.onTouchEvent(event);
    }

    /**
     *
     * 跳转到下一页，需在子类中重写
     */
    public abstract void ShowNextPage();

    /**
     * 跳转到上一页面，需在子类中重写
     */
    public abstract void ShowPreviousPage();

}
