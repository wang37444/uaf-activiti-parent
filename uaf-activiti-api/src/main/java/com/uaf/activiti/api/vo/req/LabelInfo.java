package com.uaf.activiti.api.vo.req;

import java.io.Serializable;

/**
 * @filename labelInfo
 * @description 标签信息
 * 1. 存储节点审批需要的条件等动态数据
 * 2. opinionCode[审批意见代码],leaveDays[请假天数]
 * @autor 王承
 * @date 2019/12/2 14:38
 */
public class LabelInfo implements Serializable {

	/**标签名称*/
	private String labName;

	/**标签类型[S:string,I:int,D:double]*/
	private String labType;

	/**标签值*/
	private String labValue;

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public String getLabType() {
		return labType;
	}

	public void setLabType(String labType) {
		this.labType = labType;
	}

	public String getLabValue() {
		return labValue;
	}

	public void setLabValue(String labValue) {
		this.labValue = labValue;
	}

}
