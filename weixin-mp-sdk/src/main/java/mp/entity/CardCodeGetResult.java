package mp.entity;

public class CardCodeGetResult {
	public String errcode;
	public String errmsg;
	public String openid;
	public Boolean can_consume;
	public String outer_str;
	public String user_card_status;
	public CardInfo card;

	public static class CardInfo {
		public String card_id;
		public Long begin_time;
		public Long end_time;

		@Override
		public String toString() {
			return "CardInfo [card_id=" + card_id + ", begin_time=" + begin_time + ", end_time=" + end_time + "]";
		}
	}

	@Override
	public String toString() {
		return "CardCodeInfo [errcode=" + errcode + ", errmsg=" + errmsg + ", openid=" + openid + ", can_consume="
				+ can_consume + ", outer_str=" + outer_str + ", user_card_status=" + user_card_status + ", card=" + card
				+ "]";
	}

}
