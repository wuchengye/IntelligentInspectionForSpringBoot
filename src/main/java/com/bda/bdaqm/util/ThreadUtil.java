package com.bda.bdaqm.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Spring的ThreadPoolTaskExecutor生成工具
 * @author Mars
 *
 */
public class ThreadUtil  {
	
	/**
	 * 获取线程池
	 * @param maxPoolSize 线程池核心线程数量
	 * @return
	 */
	public static ThreadPoolTaskExecutor getThreadPoolTaskExecutor(int maxPoolSize ){
		ThreadPoolTaskExecutor poolTaskExecutor =  new ThreadPoolTaskExecutor();
		//设置核心线程池数量
		poolTaskExecutor.setCorePoolSize(maxPoolSize);
		//设置最大线程池数量
		poolTaskExecutor.setMaxPoolSize(100);
		//队列容量
		poolTaskExecutor.setQueueCapacity(100);
		//线程池维护线程所允许的空闲时间
		poolTaskExecutor.setKeepAliveSeconds(60*60*5);
		//队列满，线程被拒绝执行策略
		poolTaskExecutor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy()); 
		//初始化线程池
		poolTaskExecutor.initialize();
		return poolTaskExecutor;
	}

}
