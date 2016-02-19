package com.example.kiwi.opengl_es;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Kiwi on 2016/2/16.
 */
public class Triangle {
    private FloatBuffer vertexBuffer;  // float緩衝區，用來擺放每個點的座標陣列
    private ByteBuffer indexBuffer;    // Byte緩衝區

    private float[] vertices = {  // 頂點座標陣列(x,y,z)
            0.0f,  1.0f, 0.0f, // 0. top
            -1.0f, -1.0f, 0.0f, // 1. left-bottom
            1.0f, -1.0f, 0.0f  // 2. right-bottom
    };
    private byte[] indices = { 0, 1, 2 }; // 三個點的索引列表 (逆時針)

    // 初始化頂點座標暫存
    public Triangle() {
        // 浮點數是4位元組因此需要把點陣列長度乘以4
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);    // 創建一個Direct的緩衝區，設定緩衝區大小，
                                                                            // 參與I/O時效能比使用單純allocate()更好
        vbb.order(ByteOrder.nativeOrder()); // ByteOrder.nativeOrder()回傳本機平台的位元順序
                                            // .order()設定字節序
        vertexBuffer = vbb.asFloatBuffer(); // 轉換為float形式的緩衝，並建立實例
        vertexBuffer.put(vertices); // 將頂點座標陣列放進緩衝
        vertexBuffer.position(0);   // 設定緩衝區的起始位置

        // Setup index-array buffer. Indices in byte.
        indexBuffer = ByteBuffer.allocateDirect(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    // 畫圖函式
    public void draw(GL10 gl) {

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);   // 啟動頂點陣列的緩衝區
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);  // 為畫筆指定頂點位置和資料格式

        // Draw the primitives via index-array
        /**
         *  glDrawElements(int mode, int count, int type, Buffer indices)
         *   mode: GL_POINTS, GL_LINE_STRIP, GL_LINE_LOOP, GL_LINES, GL_TRIANGLE_STRIP, GL_TRIANGLE_FAN, or GL_TRIANGLES
         *   count: the number of elements to be rendered.
         *   type: data-type of indices (must be GL_UNSIGNED_BYTE or GL_UNSIGNED_SHORT).
         *   indices: pointer to the index array.
         */
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
