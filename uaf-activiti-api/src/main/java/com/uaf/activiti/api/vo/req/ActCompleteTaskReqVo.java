package com.uaf.activiti.api.vo.req;

import java.io.Serializable;
import java.util.List;

/**
 * @filename ActCompleteTaskReqVo
 * @description 提交任务
 * @autor 王承
 * @date 2019/11/29 18:59
 */
public class ActCompleteTaskReqVo<T> implements Serializable {

	private static final long serialVersionUID = 1983716588054023065L;

	/**当前流程实例Id*/
	private String processInstanceId;

	/**用户Id*/
	private String userId;

	/**流程Id*/
	private String processId;

	/**任务批注*/
	private String comment;

	/**任务动态标签列表*/
	private List<T> labels;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<T> getLabels() {
		return labels;
	}

	public void setLabels(List<T> labels) {
		this.labels = labels;
	}
}
