window.name = "mp-assist";
var appid;
var appsecret;

var paramsSelectList = [
    {
        name: "创建会员卡",
        value: "card_create",
        url: "https://api.weixin.qq.com/card/create?access_token=",
        params_template: card_create_data
    },
    {
        name: "激活会员卡",
        value: "card_membercard_activate",
        url: "https://api.weixin.qq.com/card/membercard/activate?access_token=",
        params_template: card_membercard_activate_data
    },
    {
        name: "拉取会员信息（积分查询）接口",
        value: "card_membercard_userinfo_get",
        url: "https://api.weixin.qq.com/card/membercard/userinfo/get?access_token=",
        params_template: card_membercard_userinfo_get_data
    },
    {
        name: "设置开卡字段接口",
        value: "card_membercard_activateuserform_set",
        url: "https://api.weixin.qq.com/card/membercard/activateuserform/set?access_token=",
        params_template: card_membercard_activateuserform_set_data
    },
    {
        name: "自定义code卡券导入接口",
        value: "card_code_deposit",
        url: "http://api.weixin.qq.com/card/code/deposit?access_token=",
        params_template: card_code_deposit_data
    },
    {
        name: "修改卡券库存接口",
        value: "card_modifystock",
        url: "https://api.weixin.qq.com/card/modifystock?access_token=",
        params_template: card_modifystock_data
    },
    {
        name: "删除会员卡",
        value: "card_delete",
        url: "https://api.weixin.qq.com/card/delete?access_token=",
        params_template: card_delete_data
    },
    {
        name: "修改会员卡",
        value: "card_update",
        url: "https://api.weixin.qq.com/card/update?access_token=",
        params_template: card_update_data
    },
    {
        name: "获取用户列表",
        value: "user_get",
        url: "https://api.weixin.qq.com/cgi-bin/user/get?next_openid=&access_token=",
        params_template: {}
    },
    {
        name: "获取用户基本信息（包括UnionID机制）",
        value: "cgi_bin_user_info",
        url: "https://api.weixin.qq.com/cgi-bin/user/info?openid=OPENID&lang=zh_CN&access_token=",
        params_template: {}
    },
    {
        name: "获取用户已领取卡券接口",
        value: "card_user_getcardlist",
        url: "https://api.weixin.qq.com/card/user/getcardlist?access_token=",
        params_template: card_user_getcardlist_data
    },
    {
        name: "投放卡券创建二维码接口",
        value: "card_qrcode_create",
        url: "https://api.weixin.qq.com/card/qrcode/create?access_token=",
        params_template: card_qrcode_create_data
    },
    {
        name: "自定义菜单查询接口",
        value: "cgi_bin_menu_get",
        url: "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=",
        params_template: {}
    },
    {
        name: "客服接口-发消息",
        value: "cgi_bin_message_custom_send",
        url: "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=",
        params_template: cgi_bin_message_custom_send_data
    },
    {
        name: "获取素材列表",
        value: "cgi_bin_material_batchget_material",
        url: "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=",
        params_template: cgi_bin_material_batchget_material_data
    },
    {
        name: "自定义菜单创建接口",
        value: "cgi_bin_menu_create",
        url: "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=",
        params_template: cgi_bin_menu_create_data
    }
];

var process_list = [{name:"API创建自定义卡号的会员卡",processes:["创建会员卡","card_membercard_activateuserform_set","card_qrcode_create","用户领取后调用激活接口","card_membercard_activate"]}];

function request(params, url, callback, checkToken) {
    if (checkToken) {
        var accessToken = Settings.getValue(tokenkey);
        if (!accessToken) {
            alert("请完成更新access-token后再继续操作。");
            return false;
        }
    }
    $.ajax({
        type: "post",
        url: url,
        data: params,
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        cache: false,
        success: function (data) {
            var date = new Date();
            var resultTime = date.format('yyyy-MM-dd hh:mm:ss');
            $(".response-result p").html(resultTime);
            $(".response-result pre").html(JSON.stringify(data, null, "\t"));
            callback(data);
        }
    });
}

var tokenkey = "access-token";

function refreshAccessToken() {
    var params_appid = $(".params-appid").val();
    if (!params_appid) {
        alert("请输入appid");
        return false;
    }
    var params_appsecret = $(".params-appsecret").val();
    if (!params_appsecret) {
        alert("请输入appsecret");
        return false;
    }
    var url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + appsecret;
    request({}, url, function (data) {
        if (data && data.access_token) {
            Settings.setValue(tokenkey, data.access_token);
            $(".params-select").get(0).selectedIndex = 0;
            $(".params-input").val("");
            $(".params-url").val("");
        }
    }, false);
}

$(".refresh-btn").click(function () {
    refreshAccessToken();
});

function apiRequest(url, callback) {
    var params = $(".params-input").val();
    request(params, url, function (data) {
        if (callback) {
            callback(data);
        }
    }, true);
}

function renderparamsSelect() {
    $(".params-appid").change(function () {
        appid = $(this).val();
    });
    $(".params-appsecret").change(function () {
        appsecret = $(this).val();
    });

    $(".params-select").append("<option value=''>请选择操作</option>");
    $.each(paramsSelectList, function (index, item) {
        $(".params-select").append("<option value='" + item.value + "'>" + item.name + "</option>");
    });
    $(".params-select").change(function () {
        var value = $(".params-select").val();
        $.each(paramsSelectList, function (index, item) {
            if (item.value == value) {
                var accessToken = Settings.getValue(tokenkey);
                var url = item.url + accessToken;
                $(".params-url").val(url);
                $(".params-input").val(JSON.stringify(item.params_template, null, "\t"));
                return false;
            }
            ;
        });
    });
}

renderparamsSelect();

function submitRequest() {
    var params_appid = $(".params-appid").val();
    if (!params_appid) {
        alert("请输入appid");
        return false;
    }
    var params_appsecret = $(".params-appsecret").val();
    if (!params_appsecret) {
        alert("请输入appsecret");
        return false;
    }
    var value = $(".params-select").val();
    if (!value) {
        alert("请选择Api");
        return false;
    }
    var opreate = null;
    $.each(paramsSelectList, function (index, item) {
        if (item.value == value) {
            opreate = item.opreate;
            return false;
        }
    });

    var url = $(".params-url").val();
    apiRequest(url, opreate);
}

$(".request-btn").click(function () {
    submitRequest();
});

$(".process-btn").click(function () {
    window.open(chrome.extension.getURL('process.html'), "mp-assist-doc");
});