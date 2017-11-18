package com.mybatis3.util;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.mybatis3.domain.Student;
import com.mybatis3.type_handlers.PhoneNumberTypeHandler;

public class MyBatisSqlSessionFactory {
	private static SqlSessionFactory sqlSessionFactory;
	
	public static SqlSessionFactory getSqlSessionFactory(){
		if(sqlSessionFactory==null){
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(getConfiguration());
		}
		return sqlSessionFactory;
	}
	
	public static Environment getEnvironment(){
		DataSource dataSource = MyDataSourceFactory.getDataSourceFromPropertiesFile();
		TransactionFactory jdbcTranactionFactory = new JdbcTransactionFactory();
		Environment env = new Environment("development", jdbcTranactionFactory, dataSource);
		return env;
	}
	public static Configuration getConfiguration(){
		Configuration configuration = new Configuration(getEnvironment());
		configuration.getTypeAliasRegistry().registerAlias("student", Student.class);
		configuration.getTypeHandlerRegistry().register(PhoneNumberTypeHandler.class);
		//Mappers should be added to the confguration only after registering
		//typeAliases and typeHandlers if they have been used.
		configuration.addMappers("com.mybatis3.mappers");
		return configuration;
	}
	
	public static SqlSession openSession(){
		return getSqlSessionFactory().openSession();
	}
	
}
