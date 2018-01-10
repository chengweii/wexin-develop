package mp.entity;

public class CardCodeConsumeResult {
	public String errcode;
	public String errmsg;
	public String openid;
	public Card card;

	public static class Card {
		public String card_id;

		@Override
		public String toString() {
			return "Card [card_id=" + card_id + "]";
		}
	}

	@Override
	public String toString() {
		return "CardCodeConsumeResult [errcode=" + errcode + ", errmsg=" + errmsg + ", openid=" + openid + ", card="
				+ card + "]";
	}
}
