package com.uaf.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;

import com.uaf.log.MySlf4j;

/**
 * @filename ActProcessListener
 * @description 流程监听
 * @autor 王承
 * @date 2019/12/1 9:58
 */
public class ActProcessListener implements TaskListener {

	/**流程监听成员变量*/
	private FixedValue processCode;

	@Override
	public void notify(DelegateTask delegateTask) {
		//TODO 新建流程节点与审批用户/角色关联表，实现流程动态监听
		String process = String.valueOf(processCode.getValue(delegateTask));
		MySlf4j.textInfo("进入{0}流程节点监听.....", process);
		if ("leaveApply".equals(process)) {
			delegateTask.addCandidateUser("will");
		} else if ("deptManagerAppr".equals(process)) {
			delegateTask.addCandidateUser("john");
		} else if ("hrClerkAppr".equals(process)) {
			delegateTask.addCandidateUser("petty");
		} else if ("hrManagerAppr".equals(process)) {
			delegateTask.addCandidateUser("may");
		}

	}
}
