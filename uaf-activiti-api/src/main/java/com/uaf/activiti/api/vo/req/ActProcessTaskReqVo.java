package com.uaf.activiti.api.vo.req;

import java.io.Serializable;

/**
 * @filename ActTodoTaskReqVo
 * @description 待办任务
 * @autor 王承
 * @date 2019/12/2 9:20
 */
public class ActProcessTaskReqVo implements Serializable {

	private static final long serialVersionUID = -4155394775890102279L;

	/**用户Id*/
	private String userId;

	/**流程Id*/
	private String processId;

	/**起始记录号*/
	private Integer startNum;

	/**查询记录数*/
	private Integer recNum;

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

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getRecNum() {
		return recNum;
	}

	public void setRecNum(Integer recNum) {
		this.recNum = recNum;
	}
}
