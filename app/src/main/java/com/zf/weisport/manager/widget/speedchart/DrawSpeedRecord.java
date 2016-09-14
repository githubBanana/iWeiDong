package com.zf.weisport.manager.widget.speedchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.zf.weisport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 当前最近一次转速记录绘制
 * Created by linxiaosheng on 2016/3/11.
 *
 */
public class DrawSpeedRecord extends View {
    private List<Integer>       datasY = new ArrayList<>();
    private Paint               paint,paintZheXian,ShadowPanint,paintLine;
    private Shader              mShader;
    private float               x_1,x_2,y_1,y_2;
    private Path                path;
    private boolean             drawInit = false;
    private int                 ge_shi;
    private float               average_y;//每个单位所占的像素高度，这里单位为2k
    private int                 maxValue;
    private int                 item;
    private float               average_x;//单位为1秒
    private int                 H,W;
    /**
     * datasX内元素为距离初始测试时间的秒数
     * @param context
     * @param datasY
     */
    public DrawSpeedRecord(Context context,List<Integer> datasY) {
        super(context);
        this.datasY = datasY;
        init();
    }

    public DrawSpeedRecord(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintLine = new Paint();
        paintLine.setColor(getResources().getColor(R.color.gray));
        paintLine.setStrokeWidth(5.0f);
        paintLine.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorRed));
        paint.setStrokeWidth(10f);

        paintZheXian= new Paint();
        paintZheXian.setStyle(Paint.Style.STROKE);
        paintZheXian.setAntiAlias(true);
        paintZheXian.setColor(getResources().getColor(R.color.colorGreen));
        paintZheXian.setStrokeWidth(1f);

        ShadowPanint = new Paint();
        ShadowPanint.setAntiAlias(true);
        ShadowPanint.setStrokeWidth(2f);
        /*实现某一区域内颜色的渐变效果*/
        mShader = new LinearGradient(0, 0, 200, 500, new int[] {
                Color.argb(255, 0, 255, 0), Color.argb(150, 0, 255, 0),
                Color.argb(10, 0, 255, 0) }, null, Shader.TileMode.CLAMP);
        ShadowPanint.setShader(mShader);

    }

    public void setY(int maxValue) {
        this.maxValue = maxValue;
    }
    public void setX(int item) {
        this.item = item;
    }
    // 像素/s
    private float averageForX(int W) {
        average_x =  W / (float)item;
        return average_x;
    }
    // 像素/2k
    private float averageForY(int H) {
        int initValue = 2000;
        for(int i = 1;i <= 12;i++) {
            if(maxValue <= initValue) {
                ge_shi = i;
                average_y = H / ge_shi;
                average_y = average_y*0.95f;
                break;
            }
            initValue = initValue + 2000;
        }
        return average_y;

    }

    public float convertToY(int speed) {
        float y_value = H - speed * (average_y / 2000);
        return y_value;
    }

    public float convertToX(int number) {//距离初始time的秒数
        float x_value =  number * average_x;
        return x_value;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.maxValue > 24000)
            return;
        if(!drawInit) {
            W = getWidth();
            H = getHeight();
            average_y = averageForY(H);
            average_x = averageForX(W);
        }
        canvas.drawLine(0,H,W,H,paintLine);
        if(datasY.size() != 0) {
            if(datasY.size() >= 2) {
                x_1 = convertToX(0);
                y_1 = convertToY(datasY.get(0));
                x_2 = convertToX(1);
                y_2 = convertToY(datasY.get(1));
                for (int i = 2; i < datasY.size(); i++) {
                    canvas.drawLine(x_1, y_1, x_2, y_2, paintZheXian);
            /*        canvas.drawPoint(x_1, y_1, paint);
                    canvas.drawPoint(x_2, y_2, paint);*/

                    path = new Path();
                    path.moveTo(x_1, y_1);
                    path.lineTo(x_1, y_1);
                    path.lineTo(x_2, y_2);
                    path.lineTo(x_2, getHeight());
                    path.lineTo(x_1, getHeight());
                    path.close();
                    canvas.drawPath(path, ShadowPanint);
                    x_1 = x_2;
                    y_1 = y_2;
                    x_2 = convertToX(i);
                    y_2 = convertToY(datasY.get(i));
//                    canvas.drawPoint(x_2,y_2,paint);

                }
                //画最后一条线
                canvas.drawLine(x_1, y_1, x_2, y_2, paintZheXian);
                path = new Path();
                path.moveTo(x_1, y_1);
                path.lineTo(x_1, y_1);
                path.lineTo(x_2, y_2);
                path.lineTo(x_2, getHeight());
                path.lineTo(x_1, getHeight());
                path.close();
                canvas.drawPath(path, ShadowPanint);
            } else {
                    x_1 = W;
                    y_1 = convertToY(datasY.get(0));
                    path = new Path();
                    path.moveTo(0, y_1);
                    path.lineTo(0, y_1);
                    path.lineTo(x_1, y_1);
                    path.lineTo(x_1, getHeight());
                    path.lineTo(0, getHeight());
                    path.close();
                    canvas.drawPath(path, ShadowPanint);
//                    canvas.drawPoint(x_1, y_1, paint);
            }
        }
    }

}
