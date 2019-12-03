package com.uaf.activiti.service;

import com.uaf.activiti.api.vo.req.ActProcessReqVo;
import com.uaf.activiti.api.vo.resp.ActProcessRespVo;

/**
 * @filename IActivitiService
 * @description 流程管理
 * @autor 王承
 * @date 2019/11/29 11:03
 */
public interface IActivitiService {

	/**
	 * 流程部署(根据bpmn文件部署流程)
	 * @param actProcessReqVo 流程部署Vo
	 * @return boolean
	 * @author 王承
	 * @date 2019/11/29 12:05
	 */
	ActProcessRespVo deployProcess(ActProcessReqVo actProcessReqVo);

	//TODO 挂起、激活流程实例

	//TODO 删除部署流程

	//TODO 查询流程定义列表

}
