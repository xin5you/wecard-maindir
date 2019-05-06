package com.cn.thinkx.oms.util;

import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class FTPUtils {

    private Logger logger = LoggerFactory.getLogger(FTPUtils.class);

    /**
     * ftp 实例化客户端
     *
     * @return
     */
    public FTPClient getFTPClient() {
        FTPClient ftp = new FTPClient();
        String hostname = RedisDictProperties.getInstance().getdictValueByCode("FTPCLIENT.CONNECT.SERVER");
        String port = RedisDictProperties.getInstance().getdictValueByCode("FTPCLIENT.CONNECT.PORT");
        String username = RedisDictProperties.getInstance().getdictValueByCode("FTPCLIENT.CONNECT.USERNAME");
        String password = RedisDictProperties.getInstance().getdictValueByCode("FTPCLIENT.CONNECT.PASSWORD");
        ftp = this.ftpOpenConnect(hostname, Integer.parseInt(port), username, password);
        return ftp;
    }

    /**
     * ftp 连接
     *
     * @param hostname
     * @param port
     * @param username
     * @param password
     * @return
     */
    public FTPClient ftpOpenConnect(String hostname, int port, String username, String password) {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        try {
            //连接FTP服务器
            ftpClient.connect(hostname, port);

            //ftpClient.enterLocalActiveMode();    //主动模式
            ftpClient.enterLocalPassiveMode();     //被动模式
            //登录FTP服务器
            ftpClient.login(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * 关闭ftp连接
     *
     * @param ftpClient
     * @return
     */
    public boolean ftpCloseConnect(FTPClient ftpClient) {
        boolean flag = false;
        try {
            if (ftpClient != null) {
                ftpClient.logout();
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                    flag = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


    /** */
    /**
     * 递归创建远程服务器目录
     *
     * @param remote    远程服务器文件绝对路径
     * @param ftpClient FTPClient对象
     * @return 目录创建是否成功
     * @throws IOException
     */
    public boolean CreateDirecroty(FTPClient ftpClient, String remotePath) throws IOException {
        boolean flag = false;
        String directory = remotePath.substring(0, remotePath.lastIndexOf("/") + 1);
        if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"), "UTF-8"))) {
            //如果远程目录不存在，则递归创建远程服务器目录   
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            ftpClient.changeWorkingDirectory("/");
            end = directory.indexOf("/", start);
            while (true) {
                String subDirectory = new String(remotePath.substring(start, end).getBytes("GBK"), "UTF-8");
                if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                    if (ftpClient.makeDirectory(subDirectory)) {
                        ftpClient.changeWorkingDirectory(subDirectory);
                    } else {
                        System.out.println("创建目录失败");
                        flag = false;
                    }
                } else {
                    ftpClient.changeWorkingDirectory(subDirectory);
                }
                start = end + 1;
                end = directory.indexOf("/", start);

                //检查所有目录是否创建完毕   
                if (end <= start) {
                    break;
                }
            }
        }
        flag = true;
        return flag;
    }

    /**
     * 上传文件（可供Action/Controller层使用）
     *
     * @param ftpClient   FTP服务器
     * @param pathname    FTP服务器保存目录
     * @param fileName    上传到FTP服务器后的文件名称
     * @param inputStream 输入文件流
     * @return
     */
    public boolean uploadFile(FTPClient ftpClient, String pathname, String fileName, InputStream inputStream) {
        boolean flag = false;
        //是否成功登录FTP服务器
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            return flag;
        }
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            //進入目录
            this.CreateDirecroty(ftpClient, pathname);
            ftpClient.changeWorkingDirectory(pathname);

            boolean s = ftpClient.storeFile(fileName, inputStream);
            System.out.println(s);
            inputStream.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 上传文件（可对文件进行重命名）
     *
     * @param hostname       FTP服务器地址
     * @param port           FTP服务器端口号
     * @param username       FTP登录帐号
     * @param password       FTP登录密码
     * @param pathname       FTP服务器保存目录
     * @param filename       上传到FTP服务器后的文件名称
     * @param originfilename 待上传文件的名称（绝对地址）
     * @return
     */
    public boolean uploadFileFromProduction(String hostname, int port, String username, String password, String pathname, String filename, String originfilename) {
        boolean flag = false;
        try {
            InputStream inputStream = new FileInputStream(new File(originfilename));
            FTPClient ftpClient = ftpOpenConnect(hostname, port, username, password);
            flag = uploadFile(ftpClient, pathname, filename, inputStream);
            ftpCloseConnect(ftpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 上传文件（不可以进行文件的重命名操作）
     *
     * @param hostname       FTP服务器地址
     * @param port           FTP服务器端口号
     * @param username       FTP登录帐号
     * @param password       FTP登录密码
     * @param pathname       FTP服务器保存目录
     * @param originfilename 待上传文件的名称（绝对地址）
     * @return
     */
    public boolean uploadFileFromProduction(String hostname, int port, String username, String password, String pathname, String originfilename) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            String fileName = new File(originfilename).getName();
            InputStream inputStream = new FileInputStream(new File(originfilename));
            ftpClient = ftpOpenConnect(hostname, port, username, password);
            flag = uploadFile(ftpClient, pathname, fileName, inputStream);
            ftpCloseConnect(ftpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 删除文件
     *
     * @param ftpClient FTP服务器地址
     * @param pathname  要删除的文件全路径
     * @return
     */
    public boolean deleteFile(FTPClient ftpClient, String pathname) {
        boolean flag = false;
        try {

            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }
            //切换FTP目录
            int r = ftpClient.dele(pathname);
            if (r == 250) {
                flag = true;
            }
            ftpCloseConnect(ftpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 下载文件
     *
     * @param hostname  FTP服务器地址
     * @param port      FTP服务器端口号
     * @param username  FTP登录帐号
     * @param password  FTP登录密码
     * @param pathname  FTP服务器文件目录
     * @param filename  文件名称
     * @param localpath 下载后的文件路径
     * @return
     */
    public boolean downloadFile(String hostname, int port, String username, String password, String pathname, String filename, String localpath) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            //连接FTP服务器
            ftpClient.connect(hostname, port);
            //登录FTP服务器
            ftpClient.login(username, password);
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localpath + "/" + file.getName());
                    OutputStream os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                } catch (IOException e) {

                }
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        FTPUtils ftpUtil = new FTPUtils();
        FTPClient ftpClient = ftpUtil.getFTPClient();
        try {

            File file = new File("C:\\Users\\13501\\Desktop\\weixin.jpg");
            InputStream inputStream = new FileInputStream(file);
            ftpUtil.uploadFile(ftpClient, "/app/app_img/imgfile/", "test.jpg", inputStream);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ftpUtil.ftpCloseConnect(ftpClient);
    }
}

 
