package com.example.kiwi.opengl_es;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Kiwi on 2016/2/16.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    Context context;

    /*======================2D===========================*/
    Triangle triangle;
    Square quad;

    // 選轉角度和速度
    private float angleTriangle = 0.0f; // 旋轉角度目前怎麼調都無感...
    private float angleQuad = 0.0f;     // 同上
    private float speedTriangle = 0.5f; // 正向旋轉
    private float speedQuad = -0.4f;    // 反向旋轉

    /*======================3D===========================*/
    private Pyramid pyramid;
    private Cube cube;
    private Light light;

    private static float anglePyramid = 0.0f;
    private static float angleCube = 0.0f;
    private static float angleLight = 0.0f;
    private static float speedPyramid = 1.0f;
    private static float speedCube = 1.0f;
    private static float speedLight = 1.0f;
    private static float currect_light_position[] = { 0.0f, 0.0f, 0.0f};


    public MyGLRenderer(Context context) {
        this.context = context;
        // 初始化三角形正方形金字塔立方體各自的緩衝區
        //triangle = new Triangle();
        //quad = new Square();
        pyramid = new Pyramid();
        cube = new Cube();
        light = new Light();

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // 為顏色緩衝區指定正確的值 glClearColor(R,G,B,A)
        gl.glClearDepthf(1.0f);            // 深度值小於1.0f的物體都可被看見
        gl.glEnable(GL10.GL_DEPTH_TEST);   // 用於啟用各種功能，其功能要由參數決定。

                                           // GL_DEPTH_TEST=深度測試，根據座標遠近自動隱藏被遮住的圖形
        gl.glDepthFunc(GL10.GL_LEQUAL);    // 深度緩衝比較值，如果輸入的數值小於或等於參考值，則通過
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // 該函數在控制OpenGL在某一方
                                                                         // 面有解釋的餘地時，所採取得操
                                                                         // 作行為
                                                                         // GL_PERSPECTIVE_CORRECTION_HINT：指定颜色和纹理坐标的差值质量。
                                                                         // GL_NICEST：選擇最高質量

        gl.glShadeModel(GL10.GL_SMOOTH);   // 顏色漸層圓潤平滑
        gl.glDisable(GL10.GL_DITHER);      // 關閉服務端GL功能
                                           // GL_DITHER
                                           // 抖動算法，對于可用顔色較少的系統，可以以犧牲分辨率爲代價，
                                           // 通過顔色值的抖動來增加可用顏色數量




    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        if (height == 0) height = 1;   // 以防參數值為0
        float aspect = (float)width / height;

        gl.glViewport(0, 0, width, height); // glViewport(x軸,y軸,glView視窗寬,高)
                                            // 設定圖像的原點x,y軸，與這個窗口的寬與高

        gl.glMatrixMode(GL10.GL_PROJECTION);    // 選擇矩陣模式
                                                // GL_PROJECTION = 將當前的矩陣指定為投影矩陣
        gl.glLoadIdentity();    // 將OpenGL中的矩陣設為投影矩陣

        // Use perspective projection
        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);    // gluPerspective(gl,視角(度),
                                                            // 投影平面的寬高比,最近的成像,最遠的成像)

        gl.glMatrixMode(GL10.GL_MODELVIEW); // 選擇矩陣模式
                                            // GL_MODELVIEW = 模型矩陣
        gl.glLoadIdentity();    // 將OpenGL中的矩陣設為模組矩陣

    }

//    static void glShadowProjection(float[] l, float[] e, float[] n)
//    {
//        float d, c;
//        float mat[16];
//
//// These are c and d (corresponding to the tutorial)
//
//        d = n[0]*l[0] + n[1]*l[1] + n[2]*l[2];
//        c = e[0]*n[0] + e[1]*n[1] + e[2]*n[2] - d;
//
//// Create the matrix. OpenGL uses column by column
//// ordering
//
//        mat[0]  = l[0]*n[0]+c;
//        mat[4]  = n[1]*l[0];
//        mat[8]  = n[2]*l[0];
//        mat[12] = -l[0]*c-l[0]*d;
//
//        mat[1]  = n[0]*l[1];
//        mat[5]  = l[1]*n[1]+c;
//        mat[9]  = n[2]*l[1];
//        mat[13] = -l[1]*c-l[1]*d;
//
//        mat[2]  = n[0]*l[2];
//        mat[6]  = n[1]*l[2];
//        mat[10] = l[2]*n[2]+c;
//        mat[14] = -l[2]*c-l[2]*d;
//
//        mat[3]  = n[0];
//        mat[7]  = n[1];
//        mat[11] = n[2];
//        mat[15] = -d;
//
//// Finally multiply the matrices together *plonk*
//        glMultMatrixf(mat);
//    }

    @Override
    public void onDrawFrame(GL10 gl) {

        /*======================2D===========================*/
//        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);    // 清除螢幕和深度緩衝區
//                                                                            // GL_COLOR_BUFFER_BITGL_COLOR_BUFFER_BIT
//                                                                            // 當前可寫的顏色緩衝
//                                                                            // GL_DEPTH_BUFFER_BITGL_DEPTH_BUFFER_BIT
//                                                                            // 深度緩衝
//
//        gl.glLoadIdentity();                  // 重設為模型矩陣
//        gl.glTranslatef(-1.5f, 0.0f, -6.0f);  // 以下這是原本的三角形繪製座標
//                                              // 0.0f,  1.0f, 0.0f,
//                                              // -1.0f, -1.0f, 0.0f,
//                                              // 1.0f, -1.0f, 0.0f
//                                              // 我們將三角形位置向左平移1.5單位
//                                              // -6.0f ** 還沒弄懂為何z軸要設定為負數才能顯示 **
//
//        gl.glRotatef(angleTriangle, 0.0f, 1.0f, 0.0f);  // Rotate the triangle about the y-axis (NEW)
//                                                        // 想像一下，從原點(0,0,0)開始拉出一條線到(0,1,0)
//                                                        // 右手握線，大拇指指向原點，拉出線條
//                                                        // 此時另外四根手指彎曲的方向即是旋轉方向
//        triangle.draw(gl);                    // 三角形繪製！！！
//
//        //以下註解為單純顯示圖像(不旋轉)用途
//        //gl.glTranslatef(3.0f, 0.0f, 0.0f);  // 同理，向右平移3.0個單位
//
//        gl.glLoadIdentity();                  // Reset the mode-view matrix (NEW)
//        gl.glTranslatef(1.5f, 0.0f, -6.0f);   // 向右平移1.5個單位，z軸移動-6.0個單位
//        gl.glRotatef(angleQuad, 1.0f, 0.0f, 0.0f);  // Rotate the square about the x-axis (NEW)
//                                                    // 想像一下，從原點(0,0,0)開始拉出一條線到(1,0,0)
//                                                    // 右手握線，大拇指指向原點，拉出線條
//                                                    // 此時另外四根手指彎曲的方向即是旋轉方向
//        quad.draw(gl);                        // 繪製！！
//
//        // 每次刷新圖像時，皆將旋轉速度數值加上個別圖像的目前角度數值，在繪製新的視角圖像，顯示出來，產生圖像在旋轉的效果
//        angleTriangle += speedTriangle;
//        angleQuad += speedQuad;
        /*======================2D===========================*/

        /*======================3D===========================*/
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);  // 清除螢幕和深度緩衝區
//                                                                           GL_COLOR_BUFFER_BITGL_COLOR_BUFFER_BIT
//                                                                           當前可寫的顏色緩衝
//                                                                           GL_DEPTH_BUFFER_BITGL_DEPTH_BUFFER_BIT
//                                                                           深度緩衝




        // ----- 繪製立方體 -----
        gl.glLoadIdentity();

        gl.glTranslatef(1.5f, 0.0f, -6.0f);
        gl.glScalef(0.8f, 0.8f, 0.8f);      // 三軸放大/縮小(倍數)，可改變模型長寬高外觀
        gl.glRotatef(angleCube, 1.0f, 0.0f, 0.0f);
        cube.draw(gl);


        // ------反轉聚光燈的視角角度------- //
        if(currect_light_position[0] <= -180.0f){
            currect_light_position[0] = 0.0f;
            currect_light_position[1] = 0.0f;
            currect_light_position[2] = 0.0f;
        }
        currect_light_position[0] += -1.0f;
        currect_light_position[1] += -1.0f;
        currect_light_position[2] += -1.0f;
        Log.d("正確燈光位置", ""+currect_light_position[0]+currect_light_position[1]+currect_light_position[2]);

        // ----- 繪製金字塔 -----
        gl.glLoadIdentity();

        gl.glTranslatef(-1.5f, 0.0f, -6.0f);
        gl.glRotatef(anglePyramid, 0.1f, 1.0f, -0.1f);
        pyramid.draw(gl);

        // ----- 繪製聚光燈 -----
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        gl.glRotatef(angleLight, currect_light_position[0], currect_light_position[1], currect_light_position[2]);
        light.draw(gl);


        anglePyramid += speedPyramid;
        angleCube += speedCube;
        angleLight += speedLight;

    }
}
