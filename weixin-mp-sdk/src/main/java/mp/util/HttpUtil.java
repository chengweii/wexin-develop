package mp.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.ParseException;

public class HttpUtil {
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final Integer SO_TIMEOUT = 5000;
	private static final Integer CONNECTION_TIMEOUT = 5000;

	/**
	 * get
	 * 
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String get(String url) {
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpParams httpParams = httpGet.getParams();
			httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
			httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			String result = null;
			HttpResponse httpResponse = client.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, DEFAULT_CHARSET);
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * post
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String post(String url, String params) {
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(url);
			HttpParams httpParams = httpost.getParams();
			httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
			httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			String result = null;
			httpost.setEntity(new StringEntity(params, DEFAULT_CHARSET));
			HttpResponse response = client.execute(httpost);
			result = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
