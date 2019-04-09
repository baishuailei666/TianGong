package com.xlauncher.ics.util;

import com.xlauncher.ics.entity.ServiceInfo;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ConnectException;


/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/24 0024
 * @Desc :FTP服务器上传文件
 **/
@Component
public class FtpUtil {
    @Autowired
    private ServiceInfo serviceInfo;
    private static Logger logger = Logger.getLogger(FtpUtil.class);
    private static FTPClient ftp;

    /**
     * 上传文件
     *
     * @param imgName 文件名
     * @param img 图片资源
     *
     * @return 上传成功返回1，失败返回0
     */
    boolean upload(String imgName, byte[] img) {
        boolean isOk = false;
        //ftp服务器地址
        String hostName = serviceInfo.getFtpIp();
        //ftp服务器端口号默认为21
        Integer port = Integer.valueOf(serviceInfo.getFtpPort());
        //ftp登录账号
        String userName = serviceInfo.getFtpUserName();
        //ftp登录密码
        String password = serviceInfo.getFtpPassword();
        //ftp存储路径
        String path = serviceInfo.getFtpStorePath();
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        // 连接ftp服务器
        try {
            ftpClient.connect(hostName, port);
            logger.info("连接[FTP]服务器");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("[FTP]服务器连接异常!" + e);
            logger.info("hostName." + hostName + ", port." + port);
        }
        if (!ftpClient.isConnected()) {
            logger.error("[FTP]服务器没有被连接:" + hostName + ", port:" + port);
            boolean result = connect("", hostName, port, userName, password);
            logger.info("FTP result." + result);
        } else {
            logger.info("[FTP]服务器连接成功! " + "hostName:" + hostName +  ", port:"
                    + port +  ", userName:" + userName + ", password:" + password );
        }
        //登录ftp服务器
        try {
            ftpClient.login(userName, password);
            logger.info("登录[FTP]服务器");
            //设置编码格式
            ftpClient.setAutodetectUTF8(true);
            //设置以二进制传送
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            //设置linux ftp服务器
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
            ftpClient.configure(conf);

            ftpClient.setRemoteVerificationEnabled(false);
            ftpClient.enterLocalPassiveMode();
            //切换到上传目录
            if (!ftpClient.changeWorkingDirectory(path)) {
                ftpClient.changeWorkingDirectory(path);
            }
            logger.info("[FTP]服务器当前目录: " + ftpClient.printWorkingDirectory());
            InputStream is = new ByteArrayInputStream(img);
            // 上传文件
            isOk = ftpClient.storeFile(imgName, is);
            logger.info("上传[FTP]服务器成功! boolean." + isOk + " ,imgName." + imgName);
            is.close();
            ftpClient.logout();
        } catch (IOException e) {
            logger.error("[FTP]服务器登录失败:" + userName + ":" + password);
        }
        return isOk;
    }

    /**
     *  连接ftp服务器
     *
     * @param path 上传到ftp服务器哪个路径下，值为空代表根目录
     * @param ip ftp服务器地址
     * @param port ftp服务器端口号，默认21
     * @param username ftp服务器用户名
     * @param password ftp服务器密码
     * @return boolean
     */
    private static boolean connect(String path, String ip, int port, String username, String password) {
        ftp = new FTPClient();

        int reply;
        try {
            ftp.connect(ip,port);
            ftp.login(username,password);
            ftp.setControlEncoding("utf-8");
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }
            ftp.changeWorkingDirectory(path);
        } catch (ConnectException e) {
            logger.error("拒绝连接-" + e.getMessage());
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 创建多级目录
     *
     * @param remote ftp服务器路径
     * @return 布尔值
     * @throws IOException IOException
     */
    public boolean createDirectory(String remote) throws IOException {
        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
        String slash = "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase(slash)
                && !ftp.changeWorkingDirectory(directory)) {
            int start = 0;
            int end = 0;
            if (directory.startsWith(slash)) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            while (true) {
                String subDirectory = remote.substring(start, end);
                if (!ftp.changeWorkingDirectory(subDirectory)) {
                    if (ftp.makeDirectory(subDirectory)) {
                        ftp.changeWorkingDirectory(subDirectory);
                    } else {
                        logger.warn("创建目录失败！");
                        return false;
                    }
                }
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return true;
    }

}
