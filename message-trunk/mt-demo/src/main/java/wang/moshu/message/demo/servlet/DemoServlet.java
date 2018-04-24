package wang.moshu.message.demo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import wang.moshu.message.Message;
import wang.moshu.message.MessageTrunk;
import wang.moshu.message.demo.DemoMessage;
import wang.moshu.message.demo.MessageType;
import wang.moshu.message.demo.SpringBeanUtils;

public class DemoServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public DemoServlet()
	{

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		// 取HTTP参数
		String value = request.getParameter("message");

		if (StringUtils.isEmpty(value))
		{
			response.getOutputStream().print("message cannot be empty.");
			return;
		}

		// 获取mt实例
		MessageTrunk mt = (MessageTrunk) SpringBeanUtils.getBean("messageTrunk");

		Message message = new Message(MessageType.DEMO_MESSAGE, new DemoMessage(value, "111121333", "addsddssd"));

		ConsoleWebSocket.socket.sendMessage("开始插入消息到MT");
		// 消息入MT
		long startTime = System.currentTimeMillis();
		mt.put(message);

		ConsoleWebSocket.socket.sendMessage("结束插入消息到MT,一共耗时:" + (System.currentTimeMillis() - startTime));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
