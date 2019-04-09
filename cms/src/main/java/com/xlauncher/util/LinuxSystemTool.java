package com.xlauncher.util;

import java.io.*;
import java.util.*;

/**
* 取得linux系统下的cpu、内存信息
*/
public final class LinuxSystemTool{
        /**
         * get memory by used info
         *
         * @return int[] result
         * result.length==4;int[0]=MemTotal;int[1]=MemFree;int[2]=SwapTotal;int[3]=SwapFree;
         * @throws IOException
         * @throws InterruptedException
         */
        public static int[] getMemInfo() throws IOException, InterruptedException
        {
            File file = new File("/proc/meminfo");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));
            int[] result = new int[4];
            String str = null;
            StringTokenizer token = null;
            while((str = br.readLine()) != null)
            {
                token = new StringTokenizer(str);
                if(!token.hasMoreTokens()) {
                    continue;
                }
                str = token.nextToken();
                if(!token.hasMoreTokens()) {
                    continue;
                }
                if(str.equalsIgnoreCase("MemTotal:")) {
                    result[0] = Integer.parseInt(token.nextToken());
                }else if(str.equalsIgnoreCase("MemFree:")) {
                    result[1] = Integer.parseInt(token.nextToken());
                }else if(str.equalsIgnoreCase("SwapTotal:")) {
                    result[2] = Integer.parseInt(token.nextToken());
                }else if(str.equalsIgnoreCase("SwapFree:")) {
                    result[3] = Integer.parseInt(token.nextToken());
                }
            }

            return result;
        }

        /**
         * get memory by used info
         *
         * @return float efficiency
         * @throws IOException
         * @throws InterruptedException
         */
        public static float getCpuInfo() throws IOException, InterruptedException
        {
            File file = new File("/proc/stat");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));
            StringTokenizer token = new StringTokenizer(br.readLine());
            token.nextToken();
            long user1 = 0;
            long nice1 = 0 ;
            long sys1 = 0;
            long idle1 = 0;
            long sum1 = 0;
            try {
                user1 = Long.valueOf(token.nextToken());
                nice1 = Long.valueOf(token.nextToken());
                sys1 = Long.valueOf(token.nextToken());
                idle1 = Long.valueOf(token.nextToken());
                sum1 = (user1 + sys1 + nice1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            Thread.sleep(1000);

            br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file)));
            token = new StringTokenizer(br.readLine());
            token.nextToken();
            long user2 = 0;
            long nice2 = 0;
            long sys2 = 0;
            long idle2 = 0;
            long sum2 = 0;
            try {
                user2 = Integer.parseInt(token.nextToken());
                nice2 = Integer.parseInt(token.nextToken());
                sys2 = Integer.parseInt(token.nextToken());
                idle2 = Integer.parseInt(token.nextToken());
                sum2 = (user2 + sys2 + nice2);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            return (float)(sum2 - sum1) / (float)((user2 + nice2 + sys2 + idle2) - (user1 + nice1 + sys1 + idle1));
        }

    /**
     * 执行系统命令, 返回执行结果
     *
     * @param cmd 需要执行的命令
     * @param dir 执行命令的子进程的工作目录, null 表示和当前主进程工作目录相同
     */
    public static List<String> execCmd(String cmd, File dir) throws Exception {
        StringBuilder result = new StringBuilder();

        Process process = null;
        BufferedReader bufrIn = null;
        BufferedReader bufrError = null;

        try {
            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
            process = Runtime.getRuntime().exec(cmd, null, dir);

            // 方法阻塞, 等待命令执行完成（成功会返回0）
            process.waitFor();

            // 获取命令执行结果, 有两个结果: 正常的输出 和 错误的输出（PS: 子进程的输出就是主进程的输入）
            bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));

            // 读取输出
            String line = null;
            while ((line = bufrIn.readLine()) != null) {
                result.append(line).append('\n');
            }
            while ((line = bufrError.readLine()) != null) {
                result.append(line).append('\n');
            }

        } finally {
            closeStream(bufrIn);
            closeStream(bufrError);

            // 销毁子进程
            if (process != null) {
                process.destroy();
            }
        }

        StringTokenizer stringTokenizer = new StringTokenizer(result.toString());
        String[] list = result.toString().split(" ");
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i<list.length;i++){
            if (list[i].trim().contains("G") || list[i].trim().contains("%")) {
                list1.add(list[i].trim());
            }
        }
        return list1;
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                // nothing
            }
        }
    }

    public static int cpuUsage() {
        try {
            Map<?, ?> map1 = LinuxSystemTool.cpuinfo();
            Thread.sleep(5 * 1000);
            Map<?, ?> map2 = LinuxSystemTool.cpuinfo();

            long user1 = Long.parseLong(map1.get("user").toString());
            long nice1 = Long.parseLong(map1.get("nice").toString());
            long system1 = Long.parseLong(map1.get("system").toString());
            long idle1 = Long.parseLong(map1.get("idle").toString());

            long user2 = Long.parseLong(map2.get("user").toString());
            long nice2 = Long.parseLong(map2.get("nice").toString());
            long system2 = Long.parseLong(map2.get("system").toString());
            long idle2 = Long.parseLong(map2.get("idle").toString());

            long total1 = user1 + system1 + nice1;
            long total2 = user2 + system2 + nice2;
            float total = total2 - total1;

            long totalIdle1 = user1 + nice1 + system1 + idle1;
            long totalIdle2 = user2 + nice2 + system2 + idle2;
            float totalidle = totalIdle2 - totalIdle1;

            float cpusage = (total / totalidle) * 100;
            return (int) cpusage;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 功能：CPU使用信息
     * */
    public static Map<?, ?> cpuinfo() {
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("cpu")) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    List<String> temp = new ArrayList<String>();
                    while (tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }
                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return map;
    }

}
