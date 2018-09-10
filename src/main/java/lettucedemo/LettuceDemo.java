package lettucedemo;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.output.NestedMultiOutput;
import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

import javax.naming.directory.SearchResult;
import java.util.List;

/**
 * @author huishen
 * @date 2018/9/3 下午8:08
 *
 * Lettuce连接redis
 *
 * Lettuce是基于Netty开发的，
 * 提供了 同步 异步 两种调用方法，
 * 支持Pipeline，
 * 支持Redis Sentinel和Cluster
 * 支持自定义指令
 * 只支持Java8+
 * 线程安全
 *
 */
public class LettuceDemo {


    /**
     * 同步指令
     */
    @Test
    public void testSync() {
        RedisClient client = RedisClient.create("redis://localhost:6379/0");
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisCommands<String, String> commands = connection.sync();
        commands.set("key1", "value1");
        System.out.println(commands.get("key1"));
        connection.close();
        client.shutdown();
    }

    /**
     * 异步用法
     */
    @Test
    public void testAsync() {
        RedisClient client = RedisClient.create("redis://localhost:6379/0");
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisAsyncCommands<String, String> commands = connection.async();
        RedisFuture<String> future1 = commands.get("key1");
        future1.whenComplete((value, e) -> {
            if (null == e){
                System.out.println(value);
            }
        });

        RedisFuture<Long> saddFuture = commands.sadd("testSet", "setValue1");
        saddFuture.whenComplete((ret, e) -> {
            if (null  == e) {
                System.out.println(ret);
            }
        });

        RedisFuture<Long> future2 = commands.scard("testSet");
        future2.whenComplete((value, e) -> {
            if (null == e) {
                System.out.println(value);
            }
        });

        try {
            // 等一会，确保结果在连接关闭之前都拿到了
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connection.close();
        client.shutdown();
    }

    @Test
    public void testPool() throws Exception {
        RedisClient client = RedisClient.create("redis://localhost:6379/0");
        GenericObjectPool<StatefulRedisConnection<String, String>> pool = ConnectionPoolSupport
            .createGenericObjectPool(() -> client.connect(), new GenericObjectPoolConfig());
        try (StatefulRedisConnection<String, String> connection = pool.borrowObject()) {
            RedisCommands<String, String> commands = connection.sync();
            commands.set("dream-codehole", "Yui Aragaki");
            System.out.println(commands.get("dream-codehole"));
            commands.zadd("dreams-codehole", 10, "Yui Aragaki");
            commands.zadd("dreams-codehole", 8, "Elane Zhong");
            System.out.println(commands.zcard("dreams-codehole"));
        }
        pool.close();
        client.shutdown();
    }

    /**
     * Lettuce提供了自定义指令接口。实现自定义指令需要提供3个东西：指令名称，参数，结果解码器
     */
    @Test
    public void testCustomCommand() {
        RedisURI redisURI = RedisURI.Builder.redis("cqaso-redis", 6678).withPassword("cqaso@chuangqi").withDatabase(0).build();
        RedisClient client = RedisClient.create(redisURI);
        RedisCodec<String, String> codec = StringCodec.UTF8;

        StatefulRedisConnection<String, String> connection = client.connect();
        RedisCommands<String, String> commands = connection.sync();
        CommandArgs<String, String> args = new CommandArgs<>(codec);
        args
            .add("AppDown")
            // .add("@name:hello@genreId:36")
            .add("@country:CN@date:[20180828,20180828]")
            .add("LANGUAGE").add("chinese")
            .add("SORTBY").add("appId").add("DESC")
            .add("LIMIT").add(0).add(20);
        List<Object> list = (List<Object>) commands.dispatch(new RediSearchCommands.SingleNodeCommands().getSearchCommand(), new NestedMultiOutput<>(codec), args);
        RediSearchResult sr = new RediSearchResult(list, true, false, false);

    }

}
