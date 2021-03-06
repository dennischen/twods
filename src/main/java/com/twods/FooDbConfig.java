package com.twods;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "fooEntityManagerFactory", basePackages = { "com.twods.foo.repo" })
public class FooDbConfig {

	@Primary
	@Bean(name = {"dataSource", "fooDataSource"})
	@ConfigurationProperties(prefix = "foo.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = {"entityManagerFactory", "fooEntityManagerFactory"})
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("fooDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.twods.foo.domain").persistenceUnit("foo").build();
	}

	@Primary
	@Bean(name = {"transactionManager", "fooTransactionManager"})
	public PlatformTransactionManager transactionManager(
			@Qualifier("fooEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}