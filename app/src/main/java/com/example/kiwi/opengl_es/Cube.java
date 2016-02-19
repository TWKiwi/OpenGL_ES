package com.example.kiwi.opengl_es;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Kiwi on 2016/2/17.
 */
public class Cube {
    private FloatBuffer vertexBuffer;  // Buffer for vertex-array
    private int numFaces = 6;

    private float[][] colors = {  // Colors of the 6 faces (R,G,B,A)
            {1.0f, 0.5f, 0.0f, 1.0f},  // 0. orange
            {1.0f, 0.0f, 1.0f, 1.0f},  // 1. violet
            {0.0f, 1.0f, 0.0f, 1.0f},  // 2. green
            {0.0f, 0.0f, 1.0f, 1.0f},  // 3. blue
            {1.0f, 0.0f, 0.0f, 1.0f},  // 4. red
            {1.0f, 1.0f, 0.0f, 1.0f}   // 5. yellow
    };

    private float[] vertices = {  // Vertices of the 6 faces
            // FRONT
            -1.0f, -1.0f,  1.0f,  // 0. left-bottom-front
            1.0f, -1.0f,  1.0f,  // 1. right-bottom-front
            -1.0f,  1.0f,  1.0f,  // 2. left-top-front
            1.0f,  1.0f,  1.0f,  // 3. right-top-front
            // BACK
            1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
            -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
            1.0f,  1.0f, -1.0f,  // 7. right-top-back
            -1.0f,  1.0f, -1.0f,  // 5. left-top-back
            // LEFT
            -1.0f, -1.0f, -1.0f,  // 5. left-bottom-back
            -1.0f, -1.0f,  1.0f,  // 0. left-bottom-front
            -1.0f,  1.0f, -1.0f,  // 7. left-top-back
            -1.0f,  1.0f,  1.0f,  // 2. left-top-front
            // RIGHT
            1.0f, -1.0f,  1.0f,  // 1. right-bottom-front
            1.0f, -1.0f, -1.0f,  // 4. right-bottom-back
            1.0f,  1.0f,  1.0f,  // 3. right-top-front
            1.0f,  1.0f, -1.0f,  // 6. right-top-back
            // TOP
            -1.0f,  1.0f,  1.0f,  // 2. left-top-front
            1.0f,  1.0f,  1.0f,  // 3. right-top-front
            -1.0f,  1.0f, -1.0f,  // 7. left-top-back
            1.0f,  1.0f, -1.0f,  // 6. right-top-back
            // BOTTOM
            -1.0f, -1.0f, -1.0f,  // 5. left-bottom-back
            1.0f, -1.0f, -1.0f,  // 4. right-bottom-back
            -1.0f, -1.0f,  1.0f,  // 0. left-bottom-front
            1.0f, -1.0f,  1.0f   // 1. right-bottom-front
    };


    public Cube() {
        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind
    }


    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CCW);    // 面對鏡頭的一面逆時針旋轉
        gl.glEnable(GL10.GL_CULL_FACE); // 啟動CullFace
        gl.glCullFace(GL10.GL_BACK);    // 省略/停止繪製背對鏡頭的一面，提高效能

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);   // 啟動頂點陣列緩衝區
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);  // 為畫筆指定頂點位置和數值格式

        // 繪製每一面圖形
        for (int face = 0; face < numFaces; face++) {
            // glColor4f(R,G,B,A)
            gl.glColor4f(colors[face][0], colors[face][1], colors[face][2], colors[face][3]);
            // glDrawArrays(選擇繪製模式,從第幾組數組開始,頂點數)
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face*4, 4);
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}
