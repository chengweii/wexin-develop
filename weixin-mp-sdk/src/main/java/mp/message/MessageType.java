package mp.message;

public enum MessageType {
	DEFAULT("default", DefaultProcessor.class),
	SUBMIT_MEMBERCARD_USER_INFO("submit_membercard_user_info", SubmitMembercardUserInfoProcessor.class),
	USER_VIEW_CARD("user_view_card", UserViewCardProcessor.class),
	MENU_CLICK("CLICK", MenuClickProcessor.class),
	SUBSCRIBE("subscribe", SubscribeProcessor.class),
	MENU_VIEW("VIEW", MenuClickProcessor.class),
	USER_MESSAGE("text", UserTextMessageProcessor.class);

	private String name;
	private Class<?> value;

	private MessageType(String name, Class<?> value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getValue() {
		return value;
	}

	public void setValue(Class<?> value) {
		this.value = value;
	}

	public static Class<?> fromName(String name) {
		for (MessageType type : MessageType.values()) {
			if (type.getName().equalsIgnoreCase(name)) {
				return type.getValue();
			}
		}
		return DEFAULT.getValue();
	}
}
