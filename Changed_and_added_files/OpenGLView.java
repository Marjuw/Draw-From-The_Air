package com.google.mediapipe.apps.basic;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;


public class OpenGLView extends GLSurfaceView
{

    public ParticleRenderer pgl;

    //programmatic instantiation
    public OpenGLView(Context context)
    {
        this(context, null);
    }

    //XML inflation/instantiation
    public OpenGLView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public OpenGLView(Context context, AttributeSet attrs, int defStyle)
    {

        super(context, attrs);

        // Tell EGL to use a ES 2.0 Context
        setEGLContextClientVersion(2);

       //Background transparent
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);

        pgl= new ParticleRenderer(context);

        // Set the renderer
        setRenderer(pgl);

    }

}
