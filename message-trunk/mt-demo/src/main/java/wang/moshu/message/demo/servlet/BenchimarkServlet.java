package wang.moshu.message.demo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wang.moshu.message.Message;
import wang.moshu.message.MessageTrunk;
import wang.moshu.message.demo.DemoMessage;
import wang.moshu.message.demo.MessageType;
import wang.moshu.message.demo.SpringBeanUtils;

/**
 * 性能测试servlet
 * 
 * @category @author xiangyong.ding@weimob.com
 * @since 2017年3月28日 下午10:21:29
 */
public class BenchimarkServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private static final int TOTAL_OPERATIONS = 100000;

	public BenchimarkServlet()
	{

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// 获取mt实例
		final MessageTrunk mt = (MessageTrunk) SpringBeanUtils.getBean("messageTrunk");

		final Message message = new Message(MessageType.DEMO_MESSAGE, new DemoMessage("hello mt"));

		ConsoleWebSocket.socket.sendMessage("开始插入消息到MT");
		// 消息入MT
		long startTime = System.currentTimeMillis();

		List<Thread> tds = new ArrayList<Thread>();

		// 模拟50个线程同时put消息，一共操作10W次
		final AtomicInteger ind = new AtomicInteger();
		for (int i = 0; i < 50; i++)
		{
			Thread hj = new Thread(new Runnable()
			{
				public void run()
				{
					while (ind.getAndIncrement() < TOTAL_OPERATIONS)
					{
						mt.put(message);
					}
				}
			});
			tds.add(hj);
			hj.start();
		}

		try
		{
			for (Thread t : tds)
				t.join();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		long elapsed = System.currentTimeMillis() - startTime;
		ConsoleWebSocket.socket.sendMessage(((1000 * TOTAL_OPERATIONS) / elapsed) + " ops");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
