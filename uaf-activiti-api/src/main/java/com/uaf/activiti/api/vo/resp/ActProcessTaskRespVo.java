package com.uaf.activiti.api.vo.resp;

import java.io.Serializable;
import java.util.List;

/**
 * @filename ActTodoTaskRespVo
 * @description 流程任务响应
 * @autor 王承
 * @date 2019/12/2 9:22
 */
public class ActProcessTaskRespVo implements Serializable {

	private static final long serialVersionUID = 4184520040460809933L;

	/**总记录数*/
	private Integer totalNum;

	/**返回记录数*/
	private Integer retNum;

	/**待办任务列表*/
	private List<ActProcessTask> actProcessTasks;

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getRetNum() {
		return retNum;
	}

	public void setRetNum(Integer retNum) {
		this.retNum = retNum;
	}

	public List<ActProcessTask> getActProcessTasks() {
		return actProcessTasks;
	}

	public void setActProcessTasks(List<ActProcessTask> actProcessTasks) {
		this.actProcessTasks = actProcessTasks;
	}

	/**
	 * 内部类
	 * @return
	 * @author 王承
	 * @date 2019/12/2 9:48
	 */
	public static class ActProcessTask implements Serializable {

		/**流程任务Id*/
		private String taskId;

		/**任务状态 1:处理中，2:已结束*/
		private String taskStatus;

		/**当前流程实例Id*/
		private String processInstanceId;

		/**流程Id*/
		private String processId;

		/**任务名称*/
		private String taskName;

		/**任务所有者*/
		private String taskOwner;

		/**任务委派人*/
		private String taskAssignee;

		/**任务创建时间*/
		private String taskCreateTime;

		/**任务执行时间*/
		private String taskDueDate;

		/**流程名称*/
		private String processName;

		public String getTaskId() {
			return taskId;
		}

		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}

		public String getTaskStatus() {
			return taskStatus;
		}

		public void setTaskStatus(String taskStatus) {
			this.taskStatus = taskStatus;
		}

		public String getProcessInstanceId() {
			return processInstanceId;
		}

		public void setProcessInstanceId(String processInstanceId) {
			this.processInstanceId = processInstanceId;
		}

		public String getProcessId() {
			return processId;
		}

		public void setProcessId(String processId) {
			this.processId = processId;
		}

		public String getTaskName() {
			return taskName;
		}

		public void setTaskName(String taskName) {
			this.taskName = taskName;
		}

		public String getTaskOwner() {
			return taskOwner;
		}

		public void setTaskOwner(String taskOwner) {
			this.taskOwner = taskOwner;
		}

		public String getTaskAssignee() {
			return taskAssignee;
		}

		public void setTaskAssignee(String taskAssignee) {
			this.taskAssignee = taskAssignee;
		}

		public String getTaskCreateTime() {
			return taskCreateTime;
		}

		public void setTaskCreateTime(String taskCreateTime) {
			this.taskCreateTime = taskCreateTime;
		}

		public String getTaskDueDate() {
			return taskDueDate;
		}

		public void setTaskDueDate(String taskDueDate) {
			this.taskDueDate = taskDueDate;
		}

		public String getProcessName() {
			return processName;
		}

		public void setProcessName(String processName) {
			this.processName = processName;
		}
	}
}
