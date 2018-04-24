package wang.moshu.message;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil implements InitializingBean
{
	/**
	 * 日志
	 */
	private static final Log logger = LogFactory.getLog(RedisUtil.class);

	/**
	 * REDIS连接池
	 */
	private volatile JedisPool pool;

	/**
	 * 最大连接数，如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个JEDIS实例，则此时pool的状态为exhausted(耗尽
	 * )。
	 */
	private Integer maxTotal;

	/**
	 * 控制一个pool最多有多少个状态为idle(空闲的)的JEDIS实例
	 */
	private Integer maxIdle;

	private Integer minIdle;

	/**
	 * 最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
	 */
	private Integer holed;

	/**
	 * redis服务端口
	 */
	private Integer port;

	/**
	 * redis服务地址
	 */
	private String host;

	/**
	 * redis连接超时时间
	 */
	private Integer timeout;

	/**
	 * redis连接密码
	 */
	private String password;

	/**
	 * 
	 */
	private Integer DB;

	/**
	 * 
	 */
	private String keyPrefix;

	public void setHoled(Integer holed)
	{
		this.holed = holed;
	}

	public void setPort(Integer port)
	{
		this.port = port;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public void setTimeout(Integer timeout)
	{
		this.timeout = timeout;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setDB(Integer dB)
	{
		DB = dB;
	}

	public String getKeyPrefix()
	{
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix)
	{
		this.keyPrefix = keyPrefix;
	}

	public void setMaxTotal(Integer maxTotal)
	{
		this.maxTotal = maxTotal;
	}

	public void setMaxIdle(Integer maxIdle)
	{
		this.maxIdle = maxIdle;
	}

	public void setMinIdle(Integer minIdle)
	{
		this.minIdle = minIdle;
	}

	/**
	 * 初始化redisUtil实例，配置连接池
	 */
	public void afterPropertiesSet() throws Exception
	{

		JedisPoolConfig config = new JedisPoolConfig();

		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);

		pool = new JedisPool(config, host, port, timeout, password, DB);

	}

	/**
	 * 从连接池获得一个redis连接
	 * 
	 * @return
	 */
	public Jedis getConnent()
	{
		Jedis jedis = pool.getResource();
		return jedis;
	}

	/**
	 * 关闭当前连接实例，将连接返回连接池
	 * 
	 * @param jedis redis连接实例
	 */
	private void close(Jedis jedis)
	{
		try
		{
			if (jedis != null)
			{
				jedis.close();
			}
		}
		catch (Exception e)
		{
			logger.error("close jedis failed!", e);
		}
	}

	/**
	 * 获取数据
	 *
	 * @param key
	 * @return
	 */
	public <T> T blpop(String key, int waitSeconds, Class<T> clazz)
	{
		Jedis jedis = null;

		try
		{
			jedis = getConnent();
			List<byte[]> values = jedis.brpop(waitSeconds, key.getBytes());

			if (values != null && values.size() > 0)
			{
				byte[] value = values.get(1);
				return ConvertUtil.unserialize(value, clazz);
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{
			logger.error("redis get data failed!", e);
			return null;
		}
		finally
		{
			close(jedis);
		}
	}

	/**
	 * 存储REDIS队列 顺序存储,可设置过期时间，过期时间以秒为单位
	 * 
	 * @param key reids键名
	 * @param value 键值
	 * @param second 过期时间(秒)
	 */
	public <T> Long rpush(String key, T value, int second)
	{
		Jedis jedis = null;
		Long ret = null;
		try
		{
			jedis = getConnent();
			byte[] bytes = ConvertUtil.serialize(value);
			ret = jedis.rpush(key.getBytes(), bytes);

			if (second > 0)
			{
				jedis.expire(key, second);
			}

		}
		catch (Exception e)
		{
			logger.error("redis lpush data failed , key = " + key, e);
		}
		finally
		{
			close(jedis);
		}

		return ret;
	}

	/**
	 * 获取存储REDIS队列长度
	 *
	 * @param key reids键名
	 */
	public Long llen(String key)
	{
		Jedis jedis = null;
		Long ret = null;
		try
		{
			jedis = getConnent();
			return jedis.llen(key);

		}
		catch (Exception e)
		{
			logger.error("redis llen data failed , key = " + key, e);
		}
		finally
		{
			close(jedis);
		}

		return ret;
	}
}
