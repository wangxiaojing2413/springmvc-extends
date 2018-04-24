package wang.moshu.message.demo.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import wang.moshu.message.AbstarctMessageHandler;
import wang.moshu.message.demo.DemoMessage;
import wang.moshu.message.demo.MessageType;
import wang.moshu.message.demo.servlet.ConsoleWebSocket;

/**
 * DemoMessage消息的处理器
 * 
 * @category @author xiangyong.ding@weimob.com
 * @since 2017年2月3日 下午9:21:41
 */
@Service
public class PerformanceHandler extends AbstarctMessageHandler<DemoMessage>
{
	private static Log logger = LogFactory.getLog(PerformanceHandler.class);

	public PerformanceHandler()
	{
		// 说明该handler监控的消息类型
		super(MessageType.PERFORMANCE_MESSAGE, DemoMessage.class);
	}

	/**
	 * 监听到消息后处理方法
	 */
	@Override
	public void handle(DemoMessage message)
	{
		// do handle
	}

	@Override
	public void handleFailed(DemoMessage obj)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("msg:[").append(obj).append("], 超过失败次数，停止重试。");
		logger.warn(sb.toString());

		Assert.notNull(ConsoleWebSocket.socket);
		ConsoleWebSocket.socket.sendMessage(sb.toString());
	}

}
