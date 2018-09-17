package zookeeper.lock.zkclient;

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.TimeUnit;

public class TestDistributedLock {

    public static void main(String[] args) throws InterruptedException {

        final ZkClient zkClientExt1 = new ZkClient("127.0.0.1:12181,127.0.0.1:12182,127.0.0.1:12183", 5000, 5000,
            new BytesPushThroughSerializer());
        final BaseDistributedLock mutex1 = new BaseDistributedLock(zkClientExt1, "/locks", "Mutex");

        zkClientExt1.subscribeStateChanges(new IZkStateListener() {

            ////当我重新启动后start，监听触发
            @Override public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
                if (state == Watcher.Event.KeeperState.SyncConnected) {
                    //当我重新启动后start，监听触发
                    System.out.println("连接成功");
                }
                else if (state == Watcher.Event.KeeperState.Disconnected) {
                    System.out.println("连接断开");//当我在服务端将zk服务stop时，监听触发
                }
                else
                    System.out.println("其他状态" + state);
            }

            /**
             * Called after the zookeeper session has expired and a new session has been created. You would have to re-create
             * any ephemeral nodes here.
             *
             * @throws Exception On any error.
             */
            @Override public void handleNewSession() throws Exception {
                System.out.println("=========handleNewSession");
            }

            /**
             * Called when a session cannot be re-established. This should be used to implement connection
             * failure handling e.g. retry to connect or pass the error up
             *
             * @param error The error that prevents a session from being established
             * @throws Exception On any error.
             */
            @Override public void handleSessionEstablishmentError(Throwable error) throws Exception {
                System.out.println("=========handleSessionEstablishmentError" + error);
            }
        });


        final ZkClient zkClientExt2 = new ZkClient("127.0.0.1:12181,127.0.0.1:12182,127.0.0.1:12183", 5000, 5000,
            new BytesPushThroughSerializer());
        final BaseDistributedLock mutex2 = new BaseDistributedLock(zkClientExt2, "/locks", "Mutex");

        try {
            String lockPath = mutex1.attemptLock(1000, TimeUnit.MILLISECONDS);
            System.out.println("Client1 locked " + lockPath);
            Thread client2Thd = new Thread(new Runnable() {

                public void run() {
                    try {
                        String lockPath = mutex2.attemptLock(1000, TimeUnit.MILLISECONDS);
                        System.out.println("Client2 locked " + lockPath);
                        mutex2.releaseLock();
                        System.out.println("Client2 released lock");

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client2Thd.start();
            Thread.sleep(5000);
            mutex1.releaseLock();
            System.out.println("Client1 released lock");

            client2Thd.join();

        }
        catch (Exception e) {

            e.printStackTrace();
        }

    }

}