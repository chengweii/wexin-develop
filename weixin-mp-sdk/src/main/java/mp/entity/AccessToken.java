package mp.entity;

public class AccessToken {
	public String access_token;
	public int expires_in;

	@Override
	public String toString() {
		return "AccessToken [access_token=" + access_token + ", expires_in=" + expires_in + "]";
	}
}
