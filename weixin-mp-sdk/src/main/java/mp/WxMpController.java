package mp;

import mp.message.MessageType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "jkd1/weixin")
public class WxMpController{
	private static final Log loger = LogFactory.getLog(WxMpController.class);
	private static final Map<String, Class<?>> eventTypes = new HashMap<String, Class<?>>();
	static {
		for (MessageType type : MessageType.values()) {
			eventTypes.put(type.getName(), type.getValue());
		}
	}

	/**
	 * 微信消息推送首次验证接口
	 * 
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @param echostr
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/mp/notify.htm", method = RequestMethod.GET)
	public void mpNotifyAuth(String timestamp, String nonce, String signature, String echostr,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = WxMpService.authenticate(timestamp, nonce, signature, echostr);
		responseContent(response, result);
		return;
	}

	/**
	 * 微信消息推送处理接口
	 *
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @param encrypt_type
	 * @param msg_signature
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/mp/notify.htm", method = RequestMethod.POST)
	public void mpNotify(String timestamp, String nonce, String signature, String encrypt_type, String msg_signature,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String requestBody = getRequestContent(request);
		String result = WxMpService.handleMessage(timestamp, nonce, signature, requestBody, encrypt_type, msg_signature,
				eventTypes);
		responseContent(response, result);
		return;
	}

	private String getRequestContent(HttpServletRequest request) {
		try {
			InputStream inputStream = request.getInputStream();
			BufferedReader reCode = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			String requestBody = bufferedReader2String(reCode);
			reCode.close();
			inputStream.close();
			return requestBody;
		} catch (Exception e) {
			loger.error(e.getMessage(), e);
		}
		return null;
	}

	public String bufferedReader2String(BufferedReader reader) throws IOException {
		StringBuffer buf = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buf.append(line);
			buf.append("\r\n");
		}

		return buf.toString();
	}

	private void responseContent(HttpServletResponse response, String result) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			loger.error(e.getMessage(), e);
		}
		out.println(result);
		out.flush();
		out.close();
	}
}
