package home.nkavtur.distributedstatemachine.zookeeper;

import org.apache.curator.framework.CuratorFramework;

public class ZookeeperTemplate implements ZookeeperOperations {

    private final CuratorFramework client;

    public ZookeeperTemplate(CuratorFramework client) {
        this.client = client;
    }

    public void set(String data, String path) {
        try {
            if (client.checkExists().forPath(path) == null) {
                client.create().creatingParentsIfNeeded().forPath(path);
            }
            client.setData().forPath(path, data.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] get(String path) {
        try {
            if (client.checkExists().forPath(path) != null) {
                return client.getData().forPath(path);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
