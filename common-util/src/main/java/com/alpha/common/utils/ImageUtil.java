package com.alpha.common.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Created by huangpin on 16/7/21.
 */
public class ImageUtil {

    /**
     * 生成原图和缩略图
     */
    public static byte[] zoom(InputStream sourceFileIs, String suffix,
                                int width, int height) throws IOException {
        BufferedImage readImage = ImageIO.read(sourceFileIs);
        Image image = readImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage zoomImage = new BufferedImage(width, height, readImage.getType());
        Graphics gc = zoomImage.getGraphics();
        gc.drawImage(image, 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.flush();
        ImageIO.write(zoomImage, suffix, baos);
        return baos.toByteArray();
    }
}
