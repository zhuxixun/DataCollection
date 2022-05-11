package com.zxx.datacollection;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;

import java.nio.ShortBuffer;

public class Utils {
    private Utils() {
    }

    public static Bitmap depth16ToBitmap(Image image) {
        int imwidth = image.getWidth();
        int imheight = image.getHeight();
        Image.Plane plane = image.getPlanes()[0];
        ShortBuffer shortDepthBuffer = plane.getBuffer().asShortBuffer();

        Bitmap disBitmap = Bitmap.createBitmap(imwidth, imheight, Bitmap.Config.RGB_565);
        for (int i = 0; i < imheight; i++) {
            for (int j = 0; j < imwidth; j++) {
                int index = (i * imwidth + j);
                shortDepthBuffer.position(index);
                short depthSample = shortDepthBuffer.get();
                short depthRange = (short) (depthSample & 0x1FFF);
                byte value = (byte) (depthRange / 8000f * 255);
                disBitmap.setPixel(j, i, Color.rgb(value, value, value));
            }
        }
        return disBitmap;
    }
}
