package com.example.kiwi.opengl_es;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Kiwi on 2016/2/27.
 */
public class Light {

    // ------將聚光燈實體化方便辨識光源-------- //

    private float[] mPosition = {
            0.0f, 5.0f, 0.0f, 1.0f
    };

    private float[] mDirection = {
            10.0f, -10.0f, -10.0f
    };

    private float[] mVertices = {  // Vertices of the 6 faces
            // FRONT
            -0.05f, -0.05f,  0.05f,  // 0. left-bottom-front
            0.05f, -0.05f,  0.05f,  // 1. right-bottom-front
            -0.05f,  0.05f,  0.05f,  // 2. left-top-front
            0.05f,  0.05f,  0.05f,  // 3. right-top-front
            // BACK
            0.05f, -0.05f, -0.05f,  // 6. right-bottom-back
            -0.05f, -0.05f, -0.05f,  // 4. left-bottom-back
            0.05f,  0.05f, -0.05f,  // 7. right-top-back
            -0.05f,  0.05f, -0.05f,  // 5. left-top-back
            // LEFT
            -0.05f, -0.05f, -0.05f,  // 5. left-bottom-back
            -0.05f, -0.05f,  0.05f,  // 0. left-bottom-front
            -0.05f,  0.05f, -0.05f,  // 7. left-top-back
            -0.05f,  0.05f,  0.05f,  // 2. left-top-front
            // RIGHT
            0.05f, -0.05f,  0.05f,  // 1. right-bottom-front
            0.05f, -0.05f, -0.05f,  // 4. right-bottom-back
            0.05f,  0.05f,  0.05f,  // 3. right-top-front
            0.05f,  0.05f, -0.05f,  // 6. right-top-back
            // TOP
            -0.05f,  0.05f,  0.05f,  // 2. left-top-front
            0.05f,  0.05f,  0.05f,  // 3. right-top-front
            -0.05f,  0.05f, -0.05f,  // 7. left-top-back
            0.05f,  0.05f, -0.05f,  // 6. right-top-back
            // BOTTOM
            -0.05f, -0.05f, -0.05f,  // 5. left-bottom-back
            0.05f, -0.05f, -0.05f,  // 4. right-bottom-back
            -0.05f, -0.05f,  0.05f,  // 0. left-bottom-front
            0.05f, -0.05f,  0.05f   // 1. right-bottom-front
    };

    private FloatBuffer mLightPositionBuffer, mLightDirectionBuffer, mVertexBuffer;  // Buffer

    // 初始化頂點座標暫存
    public Light() {

        ByteBuffer vbb = ByteBuffer.allocateDirect(mVertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        mVertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        mVertexBuffer.put(mVertices);         // Copy data into buffer
        mVertexBuffer.position(0);           // Rewind

        ByteBuffer pbb = ByteBuffer.allocateDirect(mPosition.length * 4);
        pbb.order(ByteOrder.nativeOrder()); // Use native byte order
        mLightPositionBuffer = pbb.asFloatBuffer(); // Convert from byte to float
        mLightPositionBuffer.put(mPosition);         // Copy data into buffer
        mLightPositionBuffer.position(0);           // Rewind

        ByteBuffer dbb = ByteBuffer.allocateDirect(mDirection.length * 4);
        dbb.order(ByteOrder.nativeOrder()); // Use native byte order
        mLightDirectionBuffer = dbb.asFloatBuffer(); // Convert from byte to float
        mLightDirectionBuffer.put(mDirection);         // Copy data into buffer
        mLightDirectionBuffer.position(0);           // Rewind
    }

    // 畫圖函式
    public void draw(GL10 gl) {

        gl.glFrontFace(GL10.GL_CCW);    // 面對鏡頭的一面逆時針旋轉
        gl.glEnable(GL10.GL_CULL_FACE); // 啟動CullFace
        gl.glCullFace(GL10.GL_BACK);    // 省略/停止繪製背對鏡頭的一面，提高效能

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);   // 啟動頂點陣列緩衝區
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);  // 為畫筆指定頂點位置和數值格式

        // 繪製每一面圖形
        for (int face = 0; face < 6; face++) {
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face*4, 4); // (選擇繪製模式,從第幾組數組開始,頂點數)
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);

        gl.glEnable(GL10.GL_LIGHTING);  //開啟燈照
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, mLightPositionBuffer);    //(光照模式0,光源位置模組,置入座標)
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPOT_DIRECTION, mLightDirectionBuffer); //聚光燈打燈方向
        gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_CUTOFF, 45.0f); //燈光發散角度;
        gl.glEnable(GL10.GL_LIGHT0);    //開啟光照0
    }

    public String getPositionParameter(){

        String parameter_array = "mPosition :";
        for(float s : mPosition) {
            parameter_array += " "+s;
        }
        return parameter_array;
    }
}
