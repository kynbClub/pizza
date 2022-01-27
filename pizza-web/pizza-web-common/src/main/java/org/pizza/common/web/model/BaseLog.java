
package org.pizza.common.web.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 日志记录父类
 * @author gv
 */
@Data
public class BaseLog implements Serializable {
	protected static final long serialVersionUID = 1L;
	/**
	 * 方法类
	 */
	protected String methodClass;
	/**
	 * 方法名
	 */
	protected String methodName;
	/**
	 * 操作提交的数据
	 */
	protected String methodParam;
	/**
	 * 操作提交的数据
	 */
	protected String returnValue;
	/**
	 * 开始时间
	 */
	protected Long startTime;
	/**
	 * 结束时间
	 */
	protected Long endTime;
	/**
	 * 执行时间
	 */
	protected Long execTime;


}
