package com.xlauncher.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 图片的BASE64编码解码工具类
 * @author 张霄龙
 * @since 2018-03-23
 */
public class Base64ImgUtil {

    /**
     * 将base64编码的字符串转换为图片
     * @param imgStr 图片的base64编码字符串
     * @param imgPath 需要输出的图片的路径
     * @return 生成图片是否成功，true为成功；false为失败；
     */
    public static boolean generateImgage(String imgStr, String imgPath) throws IOException {
        OutputStream outputStream = null;
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] data = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < data.length; ++i) {
                if (data[i] < 0) {
                    data[i] += 256;
                }
            }
            outputStream = new FileOutputStream(imgPath);
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
            outputStream = null;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据图片本地地址将图片转换为base64编码字符串
     * @param imgFilePath
     * @return
     */
    public static String getImgBase64StrByFileName(String imgFilePath) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFilePath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
            inputStream = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 对图片的字节数组信息进行Base64编码
     * @param imgData
     * @return 图片Base64编码后的结果
     */
    public static String getImgBase64StrByBytes(byte[] imgData) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(imgData);
    }

    /**
     * 根据图片网络地址将图片转换为base64编码字符串
     */
    public static String getImgBase64StrByPostUrl(String targetUrl) {
        try {
            URL url = new URL(targetUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5 * 1000);
            InputStream inputStream = httpURLConnection.getInputStream();
            BASE64Encoder encoder = new BASE64Encoder();
            String imgBase64Str = encoder.encode(readInputStream(inputStream));
            return imgBase64Str;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据输入流获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        inputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

}
