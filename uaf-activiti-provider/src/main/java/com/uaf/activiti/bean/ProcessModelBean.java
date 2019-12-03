package com.uaf.activiti.bean;

import java.io.Serializable;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 * @filename ProcessModelBean
 * @description 流程待办任务
 * @autor 王承
 * @date 2019/11/29 17:59
 */
public class ProcessModelBean implements Serializable {

	private static final long serialVersionUID = 3010937892996342870L;

	/**任务实体*/
	private Task task;

	/**流程实体*/
	private ProcessInstance processInstance;

	/**流程定义实体*/
	private ProcessDefinition processDefinition;

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
}
