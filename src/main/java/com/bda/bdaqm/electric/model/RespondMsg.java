package com.bda.bdaqm.electric.model;


public class RespondMsg {

	/**
	 * 请求响应编码
	 */
	private String respCode;
	/**
	 * 异常响应说明
	 */
	private String expMsg;
	/**
	 * 响应消息主体
	 */
	private WebServiceVo respMsg;

	public RespondMsg() {
		super();
	}

	public RespondMsg(String respCode, String expMsg, WebServiceVo respMsg) {
		super();
		this.respCode = respCode;
		this.expMsg = expMsg;
		this.respMsg = respMsg;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getExpMsg() {
		if(expMsg == null) {
			return "";
		}
		return expMsg;
	}

	public void setExpMsg(String expMsg) {
		this.expMsg = expMsg;
	}

	public WebServiceVo getRespMsg() {
		if(respMsg == null) {
			return null;
		}
		return respMsg;
	}

	public void setRespMsg(WebServiceVo respMsg) {
		this.respMsg = respMsg;
	}

}
