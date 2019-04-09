package com.xlauncher.util;

import com.xlauncher.dao.ComponentDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导出sql文件工具类
 * @author 白帅雷
 * @date 2018-06-02
 */
@Component
public class SqlDumpUtil extends Thread {

    @Autowired
    OsUtil osUtil;
    @Autowired
    ComponentDao componentDao;
    private Logger logger = Logger.getLogger(SqlDumpUtil.class);


    /**
     * 导出SQL文件
     *
     * @param fileName 文件名
     */
    public static void backup(String fileName){
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStreamWriter writer = null;
        FileOutputStream fos = null;
        try {
            Runtime rt = Runtime.getRuntime();
            Process chile = rt.exec("/usr/bin/mysqldump -uroot -pdocker@1302 tiangong_cms cms_operation_log --where=date_format(operation_time,'%Y-%m-%d')=" + "\'"+ DatetimeUtil.getDate(DatetimeUtil.getDate(System.currentTimeMillis())) + "\'" );
            is = chile.getInputStream();
            isr = new InputStreamReader(is,"utf8");
            String inStr;
            StringBuilder sb = new StringBuilder("");
            String outStr;
            br = new BufferedReader(isr);
            while ((inStr = br.readLine()) !=null) {
                sb.append(inStr).append("\r\n");
            }
            outStr = sb.toString();

            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fos = new FileOutputStream(file);
            writer = new OutputStreamWriter(fos,"utf8");
            writer.write(outStr);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (br != null) {
                    br.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断某个文件与当前时间是否相差30天
     * @param file 文件路径/文件名
     * @return 是返回true，否则返回false
     */
    public File checkIsMonth(File file) {
        if (0 == file.list().length) {
            return file;
        } else {
            File[] files = file.listFiles();
            files = FileSortUtil.sort(files);
            if (files[0].isFile()) {
                String fileName = files[0].getName();
                if (!fileName.equals("Log" + "tiangong_cms.sql")) {
                    String fileDay = fileName.substring(3, 13);
                    logger.info("fileDay: " + fileDay);
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    int logTime = (int) componentDao.getComponentByAbbr("ftp").getComponentConfiguration().get("log_time");
                    logger.info("获取日志配置存储时间：" + logTime);
                    cal.add(Calendar.DATE, - logTime);
                    Date date = cal.getTime();
                    if (Objects.equals(df.format(date), fileDay)) {
                        return files[0];
                    }
                }
            }
        }
        return file;
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.info("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                logger.info("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            logger.info("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除文件夹下所有文件
     *
     * @param filePath 文件夹完整绝对路径
     * @return flag
     */
    public static boolean deleteAllFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        String[] tempList = file.list();
        File temp;
        for (int i = 0; i< (tempList != null ? tempList.length : 0); i++) {
            if (filePath.endsWith(File.separator)) {
                temp = new File(filePath + tempList[i]);
            } else {
                temp = new File(filePath + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                deleteAllFile(filePath + "/" + tempList[i]);
                return true;
            }
        }
        return false;
    }

    /**
     * 启动一个定时任务
     */
    @Override
    public synchronized void run() {
        /* 计算当前时间距离凌晨12点多少毫秒数*/
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long delay = cal.getTimeInMillis() - System.currentTimeMillis();
        long period = 24 * 1000 * 60 * 60;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String fileName = osUtil.whichOsLog() + "Log" + DatetimeUtil.getDate(DatetimeUtil.getDate(System.currentTimeMillis())) + "tiangong_cms.sql";
                backup(fileName);
                logger.info("每隔一段时间执行导出SQL文件" + fileName + ", " + DatetimeUtil.getDate(System.currentTimeMillis()));
                System.out.println("******华丽丽滴分割线******");
                File f = new File(osUtil.whichOsLog());
                File apartMonth = checkIsMonth(f);
                if (apartMonth.isFile()) {
                    FtpUtil ftp = new FtpUtil();
                    String ip = componentDao.getComponentByAbbr("ftp").getComponentIp();
                    int port = Integer.parseInt(componentDao.getComponentByAbbr("ftp").getComponentPort());
                    String userName = String.valueOf(componentDao.getComponentByAbbr("ftp").getComponentConfiguration().get("userName"));
                    String password = String.valueOf(componentDao.getComponentByAbbr("ftp").getComponentConfiguration().get("password"));
                    logger.info("FTP服务器：" + ip + port +userName + password);
                    boolean connect = FtpUtil.connect(" ", ip, port, userName, password);
                    if (connect) {
                        logger.info("连接FTP服务器成功");
                        if (ftp.upload(apartMonth) != 0) {
                            deleteFile(apartMonth.getName());
                            logger.info("上传文件返回状态：" + ftp.upload(apartMonth));
                        }
                    }
                }
            }
        }, delay,period);

    }
}
