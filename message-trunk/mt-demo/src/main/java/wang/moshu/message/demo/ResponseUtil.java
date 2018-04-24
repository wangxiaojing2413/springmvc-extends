package wang.moshu.message.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

public class ResponseUtil
{
	/**
	 * 默认字符编码集
	 */
	private static final String CHARSET = "UTF-8";

	/**
	 * 向请求发起方输出字符串
	 * 
	 * @param response HttpServletResponse
	 * @param list 输出的字符列表
	 */
	public static void write(HttpServletResponse response, String[] list)
	{
		write(response, null, CHARSET, list);
	}

	/**
	 * 向请求发起方输出字符串
	 * 
	 * @param response HttpServletResponse
	 * @param header 响应头属性
	 * @param charSet 字符编码集
	 * @param list 输出的字符列表
	 */
	public static void write(HttpServletResponse response, Map<String, String> header, String charSet, String[] list)
	{
		if (null != header && header.isEmpty())
		{
			for (Entry<String, String> et : header.entrySet())
			{
				response.setHeader(et.getKey(), et.getValue());
			}
		}
		else
		{
			response.setHeader("content-type", "text/html;charset=UTF-8");
		}

		if (StringUtils.isEmpty(charSet))
		{
			charSet = CHARSET;
		}

		response.setCharacterEncoding(charSet);

		PrintWriter out = null;
		try
		{
			out = response.getWriter();

			if (null != list)
			{
				for (String str : list)
				{
					out.println(str);
				}
			}
		}
		catch (IOException e)
		{
			//
		}
		finally
		{

		}
	}

}
