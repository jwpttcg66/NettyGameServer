package com.snowcattle.game.common.zookeeper.zkclient;

import org.apache.zookeeper.*;

/**
 * Created by jiangwenping on 17/3/30.
 */
public class Test {
    /**
     * 会话超时时间，设置为系统默认时间
     */
    private static final int Session_timeout = 30 * 1000;
    /**
     * zookeeper实例
     */
    private ZooKeeper zk;

    private Watcher wh = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            System.out.println("WatchedEvent >>>" + event.toString());
        }
    };

    private void createZkServie() throws Exception{
        String host = "127.0.0.1:2181:127.0.0.1:2181";
        String singleHost = "127.0.0.1";
        zk = new ZooKeeper(singleHost, Test.Session_timeout,this.wh);
    }

    public void operations() throws Exception{
        System.out.println("\n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
        zk.create("/zoo2", "myData2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("\n2. 查看是否创建成功： ");
        System.out.println(new String(zk.getData("/zoo2", this.wh, null)));// 添加Watch

        // 前面一行我们添加了对/zoo2节点的监视，所以这里对/zoo2进行修改的时候，会触发Watch事件。
        System.out.println("\n3. 修改节点数据 ");
        zk.setData("/zoo2", "shanhy20160310".getBytes(), -1);

        // 这里再次进行修改，则不会触发Watch事件，这就是我们验证ZK的一个特性“一次性触发”，也就是说设置一次监视，只会对下次操作起一次作用。
        System.out.println("\n3-1. 再次修改节点数据 ");
        zk.setData("/zoo2", "shanhy20160310-ABCD".getBytes(), -1);

        System.out.println("\n4. 查看是否修改成功： ");
        System.out.println(new String(zk.getData("/zoo2", false, null)));

        System.out.println("\n5. 删除节点 ");
        zk.delete("/zoo2", -1);

        System.out.println("\n6. 查看节点是否被删除： ");
        System.out.println(" 节点状态： [" + zk.exists("/zoo2", false) + "]");
    }

    private void close() throws Exception{
        zk.close();
    }

    public static void main(String[] args) throws  Exception{
        Test test = new Test();
        test.createZkServie();;
        test.operations();
        test.close();
    }
}

