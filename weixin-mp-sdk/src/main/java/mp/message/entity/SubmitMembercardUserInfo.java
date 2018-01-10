package mp.message.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubmitMembercardUserInfo extends BaseMessage {
	@XmlElement(name = "CardId")
	private String cardId;
	@XmlElement(name = "UserCardCode")
	private String userCardCode;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUserCardCode() {
		return userCardCode;
	}

	public void setUserCardCode(String userCardCode) {
		this.userCardCode = userCardCode;
	}

	@Override
	public String toString() {
		return "SubmitMembercardUserInfo [getCardId()=" + getCardId() + ", getUserCardCode()=" + getUserCardCode()
				+ ", getToUserName()=" + getToUserName() + ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()=" + getMsgType() + ", getEvent()="
				+ getEvent() + "]";
	}
	
}
