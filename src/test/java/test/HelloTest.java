package test;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class HelloTest {

    @Rule
    public GenericContainer<?> container = new GenericContainer<>("redis:5.0.8").withExposedPorts(6379);

    @Test
    public void test() {
        String host = container.getContainerIpAddress();
        int port = container.getFirstMappedPort();

        RedisURI uri = RedisURI.create(host, port);
        RedisClient client = RedisClient.create(uri);
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            String result = connection.sync().set("message", "Hello Redis on TestContainers!");
            assertThat(result).isEqualTo("OK");

            String message = connection.sync().get("message");
            assertThat(message).isEqualTo("Hello Redis on TestContainers!");
        } finally {
            client.shutdown();
        }
    }
}