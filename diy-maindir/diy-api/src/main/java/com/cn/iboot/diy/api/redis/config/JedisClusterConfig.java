package com.cn.iboot.diy.api.redis.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.cn.iboot.diy.api.base.utils.StringUtil;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 集群连接配置
 * @author 
 *
 */
@Configuration
@ConditionalOnClass({JedisCluster.class})
public class JedisClusterConfig {
	
	@Autowired
	private RedisConfigProperies redisConfig;
	
	@Autowired
	private JedisPoolConfig jedisPoolConfig;
	
	@Bean
	public JedisCluster getJedisCluster() {
		String[] serverArray = redisConfig.getClusterNodes().split(",");
		Set<HostAndPort> nodes = new HashSet<>();
			for (String ipPort : serverArray) {
			String[] ipPortPair = ipPort.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
		}
		if(StringUtil.isNotEmpty(redisConfig.getPassword())){
			return new JedisCluster(nodes,redisConfig.getConnectionTimeout(),redisConfig.getSoTimeout(),redisConfig.getMaxAttempts(),redisConfig.getPassword(),jedisPoolConfig);
		}else{
			return new JedisCluster(nodes,redisConfig.getConnectionTimeout(),redisConfig.getSoTimeout(),redisConfig.getMaxAttempts(),jedisPoolConfig);
		}
	}
	
	 @Bean
     public RedisClusterConfiguration getClusterConfiguration() {
	      Map<String, Object> source = new HashMap<String, Object>();
	      source.put("spring.redis.cluster.nodes", redisConfig.getClusterNodes());
	      source.put("spring.redis.cluster.timeout", redisConfig.getTimeout());
	      source.put("spring.redis.cluster.max-redirects", redisConfig.getMaxRedirects());
	      return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
     }
	 
	  @Bean
	  public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory connection = new JedisConnectionFactory(getClusterConfiguration(),jedisPoolConfig);
        return connection;
	  }
}
