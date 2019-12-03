package com.uaf.activiti.api.vo.req;

import java.io.Serializable;

/**
 * @filename ActClaimTaskReqVo
 * @description 签收任务
 * @autor 王承
 * @date 2019/11/29 15:27
 */
public class ActClaimTaskReqVo implements Serializable {

	private static final long serialVersionUID = 1006193011602558676L;

	/**用户Id*/
	private String userId;

	/**业务Id*/
	private String businessId;

	/**任务Id*/
	private String taskId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
