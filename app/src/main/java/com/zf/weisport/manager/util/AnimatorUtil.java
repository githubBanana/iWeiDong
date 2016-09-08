package com.zf.weisport.manager.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-08 17:17
 * @email Xs.lin@foxmail.com
 */
public class AnimatorUtil {

    public static void play(View view, boolean action) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorX;
        ObjectAnimator animatorY;
        if (action) { //true:down
            animatorX = ObjectAnimator.ofFloat(view,"scaleX", 1f,1.05f);
            animatorY = ObjectAnimator.ofFloat(view,"scaleY", 1f,1.05f);
        } else {
            animatorX = ObjectAnimator.ofFloat(view,"scaleX", 1.05f,1f);
            animatorY = ObjectAnimator.ofFloat(view,"scaleY", 1.05f,1f);
        }
        animatorSet.setDuration(500);
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.start();
    }

}

