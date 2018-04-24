package wang.moshu.message.demo.servlet;

import wang.moshu.message.MessageMonitor;
import wang.moshu.message.demo.MessageType;
import wang.moshu.message.demo.SpringBeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 监听servlet
 */
public class MonitorServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public MonitorServlet()
	{

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        // 获取mt实例
        MessageMonitor messageMonitor = (MessageMonitor) SpringBeanUtils.getBean("messageMonitor");
        ConsoleWebSocket.socket.sendMessage("待处理消息个数：" + messageMonitor.getMessageLeft(MessageType.DEMO_MESSAGE));

		//response.getOutputStream().print("message gas been saved to MessageTrunk, cost "+(System.currentTimeMillis()-startTime)+"ms. See result in console.");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
