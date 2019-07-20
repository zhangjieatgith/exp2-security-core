package cn.zhang.jie.core.properties;

public class SocialProperties {

	private QQproperties qq = new QQproperties();
	private String filterProcessUrl = "/auth";

	public QQproperties getQq() {
		return qq;
	}
	public void setQq(QQproperties qq) {
		this.qq = qq;
	}
	public String getFilterProcessUrl() {
		return filterProcessUrl;
	}
	public void setFilterProcessUrl(String filterProcessUrl) {
		this.filterProcessUrl = filterProcessUrl;
	}
}
