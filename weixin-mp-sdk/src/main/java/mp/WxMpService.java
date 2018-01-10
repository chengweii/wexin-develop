package mp;

import com.google.gson.reflect.TypeToken;
import mp.aes.SHA1;
import mp.aes.WxMpCryptUtil;
import mp.entity.*;
import mp.message.MessageProcessor;
import mp.util.GsonUtil;
import mp.util.XMLUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class WxMpService {
    private static final Log LOGGER = LogFactory.getLog(WxMpService.class);

    /**
     * 拉取会员信息（积分查询）接口
     *
     * @param cardid
     * @param code
     * @return
     */
    public static CardMembercardUserinfoGetResult cardMembercardUserinfoGet(String cardid, String code) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("card_id", cardid);
        params.put("code", code);
        String result = WxMpConfig.request(WxMpConfig.RequestMethd.POST, "/card/membercard/userinfo/get", params);
        return GsonUtil.getEntityFromJson(result, new TypeToken<CardMembercardUserinfoGetResult>() {
        });
    }

    /**
     * 微信卡券》管理卡券》查询Code接口
     *
     * @param cardid
     * @param code
     * @param checkConsume
     * @return
     */
    public static CardCodeGetResult cardCodeGet(String cardid, String code, Boolean checkConsume) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(cardid)) {
            params.put("card_id", cardid);
        }
        params.put("code", code);
        params.put("check_consume", checkConsume == null ? false : checkConsume);

        String result = WxMpConfig.request(WxMpConfig.RequestMethd.POST, "/card/code/get", params);
        return GsonUtil.getEntityFromJson(result, new TypeToken<CardCodeGetResult>() {
        });
    }

    /**
     * 微信卡券》管理卡券》查看卡券详情
     *
     * @param cardid
     * @return
     */
    public static CardGetResult cardGet(String cardid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("card_id", cardid);
        String result = WxMpConfig.request(WxMpConfig.RequestMethd.POST, "/card/get", params);
        return GsonUtil.getEntityFromJson(result, new TypeToken<CardGetResult>() {
        });
    }

    /**
     * 微信卡券》管理卡券》核销Code接口
     *
     * @param cardid
     * @param code
     * @return
     */
    public static CardCodeConsumeResult cardCodeConsume(String cardid, String code) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(cardid)) {
            params.put("card_id", cardid);
        }
        params.put("code", code);

        String result = WxMpConfig.request(WxMpConfig.RequestMethd.POST, "/card/code/consume", params);
        return GsonUtil.getEntityFromJson(result, new TypeToken<CardCodeConsumeResult>() {
        });
    }

    /**
     * 微信卡券》会员卡专区》创建会员卡》更新会员信息
     *
     * @param cardid
     * @param code
     * @param otherParams
     * @return
     */
    public static CardMembercardUpdateuserResult cardMembercardUpdateuser(String cardid, String code,
                                                                          Map<String, Object> otherParams) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        params.put("card_id", cardid);
        params.putAll(otherParams);
        String result = WxMpConfig.request(WxMpConfig.RequestMethd.POST, "/card/membercard/updateuser", params);
        return GsonUtil.getEntityFromJson(result, new TypeToken<CardMembercardUpdateuserResult>() {
        });
    }

    /**
     * 微信卡券》会员卡专区》创建会员卡》更新会员积分
     *
     * @param cardid
     * @param code
     * @param bonus
     * @return
     */
    public static CardMembercardUpdateuserResult cardMembercardUpdateuser(String cardid, String code, Integer bonus) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bonus", bonus);
        return cardMembercardUpdateuser(cardid, code, params);
    }

    /**
     * 客服接口-发消息
     *
     * @param touser
     * @param msgtype
     * @param message
     * @return
     */
    public static BaseResult messageCustomSend(String touser, String msgtype, Map<String, Object> message) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("touser", touser);
        params.put("msgtype", msgtype);
        params.put(msgtype, message);
        String result = WxMpConfig.request(WxMpConfig.RequestMethd.POST, "/cgi-bin/message/custom/send", params);
        return GsonUtil.getEntityFromJson(result, new TypeToken<BaseResult>() {
        });
    }

    /**
     * 校验接收消息签名
     *
     * @param timestamp
     * @param nonce
     * @param signature
     * @return
     */
    public static boolean checkSignature(String timestamp, String nonce, String signature) {
        try {
            return SHA1.gen(WxMpConfig.TOKEN, timestamp, nonce).equals(signature);
        } catch (Exception e) {
            LOGGER.error("Checking signature failed, and the reason is :" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 微信服务器认证
     *
     * @param timestamp
     * @param nonce
     * @param signature
     * @param echostr
     * @return
     */
    public static String authenticate(String timestamp, String nonce, String signature, String echostr) {
        LOGGER.info("Receive authentication messages from Weixin server:[" + signature + ", " + timestamp + ", " + nonce
                + ", " + echostr + "].");

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("Request parameter is illegal. Please verify!");
        }
        if (WxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "Illegal request.";
    }

    /**
     * 处理微信推送消息
     *
     * @param timestamp
     * @param nonce
     * @param signature
     * @param requestBody
     * @param encryptType
     * @param msgSignature
     * @param messageTypes
     * @return
     */
    public static String handleMessage(String timestamp, String nonce, String signature, String requestBody,
                                       String encryptType, String msgSignature, Map<String, Class<?>> messageTypes) {
        LOGGER.info("Receive messages from Weixin server:[" + signature + ", " + timestamp + ", " + nonce + ", "
                + encryptType + ", " + msgSignature + ", " + requestBody + "].");

        if (StringUtils.isAnyBlank(signature, timestamp, nonce)) {
            throw new IllegalArgumentException("Request parameter is illegal. Please verify!");
        }
        if (!WxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("An illegal request may be a forgery request!");
        }

        String xmlData = null;
        if (encryptType == null) {
            xmlData = requestBody;
        } else if ("aes".equals(encryptType)) {
            String plainXml = WxMpCryptUtil.decrypt(WxMpConfig.TOKEN, msgSignature, timestamp, nonce, requestBody);
            if (!StringUtils.isEmpty(plainXml)) {
                xmlData = plainXml;
            }
        }
        if (xmlData == null) {
            throw new IllegalArgumentException("Received message is empty!");
        }

        LOGGER.info("plainXml:[" + xmlData + "].");

        Map<String, String> message = XMLUtil.xmlToMap(xmlData);
        MessageProcessor messageProcessor = routeMessageProcessor(message, messageTypes);
        return messageProcessor.handle(xmlData);
    }

    /**
     * 路由消息到指定的处理器
     *
     * @param message
     * @param messageTypes
     * @return
     */
    private static MessageProcessor routeMessageProcessor(Map<String, String> message,
                                                          Map<String, Class<?>> messageTypes) {
        String processorName = "default";
        if (message != null) {
            if ("text".equals(message.get("MsgType"))) {
                processorName = message.get("MsgType");
            } else if ("event".equals(message.get("MsgType")) && message.get("Event") != null) {
                processorName = message.get("Event");
            }
        }
        Class<?> messageType = messageTypes.get(processorName);
        if (messageType == null) {
            messageType = messageTypes.get("default");
        }
        return (MessageProcessor) null;
    }

    public static void main(String[] args) {
        // CardGetResult result =
        // WxMpService.cardGet("pKot_tzv0Ec56GzRocax2IroREnM");
        // CardCodeGetResult result = WxMpService.cardCodeGet(null,
        // "068257372883", false);
        // CardCodeConsumeResult result = WxMpService.cardCodeConsume(null,
        // "068257372883");
        // System.out.println(result);
    }
}
