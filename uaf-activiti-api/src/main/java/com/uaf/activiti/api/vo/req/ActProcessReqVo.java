package com.uaf.activiti.api.vo.req;

import java.io.Serializable;

/**
 * @filename ActProcessVo
 * @description 流程部署
 * @autor 王承
 * @date 2019/11/29 11:39
 */
public class ActProcessReqVo implements Serializable {

	private static final long serialVersionUID = -7287776981644792247L;

	/**流程Id*/
	private String processId;

	/**流程名称*/
	private String processName;

	/**流程文件类型[zip,tar,bpmn]*/
	private String processFileType;

	/**流程base64流*/
	private String processBase64Str;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessFileType() {
		return processFileType;
	}

	public void setProcessFileType(String processFileType) {
		this.processFileType = processFileType;
	}

	public String getProcessBase64Str() {
		return processBase64Str;
	}

	public void setProcessBase64Str(String processBase64Str) {
		this.processBase64Str = processBase64Str;
	}
}
