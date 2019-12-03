package com.uaf.activiti.service.impl;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uaf.activiti.api.vo.req.ActProcessReqVo;
import com.uaf.activiti.api.vo.resp.ActProcessRespVo;
import com.uaf.activiti.service.IActivitiService;
import com.uaf.common.security.utils.Base64Utils;
import com.uaf.log.MySlf4j;

/**
 * @filename ActivitiServiceImpl
 * @description
 * @autor 王承
 * @date 2019/11/29 11:13
 */
@Service
public class ActivitiServiceImpl implements IActivitiService {

	@Autowired
	RepositoryService repositoryService;

	@Override
	public ActProcessRespVo deployProcess(ActProcessReqVo actProcessReqVo) {
		Deployment deployment = null;
		try {
			//1. 转输入流
			byte[] bytes = Base64Utils.decode(actProcessReqVo.getProcessBase64Str());
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

			//2. 判断上传流程文件类型，并进行相应处理
			if (actProcessReqVo.getProcessFileType().equals("zip") || actProcessReqVo.getProcessFileType()
					.equals("tar")) {
				ZipInputStream zip = new ZipInputStream(inputStream);
				deployment = this.repositoryService.createDeployment()
						.name(actProcessReqVo.getProcessId().concat(".").concat(actProcessReqVo.getProcessFileType()))
						.addZipInputStream(zip).deploy();
			} else {
				deployment = this.repositoryService.createDeployment().addInputStream(
						actProcessReqVo.getProcessId().concat(".").concat(actProcessReqVo.getProcessFileType()),
						inputStream).deploy();
			}
			MySlf4j.textInfo("部署信息:{0}", deployment.getId());
			//3. 判断流程是否部署成功
			List list = this.repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
			ActProcessRespVo actProcessRespVo = new ActProcessRespVo();
			if (list != null && list.size() > 0) {
				actProcessRespVo.setDeploymentId(deployment.getId());
				actProcessRespVo.setProcessId(actProcessReqVo.getProcessId());
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
