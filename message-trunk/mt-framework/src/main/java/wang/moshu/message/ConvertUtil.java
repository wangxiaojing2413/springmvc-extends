package wang.moshu.message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class ConvertUtil
{

	private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	private static <T> Schema<T> getSchema(Class<T> cls)
	{
		Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
		if (schema == null)
		{
			schema = RuntimeSchema.createFrom(cls);
			if (schema != null)
			{
				cachedSchema.put(cls, schema);
			}
		}
		return schema;
	}

	@SuppressWarnings("unchecked")
	public static <T> byte[] serialize(T obj)
	{
		Class<T> cls = (Class<T>) obj.getClass();
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try
		{
			Schema<T> schema = getSchema(cls);
			return ProtobufIOUtil.toByteArray(obj, schema, buffer);
		}
		catch (Exception e)
		{
			throw new IllegalStateException(e.getMessage(), e);
		}
		finally
		{
			buffer.clear();
		}
	}

	public static <T> T unserialize(byte[] data, Class<T> cls)
	{
		try
		{
			T message = cls.newInstance();
			Schema<T> schema = getSchema(cls);
			ProtobufIOUtil.mergeFrom(data, message, schema);
			return message;
		}
		catch (Exception e)
		{
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

}
