package wang.moshu.message;/**
 * Created by dingxiangyong on 2017/3/12.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息监听器
 * @auth xiangyong.ding
 * 2017/3/12 21:28
 **/
@Service
public class MessageMonitor {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取待处理消息个数
     * @param messageType
     * @return
     */
    public int getMessageLeft(String messageType){
        return redisUtil.llen(messageType).intValue();
    }
}
