package com.uaf.activiti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.uaf.activiti.api.vo.req.ActClaimTaskReqVo;
import com.uaf.activiti.api.vo.req.ActCompleteTaskReqVo;
import com.uaf.activiti.api.vo.req.ActProcessReqVo;
import com.uaf.activiti.api.vo.req.ActProcessTaskReqVo;
import com.uaf.activiti.api.vo.req.ActWorkflowReqVo;
import com.uaf.activiti.api.vo.req.LabelInfo;
import com.uaf.activiti.api.vo.resp.ActProcessTaskRespVo;
import com.uaf.activiti.api.vo.resp.ActWorkflowRespVo;
import com.uaf.activiti.service.IActivitiService;
import com.uaf.activiti.service.IWorkflowService;
import com.uaf.common.json.utils.JsonUtils;
import com.uaf.common.security.utils.Base64Utils;
import com.uaf.log.MySlf4j;

/**
 * @filename ActivitiProcessTest
 * @description
 * @autor 王承
 * @date 2019/11/29 12:36
 */
@SpringBootTest(classes = ActivitiApplication.class)
@RunWith(SpringRunner.class)
public class ActivitiProcessTest {

	@Autowired
	private IActivitiService activitiService;
	@Autowired
	private IWorkflowService workflowService;

	/**流程文件路径*/
	static String filePath = System.getProperty("user.dir") + "/src/main/resources/processes/leaveProcess.bpmn";

	/**
	 * 流程部署
	 * @return void
	 * @author 王承
	 * @date 2019/11/29 12:37
	 */
	@Test
	public void testDeployProcess() throws IOException {
		byte[] b = Files.readAllBytes(Paths.get(filePath));
		ActProcessReqVo actProcessReqVo = new ActProcessReqVo();
		actProcessReqVo.setProcessId("leaveProcess");
		actProcessReqVo.setProcessName("请假流程");
		actProcessReqVo.setProcessFileType("bpmn");
		actProcessReqVo.setProcessBase64Str(Base64Utils.encode(b));
		activitiService.deployProcess(actProcessReqVo);
	}

	/**
	 * 启动流程
	 * @param
	 * @return void
	 * @author 王承
	 * @date 2019/11/30 18:13
	 */
	@Test
	public void testStartWorkflow() {
		ActWorkflowReqVo<LabelInfo> actWorkflowReqVo = new ActWorkflowReqVo<>();
		actWorkflowReqVo.setBusinessId("201912030001");
		actWorkflowReqVo.setProcessId("leaveProcess");

		List<LabelInfo> labelInfos = new ArrayList<>();
		LabelInfo labelInfo1 = new LabelInfo();
		labelInfo1.setLabName("userName");
		labelInfo1.setLabType("S");
		labelInfo1.setLabValue("will");
		labelInfos.add(labelInfo1);
		actWorkflowReqVo.setLabels(labelInfos);

		ActWorkflowRespVo actWorkflowRespVo = workflowService.startWorkflow(actWorkflowReqVo);
		MySlf4j.textInfo("启动流程输出:{0}", JsonUtils.toJson(actWorkflowRespVo));
		//{"taskId":"35006","processInstanceId":"35001"}
	}

	/**
	 * 提交任务-请假申请
	 * @param
	 * @return void
	 * @author 王承
	 * @date 2019/11/30 18:42
	 */
	@Test
	public void testCompleteTask1() {
		ActCompleteTaskReqVo actCompleteTaskReqVo = new ActCompleteTaskReqVo();
		actCompleteTaskReqVo.setComment("个人事务请假1天.....");
		actCompleteTaskReqVo.setProcessInstanceId("2501");
		actCompleteTaskReqVo.setUserId("will");
		actCompleteTaskReqVo.setProcessId("leaveProcess");
		boolean res = workflowService.completeTask(actCompleteTaskReqVo);
		MySlf4j.textInfo("签收任务流程输出:{0}", res);
	}

	/**
	 * 提交任务-部门主管审批
	 * @param
	 * @return void
	 * @author 王承
	 * @date 2019/11/30 18:42
	 */
	@Test
	public void testCompleteTask2() {
		//{"taskId":"47506","processInstanceId":"47501"}
		ActCompleteTaskReqVo<LabelInfo> actCompleteTaskReqVo = new ActCompleteTaskReqVo<>();
		actCompleteTaskReqVo.setComment("同意");
		actCompleteTaskReqVo.setProcessInstanceId("2501");
		actCompleteTaskReqVo.setUserId("john");
		actCompleteTaskReqVo.setProcessId("leaveProcess");

		List<LabelInfo> labelInfos = new ArrayList<>();
		LabelInfo labelInfo1 = new LabelInfo();
		labelInfo1.setLabName("opinion");
		labelInfo1.setLabType("S");
		labelInfo1.setLabValue("TH");

		LabelInfo labelInfo2 = new LabelInfo();
		labelInfo2.setLabName("days");
		labelInfo2.setLabType("I");
		labelInfo2.setLabValue("1");

		labelInfos.add(labelInfo1);
		labelInfos.add(labelInfo2);
		actCompleteTaskReqVo.setLabels(labelInfos);

		boolean res = workflowService.completeTask(actCompleteTaskReqVo);
		MySlf4j.textInfo("签收任务流程输出:{0}", res);
	}

	/**
	 * 提交任务-HR审批
	 * @param
	 * @return void
	 * @author 王承
	 * @date 2019/11/30 18:42
	 */
	@Test
	public void testCompleteTask3() {
		//{"taskId":"47506","processInstanceId":"47501"}
		ActCompleteTaskReqVo actCompleteTaskReqVo = new ActCompleteTaskReqVo();
		actCompleteTaskReqVo.setComment("同意");
		actCompleteTaskReqVo.setProcessInstanceId("2501");
		actCompleteTaskReqVo.setUserId("petty");
		actCompleteTaskReqVo.setProcessId("leaveProcess");

		List<LabelInfo> labelInfos = new ArrayList<>();
		LabelInfo labelInfo1 = new LabelInfo();
		labelInfo1.setLabName("opinion");
		labelInfo1.setLabType("S");
		labelInfo1.setLabValue("TH");

		labelInfos.add(labelInfo1);
		actCompleteTaskReqVo.setLabels(labelInfos);

		boolean res = workflowService.completeTask(actCompleteTaskReqVo);
		MySlf4j.textInfo("签收任务流程输出:{0}", res);
	}

	/**
	 * 查看任务是否签收
	 * @param
	 * @return void
	 * @author 王承
	 * @date 2019/12/1 10:42
	 */
	@Test
	public void testCheckClaimTask() {
		ActClaimTaskReqVo actClaimTaskReqVo = new ActClaimTaskReqVo();
		actClaimTaskReqVo.setUserId("will");
		actClaimTaskReqVo.setTaskId("2507");
		boolean res = workflowService.checkClaimTask(actClaimTaskReqVo);
		MySlf4j.textInfo("查看任务是否签收输出:{0}", res);
	}

	/**
	 * 查看待办任务
	 * @return void
	 * @author 王承
	 * @date 2019/12/2 15:49
	 */
	@Test
	public void testQueryTodoTasks() {
		ActProcessTaskReqVo actProcessTaskReqVo = new ActProcessTaskReqVo();
		actProcessTaskReqVo.setProcessId("leaveProcess");
		actProcessTaskReqVo.setUserId("petty");
		actProcessTaskReqVo.setStartNum(0);
		actProcessTaskReqVo.setRecNum(10);
		ActProcessTaskRespVo actProcessTaskRespVo = workflowService.queryTodoTasks(actProcessTaskReqVo);
		MySlf4j.textInfo("查询待办任务输出:{0}", JsonUtils.toJson(actProcessTaskRespVo));
	}

}
