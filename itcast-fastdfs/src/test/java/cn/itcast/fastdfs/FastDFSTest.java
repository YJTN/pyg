package cn.itcast.fastdfs;

import org.csource.fastdfs.*;
import org.junit.Test;

public class FastDFSTest {

    @Test
    public void test() throws Exception{
        //1. 配置追踪服器信息，设置全局的配置；
        String conf_filename = ClassLoader.getSystemResource("fastdfs/tracker.conf").getPath().toString();
        System.out.println(conf_filename);
        ClientGlobal.init(conf_filename);

        //创建追踪客户端对象
        TrackerClient trackerClient = new TrackerClient();
        //创建追踪服务器对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //创建存储服务器对象
        StorageServer storageServer = null;
        //2. 利用StorageClient对象上传图片，返回图片地址信息；
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        /**
         * 上传文件
         * 参数1：文件路径
         * 参数2：文件扩展名（后缀）
         * 参数3：文件信息
         * 上传之后的返回内容：
         * group1 组名
         * M00/00/00/wKgMqFxNMTOAW4GOAABw0se6LsY242.jpg 文件相对路径
         */
        String[] upload_file = storageClient.upload_file("D:\\itcast\\pics\\575968fcN2faf4aa4.jpg", "jpg", null);
        if (upload_file != null && upload_file.length > 0) {
            for (String str : upload_file) {
                System.out.println(str);
            }

            //3. 拼接正确的图片访问地址并访问
            //获取存储服务器地址
            String groupName = upload_file[0];
            String filename = upload_file[1];
            ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer, groupName, filename);
            for (ServerInfo serverInfo : serverInfos) {
                System.out.println("ip = " + serverInfo.getIpAddr() + "；port=" + serverInfo.getPort());
            }

            //图片可访问地址
            String url = "http://" + serverInfos[0].getIpAddr() + "/" + groupName + "/" + filename;

            System.out.println(url);
        }
    }
}
