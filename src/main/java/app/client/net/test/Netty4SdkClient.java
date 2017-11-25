package app.client.net.test;

import app.client.net.dispacher.DispacherManager;
import app.client.net.socket.*;
import app.client.net.task.TaskManager;
import com.google.common.annotations.VisibleForTesting;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.swagger.annotations.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author syriana.zh
 */
public class Netty4SdkClient implements Closeable{

    private static final Logger logger = LoggerFactory
            .getLogger(Netty4SdkClient.class);

    private static final int DEFAULT_MSG_SIZE_LIMIT = 1200;

    private final int port = 6130;
    private final String host = "127.0.0.1";


    public void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.remoteAddress(new InetSocketAddress(host, port));
            b.handler(new ChannelInitializer<SocketChannel>() {

                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new GowildEncoder());
                    ch.pipeline().addLast(new GowildDecoder());
                    ch.pipeline().addLast(new GowildHandler());

                }
            });
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    @Override
    public void close(){
    }

    public void init() throws Exception{
        // 初始化协议原型加载
        Class.forName("app.client.net.protocol.ProtocolFactory");
        // 初始化协议加载
        Class.forName("app.client.net.dispacher.DispacherManager");
        // 初始化分发器
        DispacherManager.getInstance().init();
        // 初始化线程池
        TaskManager.getInstance().init();
    }

    public static void main(String[] args) throws Exception {
        Netty4SdkClient client = new Netty4SdkClient();
        client.init();
        client.start();
    }
}