
package org.pizza.common.web.model;


import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author gv
 */
@Data
public class WebLog extends BaseLog implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 服务ID
	 */
	protected String serviceId;
	/**
	 * 服务器 ip
	 */
	protected String serverIp;
	/**
	 * 服务器名
	 */
	protected String serverHost;
	/**
	 * 环境
	 */
	protected String env;
	/**
	 * 操作IP地址
	 */
	protected String remoteIp;
	/**
	 * 用户代理
	 */
	protected String userAgent;
	/**
	 * 请求URI
	 */
	protected String requestUri;
	/**
	 * 操作方式
	 */
	protected String method;
	/**
	 * 日志类型
	 */
	private String type;
	/**
	 * 日志标题
	 */
	private String title;

}
