package com.example.buixu.ipcamera;

/**
 * Created by Buixu on 30/03/2017.
 */

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private Camera camera;

    public CameraPreview(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder2, int format, int w, int h) {
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewSize(w, h);
        camera.setParameters(parameters);
        camera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder1) {
        try {
            camera = Camera.open();
            camera.setPreviewDisplay(holder1);
        }
        catch (Exception e) {
            Log.i("Exception surfaceCreated()", "e=" + e);
            camera.release();
            camera = null;
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

}