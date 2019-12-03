package com.uaf.activiti.api.vo.resp;

import java.io.Serializable;

/**
 * @filename ActWorkflowReqVo
 * @description 工作流
 * @autor 王承
 * @date 2019/11/29 14:31
 */
public class ActWorkflowRespVo implements Serializable {

	private static final long serialVersionUID = -253845507895675738L;

	/**流程任务Id*/
	private String taskId;

	/**当前流程实例Id*/
	private String processInstanceId;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
}
