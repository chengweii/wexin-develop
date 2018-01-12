package mp.message;

import mp.WxMpService;
import mp.message.entity.MenuClick;
import mp.util.XMLUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SubscribeProcessor extends MessageProcessor {
	private static final Log LOGGER = LogFactory.getLog(SubscribeProcessor.class);
	
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

		Map<String, Object> reply = new HashMap<String, Object>();
		reply.put("content", "");
		WxMpService.messageCustomSend(messageObject.getFromUserName(), "text", reply);

		return "";
	}
}
