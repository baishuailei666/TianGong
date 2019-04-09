package com.xlauncher.utils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.regex.Pattern;


/**
 * ImageUtil: Get Image stream from URL or filepath
 * @author zhangxiaolong
 * @date 2018-02-11
 */
public class ImgUtil {

    /**
     * Get byte[] from resource, URL or filepath
     * @param imgSource resource path
     * @return byte[]
     * @throws IOException: 1.FileNotFoundException 2.MalformedURLException 3.ProtocolException
     */
    public static byte[] getImgDataFromSource(String imgSource) throws IOException {
        return checkImgSource(imgSource) ? getImgByPostUrl(imgSource) : getImgByFileName(imgSource);
    }

    /**
     * Get image byte[] from URL
     * @param postUrl Image URL
     * @return byte[]
     * @throws IOException
     */
    public static byte[] getImgByPostUrl(String postUrl) throws IOException {
        URL url = new URL(postUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(5 * 1000);
        InputStream inputStream = httpURLConnection.getInputStream();
        return readFromInputStream(inputStream);
    }

    /**
     * Get image byte[] from file
     * @param fileName File path
     * @return byte[]
     * @throws FileNotFoundException
     */
    public static byte[] getImgByFileName(String fileName) throws IOException {
        File imageFile = new File(fileName);
        InputStream inputStream = new FileInputStream(imageFile);
        return readFromInputStream(inputStream);
    }

    /**
     * Get byte[] from inputstream
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
     * @param sourcePath Source path
     * @return URL return true; Other return false
     */
    public static boolean checkImgSource(String sourcePath) {
        String regex = "^((https|http|ftp)?://).*";
        boolean isUrl = Pattern.matches(regex, sourcePath);
        return isUrl;
    }

    /**
     * Turn byte[] into java.sql.Blob
     * @param imgByte Image byte[]
     * @return Blob Object
     * @throws SQLException
     */
    public static Blob getBlobViaInputStream(byte[] imgByte) throws SQLException {
        Blob imgBlob = new SerialBlob(imgByte);
        return imgBlob;
    }

}
