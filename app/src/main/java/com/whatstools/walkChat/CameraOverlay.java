package com.whatstools.walkChat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build.VERSION;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import java.io.IOException;

public class CameraOverlay extends TextureView implements SurfaceTextureListener {
    static CameraOverlay objCamOverlay;
    static WindowManager windowManager;
    Camera camera;

    public CameraOverlay(Context context) {
        super(context);
        setSurfaceTextureListener(this);
    }

    @SuppressLint({"WrongConstant"})
    public static View methOverlayCheck(Context context) {
        int i;
        if (objCamOverlay == null) {
            objCamOverlay = new CameraOverlay(context);
        }
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        if (VERSION.SDK_INT >= 26) {
            i = 2032;
        } else {
            i = 2002;
        }
        windowManager.addView(objCamOverlay, new LayoutParams(-1, -1, i, -2147220456, -3));
        return objCamOverlay;
    }

    public static void methWinManager() {
        if (objCamOverlay != null) {
            windowManager.removeView(objCamOverlay);
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        int i3 = 0;
        if (this.camera == null) {
            this.camera = Camera.open();
        }
        if (this.camera != null) {
            Parameters parameters = this.camera.getParameters();
            Size preferredPreviewSizeForVideo = parameters.getPreferredPreviewSizeForVideo();
            parameters.setPreviewSize(preferredPreviewSizeForVideo.width, preferredPreviewSizeForVideo.height);
            int[] iArr = parameters.getSupportedPreviewFpsRange().get(0);
            parameters.setPreviewFpsRange(iArr[0], iArr[1]);
            this.camera.setParameters(parameters);
            Matrix matrix = new Matrix();
            int rotation = windowManager.getDefaultDisplay().getRotation();
            int i4 = preferredPreviewSizeForVideo.width;
            int i5 = preferredPreviewSizeForVideo.height;
            switch (rotation) {
                case 0:
                    i3 = 90;
                    i4 = preferredPreviewSizeForVideo.height;
                    i5 = preferredPreviewSizeForVideo.width;
                    break;
                case 1:
                    i4 = preferredPreviewSizeForVideo.width;
                    i5 = preferredPreviewSizeForVideo.height;
                    break;
                case 2:
                    i3 = 270;
                    i4 = preferredPreviewSizeForVideo.height;
                    i5 = preferredPreviewSizeForVideo.width;
                    break;
                case 3:
                    i3 = 180;
                    i4 = preferredPreviewSizeForVideo.width;
                    i5 = preferredPreviewSizeForVideo.height;
                    break;
            }
            this.camera.setDisplayOrientation(i3);
            setLayoutParams(new FrameLayout.LayoutParams(i4, i5, 17));
            setTransform(matrix);
            try {
                this.camera.setPreviewTexture(surfaceTexture);
                this.camera.startPreview();
            } catch (IOException e) {
                this.camera.release();
                this.camera = null;
            }
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        if (this.camera != null) {
            this.camera.stopPreview();
            this.camera.release();
            this.camera = null;
        }
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }
}
