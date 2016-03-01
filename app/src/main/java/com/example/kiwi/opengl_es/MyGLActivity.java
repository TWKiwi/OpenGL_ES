package com.example.kiwi.opengl_es;

import android.app.Activity;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * https://www3.ntu.edu.sg/home/ehchua/programming/android/Android_3D.html
 */
public class MyGLActivity extends Activity {

    private GLSurfaceView glView;
    public static TextView mLightTextView;

    Light mLight = new Light();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glView = new GLSurfaceView(this);
        glView.setRenderer(new MyGLRenderer(this));
        this.setContentView(glView);
        visibleParameter(mLight.getClass().getSimpleName(),mLight.getPositionParameter());
    }


    public void visibleParameter(String classname,String parameter_array) {

        mLightTextView = new TextView(this);
        mLightTextView.setText(classname + "類別" + parameter_array);
        mLightTextView.setTextSize(12);
        mLightTextView.setTextColor(Color.WHITE);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.
                WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        addContentView(mLightTextView, params);

    }


    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }
}
