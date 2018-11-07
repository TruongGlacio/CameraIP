package com.example.buixu.ipcamera;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

CameraDevice mcameraDevice;
    Button mButton_OpenCamera;
    android.hardware.Camera mcamera;
    boolean statut_camera=false;
    TextView mTextView_idCamera;
    SurfaceView mSurfaceView_Camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        setGui();
        final int id=findBacktFacingCameraID();
        mTextView_idCamera.setText(id+"");
        mButton_OpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcamera=Camera.open(id);

                setCameraDisplayOrientation(MainActivity.this,id,mcamera);
            }
        });




} public void setGui(){
        mButton_OpenCamera=(Button)findViewById(R.id.button_openCamera);
        mTextView_idCamera=(TextView)findViewById(R.id.textView_idCamera);
        mSurfaceView_Camera=(SurfaceView)findViewById(R.id.surfaceView_camera);
    }
    public static void setCameraDisplayOrientation(Activity activity,int cameraId, Camera camera) {

        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
    private int findBacktFacingCameraID() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
              //  Log.d(TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

}
