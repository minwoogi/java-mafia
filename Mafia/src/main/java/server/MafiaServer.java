package server;

import java.util.Scanner;

import handling.lobby.Lobby;
import handling.lobby.WaitingRoom;
import handling.login.handler.LoginHandler;
import handling.netty.MafiaNettyDecoder;
import handling.netty.MafiaNettyEncoder;
import handling.netty.MafiaNettyHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import tools.Manager;

public class MafiaServer {
	private int port;
	public MafiaServer(int port) {
		this.port = port;
	}
	public static void main(String[] args) throws Exception {
		new MafiaServer(7777).run();
	}
	
	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) 
							throws Exception {
						ch.pipeline().addLast("decoder", new MafiaNettyDecoder());
						ch.pipeline().addLast("encoder", new MafiaNettyEncoder());
						ch.pipeline().addLast("handler", new MafiaNettyHandler());
					};
				}).option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture f = b.bind(port).sync();
			System.out.println(port + " 포트로 서버를 개방했습니다.");
			LoginHandler.disconnectAll();
			Manager.start();
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			
		}
	}
	
	public static void BroadCast(byte[] packet) {
		
	}

}
