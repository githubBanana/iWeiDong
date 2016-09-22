package com.zf.weisport.manager.widget.pointer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zf.weisport.R;

/**
 * Created by Administrator on 2016/3/7.
 */
public class DrawDangradView extends View implements Runnable {

    private RectF       mDangradBg;
    private Paint       mPaint,mBatteryPaint;
    private int         father_width,father_height;
    private float       progress = 0;
    private int         victor,victor2,rememberVictor;
    private boolean     init_flag = false;
    private boolean     control_threadDie;

    public DrawDangradView(Context context) {
        super(context);
        init();
    }

    public DrawDangradView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化画笔
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.lightGray));
        mPaint.setStyle(Paint.Style.FILL);//充满
        mPaint.setAntiAlias(true);// 设置画笔的锯齿效果

        mBatteryPaint = new Paint();
        mBatteryPaint.setColor(getResources().getColor(R.color.colorRed));
        mBatteryPaint.setStyle(Paint.Style.FILL);//充满
        mBatteryPaint.setAntiAlias(true);// 设置画笔的锯齿效果
        new Thread(this).start();
    }

    /**
     * 设置段位值
     * @param victor
     */
    public void setVictorValue(int victor) {
        int init = 3500;
        victor2 = rememberVictor;
        if (victor < 5000)
            this.victor = 0;
        else if (victor < 7000)
            this.victor = 5000 - init;
        else if (victor < 9000)
            this.victor = 7000 - init;
        else if (victor < 12000)
            this.victor = 9000 - init + 400;
        else if (victor < 14000)
            this.victor = 12000 - init - 500;
        else
            this.victor = 14000 - init - 500;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawSub(canvas);
    }

    /**
     * 绘制模块
     * @param canvas
     */
    private void drawSub(Canvas canvas) {
        father_width = getWidth();
        father_height = getHeight();
        if (!init_flag) {
            progress = 0.98f*father_height;
            init_flag = true;
        }
//        float x = father_height*0.85f;          father_height*0.05f <= x <= father_height*0.98f
        mDangradBg = new RectF(father_width*0.8f, father_height*0.05f, father_width*1f, father_height*0.98f);// 设置个新的长方形
        canvas.drawRoundRect(mDangradBg, father_width * 0.2f, father_width * 0.2f, mPaint);//第二个参数是x半径，第三个参数是y半径
        mDangradBg = new RectF(father_width*0.8f, progress, father_width*1f, father_height*0.98f);// 设置个新的长方形
        canvas.drawRoundRect(mDangradBg, father_width * 0.2f, father_width * 0.2f, mBatteryPaint);//第二个参数是x半径，第三个参数是y半径
    }

    /**
     * 逻辑模块
     */
    private void logic() {
        if(victor2 <= victor) {
            progress = 0.98f*father_height - (0.98f-0.05f)*father_height/(10000)*victor2;
            victor2 ++;
            post(()->postInvalidate());
        } else {
            rememberVictor = victor;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1);
                if(control_threadDie)
                    break;
                logic();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        control_threadDie = true;
    }


}
