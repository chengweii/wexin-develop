package mp.message;

import mp.message.entity.SubmitMembercardUserInfo;
import mp.util.XMLUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class SubmitMembercardUserInfoProcessor extends MessageProcessor {
	private static final Log LOGGER = LogFactory.getLog(SubmitMembercardUserInfoProcessor.class);

	@Override
	public String handle(String message) {
		return null;
	}

	private String bindWeixinCard(String message) {
		LOGGER.info("Prepare to bind user_card_code...");
		SubmitMembercardUserInfo messageObject = XMLUtil.xmlToObject(SubmitMembercardUserInfo.class, message);
		if (messageObject == null) {
			return null;
		}

		if (isHandled(messageObject.getFromUserName(), messageObject.getCreateTime())) {
			LOGGER.info("Message has been processed:" + messageObject);
			return null;
		}

		return null;
	}
}
