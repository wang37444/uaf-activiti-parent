package com.uaf.activiti.api.vo.resp;

import java.io.Serializable;

/**
 * @filename ActProcessRespVo
 * @description 流程部署
 * @autor 王承
 * @date 2019/11/29 14:05
 */
public class ActProcessRespVo implements Serializable {

	private static final long serialVersionUID = 1875404051482479801L;

	/**流程部署Id*/
	private String deploymentId;

	/**流程Id*/
	private String processId;

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
}
