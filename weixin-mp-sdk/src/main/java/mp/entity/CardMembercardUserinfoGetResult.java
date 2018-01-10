package mp.entity;

import java.util.List;

public class CardMembercardUserinfoGetResult {
	public String errcode;
	public String errmsg;
	public String openid;
	public String nickname;
	public String membership_number;
	public String bonus;
	public String balance;
	public String sex;
	public UserInfo user_info;
	public String user_card_status;

	public static class UserInfo {
		public List<Field> common_field_list;
		public List<Field> custom_field_list;

		public static class Field {
			public String name;
			public String value;

			@Override
			public String toString() {
				return "Field [name=" + name + ", value=" + value + "]";
			}
		}

		@Override
		public String toString() {
			return "UserInfo [common_field_list=" + common_field_list + ", custom_field_list=" + custom_field_list
					+ "]";
		}

	}

	@Override
	public String toString() {
		return "CardMembercardUserinfoGetResult [errcode=" + errcode + ", errmsg=" + errmsg + ", openid=" + openid
				+ ", nickname=" + nickname + ", membership_number=" + membership_number + ", bonus=" + bonus
				+ ", balance=" + balance + ", sex=" + sex + ", user_info=" + user_info + ", user_card_status="
				+ user_card_status + "]";
	}
}
