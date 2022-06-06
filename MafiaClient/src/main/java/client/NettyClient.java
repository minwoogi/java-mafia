package client;

import handling.netty.ClientHandler;
import handling.netty.MafiaNettyDecoder;
import handling.netty.MafiaNettyEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import ui.LoginFrame;

/**
 * class for server connection
 * 서버 연결을 위한 클래스
 */

public class NettyClient {

	private final static int PORT = 7777;
	private final static String HOST = "localhost";  

	public NettyClient() {
		try {
			this.run();
		} catch (Exception e) {
			System.out.println("[run error]");
			e.printStackTrace();
		}
	}

	public void run() throws Exception {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("decoder", new MafiaNettyDecoder());
					ch.pipeline().addLast("encoder", new MafiaNettyEncoder());
					ch.pipeline().addLast("handler", new ClientHandler());
				}
			});
			ChannelFuture f = b.connect(HOST, PORT).sync();
			new LoginFrame();
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
