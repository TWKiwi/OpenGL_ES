package com.example.kiwi.opengl_es;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/**
 * https://www3.ntu.edu.sg/home/ehchua/programming/android/Android_3D.html
 */
public class MyGLActivity extends Activity {

    private GLSurfaceView glView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glView = new GLSurfaceView(this);
        glView.setRenderer(new MyGLRenderer(this));
        this.setContentView(glView);

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
