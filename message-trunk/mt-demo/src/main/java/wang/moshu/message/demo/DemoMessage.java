package wang.moshu.message.demo;

import java.io.Serializable;

/**
 * 例子消息
 * 
 * @category @author xiangyong.ding@weimob.com
 * @since 2017年2月3日 下午9:17:27
 */
public class DemoMessage implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5016244951421710684L;

	private String value;

	/**
	 * 手机号，标识用户唯一身份
	 */
	private String mobile;

	/**
	 * 秒杀商品编号
	 */
	private String goodsRandomName;

	public DemoMessage()
	{
		super();
	}

	public DemoMessage(String value)
	{
		super();
		this.value = value;
	}

	public DemoMessage(String value, String mobile, String goodsRandomName)
	{
		super();
		this.value = value;
		this.mobile = mobile;
		this.goodsRandomName = goodsRandomName;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getGoodsRandomName()
	{
		return goodsRandomName;
	}

	public void setGoodsRandomName(String goodsRandomName)
	{
		this.goodsRandomName = goodsRandomName;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("DemoMessage [value=");
		builder.append(value);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", goodsRandomName=");
		builder.append(goodsRandomName);
		builder.append("]");
		return builder.toString();
	}

}
