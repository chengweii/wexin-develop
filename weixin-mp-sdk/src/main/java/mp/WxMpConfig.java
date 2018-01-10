package mp;

import com.google.gson.reflect.TypeToken;
import mp.entity.AccessToken;
import mp.entity.BaseResult;
import mp.util.GsonUtil;
import mp.util.HttpUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class WxMpConfig {
    private static final Log LOGGER = LogFactory.getLog(WxMpConfig.class);

    /**
     * 公众号ID
     */
    public static final String APP_ID;
    /**
     * 公众号密钥
     */
    public static final String APP_SECRET;
    /**
     * 消息推送加解密密钥
     */
    public static final String ENCODING_AES_KEY;
    /**
     * 消息推送令牌
     */
    public static final String TOKEN;
    /**
     * 会员卡ID
     */
    public static final String CARD_ID;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("weixin", Locale.getDefault());
        APP_ID = bundle.getString("weixin.mp.app_id");
        APP_SECRET = bundle.getString("weixin.mp.app_secret");
        ENCODING_AES_KEY = bundle.getString("weixin.mp.encoding_aes_key");
        TOKEN = bundle.getString("weixin.mp.token");
        CARD_ID = bundle.getString("weixin.mp.card_id");
    }

    private static final String ACCESS_TOKEN_KEY = "weixin_access_token";
    protected static final String WEIXIN_API_BASE = "https://api.weixin.qq.com";

    private static final String ACCESS_TOKEN_URL = WEIXIN_API_BASE
            + "/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 获取访问微信公众号API的access-token
     *
     * @param isRefresh 是否要强制刷新access-token
     * @return
     */
    public static String getAccessToken(boolean isRefresh) {
        try {
            //Object accessToken = cacheService.get(ACCESS_TOKEN_KEY);
            Object accessToken = "cached_weixin_access_token";
            if (accessToken == null || isRefresh) {
                LOGGER.info("Prepare to refresh access-token.");
                return retryGetToken();
            }
            return accessToken.toString();
        } catch (Exception e) {
            LOGGER.error("Get access-token error.", e);
            throw new RuntimeException("Get access-token error.");
        }
    }

    /**
     * 尝试获取access-token 若获取失败，则重试10次，直到成功为止
     *
     * @return
     * @throws InterruptedException
     */
    private static String retryGetToken()
            throws InterruptedException {
        int count = 0;
        String accessToken = null;
        while (count < 10) {
            accessToken = requestToken();
            if (accessToken == null) {
                count++;
                Thread.sleep(500);
            } else {
                break;
            }
        }
        if (accessToken == null) {
            LOGGER.info("Retry get access-token failed.");
        }
        return accessToken;
    }

    private static final String REFRESH_TOKEN_LOCK_KEY = "REFRESH_TOKEN_LOCK_KEY";

    /**
     * 请求access-token并缓存到本地缓存中
     *
     * @return
     */
    private static String requestToken() {
        try {
            Object cacheAccessToken = "cached_weixin_access_token";
            if (cacheAccessToken != null) {
                return cacheAccessToken.toString();
            }

            String url = ACCESS_TOKEN_URL.replace("APPID", APP_ID).replace("APPSECRET", APP_SECRET);
            String result = HttpUtil.get(url);
            LOGGER.info("Request access-token result:" + result);
            if (result != null) {
                AccessToken token = GsonUtil.getEntityFromJson(result, new TypeToken<AccessToken>() {
                });
                if (token != null) {
                    return token.access_token;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Request access-token failed.", e);
        }
        return null;
    }

    private static final String ACCESS_TOKEN_SPACE_HOLDER = "ACCESS_TOKEN";
    private static final String ACCESS_TOKEN_SUB_FIX = "access_token=" + ACCESS_TOKEN_SPACE_HOLDER;

    /**
     * 添加access-token到访问api的url中
     *
     * @param url
     * @return
     */
    protected static String addAccessTokenToUrl(String url) {
        if (url == null)
            return null;
        String newUrl = null;
        if (url.contains("?")) {
            newUrl = url + "&" + ACCESS_TOKEN_SUB_FIX;
        } else {
            newUrl = url + "?" + ACCESS_TOKEN_SUB_FIX;
        }
        String accessToken = getAccessToken(false);
        String apiUrl = newUrl.replace(ACCESS_TOKEN_SPACE_HOLDER, accessToken);
        return apiUrl;
    }

    /**
     * 请求API
     *
     * @param requestMethd
     * @param url
     * @param params
     * @return
     */
    protected static String request(RequestMethd requestMethd, String url, Map<String, Object> params) {
        String result = null;
        if (requestMethd == RequestMethd.GET) {
            result = HttpUtil.get(WxMpConfig.addAccessTokenToUrl(WEIXIN_API_BASE + url));
        } else {
            String paramsJson = GsonUtil.toJson(params);
            result = HttpUtil.post(WxMpConfig.addAccessTokenToUrl(WEIXIN_API_BASE + url), paramsJson);
        }
        BaseResult entity = GsonUtil.getEntityFromJson(result, new TypeToken<BaseResult>() {
        });
        if (entity != null && "40001".equals(entity.errcode)) {
            getAccessToken(true);
            return request(requestMethd, url, params);
        }
        return result;
    }

    protected enum RequestMethd {
        GET, POST;
    }
}
