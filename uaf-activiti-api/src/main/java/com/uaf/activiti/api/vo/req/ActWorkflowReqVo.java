package com.uaf.activiti.api.vo.req;

import java.io.Serializable;
import java.util.List;

/**
 * @filename ActWorkflowReqVo
 * @description 工作流
 * @autor 王承
 * @date 2019/11/29 14:31
 */
public class ActWorkflowReqVo<T> implements Serializable {

	private static final long serialVersionUID = -253845507895675738L;

	/**用户Id*/
	private String userId;

	/**流程Id*/
	private String processId;

	/**业务Id*/
	private String businessId;

	/**任务动态标签列表*/
	private List<T> labels;

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

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public List<T> getLabels() {
		return labels;
	}

	public void setLabels(List<T> labels) {
		this.labels = labels;
	}
}
