package mp.message;

import mp.WxMpConfig;
import mp.WxMpService;
import mp.message.entity.MenuClick;
import mp.util.XMLUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Component
public class MenuClickProcessor extends MessageProcessor {
	private static final Log LOGGER = LogFactory.getLog(MenuClickProcessor.class);

	private static final String SHOP_DETIAL_MEDIA_ID;
	private static String CONTACT_US_CONTENT;

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("weixin", Locale.getDefault());
		SHOP_DETIAL_MEDIA_ID = bundle.getString("weixin.mp.shop_detial.media_id");
		try {
			CONTACT_US_CONTENT = new String(bundle.getString("weixin.mp.contact_us.content").getBytes("ISO-8859-1"),
					"UTF-8");
		} catch (UnsupportedEncodingException e) {
			CONTACT_US_CONTENT = "";
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public String handle(String message) {
		LOGGER.info("开始处理用户点击菜单事件...");

		MenuClick messageObject = XMLUtil.xmlToObject(MenuClick.class, message);
		if (messageObject == null||StringUtils.isEmpty(messageObject.getEventKey())) {
			return "";
		}

		if (isHandled(messageObject.getFromUserName(), messageObject.getCreateTime())) {
			LOGGER.info("消息已处理：" + messageObject);
			return "";
		}

		Eventkey event = Eventkey.fromName(messageObject.getEventKey());
		if (event == Eventkey.CLICK_001) {
			receiveMembershipCard(messageObject);
		} else if (event == Eventkey.CLICK_002) {
			showShopDetail(messageObject);
		} else if (event == Eventkey.CLICK_003) {
			contactUs(messageObject);
		}

		return "";
	}

	public void receiveMembershipCard(MenuClick messageObject) {
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("card_id", WxMpConfig.CARD_ID);
		WxMpService.messageCustomSend(messageObject.getFromUserName(), "wxcard", message);
	}

	public void showShopDetail(MenuClick messageObject) {
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("media_id", SHOP_DETIAL_MEDIA_ID);
		WxMpService.messageCustomSend(messageObject.getFromUserName(), "image", message);
	}

	public void contactUs(MenuClick messageObject) {
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("content", CONTACT_US_CONTENT);
		WxMpService.messageCustomSend(messageObject.getFromUserName(), "text", message);
	}

	public static enum Eventkey {
		CLICK_001("CLICK_001", "会员尊享》会员卡"), CLICK_002("CLICK_002", "加盟酒仙》店铺详情"), CLICK_003("CLICK_003", "加盟酒仙》联系我们");

		private String name;
		private String value;

		private Eventkey(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public static Eventkey fromName(String name) {
			for (Eventkey item : Eventkey.values()) {
				if (item.getName().equalsIgnoreCase(name)) {
					return item;
				}
			}
			return null;
		}
	}
}
