package com.uaf.activiti.api.exception;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 工作流引擎服务异常定义
 * @filename ActivitiServiceException
 * @author 王承
 * @date 2019/9/23 10:35
 */
public class ActivitiServiceException extends RuntimeException {

	/** 版本号 */
	private static final long serialVersionUID = 1L;

	/**错误消息Map*/
	private static Map<String, String> errMsgMap = new HashMap<String, String>();

	/**异常编码*/
	private String code;

	static {
		errMsgMap.put(ActivitiException.ERR_100000, "系统异常");
		errMsgMap.put(ActivitiException.ERR_100001, "服务器运行时异常");
		errMsgMap.put(ActivitiException.ERR_100002, "未获取到对应信息");
		errMsgMap.put(ActivitiException.ERR_100003, "参数校验不通过");
		errMsgMap.put(ActivitiException.ERR_100004, "商户不可用");
		errMsgMap.put(ActivitiException.ERR_100005, "请求IP不可用或者被不被允许");
		errMsgMap.put(ActivitiException.ERR_100006, "调用异常");
		errMsgMap.put(ActivitiException.ERR_100007, "参数校验异常");
		errMsgMap.put(ActivitiException.ERR_100008, "非法请求");
	}

	/**
	 * 设置自定义异常编码
	 * @param code 异常编码
	 * @author 王承
	 * @date 2019/12/02 10:12
	 */
	public ActivitiServiceException(String code) {
		super(errMsgMap.get(code) == null ? ("未知代码:" + code) : errMsgMap.get(code));
		this.code = code;
	}

	/**
	 * 设置自定义异常
	 * @param code 异常编码
	 * @param message 异常消息
	 * @author 王承
	 * @date 2019/12/02 10:13
	 */
	public ActivitiServiceException(String code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * 设置自定义异常
	 * @param code 异常编码
	 * @param args 异常消息
	 * @author 王承
	 * @date 2019/12/02 10:14
	 */
	public ActivitiServiceException(String code, Object[] args) {
		super(errMsgMap.get(code) == null ? ("未知代码:" + code) : (MessageFormat.format(errMsgMap.get(code), args)));
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
