package com.spring.boot;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableMongoRepositories
//@EnableScheduling
public class FinalProjectApplication {

	//주석대신 사용하려면 의존성주입을 여기서 해줘야한다.
	@Autowired
	ApplicationContext applicationContext; //Context가 있으면 앞과 뒤에 뭐가 붙더라도 프로젝트의 전체로 본다.
	
	
	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
	}
	
	@Bean //객체를 생성
	public SqlSessionFactory SqlSessionFactory(DataSource dataSource) throws Exception{
		
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		
		sessionFactory.setDataSource(dataSource);
		
		/*
		 * Resource[] res = new PathMatchingResourcePatternResolver().getResources(
		 * "classpath:mybatis/mapper/*.xml");
		 * 
		 * sessionFactory.setMapperLocations(res);
		 */
		
		//위에 주석대신 이렇게 작성도 가능함
		sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/*.xml"));
		return sessionFactory.getObject();
		
	}

}
