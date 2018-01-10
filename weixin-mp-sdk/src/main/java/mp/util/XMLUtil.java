package mp.util;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class XMLUtil {

	private static final ThreadLocal<Map<Class<?>, Marshaller>> mMapLocal = new ThreadLocal<Map<Class<?>, Marshaller>>() {
		@Override
		protected Map<Class<?>, Marshaller> initialValue() {
			return new HashMap<Class<?>, Marshaller>();
		}
	};

	private static final ThreadLocal<Map<Class<?>, Unmarshaller>> uMapLocal = new ThreadLocal<Map<Class<?>, Unmarshaller>>() {
		@Override
		protected Map<Class<?>, Unmarshaller> initialValue() {
			return new HashMap<Class<?>, Unmarshaller>();
		}
	};

	public static <T> T xmlToObject(Class<T> clazz, String xml) {
		return xmlToObject(clazz, new StringReader(xml));
	}

	public static <T> T xmlToObject(Class<T> clazz, InputStream inputStream) {
		return xmlToObject(clazz, new InputStreamReader(inputStream));
	}

	public static <T> T xmlToObject(Class<T> clazz, InputStream inputStream, Charset charset) {
		return xmlToObject(clazz, new InputStreamReader(inputStream, charset));
	}

	@SuppressWarnings("unchecked")
	public static <T> T xmlToObject(Class<T> clazz, Reader reader) {
		try {
			Map<Class<?>, Unmarshaller> uMap = uMapLocal.get();
			if (!uMap.containsKey(clazz)) {
				JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				uMap.put(clazz, unmarshaller);
			}
			return (T) uMap.get(clazz).unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static String objectToXML(Object object) {
		try {
			Map<Class<?>, Marshaller> mMap = mMapLocal.get();
			if (!mMap.containsKey(object.getClass())) {
				JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
					public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
						writer.write(ac, i, j);
					}
				});
				mMap.put(object.getClass(), marshaller);
			}
			StringWriter stringWriter = new StringWriter();
			mMap.get(object.getClass()).marshal(object, stringWriter);
			return stringWriter.getBuffer().toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, String> xmlToMap(String xml) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			if (root != null) {
				NodeList childNodes = root.getChildNodes();
				if (childNodes != null && childNodes.getLength() > 0) {
					for (int i = 0; i < childNodes.getLength(); i++) {
						Node node = childNodes.item(i);
						if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
							map.put(node.getNodeName(), node.getTextContent());
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return map;
	}

	@XmlRootElement(name = "xml")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Demo {
		@XmlElement(name = "name")
		private String name;
		@XmlElement(name = "node")
		private Inner value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Inner getValue() {
			return value;
		}

		public void setValue(Inner value) {
			this.value = value;
		}

		@XmlRootElement(name = "node")
		@XmlAccessorType(XmlAccessType.FIELD)
		public static class Inner {
			@XmlElement(name = "name")
			private String name;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}
	}

	public static void main(String[] args) {
		Demo demo = new Demo();
		demo.name = "demo";
		demo.value= new Demo.Inner();
		demo.value.name ="inner";

		String xml = XMLUtil.objectToXML(demo);
		System.out.println(xml);

		Map<String, String> map = XMLUtil.xmlToMap(xml);
		System.out.println(map);
	}
}
