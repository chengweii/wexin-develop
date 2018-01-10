package mp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

public class GsonUtil {

	private static final Log LOGGER = LogFactory.getLog(GsonUtil.class);

	public static Gson gson = null;

	static {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.setPrettyPrinting().serializeNulls().create();
	}

	public static Map<String, String> getMapFromJson(String json) {
		if (json == null || "".equals(json.trim()))
			return null;

		Map<String, String> map = null;
		try {
			map = getEntityFromJson(json, new TypeToken<Map<String, String>>() {
			});
		} catch (Exception e) {
			LOGGER.error("Json string is :" + json, e);
		}
		return map;
	}

	public static <T> T getEntityFromJson(String json, TypeToken<?> typeToken) {
		if (json == null || "".equals(json.trim()))
			return null;

		T data = null;
		try {
			data = GsonUtil.gson.fromJson(json, typeToken.getType());
		} catch (Exception e) {
			LOGGER.error("Json string is :" + json, e);
		}
		return data;
	}

	public static String toJson(Object src) throws JsonSyntaxException {
		return GsonUtil.gson.toJson(src);
	}

	public static void main(String[] args) {
	}

}
