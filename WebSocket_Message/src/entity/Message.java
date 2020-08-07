package entity;

import java.sql.Timestamp;

import com.alibaba.fastjson.annotation.JSONField;

public class Message {
	
	private Integer msgId;
	private String msgContent;
	private String fromName;
	private String toName;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Timestamp msgDate;
	private Integer msgStatus;
	
	public Message() {}
	
	public Message(Integer msgId, String msgContent, String fromName,
			String toName, Timestamp msgDate, Integer msgStatus) {
		super();
		this.msgId = msgId;
		this.msgContent = msgContent;
		this.fromName = fromName;
		this.toName = toName;
		this.msgDate = msgDate;
		this.msgStatus = msgStatus;
	}
	public Integer getMsgId() {
		return msgId;
	}
	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public Timestamp getMsgDate() {
		return msgDate;
	}
	public void setMsgDate(Timestamp msgDate) {
		this.msgDate = msgDate;
	}
	public Integer getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(Integer msgStatus) {
		this.msgStatus = msgStatus;
	}
	
	

}
