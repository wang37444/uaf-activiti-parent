package com.uaf.activiti.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uaf.activiti.api.vo.req.ActClaimTaskReqVo;
import com.uaf.activiti.api.vo.req.ActCompleteTaskReqVo;
import com.uaf.activiti.api.vo.req.ActProcessTaskReqVo;
import com.uaf.activiti.api.vo.req.ActWorkflowReqVo;
import com.uaf.activiti.api.vo.req.LabelInfo;
import com.uaf.activiti.api.vo.resp.ActProcessTaskRespVo;
import com.uaf.activiti.api.vo.resp.ActWorkflowRespVo;
import com.uaf.activiti.bean.ProcessModelBean;
import com.uaf.activiti.service.IWorkflowService;
import com.uaf.common.date.utils.DateUtils;
import com.uaf.log.MySlf4j;

/**
 * @filename WorkflowServiceImpl
 * @description
 * @autor 王承
 * @date 2019/11/29 14:25
 */
@Service
public class WorkflowServiceImpl implements IWorkflowService {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public ActWorkflowRespVo startWorkflow(ActWorkflowReqVo<LabelInfo> actWorkflowReqVo) {
		try {
			//1. 设置发起流程用户信息
			//identityService.setAuthenticatedUserId(actWorkflowReqVo.getUserId());
			Map<String, Object> variables = new HashMap<>();
			if (null != actWorkflowReqVo.getLabels()) {
				for (LabelInfo labelInfo : actWorkflowReqVo.getLabels()) {
					if (labelInfo.getLabType().equalsIgnoreCase("S")) {
						variables.put(labelInfo.getLabName(), labelInfo.getLabValue());
					} else if (labelInfo.getLabType().equalsIgnoreCase("I")) {
						variables.put(labelInfo.getLabName(), Integer.parseInt(labelInfo.getLabValue()));
					} else if (labelInfo.getLabType().equalsIgnoreCase("D")) {
						variables.put(labelInfo.getLabName(), Double.parseDouble(labelInfo.getLabValue()));
					}
				}
			}

			//2. 启动流程,并设置流程关联业务
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(actWorkflowReqVo.getProcessId(), actWorkflowReqVo.getBusinessId(),
							variables);

			//3. 返回当前流程实例Id
			ActWorkflowRespVo actWorkflowRespVo = new ActWorkflowRespVo();
			actWorkflowRespVo.setProcessInstanceId(processInstance.getId());

			//4. 获取当前任务Id
			List<ProcessModelBean> processModelBeans = this.findTodoTasks(actWorkflowReqVo.getUserId(), actWorkflowReqVo.getProcessId());
			String taskId = "";
			for (ProcessModelBean processModelBean : processModelBeans) {
				if (processInstance.getId().equals(processModelBean.getProcessInstance().getId())) {
					taskId = processModelBean.getTask().getId();
				}
			}
			actWorkflowRespVo.setTaskId(taskId);
			return actWorkflowRespVo;
		} catch (Exception ex) {
			MySlf4j.textError("当前流程Id:{0},流程启动异常:{1}", actWorkflowReqVo.getProcessId(), MySlf4j.ExceptionToString(ex));
			throw ex;
		} finally {
			//taskService.deleteCandidateUser(null,null);
			identityService.setAuthenticatedUserId(null);
		}
	}

	@Override
	public boolean claimTask(ActClaimTaskReqVo actClaimTaskReqVo) {
		boolean flag = false;
		try {
			taskService.claim(actClaimTaskReqVo.getTaskId(), actClaimTaskReqVo.getUserId());
			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean completeTask(ActCompleteTaskReqVo<LabelInfo> actCompleteTaskReqVo) {
		try {
			//1. 获取当前任务Id
			List<ProcessModelBean> processModelBeans = this
					.findTodoTasks(actCompleteTaskReqVo.getUserId(), actCompleteTaskReqVo.getProcessId());
			String taskId = "";
			for (ProcessModelBean processModelBean : processModelBeans) {
				if (actCompleteTaskReqVo.getProcessInstanceId().equals(processModelBean.getProcessInstance().getId())) {
					taskId = processModelBean.getTask().getId();
				}
			}

			//2. 签收任务
			taskService.claim(taskId, actCompleteTaskReqVo.getUserId());

			//3. 添加任务批注(ACT_HI_COMMENT)
			taskService.addComment(taskId, actCompleteTaskReqVo.getProcessInstanceId(), actCompleteTaskReqVo.getComment());

			//4. 添加审批意见(动态标签赋值)
			Map<String, Object> variables = new HashMap<>();
			if (null != actCompleteTaskReqVo.getLabels()) {
				for (LabelInfo labelInfo : actCompleteTaskReqVo.getLabels()) {
					if (labelInfo.getLabType().equalsIgnoreCase("S")) {
						variables.put(labelInfo.getLabName(), labelInfo.getLabValue());
					} else if (labelInfo.getLabType().equalsIgnoreCase("I")) {
						variables.put(labelInfo.getLabName(), Integer.parseInt(labelInfo.getLabValue()));
					} else if (labelInfo.getLabType().equalsIgnoreCase("D")) {
						variables.put(labelInfo.getLabName(), Double.parseDouble(labelInfo.getLabValue()));
					}
				}
			}

			//5. 设置当前处理任务的用户的userId
			identityService.setAuthenticatedUserId(actCompleteTaskReqVo.getUserId());

			//TODO 采用监听方式时需要指定下一节点任务处理人。

			// variables.put("userName","petty");
			//6. 提交任务
			taskService.complete(taskId, variables);
			return true;
		} catch (Exception ex) {
			MySlf4j.textError("当前流程实例Id:{0},任务提交异常:{1}", actCompleteTaskReqVo.getProcessInstanceId(),
					MySlf4j.ExceptionToString(ex));
			throw ex;
		}

	}

	@Override
	public boolean checkClaimTask(ActClaimTaskReqVo actClaimTaskReqVo) {
		boolean isReturn = false;
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(actClaimTaskReqVo.getUserId()).list();
		for (Task task : tasks) {
			if (actClaimTaskReqVo.getTaskId().equals(task.getId())) {
				isReturn = true;
			} else {
				isReturn = false;
			}
		}
		return isReturn;
	}

	@Override
	public boolean releaseTask(String taskId) {
		boolean flag = false;
		try {
			//TODO 添加任务批注(ACT_HI_COMMENT)
			taskService.setAssignee(taskId, null);
			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public ActProcessTaskRespVo queryTodoTasks(ActProcessTaskReqVo actProcessTaskReqVo) {
		//1. 待办查询
		List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(actProcessTaskReqVo.getProcessId())
				.taskCandidateOrAssigned(actProcessTaskReqVo.getUserId()).active().orderByTaskPriority().desc()
				.orderByTaskCreateTime().desc()
				.listPage(actProcessTaskReqVo.getStartNum(), actProcessTaskReqVo.getRecNum());
		if (null == tasks || tasks.size() == 0) {
			return null;
		}

		//2. 封装返回
		List<ActProcessTaskRespVo.ActProcessTask> actProcessTasks = new ArrayList<>();
		ActProcessTaskRespVo actProcessTaskRespVo = new ActProcessTaskRespVo();
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).active().singleResult();

			ActProcessTaskRespVo.ActProcessTask processTask = new ActProcessTaskRespVo.ActProcessTask();
			processTask.setTaskId(task.getId());
			processTask.setTaskStatus("1");   //处理中
			processTask.setProcessInstanceId(task.getProcessInstanceId());
			processTask.setProcessId(actProcessTaskReqVo.getProcessId());
			processTask.setTaskName(task.getName());
			processTask.setTaskOwner(task.getOwner());
			processTask.setTaskAssignee(task.getAssignee());
			processTask.setTaskCreateTime(DateUtils.dateToStr(task.getCreateTime(), DateUtils.DATETIME_DEFAULT_FORMAT));
			processTask.setTaskDueDate(DateUtils.dateToStr(task.getDueDate(), DateUtils.DATETIME_DEFAULT_FORMAT));
			processTask.setProcessName(processInstance.getName());
			actProcessTasks.add(processTask);
		}
		actProcessTaskRespVo.setRetNum(actProcessTaskReqVo.getRecNum());
		actProcessTaskRespVo.setTotalNum(tasks.size());
		actProcessTaskRespVo.setActProcessTasks(actProcessTasks);
		return actProcessTaskRespVo;
	}

	@Override
	public ActProcessTaskRespVo queryFinishTasks(ActProcessTaskReqVo actProcessTaskReqVo) {
		//TODO 查已结束任务

		return null;
	}

	/**
	 * 查询待办任务
	 * @param userId 用户Id
	 * @param processId 流程Id
	 * @return java.util.List<com.uaf.activiti.bean.ProcessModelBean>
	 * @author 王承
	 * @date 2019/11/29 18:09
	 */
	public List<ProcessModelBean> findTodoTasks(String userId, String processId) {
		List<ProcessModelBean> results = new ArrayList<ProcessModelBean>();
		//1. 待办任务查询
		List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(processId).taskCandidateOrAssigned(userId)
				.active().orderByTaskPriority().desc().orderByTaskCreateTime().desc().list();

		//2. 根据流程的业务ID查询实体并关联
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).active().singleResult();
			String businessKey = processInstance.getBusinessKey();
			ProcessModelBean processModelBean = new ProcessModelBean();
			processModelBean.setTask(task);
			processModelBean.setProcessInstance(processInstance);
			processModelBean.setProcessDefinition(repositoryService.createProcessDefinitionQuery()
					.processDefinitionId((processInstance.getProcessDefinitionId())).singleResult());
			results.add(processModelBean);
		}
		return results;
	}

}
