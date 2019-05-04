package com.cn.iboot.diy.api.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfigProperies {
	
    @Value("${spring.redis.cluster.nodes}")
	private String clusterNodes;  //redis 集群所有节点
    
	@Value("${spring.redis.cluster.connection-timeout}")
    private int connectionTimeout;//连接超时时间
    
	@Value("${spring.redis.cluster.soTimeout}")
    private int soTimeout;
	
	@Value("${spring.redis.cluster.max-attempts}")
    private int maxAttempts; //重连次数

	@Value("${spring.redis.cluster.password}")
	private String password;
	
	@Value("${spring.redis.cluster.max-redirects}")
	private int maxRedirects;

	
	@Value("${spring.redis.timeout}") //redis 超时时间
	private int timeout;
	
	@Value("${spring.redis.pool.max-total}")
	private int maxTotal; //最大连接数, 默认8个
	
	@Value("${spring.redis.pool.max-idle}")
	private int maxIdle; //#最大能够保持idel状态的对象数 
	
	@Value("${spring.redis.pool.max-wait}")
	private int maxWait;//#当池内没有返回对象时，最大等待时间  
	
	@Value("${spring.redis.pool.min-idle}")
	private int minIdle; //最小空闲连接数, 默认0
	
	@Value("${spring.redis.pool.block-when-exhausted}")
	private boolean blockWhenExhausted;//连接耗尽时是否阻塞
	
	@Value("${spring.redis.pool.max-wait-millis}")
	private long maxWaitMillis; //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
	
	@Value("${spring.redis.pool.test-on-borrow}")
	private boolean testOnBorrow; //在获取连接的时候检查有效性, 默认false
	
	@Value("${spring.redis.pool.test-on-return}")
	private boolean testOnReturn;  //在return给pool时，是否提前进行validate操作
	
	@Value("${spring.redis.pool.test-while-idle}")
	private boolean testWhileIdle; //在空闲时检查有效性, 默认false
	
	@Value("${spring.redis.pool.minEvictableIdleTimeMillis}")
	private long minEvictableIdleTimeMillis; //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
	
	@Value("${spring.redis.pool.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis; //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
	
	@Value("${spring.redis.pool.numTestsPerEvictionRun}")
	private int numTestsPerEvictionRun; //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
	
	@Bean
    public JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMinIdle(minIdle);
		jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		jedisPoolConfig.setTestOnReturn(testOnReturn);
		jedisPoolConfig.setTestWhileIdle(testWhileIdle);
		jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		return jedisPoolConfig;
	}

	public String getClusterNodes() {
		return clusterNodes;
	}

	public void setClusterNodes(String clusterNodes) {
		this.clusterNodes = clusterNodes;
	}
	
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public int getMaxAttempts() {
		return maxAttempts;
	}

	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxRedirects() {
		return maxRedirects;
	}

	public void setMaxRedirects(int maxRedirects) {
		this.maxRedirects = maxRedirects;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public boolean isBlockWhenExhausted() {
		return blockWhenExhausted;
	}

	public void setBlockWhenExhausted(boolean blockWhenExhausted) {
		this.blockWhenExhausted = blockWhenExhausted;
	}

	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public int getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}
}
