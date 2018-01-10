package mp.message;

import mp.WxMpService;
import mp.message.entity.TextMessage;
import mp.util.XMLUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserTextMessageProcessor extends MessageProcessor {
    private static final Log LOGGER = LogFactory.getLog(UserTextMessageProcessor.class);

    @Override
    public String handle(String message) {
        LOGGER.info("开始处理用户文本消息...");

        TextMessage messageObject = XMLUtil.xmlToObject(TextMessage.class, message);
        if (messageObject == null || StringUtils.isEmpty(messageObject.getContent())) {
            return "";
        }

        if (isHandled(messageObject.getFromUserName(), messageObject.getCreateTime())) {
            LOGGER.info("消息已处理：" + messageObject);
            return "";
        }

        MessageReplyConfig messageReply = new MessageReplyConfig();

        WxMpService.messageCustomSend(messageObject.getFromUserName(), messageReply.msgtype, messageReply.body);

        return "";
    }

    public static class MessageReplyConfig {
        public String msgtype;
        public Map<String, Object> body;
    }

}
