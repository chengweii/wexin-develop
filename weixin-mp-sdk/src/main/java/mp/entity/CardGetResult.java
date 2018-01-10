package mp.entity;

import java.util.List;

public class CardGetResult {
	public String errcode;
	public String errmsg;
	public CardInfo card;

	public static class CardInfo {
		public String card_type;
		public GeneralCoupon general_coupon;

		public static class GeneralCoupon {
			public BaseInfo base_info;
			public String default_detail;
			public AdvancedInfo advanced_info;

			public static class AdvancedInfo {
				public Boolean share_friends;
				public UseCondition use_condition;

				public static class UseCondition {
					public Boolean can_use_with_other_discount;
					public Boolean can_use_with_membercard;

					@Override
					public String toString() {
						return "UseCondition [can_use_with_other_discount=" + can_use_with_other_discount
								+ ", can_use_with_membercard=" + can_use_with_membercard + "]";
					}
				}

				@Override
				public String toString() {
					return "AdvancedInfo [share_friends=" + share_friends + ", use_condition=" + use_condition + "]";
				}
			}

			public static class BaseInfo {
				public String id;
				public String logo_url;
				public String code_type;
				public String brand_name;
				public String title;
				public String sub_title;
				public DateInfo date_info;
				public String color;
				public String notice;
				public String description;
				public List<String> location_id_list;
				public String get_limit;
				public String can_share;
				public String can_give_friend;
				public String status;
				public Sku sku;
				public Long create_time;
				public Long update_time;

				public static class Sku {
					public String quantity;
					public String total_quantity;

					@Override
					public String toString() {
						return "Sku [quantity=" + quantity + ", total_quantity=" + total_quantity + "]";
					}
				}

				public static class DateInfo {
					public String type;
					public String fixed_term;
					public String fixed_begin_term;

					@Override
					public String toString() {
						return "DateInfo [type=" + type + ", fixed_term=" + fixed_term + ", fixed_begin_term="
								+ fixed_begin_term + "]";
					}
				}

				@Override
				public String toString() {
					return "BaseInfo [id=" + id + ", logo_url=" + logo_url + ", code_type=" + code_type
							+ ", brand_name=" + brand_name + ", title=" + title + ", sub_title=" + sub_title
							+ ", date_info=" + date_info + ", color=" + color + ", notice=" + notice + ", description="
							+ description + ", location_id_list=" + location_id_list + ", get_limit=" + get_limit
							+ ", can_share=" + can_share + ", can_give_friend=" + can_give_friend + ", status=" + status
							+ ", sku=" + sku + ", create_time=" + create_time + ", update_time=" + update_time + "]";
				}
			}

			@Override
			public String toString() {
				return "GeneralCoupon [base_info=" + base_info + ", default_detail=" + default_detail
						+ ", advanced_info=" + advanced_info + "]";
			}
		}

		public static enum CardType {
			GENERAL_COUPON("GENERAL_COUPON", "通用优惠券");

			private String name;
			private String value;

			private CardType(String name, String value) {
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

			public static String fromName(String name) {
				for (CardType type : CardType.values()) {
					if (type.getName().equalsIgnoreCase(name)) {
						return type.getValue();
					}
				}
				return null;
			}
		}

		@Override
		public String toString() {
			return "CardInfo [card_type=" + card_type + ", general_coupon=" + general_coupon + "]";
		}
	}

	@Override
	public String toString() {
		return "CardGetResult [errcode=" + errcode + ", errmsg=" + errmsg + ", card=" + card + "]";
	}
}
