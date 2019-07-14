package home.nkavtur.distributedstatemachine.app.config;

import home.nkavtur.distributedstatemachine.zookeeper.ZookeeperOperations;
import home.nkavtur.distributedstatemachine.zookeeper.ZookeeperTemplate;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {

    @Bean
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client.start();
        return client;
    }

    @Bean
    public ZookeeperOperations zookeeperOperations(CuratorFramework client) {
        return new ZookeeperTemplate(client);
    }
}
