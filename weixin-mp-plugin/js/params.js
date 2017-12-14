var card_create_data = {
	"card": {
		"card_type": "MEMBER_CARD",
		"member_card": {
			"background_pic_url": "https://mmbiz.qlogo.cn/mmbiz/pPhKHgrOjvRPIUYx2zPt10Dw2HssgnWb10wueriaNOejicHMUyV5c0Jjs7yz8sTtgWfUpXBX5LWy4cHymWddWvqg/0?wx_fmt=png",
			"base_info": {
				"logo_url": "https://mmbiz.qlogo.cn/mmbiz/pPhKHgrOjvQ4wyqxD9uLhrdTv5a9clQtXiaeZjSzWq7fJGKUjibib94ukWvJyeSqAswQianA0TnT52dfDlKFXNB0EQ/0",
				"brand_name": "酒快到测试",
				"code_type": "CODE_TYPE_QRCODE",
				"title": "酒快到测试会员卡",
				"color": "Color050",
				"notice": "使用时向服务员出示此券",
				"service_phone": "020-99999999",
				"description": "不可与其他优惠同享",
				"date_info": {
					"type": "DATE_TYPE_PERMANENT"
				},
				"sku": {
					"quantity": 0
				},
				"get_limit": 1,
				"use_custom_code": true,
				"get_custom_code_mode": "GET_CUSTOM_CODE_MODE_DEPOSIT",
				"can_give_friend": true,
				"location_id_list": [
					402964956
				],
				"custom_url_name": "我要买酒",
				"custom_url": "http://test.m.jiukuaidao.com",
				"custom_url_sub_title": "不买白不买",
				"promotion_url_name": "就要买酒",
				"promotion_url": "http://test.m.jiukuaidao.com",
				"need_push_on_view": true
			},
			"advanced_info": {
				"use_condition": {
					"accept_category": "白酒",
					"reject_category": "红酒",
					"can_use_with_other_discount": true
				},
				"abstract": {
					"abstract": "酒快到测试",
					"icon_url_list": [
						"https://mmbiz.qlogo.cn/mmbiz/pPhKHgrOjvRPIUYx2zPt10Dw2HssgnWb10wueriaNOejicHMUyV5c0Jjs7yz8sTtgWfUpXBX5LWy4cHymWddWvqg/0?wx_fmt=png"
					]
				},
				"text_image_list": [
					{
						"image_url": "https://mmbiz.qlogo.cn/mmbiz/pPhKHgrOjvRPIUYx2zPt10Dw2HssgnWb10wueriaNOejicHMUyV5c0Jjs7yz8sTtgWfUpXBX5LWy4cHymWddWvqg/0?wx_fmt=png",
						"text": "酒快到测试"
					}
				],
				"time_limit": [
					{
						"type": "MONDAY",
						"begin_hour": 0,
						"end_hour": 10,
						"begin_minute": 10,
						"end_minute": 59
					},
					{
						"type": "HOLIDAY"
					}
				],
				"business_service": [
					"BIZ_SERVICE_FREE_WIFI",
					"BIZ_SERVICE_WITH_PET",
					"BIZ_SERVICE_FREE_PARK",
					"BIZ_SERVICE_DELIVER"
				]
			},
			"supply_bonus": true,
			"supply_balance": false,
			"prerogative": "test_prerogative",
			"auto_activate": false,
			"wx_activate": true,
			"custom_field1": {
				"name_type": "FIELD_NAME_TYPE_LEVEL",
				"url": "http://test.m.jiukuaidao.com"
			},
			"custom_cell1": {
				"name": "测试入口",
				"tips": "激活后显示",
				"url": "http://test.m.jiukuaidao.com"
			},
			"bonus_rule": {
				"cost_money_unit": 100,
				"increase_bonus": 1,
				"max_increase_bonus": 200,
				"init_increase_bonus": 10,
				"cost_bonus_unit": 5,
				"reduce_money": 100,
				"least_money_to_use_bonus": 1000,
				"max_reduce_bonus": 50
			},
			"discount": 10
		}
	}
};

var card_delete_data = { "card_id": "pFS7Fjg8kV1IdDz01r4SQwMkuCKc" };

var card_user_getcardlist_data = {
	"openid": "oKot_t872sjz6hR_n-D2R1OIOwgM",
	"card_id": "pKot_txWvdHNTulLqwTSqfqveXqs"
};

var card_qrcode_create_data = {
	"action_name": "QR_MULTIPLE_CARD",
	"action_info": {
		"multiple_card": {
			"card_list": [
				{
					"card_id": "pKot_tzao5PIERhD8g0cW9Z5hdEY",
					"code": "999999999999999",
					"outer_str": "12b"
				},
				{
					"card_id": "pKot_tzao5PIERhD8g0cW9Z5hdEY",
					"code": "888888888888888",
					"outer_str": "12b"
				}
			]
		}
	}
};

var card_membercard_activateuserform_set_data = {
	"card_id": "pbLatjnrwUUdZI641gKdTMJzHGfc",
	"required_form": {
		"can_modify": false,
		"common_field_id_list": [
			"USER_FORM_INFO_FLAG_MOBILE",
			"USER_FORM_INFO_FLAG_NAME"
		]
	},
	"optional_form": {
		"can_modify": false,
		"common_field_id_list": [
			"USER_FORM_INFO_FLAG_LOCATION",
			"USER_FORM_INFO_FLAG_BIRTHDAY"
		]
	}
};

// 推送微信卡券消息接口
var cgi_bin_message_custom_send_data = {
	"touser": "oKot_t872sjz6hR_n-D2R1OIOwgM",
	"msgtype": "wxcard",
	"wxcard": {
		"card_id": "pKot_t3IrMRbbWgdIlIz3976cSnk"
	},
};

// 导入code接口
var card_code_deposit_data = {
	"card_id": "pDF3iY0_dVjb_Pua96MMewA96qvA",
	"code": [
		"11111",
		"22222",
		"33333",
		"44444",
		"55555"
	]
};

// 修改卡券库存接口
var card_modifystock_data = {
	"card_id": "pFS7Fjg8kV1IdDz01r4SQwMkuCKc",
	"increase_stock_value": 10
};

var cgi_bin_material_batchget_material_data = {
	"type": "image",
	"offset": 0,
	"count": 20
};

var cgi_bin_menu_create_data = {
	"button": [
		{
			"type": "view",
			"name": "我要买酒",
			"url": "http://test.m.jiukuaidao.com"
		},
		{
			"name": "会员尊享",
			"sub_button": [
				{
					"type": "click",
					"name": "会员卡",
					"key": "CLICK_001"
				}
			]
		},
		{
			"name": "加盟酒仙",
			"sub_button": [
				{
					"type": "view",
					"name": "加盟详情",
					"url": "http://test.m.jiukuaidao.com"
				},
				{
					"type": "click",
					"name": "店铺详情",
					"key": "CLICK_002"
				},
				{
					"type": "click",
					"name": "联系我们",
					"key": "CLICK_003"
				}
			]
		}
	]
};

var card_membercard_userinfo_get_data = {
	"card_id": "pbLatjtZ7v1BG_ZnTjbW85GYc_E8",
	"code": "916679873278"
};

var card_update_data = {
	"card_id": "pYmlZ0bn9fFKgJdZEZ69arRFqbXY",
	"member_card": {
		"custom_field1": {
			"name": "订单",
			"url": "http://m.jiukuaidao.com/order"
		},
		"custom_field2": {
			"name": "优惠券",
			"url": "http://m.jiukuaidao.com/coupon/my_coupon"
		}
	}
};