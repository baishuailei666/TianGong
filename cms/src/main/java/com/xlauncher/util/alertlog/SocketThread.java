package com.xlauncher.util.alertlog;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socketThread
 * @author Xiaolong Zhang
 * @since 2018-02-13
 */
public class SocketThread extends Thread {

    private ServerSocket serverSocket = null;
    private Thread clientThread;
    private Logger logger = Logger.getLogger(SocketThread.class);
    public SocketThread(ServerSocket serverSocket) {
        try {
            if (null == serverSocket) {
                this.serverSocket = new ServerSocket(4560);
                logger.info("SocketThread-启动socket监听4560端口");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("SocketThread-socket异常报错: " + e.getMessage() + e.getCause() + e.getLocalizedMessage());
        }
    }

    @Override
    public void run() {
        logger.info("SocketThread - run() - isInterrupted()：" + isInterrupted());
        while (!isInterrupted()) {
            Socket socket = null;
            try {
                if (!serverSocket.isClosed()) {
                    socket = serverSocket.accept();
                }
                if (null != socket && !socket.isClosed()) {
                    clientThread = new Thread(new LogRunner(socket));
                    clientThread.start();
                    logger.info("SocketThread-建立连接并启动线程LogRunner：" + clientThread.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("SocketThread-run socket异常报错: " + e.getMessage() + e.getCause() + e.getLocalizedMessage());
            }
        }
    }

    public void closeSocketServer() {
        if (null != serverSocket && !serverSocket.isClosed()) {
            try {
                clientThread.interrupt();
                serverSocket.close();
                logger.info("SocketThread-closeSocketServer socket连接关闭" );
                logger.info("关闭serverSocket服务和clientThread线程：" + serverSocket.isClosed() + clientThread.isInterrupted());
            } catch (IOException e) {
                logger.error("SocketThread-closeSocketServer socket异常报错: " + e.getMessage() + e.getCause() + e.getLocalizedMessage());
            }
        }
    }
}
