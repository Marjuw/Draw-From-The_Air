package com.google.mediapipe.apps.basic;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class ParticleRenderer implements GLSurfaceView.Renderer
{

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    //Default-Values for Line Coordinates, wich could be changed later.
    // X,Y and Z form the coordinates for a point. The connection to the point at points X2, Y2 and Z2 creates a line.
    public float x=-0f;
    public float y=-0f;
    public float z=-0f;

    public float x2=-0f;
    public float y2=-0f;
    public float z2=-0f;

    //RGB Colors  (Default to green)
    public float fr=0f; //red
    public float fg=.8f;  //green
    public float fb=0f;  //blue

    private  int aktuelle_line=0;  //should count the lines, wich are drwan
    public int glcear_count=0; //should count, how often GlClear will be called
    public int reset=0; //soll angeben, ob der Reset Button gedrückt wurde (=1, wenn es so ist)
    public int i=0;
    Boolean neue_linie =true; //with this varible should be proofed, if a line out of the onDraw method was added
    int drawingagain=0;



    ArrayList<Line> linex = new ArrayList<Line>(); //A list of lines is required so that it is automatic and dynamic
    // all and any number of lines can be output and added, which can also be changed outside of the onDrawFrame method.

    private Line mLine;

    public ParticleRenderer(Context context)
    {
        //context = context;
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }


    public void drawfirsttime(float xn, float yn, float zn, float frn, float fgn,float fbn)  // When a coordinate point is tracked for the first time,
    // it should be started from this point. Since a line consists of at least two coordinate points that are connected,
    // a second point is filled with the same values ​​as the first.
    {

        //To fill the coordinates
        x=-xn;
        y=yn;
        z=zn;

        x2=-xn;
        y2=yn;
        z2=zn;

        //Color of the Line
        fr=frn; //red
        fg=fgn;  //green
        fb=fbn; //blue

        neue_linie=true; //indicates that a new line will be created in order to add and display it in the onDrawFrame.

    }


    public void drawanothertime(float xn, float yn, float zn, float frn, float fgn,float fbn) //In order to start at the first point
    // (or the first line) and to create another, a new line is created, which on the one hand consists of the coordinate point where the previous one ends.
    // The second coordinate point is the one that is passed from the outside.
    {
        if(drawingagain==0) { //Lines that are connected to one another must be set up alternately at the coordinate point added last with a new one

            //To fill the coordinates
            x = -xn;
            y = yn;
            z = zn;
        }

        else{

            //To fill the coordinates
            x2=-xn;
            y2=yn;
            z2=zn;

        }

        if (drawingagain==0) { //see above
            drawingagain=1;
        } else{
            drawingagain=0;
        }

        //Color of the Line
        fr=frn; //red
        fg=fgn;  //green
        fb=fbn; //blue

        neue_linie=true; //indicates that a new line will be created in order to add and display it in the onDrawFrame.


    }


    public void gl_clear(){

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

    }


    @Override
    public void onDrawFrame(GL10 gl)
    {
        //To ensure that gl_clear is only called once at the beginning of the run and not for every automatic
        //Run through this onDrawFrame method. In the latter case, no more than one line would otherwise be displayed
        if (glcear_count==0){
            gl_clear();
            glcear_count=1; //ends the new run
        }


        if (reset==1 && i<10){  //delete the last 10 drawn lines

            linex.clear();

            aktuelle_line=0;

            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            i++;


            if(i==10){

                reset=0;
                i=0;
            }


        }


        //GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT); //if only the last 10 lines should be drawn


        if(neue_linie==true) { //so that a new line is only added if new coordinates have been added from outside (since the onDrawFrame is
            // constantly running automatically, new lines would otherwise be constantly added and then displayed (see below).
            // This would then also overwhelm the compiler and make the lines in the app look like a crash.


            linex.add(new Line());
            linex.get(aktuelle_line).SetVerts(x, y, z, x2, y2, z2);  //Set the line Coordinates
            linex.get(aktuelle_line).SetColor(fr, fg, fb, 1.0f); //Set the Colore


            aktuelle_line++;
            neue_linie=false;

        }




        //so that the output also comes after when new values ​​are sent from Mediapipe very often. (Since the OnDraw method runs permanently)

       if(aktuelle_line<=10) {

            for (int i = 0; i <linex.size(); i++) { // draw the first 10 lines

                linex.get(i).draw();

                linex.get(i).draw();

            }

        }

       else {

            for (int i =linex.size()-10; i < linex.size(); i++) { // draw the last 10 lines

                linex.get(i).draw();

                linex.get(i).draw();

            }

        }


        //run through as often as there are number of entries in linex and display each of these individually.
        // Generates visual error because Ogl is quickly overwhelmed with many lines
        /*
        for (int i=0; i < linex.size(); i++) {

            linex.get(i).draw(vPMatrix);

            // Draw shape
            linex.get(i).draw(vPMatrix);


        }*/


       /* // Set the camera position (View matrix)
           Matrix.setLookAtM(viewMatrix, 0, 0, 0, -5f, 0f, 0f, 0f, 0.0f, 2.0f, 0.0f);

        // Calculate the projection and view transformation
           Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);*/


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);

        // float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        //  Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        mLine = new Line();
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);


    }

    public class Line {

        // Use to access and set the view transformation
        // private int vPMatrixHandle;


        private final int mProgram;

        static private final String vertexShaderCode =
                // This matrix member variable provides a hook to manipulate
                // the coordinates of the objects that use this vertex shader
                /* "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "void main() {" +
                        // the matrix must be included as a modifier of gl_Position
                        // Note that the uMVPMatrix factor *must be first* in order
                        // for the matrix multiplication product to be correct.
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "}"; */
                "attribute vec4 vPosition;" +
                        "void main() {" +
                        "  gl_Position = vPosition;" +
                        "}";

        private final String fragmentShaderCode =
                "precision mediump float;" +
                        "uniform vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}";

        private FloatBuffer vertexBuffer;

        // number of coordinates per vertex in this array
        static final int COORDS_PER_VERTEX = 3; //x,Y und Z

        float lineCoords[] = {   // in counterclockwise order:
                0.0f,  0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                // 0.5f, -0.311004243f, 0.0f  // bottom right
        };

        // Set color with red, green, blue and alpha (opacity) values
        float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

        public Line() {
            // initialize vertex byte buffer for shape coordinates
            ByteBuffer bb = ByteBuffer.allocateDirect(
                    // (number of coordinate values * 4 bytes per float)
                    lineCoords.length * 4);
            // use the device hardware's native byte order
            bb.order(ByteOrder.nativeOrder());

            // create a floating point buffer from the ByteBuffer
            vertexBuffer = bb.asFloatBuffer();
            // add the coordinates to the FloatBuffer
            vertexBuffer.put(lineCoords);
            // set the buffer to read the first coordinate
            vertexBuffer.position(0);

            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                    vertexShaderCode);
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                    fragmentShaderCode);

            // create empty OpenGL ES Program
            mProgram = GLES20.glCreateProgram();

            // add the vertex shader to program
            GLES20.glAttachShader(mProgram, vertexShader);

            // add the fragment shader to program
            GLES20.glAttachShader(mProgram, fragmentShader);

            // creates OpenGL ES program executables
            GLES20.glLinkProgram(mProgram);
        }

        private  int positionHandle;
        private  int colorHandle;

        private  final int vertexCount = lineCoords.length / COORDS_PER_VERTEX;
        private static final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

        public void SetVerts(float v0, float v1, float v2, float v3, float v4, float v5) {
            lineCoords[0] = v0;
            lineCoords[1] = v1;
            lineCoords[2] = v2;
            lineCoords[3] = v3;
            lineCoords[4] = v4;
            lineCoords[5] = v5;

            vertexBuffer.put(lineCoords);
            // set the buffer to read the first coordinate
            vertexBuffer.position(0);
        }

        public void SetColor(float red, float green, float blue, float alpha) {
            color[0] = red;
            color[1] = green;
            color[2] = blue;
            color[3] = alpha;
        }

        public void draw(/*float[] mvpMatrix*/) {

            // Add program to OpenGL ES environment
            GLES20.glUseProgram(mProgram);

            // get handle to vertex shader's vPosition member
            positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

            // Enable a handle to the Line vertices
            GLES20.glEnableVertexAttribArray(positionHandle);

            // Prepare the Line coordinate data
            GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT, false,
                    vertexStride, vertexBuffer);

            // get handle to fragment shader's vColor member
            colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

            // Set color for drawing the Line
            GLES20.glUniform4fv(colorHandle, 1, color, 0);

            GLES20.glLineWidth(10.0f);  //thickness of the line (max width is allegedly 10.0f)


            // Draw the Line
            GLES20.glDrawArrays(GLES20.GL_LINES, 0, vertexCount);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(positionHandle);


            /* get handle to shape's transformation matrix
            vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

            // Pass the projection and view transformation to the shader
            GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

            // Draw the Line
            GLES20.glDrawArrays(GLES20.GL_LINES, 0, vertexCount);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(positionHandle);*/
        }
    }
}
