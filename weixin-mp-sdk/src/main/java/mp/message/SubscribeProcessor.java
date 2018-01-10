package mp.message;

import com.jiuxian.service.sys.SystemService;
import com.jiuxian.weixin.mp.WxMpService;
import com.jiuxian.weixin.mp.message.entity.MenuClick;
import com.jiuxian.weixin.mp.util.XMLUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SubscribeProcessor extends MessageProcessor {
	private static final Log LOGGER = LogFactory.getLog(SubscribeProcessor.class);

	@Autowired
	private SystemService systemService;
	
	private static final String SUBSCRIBE_AUTOMATIC_REPLY_KEY = "weixin_subscribe_automatic_reply";

	@Override
	public String handle(String message) {
		LOGGER.info("开始处理用户订阅公众号事件...");

		MenuClick messageObject = XMLUtil.xmlToObject(MenuClick.class, message);
		if (messageObject == null) {
			return "";
		}

		if (isHandled(messageObject.getFromUserName(), messageObject.getCreateTime())) {
			LOGGER.info("消息已处理：" + messageObject);
			return "";
		}

		String subscribeAutomaticReply = systemService.selectConfig(SUBSCRIBE_AUTOMATIC_REPLY_KEY);
		Map<String, Object> reply = new HashMap<String, Object>();
		reply.put("content", subscribeAutomaticReply);
		WxMpService.messageCustomSend(messageObject.getFromUserName(), "text", reply);

		return "";
	}
}
