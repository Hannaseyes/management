package com.management.rd.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.management.rd.constants.SuffixConsts;

public class QRCodeUtil {
    private static final int IMAGE_WIDTH = 100;
    private static final int IMAGE_HEIGHT = 100;
    private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
    private static final int FRAME_WIDTH = 2;
    private static MultiFormatWriter mutiWriter = new MultiFormatWriter();

    /**
     * 直接返回
     * 无背景
     * 有LOGO
     * @param content
     * @param width
     * @param height
     * @param srcImagePath
     * @param response
     */
    public static void logoQRCode(String content, int width, int height, String srcImagePath, HttpServletResponse response) {
        try {
            IOUtil.imageWrite(genBarcode(content, width, height, srcImagePath), SuffixConsts.JPG, response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 直接返回
     * 无背景
     * 无LOGO二维码
     * @param content
     * @param width
     * @param height
     * @param srcImagePath
     * @param response
     */
    public static void qrCode(String content, int width, int height, HttpServletResponse response) {
        try {
            IOUtil.imageWrite(getQRCode(content, width, height), SuffixConsts.JPG, response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 直接返回
     * 有背景
     * 无LOGO
     * @param content 内容
     * @param width 二维码宽度
     * @param height 二维码高度
     * @param bgPath 背景图地址
     * @param response
     */
    public static void bgQRCode(String content, int width, int height, int x, int y, String bgPath, HttpServletResponse response) {
        try {
            BufferedImage image = getQRCode(content, width, height);// 二维码
            BufferedImage bg = ImageIO.read(new File(bgPath));// 获取背景图片
            Graphics2D g = bg.createGraphics();
            int bgWidth = image.getWidth(null) > bg.getWidth() / 2 ? (bg
                    .getWidth() / 2) : image.getWidth(null);
            int bgHeight = image.getHeight(null) > bg.getHeight() / 2 ? (bg
                    .getHeight() / 2) : image.getWidth(null);
            g.drawImage(image, x, y, bgWidth, bgHeight, null);
            g.dispose();
            bg.flush();
            IOUtil.imageWrite(bg, SuffixConsts.JPG, response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * 无背景
     * 无LOGO
     * @param content
     * @param width
     * @param height
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage getQRCode(String content, int width, int height) throws WriterException,
    IOException {
        Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 生成二维码
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,
                width, height, hint);
        // 二维矩阵转为一维像素数组
        int[] pixels = new int[width * height];
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                // 左上角颜色,根据自己需要调整颜色范围和颜色
                if (x > 0 && x < 170 && y > 0 && y < 170) {
                    Color color = new Color(0, 0, 0);
                    int colorInt = color.getRGB();
                    pixels[y * width + x] = matrix.get(x, y) ? colorInt : 16777215;
                } else {
                    // 二维码颜色
                    /*int num1 = (int) (50 - (50.0 - 13.0) / matrix.getHeight()
                            * (y + 1));
                    int num2 = (int) (165 - (165.0 - 72.0) / matrix.getHeight()
                            * (y + 1));
                    int num3 = (int) (162 - (162.0 - 107.0)
                            / matrix.getHeight() * (y + 1));*/
                    int num1 = 0;
                    int num2 = 0;
                    int num3 = 0;
                    Color color = new Color(num1, num2, num3);
                    int colorInt = color.getRGB();
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    pixels[y * width + x] = matrix.get(x, y) ? colorInt
                            : 16777215;
                }
            }
        }
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, width, height, pixels);
        return image;
    }
    
    /**
     * 无背景
     * 有LOGO
     * @param content
     * @param width
     * @param height
     * @param srcImagePath
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage genBarcode(String content, int width, int height, String srcImagePath) throws WriterException,
            IOException {
        BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH,
                IMAGE_HEIGHT, true);
        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
        for (int i = 0; i < scaleImage.getWidth(); i++) {
            for (int j = 0; j < scaleImage.getHeight(); j++) {
                srcPixels[i][j] = scaleImage.getRGB(i, j);
            }
        }
        Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 生成二维码
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,
                width, height, hint);
        // 二维矩阵转为一维像素数组
        int halfW = matrix.getWidth() / 2;
        int halfH = matrix.getHeight() / 2;
        int[] pixels = new int[width * height];
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                // 左上角颜色,根据自己需要调整颜色范围和颜色
                if (x > 0 && x < 170 && y > 0 && y < 170) {
                    Color color = new Color(231, 144, 56);
                    int colorInt = color.getRGB();
                    pixels[y * width + x] = matrix.get(x, y) ? colorInt
                            : 16777215;
                }
                // 读取图片
                else if (x > halfW - IMAGE_HALF_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH
                        && y < halfH + IMAGE_HALF_WIDTH) {
                    pixels[y * width + x] = srcPixels[x - halfW
                            + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
                } else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH
                                && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                                && y > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                                + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                                && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                                && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                                - IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                                && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                                && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                                + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
                    pixels[y * width + x] = 0xfffffff;
                    // 在图片四周形成边框
                } else {
                    // 二维码颜色
                    int num1 = (int) (50 - (50.0 - 13.0) / matrix.getHeight()
                            * (y + 1));
                    int num2 = (int) (165 - (165.0 - 72.0) / matrix.getHeight()
                            * (y + 1));
                    int num3 = (int) (162 - (162.0 - 107.0)
                            / matrix.getHeight() * (y + 1));
                    Color color = new Color(num1, num2, num3);
                    int colorInt = color.getRGB();
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    pixels[y * width + x] = matrix.get(x, y) ? colorInt
                            : 16777215;
                    // 0x000000:0xffffff
                }
            }
        }
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, width, height, pixels);
        return image;
    }

    /**
     * 处理LOGO图片
     * @param srcImageFile
     * @param height
     * @param width
     * @param hasFiller
     * @return
     * @throws IOException
     */
    private static BufferedImage scale(String srcImageFile, int height, int width, boolean hasFiller) throws IOException {
        double ratio = 0.0; // 缩放比例
        File file = new File(srcImageFile);
        BufferedImage srcImage = ImageIO.read(file);
        Image destImage = srcImage.getScaledInstance(width, height,
                BufferedImage.SCALE_SMOOTH);
        // 计算比例
        if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
            if (srcImage.getHeight() > srcImage.getWidth()) {
                ratio = (new Integer(height)).doubleValue()
                        / srcImage.getHeight();
            } else {
                ratio = (new Integer(width)).doubleValue()
                        / srcImage.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            destImage = op.filter(srcImage, null);
        }
        if (hasFiller) {
            // 补白
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphic = image.createGraphics();
            graphic.setColor(Color.white);
            graphic.fillRect(0, 0, width, height);
            if (width == destImage.getWidth(null))
                graphic.drawImage(destImage, 0,
                        (height - destImage.getHeight(null)) / 2,
                        destImage.getWidth(null), destImage.getHeight(null),
                        Color.white, null);
            else
                graphic.drawImage(destImage,
                        (width - destImage.getWidth(null)) / 2, 0,
                        destImage.getWidth(null), destImage.getHeight(null),
                        Color.white, null);
            graphic.dispose();
            destImage = image;
        }
        return (BufferedImage) destImage;
    }

}