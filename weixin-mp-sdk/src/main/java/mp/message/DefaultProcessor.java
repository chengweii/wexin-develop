package mp.message;

import org.springframework.stereotype.Component;

@Component
public class DefaultProcessor extends MessageProcessor {

	@Override
	public String handle(String message) {
		return "";
	}

}
