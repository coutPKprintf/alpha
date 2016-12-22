package com.alpha.tools.queue.rabbitmq;


import lombok.Data;

/**
 * User: chenwen
 * Date: 2015-10-29
 */
@Data
public class EventControlConfig {
	private final static int DEFAULT_PORT = 5672;
	
	private final static String DEFAULT_USERNAME = "guest";
	
	private final static String DEFAULT_PASSWORD = "guest";
	
	private final static int DEFAULT_PROCESS_THREAD_NUM = Runtime.getRuntime().availableProcessors() * 2;
	
	private static final int PREFETCH_SIZE = 1;
	
	private String serverHost ;
	
	private int port = DEFAULT_PORT;
	
	private String username = DEFAULT_USERNAME;
	
	private String password = DEFAULT_PASSWORD;
	
	private String virtualHost;
	
	/**
	 * 和rabbitmq建立连接的超时时间
	 */
	private int connectionTimeout = 0;
	
	/**
	 * 事件消息处理线程数，默认是 CPU核数 * 2
	 */
	private int eventMsgProcessNum;
	
	/**
	 * 每次消费消息的预取值
	 */
	private int prefetchSize;
	
	public EventControlConfig(String serverHost) {
		this(serverHost,DEFAULT_PORT,DEFAULT_USERNAME,DEFAULT_PASSWORD,null,0,DEFAULT_PROCESS_THREAD_NUM,DEFAULT_PROCESS_THREAD_NUM);
	}

	public EventControlConfig(String serverHost, int port, String username,
							  String password, String virtualHost, int connectionTimeout,
							  int eventMsgProcessNum, int prefetchSize) {
		super();
		this.serverHost = serverHost;
		this.port = port>0?port:DEFAULT_PORT;
		this.username = username;
		this.password = password;
		this.virtualHost = virtualHost;
		this.connectionTimeout = connectionTimeout>0?connectionTimeout:0;
		this.eventMsgProcessNum = eventMsgProcessNum>0?eventMsgProcessNum:DEFAULT_PROCESS_THREAD_NUM;
		this.prefetchSize = prefetchSize>0?prefetchSize:PREFETCH_SIZE;
	}
}
