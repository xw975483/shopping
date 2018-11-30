package com.taotao.text;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	
	@SuppressWarnings("resource")
	@Test
	public void testpool(){
		JedisPool pool = new JedisPool("192.168.43.100", 6379);
		Jedis jedis = pool.getResource();
		jedis.set("abcd", "1234");
		String string = jedis.get("abcd");
		System.out.println(string);
		jedis.close();
		pool.close();
	}
	
	@SuppressWarnings("resource")
	@Test
	public void testJedisCluster(){
		HashSet<HostAndPort> nodes= new HashSet<>();
		nodes.add(new HostAndPort("192.168.43.100", 7001));
		nodes.add(new HostAndPort("192.168.43.100", 7002));
		nodes.add(new HostAndPort("192.168.43.100", 7003));
		nodes.add(new HostAndPort("192.168.43.100", 7004));
		nodes.add(new HostAndPort("192.168.43.100", 7005));
		nodes.add(new HostAndPort("192.168.43.100", 7006));
		 JedisCluster cluster = new JedisCluster(nodes);
		 cluster.set("a", "ABCD");
		 String string = cluster.get("a");
		 System.out.println(string);
	}
	
	@Test
	public void testJedisTest2(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
		JedisCluster jCluster = (JedisCluster) applicationContext.getBean("redisClient");
		String string = jCluster.get("a");
		System.out.println(string);
		jCluster.close();
	}
	
}
