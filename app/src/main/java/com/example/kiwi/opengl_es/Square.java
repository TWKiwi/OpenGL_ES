package com.example.kiwi.opengl_es;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Kiwi on 2016/2/16.
 */
public class Square {
    private FloatBuffer vertexBuffer;  // float緩衝區，用來擺放每個點的座標陣列

    private float[] vertices = {  // 正方型四個頂點
            -1.0f, -1.0f,  0.0f,  // 0. left-bottom
            1.0f, -1.0f,  0.0f,  // 1. right-bottom
            -1.0f,  1.0f,  0.0f,  // 2. left-top
            1.0f,  1.0f,  0.0f   // 3. right-top
    };

    // 初始化頂點座標暫存
    public Square() {
        // 浮點數是4位元組因此需要把點陣列長度乘以4
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // ByteOrder.nativeOrder()回傳本機平台的位元順序
                                            // .order()設定字節序
        vertexBuffer = vbb.asFloatBuffer(); // 轉換為float形式的緩衝，並建立實例
        vertexBuffer.put(vertices);         // 將頂點座標陣列放進緩衝
        vertexBuffer.position(0);           // 設定緩衝區的起始位置
    }

    // 畫圖函式
    public void draw(GL10 gl) {
        // Enable vertex-array and define its buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);   // 啟動頂點陣列的緩衝區
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);  // 為畫筆指定頂點位置和資料格式
        // Draw the primitives from the vertex-array directly
        /**
         *  glDrawArrays(int mode, int first, int count)
         *   mode: GL_POINTS, GL_LINE_STRIP, GL_LINE_LOOP, GL_LINES, GL_TRIANGLE_STRIP, GL_TRIANGLE_FAN, or GL_TRIANGLES
         *   first: the starting index in the enabled arrays.
         *   count: the number of indices to be rendered.
         */
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);    //OpenGL ES 1.0本身不支援四邊形繪製
                                                                            //所以我們使用GL_TRIANGLE_STRIP
                                                                            //將兩片三角形組成四邊形
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
