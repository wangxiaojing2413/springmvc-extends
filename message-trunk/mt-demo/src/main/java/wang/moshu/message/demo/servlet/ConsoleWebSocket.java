package wang.moshu.message.demo.servlet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 将控制台信息输出到前端的websocket
 * 
 * @category @author xiangyong.ding@weimob.com
 * @since 2017年3月10日 上午11:54:10
 */
// 该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
@ServerEndpoint("/websocket")
public class ConsoleWebSocket
{
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	public static ConsoleWebSocket socket;

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session)
	{
		this.session = session;
		socket = this;
		sendMessage("连接控制台成功");
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose()
	{
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session)
	{
		// System.out.println("来自客户端的消息:" + message);
		//
		// // 群发消息
		// for (ConsoleWebSocket item : webSocketSet)
		// {
		// try
		// {
		// item.sendMessage(message);
		// }
		// catch (IOException e)
		// {
		// e.printStackTrace();
		// continue;
		// }
		// }
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error)
	{
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message)
	{
		try
		{
			this.session.getBasicRemote().sendText(message);
		}
		catch (Exception e)
		{
			System.out.println("发送消息失败");
		}
	}

}