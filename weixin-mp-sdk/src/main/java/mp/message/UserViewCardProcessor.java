package mp.message;

import mp.message.entity.UserViewCard;
import mp.util.XMLUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class UserViewCardProcessor extends MessageProcessor {
	private static final Log LOGGER = LogFactory.getLog(UserViewCardProcessor.class);

	@Override
	public String handle(String message) {
		LOGGER.info("开始处理用户查看会员卡事件");

		UserViewCard messageObject = XMLUtil.xmlToObject(UserViewCard.class, message);
		if (messageObject == null) {
			return null;
		}

		if (isHandled(messageObject.getFromUserName(), messageObject.getCreateTime())) {
			LOGGER.info("消息已处理：" + messageObject);
			return null;
		}

		return null;
	}

}
