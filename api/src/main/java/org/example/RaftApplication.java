package org.example;

import org.example.core.net.client.BoltClient;
import org.example.core.net.server.BoltServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 * @author haifeng, pang
 */
public class RaftApplication {
    private static Logger logger = LoggerFactory.getLogger(RaftApplication.class);

    private static int port = 11111;

    private static String address = "127.0.0.1:11111";

    private static BoltServer boltServer;

    private static BoltClient boltClient;


    public static void main(String[] args) {

        boltServer = new BoltServer(port);
        boltServer.startup();
        boltClient = new BoltClient(address);
        boltClient.startup();
        logger.info("启动成功");
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                boltClient.shutdown();
                boltServer.shutdown();
            }
        }));
    }
}
