package mp.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 微信推送消息处理器
 *
 */
public abstract class MessageProcessor {

	private static final Log LOGGER = LogFactory.getLog(MessageProcessor.class);

	/**
	 * 处理微信推送消息
	 * 
	 * @param message
	 * @return
	 */
	public abstract String handle(String message);

	// 微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次，所以设置超时时间为expireTime>3*5
	private static final int EXPIRE_TIME = 150;

	protected boolean isHandled(String fromUserName, String createTime) {
		String uniqueKey = fromUserName + createTime;
		LOGGER.info("Message uniqueKey:" + uniqueKey);
		return false;
	}
}
