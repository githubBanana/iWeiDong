package com.zf.weisport.manager.widget.speedchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.zf.weisport.R;

/**
 * 转速记录Y轴绘制
 * Created by siushen on 2016/3/15.
 */
public class DrawYPortRait extends View {

    private int             maxValue;
    private Paint           paint,paint2,paintLine;
    private boolean         drawInit = false;
    private int             ge_shi;
    private float           average;
    private int             W,H;
    private String[]        strs = new String[]{"0","2k","4k","6k","8k","1w","1.2w","1.4w","1.6w","1.8w","2w","2.2w","2.4w"};

    public DrawYPortRait(Context context) {
        super(context);
        init();
    }

    public DrawYPortRait(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     *  初始化画笔
     */
    private void init() {
        paintLine = new Paint();
        paintLine.setColor(getResources().getColor(R.color.gray));
        paintLine.setStrokeWidth(5.0f);
        paintLine.setAntiAlias(true);
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.black));
        paint.setAntiAlias(true);
        paint2 = new Paint();
        paint2.setColor(getResources().getColor(R.color.colorRed));
        paint2.setAntiAlias(true);
        paint2.setStrokeWidth(10f);
    }
    public void setY(int maxValue) {
        this.maxValue = maxValue;
    }
    private void sortY(int H) {
        int initValue = 2000;
        for(int i = 1;i <= 12;i++) {
            if(maxValue <= initValue) {
                ge_shi = i;
                average = H / ge_shi;
                average = average*0.95f;
                break;
            }
            initValue = initValue + 2000;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawSub(canvas);
    }

    private void drawSub(Canvas canvas) {
        if (!drawInit) {
            if (this.maxValue > 24000)
                return;
            W = getWidth();
            H = getHeight();
            sortY(H);
            drawInit = true;
        }
        float y = H;
        paint.setTextSize(H * 0.05f);
        canvas.drawLine(W,0,W,H,paintLine);
        for (int i = 0; i <= ge_shi;i++) {
            float x = 0f ;
            if (i == 0 ) {
                x = W * 0.60f;
            }else if (i == 5 || i == 10) {
                x = W * 0.30f;
            }else if (i > 0 && i < 5) {
                x = W * 0.40f;
            }else if ((5 < i && i < 10) || i > 10){
                x = 0f;
            }
            canvas.drawText(strs[i],x,y,paint);//x*0.05f
            y = y - average;
        }
    }
}
