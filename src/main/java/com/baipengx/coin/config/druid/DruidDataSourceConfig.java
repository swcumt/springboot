package com.baipengx.coin.config.druid;

import java.sql.SQLException;

import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.baipengx.common.BaseLogger;

@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfig extends BaseLogger implements EnvironmentAware {

	private RelaxedPropertyResolver propertyResolver;

	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
	}

	@Bean
	public DruidDataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(propertyResolver.getProperty("url"));
		datasource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
		datasource.setUsername(propertyResolver.getProperty("username"));
		datasource.setPassword(propertyResolver.getProperty("password"));
		datasource.setInitialSize(Integer.valueOf(propertyResolver.getProperty("druid.initialSize")));
		datasource.setMinIdle(Integer.valueOf(propertyResolver.getProperty("druid.minIdle")));
		datasource.setMaxWait(Long.valueOf(propertyResolver.getProperty("druid.maxWait")));
		datasource.setMaxActive(Integer.valueOf(propertyResolver.getProperty("druid.maxActive")));
		datasource.setMinEvictableIdleTimeMillis(
				Long.valueOf(propertyResolver.getProperty("druid.minEvictableIdleTimeMillis")));
		datasource.setValidationQuery(propertyResolver.getProperty("druid.validationQuery"));
		datasource.setTestWhileIdle(Boolean.valueOf(propertyResolver.getProperty("druid.testWhileIdle")));
		datasource.setTestOnBorrow(Boolean.valueOf(propertyResolver.getProperty("druid.testOnBorrow")));
		datasource.setTestOnReturn(Boolean.valueOf(propertyResolver.getProperty("druid.testOnReturn")));
		datasource.setPoolPreparedStatements(
				Boolean.valueOf(propertyResolver.getProperty("druid.poolPreparedStatements")));
		datasource.setMaxPoolPreparedStatementPerConnectionSize(
				Integer.valueOf(propertyResolver.getProperty("druid.maxPoolPreparedStatementPerConnectionSize")));
		datasource.setRemoveAbandoned(Boolean.valueOf(propertyResolver.getProperty("druid.removeAbandoned")));
		datasource.setRemoveAbandonedTimeout(
				Integer.valueOf(propertyResolver.getProperty("druid.removeAbandonedTimeout")));
		datasource.setLogAbandoned(Boolean.valueOf(propertyResolver.getProperty("druid.logAbandoned")));
		try {
			datasource.setFilters(propertyResolver.getProperty("druid.filters"));
		}
		catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		return datasource;
	}

	@Bean
	public JdkRegexpMethodPointcut pointcut() {
		JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
		pointcut.setPattern(propertyResolver.getProperty("druid.statPointCut"));
		return pointcut;
	}
}