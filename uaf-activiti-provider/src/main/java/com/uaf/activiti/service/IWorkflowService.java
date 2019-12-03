package com.uaf.activiti.service;

import com.uaf.activiti.api.vo.req.ActClaimTaskReqVo;
import com.uaf.activiti.api.vo.req.ActCompleteTaskReqVo;
import com.uaf.activiti.api.vo.req.ActProcessTaskReqVo;
import com.uaf.activiti.api.vo.req.ActWorkflowReqVo;
import com.uaf.activiti.api.vo.req.LabelInfo;
import com.uaf.activiti.api.vo.resp.ActProcessTaskRespVo;
import com.uaf.activiti.api.vo.resp.ActWorkflowRespVo;

/**
 * @filename IWorkflowService
 * @description 工作流
 * @autor 王承
 * @date 2019/11/29 14:19
 */
public interface IWorkflowService {

	/**
	 * 启动流程
	 * @param actWorkflowReqVo
	 * @return java.lang.String
	 * @author 王承
	 * @date 2019/11/29 14:43
	 */
	ActWorkflowRespVo startWorkflow(ActWorkflowReqVo<LabelInfo> actWorkflowReqVo);

	/**
	 * 签收任务(该接口已并入提交任务接口)
	 * @param actClaimTaskReqVo
	 * @return boolean
	 * @author 王承
	 * @date 2019/11/29 18:10
	 */
	boolean claimTask(ActClaimTaskReqVo actClaimTaskReqVo);

	/**
	 * 提交任务
	 * @param actCompleteTaskReqVo
	 * @return boolean
	 * @author 王承
	 * @date 2019/11/29 19:02
	 */
	boolean completeTask(ActCompleteTaskReqVo<LabelInfo> actCompleteTaskReqVo);

	/**
	 * 查询任务是否已签收
	 * @param actClaimTaskReqVo
	 * @return boolean
	 * @author 王承
	 * @date 2019/12/1 10:28
	 */
	boolean checkClaimTask(ActClaimTaskReqVo actClaimTaskReqVo);

	/**
	 * 释放任务
	 * @param taskId 任务Id
	 * @return boolean
	 * @author 王承
	 * @date 2019/11/29 19:11
	 */
	boolean releaseTask(String taskId);

	/**
	 * 查询待办任务
	 * @param actProcessTaskReqVo
	 * @return com.uaf.activiti.api.vo.resp.ActTodoTaskRespVo
	 * @author 王承
	 * @date 2019/12/2 9:45
	 */
	ActProcessTaskRespVo queryTodoTasks(ActProcessTaskReqVo actProcessTaskReqVo);

	/**
	 * 查询已结束任务
	 * @param actTodoTaskReqVo
	 * @return com.uaf.activiti.api.vo.resp.ActProcessTaskRespVo
	 * @author 王承
	 * @date 2019/12/2 11:17
	 */
	ActProcessTaskRespVo queryFinishTasks(ActProcessTaskReqVo actTodoTaskReqVo);

	//TODO 查询流程监控列表（含待处理、处理中、已结束任务）

	//TODO 查询流程监控详情

}
