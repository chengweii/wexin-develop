package mp.entity;

public class BaseResult {
	public String errcode;
	public String errmsg;

	@Override
	public String toString() {
		return "BaseResult [errcode=" + errcode + ", errmsg=" + errmsg + "]";
	}
}
