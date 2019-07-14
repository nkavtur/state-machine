package home.nkavtur.distributedstatemachine.zookeeper;

/**
 * Provides crud api over zookeeper nodes.
 */
public interface ZookeeperOperations {

    void set(String data, String path);

    byte[] get(String path);

}
