package com.xlauncher.util;

import java.io.*;
import java.util.ArrayList;

/**
 * 将文件数组排序，目录放在上面，文件在下面。
 * @author 白帅雷
 * @date 2018-06-13
 */
public class FileSortUtil {

    /**
     * 将文件数组排序，目录放在上面，文件在下面 。`
     * @param file
     * @return
     */
    public static File[] sort(File[] file) {
        ArrayList<File> list = new ArrayList<File>();
        for (File f : file) {
            if (f.isDirectory()) {
                list.add(f);
            }
        }
        for (File f : file) {
            if (f.isFile()) {
                list.add(f);
            }
        }
        return list.toArray(new File[file.length]);
    }

    /**
     * @param fileName 保存的文件名
     * @param data 数据
     * @param allowFileSize 文件允许存储的大小
     */
    public static void saveLogFile(String fileName, String data, long allowFileSize) {
        File f = new File(fileName);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        FileWriter fwr = null;
        BufferedWriter bwr = null;
        try {
            f.createNewFile();
            fwr = new FileWriter(f, true);
            bwr = new BufferedWriter(fwr);
            if (f.length() < (allowFileSize)) {
                fwr.write(data);
                bwr.newLine();
            } else {
                f = new File(fileName);
                f.createNewFile();
                fwr = new FileWriter(f,true);
                bwr = new BufferedWriter(fwr);
                bwr.write(data);
                bwr.newLine();
            }
            bwr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bwr != null) {
                    bwr.close();
                }
                if (fwr != null) {
                    fwr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
