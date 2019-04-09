package com.xlauncher.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * ImageUtil: Get Image stream from URL or filepath
 *
 * @author zhangxiaolong
 * @date 2018-02-11
 */
@Component
public class ImgUtil {
    private static Logger logger = Logger.getLogger(ImgUtil.class);
    @Autowired
    private FtpUtil ftpUtil;
    /**
     * Get byte[] from resource, URL or filepath
     *
     * @param imgSource resource path
     * @return byte[]
     * @throws IOException: 1.FileNotFoundException 2.MalformedURLException 3.ProtocolException
     */
    public byte[] getImgDataFromSource(String imgSource) {
        switch (checkImgSource(imgSource)) {
            //从指定的网址获取图片资源
            case 1:
                return getImgByPostUrl(imgSource);
            //从FTP服务器获取图片资源
            case 2:
                return getImgFromFtpServer(imgSource);
            //从指定的文件获取资源
            case 3:
                return getImgByFileName(imgSource);
            default:
                return null;
        }
    }

    /**
     * Get image byte[] from URL
     *
     * @param postUrl Image URL
     * @return byte[]
     * @throws IOException
     */
    public static byte[] getImgByPostUrl(String postUrl) {
        URL url = null;
        try {
            url = new URL(postUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5 * 1000);
            InputStream inputStream = httpURLConnection.getInputStream();
            return readFromInputStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从指定的Ftp服务器获取图片资源
     *
     * @param sourcePath ftp://ip:port/path/filename
     * @return 图片的字节数组
     */
    public byte[] getImgFromFtpServer(String sourcePath) {
        logger.info("从指定的Ftp服务器获取图片资源:" + sourcePath);
        return ftpUtil.getImgFromFtpServer(sourcePath);
    }

    /**
     * Get image byte[] from file
     *
     * @param fileName File path
     * @return byte[]
     * @throws FileNotFoundException
     */
    public static byte[] getImgByFileName(String fileName) {
        File imageFile = new File(fileName);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imageFile);
            return readFromInputStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get byte[] from inputstream
     *
     * @param inputStream Inputstream Object
     * @return byte[]
     * @throws IOException
     */
    public static byte[] readFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        inputStream.close();
        byte[] imgData = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.flush();
        imgData = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return imgData;
    }

    /**
     * Check the resource. If the resource is URL, return true; Or return false.
     *
     * @param sourcePath Source path
     * @return URL return true; Other return false
     */
    public static int checkImgSource(String sourcePath) {
        String regexHttp = "^((https|http)?://).*";
        String regexFtp = "^((ftp)?://).*";
        if (Pattern.matches(regexHttp, sourcePath)) {
            return 1;
        } else if (Pattern.matches(regexFtp, sourcePath)) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * Turn byte[] into java.sql.Blob
     *
     * @param imgByte Image byte[]
     * @return Blob Object
     * @throws SQLException
     */
    public static Blob getBlobViaInputStream(byte[] imgByte) throws SQLException {
        Blob imgBlob = new SerialBlob(imgByte);
        return imgBlob;
    }

}
