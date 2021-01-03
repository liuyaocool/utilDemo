package com.liuyao.demo.test.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZkApi {

    public static void main(String[] args) throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ZooKeeper zk = new ZooKeeper("192.168.1.161:2181,192.168.1.162:2181,192.168.1.163:2181",
                3000, (e) -> {
            Watcher.Event.KeeperState state = e.getState();
            Watcher.Event.EventType type = e.getType();
            String path = e.getPath();
            System.out.println(e.toString());
            switch (state) {
                case SyncConnected:
                    latch.countDown();

            }

            switch (type) {

            }

        });
        latch.await();
        ZooKeeper.States zkState = zk.getState();
        switch (zkState){
            case CONNECTED:
        }
//        zk.create()
    }
}
