package mp.aes;

import com.jiuxian.weixin.mp.WxMpConfig;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class WxMpCryptUtil {
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	private static final ThreadLocal<DocumentBuilder> builderLocal = new ThreadLocal<DocumentBuilder>() {
		@Override
		protected DocumentBuilder initialValue() {
			try {
				return DocumentBuilderFactory.newInstance().newDocumentBuilder();
			} catch (ParserConfigurationException exc) {
				throw new IllegalArgumentException(exc);
			}
		}
	};

	public static String decrypt(String token, String msgSignature, String timeStamp, String nonce,
			String encryptedXml) {
		String cipherText = extractEncryptPart(encryptedXml);
		String signature = SHA1.gen(token, timeStamp, nonce, cipherText);
		if (!signature.equals(msgSignature)) {
			throw new RuntimeException("Encrypted message signature check failed.");
		}

		byte[] aesKey = Base64.decodeBase64(WxMpConfig.ENCODING_AES_KEY + "=");
		return decrypt(aesKey, cipherText, WxMpConfig.APP_ID);
	}

	private static String extractEncryptPart(String xml) {
		try {
			DocumentBuilder db = builderLocal.get();
			Document document = db.parse(new InputSource(new StringReader(xml)));

			Element root = document.getDocumentElement();
			return root.getElementsByTagName("Encrypt").item(0).getTextContent();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String decrypt(byte[] aesKey, String cipherText, String appidOrCorpid) {
		byte[] original;
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);

			byte[] encrypted = Base64.decodeBase64(cipherText);
			original = cipher.doFinal(encrypted);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		String xmlContent, from_appid;
		try {
			byte[] bytes = PKCS7Encoder.decode(original);
			byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
			int xmlLength = bytesNetworkOrder2Number(networkOrder);
			xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
			from_appid = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), CHARSET);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (!from_appid.equals(appidOrCorpid)) {
			throw new RuntimeException("AppID is wrong.");
		}
		return xmlContent;
	}

	private static int bytesNetworkOrder2Number(byte[] bytesInNetworkOrder) {
		int sourceNumber = 0;
		for (int i = 0; i < 4; i++) {
			sourceNumber <<= 8;
			sourceNumber |= bytesInNetworkOrder[i] & 0xff;
		}
		return sourceNumber;
	}
}
