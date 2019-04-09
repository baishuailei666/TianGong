package com.xlauncher.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.junit.Test;

import java.io.*;
import java.net.SocketException;

public class FtpUtilTest {
    //ftp服务器地址
    public String hostName = "8.14.0.108";
    //ftp服务器端口号默认为21
    public Integer port = 21;
    //ftp登录账号
    public String userName = "xlauncher";
    //ftp登录密码
    public String password = "docker@1302";
    //ftp远端路径
    public String remoteDirectory = "/alarm-imgs/";
    //文件路径ftp://8.14.0.108:21
    public String remoteFileName = "2019-02-13_11:23:44.915321-img.jpg";
    //本地存储路径
    public String localDirectory = "/ftp/";
    //本地文件名称
    public String localFileName = "2018-07-23-13:42:07.736601-person.jpg";

    public void getImg() {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            //链接ftp服务器
            ftpClient.connect(hostName, port);
            //设置编码格式
            ftpClient.setAutodetectUTF8(true);
            //设置以二进制传送
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //设置linux ftp服务器
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
            ftpClient.configure(conf);
            //登录ftp服务器
            ftpClient.login(userName, password);

            if (!ftpClient.isConnected()) {
                System.out.println("connect failed...ftp服务器:" + this.hostName + ":" + this.port);
            } else {
                System.out.println("connect success...ftp服务器:" + this.hostName + ":" + this.port);
            }
            //设置被动访问模式
//            ftpClient.setRemoteVerificationEnabled(false);
            ftpClient.enterLocalPassiveMode();
            //更改远程工作目录
            ftpClient.changeWorkingDirectory(remoteDirectory);


            File localFile = new File(localFileName);
            /*OutputStream os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(remoteFileName, os);
            os.flush();
            os.close();*/
            InputStream is = ftpClient.retrieveFileStream(remoteFileName);

            //将从服务器上拉取的图片字节流存入本地文件
            File file = new File("d:\\test1.jpg");
            FileOutputStream fo = new FileOutputStream(file);
            byte[] cache = new byte[1024 * 128];
            int len;
            while (is != null && (len = is.read(cache)) != -1) {
                System.out.println("len:" + len);
                fo.write(cache, 0, len);
            }
            fo.flush();
            fo.close();

//            OutputStream os = new FileOutputStream(remoteFileName);
//            int count = 0;
//            byte[] bytes = new byte[1024 * 12];
//            while ((count = is.read(bytes)) != -1) {
//                os.write(bytes, 0, count);
//            }
//            os.flush();
//            os.close();


            /*for (FTPFile ff : ftpClient.listFiles()) {
                System.out.println(ff.getName());
                File localFile = new File(ff.getName());
                OutputStream os = new FileOutputStream(localFile);
                InputStream is = ftpClient.retrieveFileStream(remoteFileName);
                int count = 0;
                byte[] bytes = new byte[1024 * 12];
                while ((count = is.read(bytes)) != -1) {
                    os.write(bytes);
                }
//                ftpClient.retrieveFile(ff.getName(), os);
                os.flush();
                os.close();
            }*/

            ftpClient.logout();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Test
    public void ftpUtilTest() {
        FtpUtilTest ftp = new FtpUtilTest();
        ftp.getImg();
    }
}
