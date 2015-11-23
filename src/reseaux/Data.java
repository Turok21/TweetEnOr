package reseaux;

import java.io.Serializable;

public class Data implements Serializable {
	private static final long serialVersionUID = 1350092881346723569L;

	private String type;
	private Object content;

	public Data(String type, Object content) {
		this.type = type;
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}


}
