package cn.edu.nuc.springbootmybatisdruid.dao.mysql.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
//扫描 Mapper 接口并容器管理
@MapperScan(basePackages = DatasourceConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class DatasourceConfig {
	// 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "cn.edu.nuc.springbootmybatisdruid.dao.mysql";
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Value("${ds1.datasource.url}")
    private String url;
    @Value("${ds1.datasource.username}")
    private String user;
    @Value("${ds1.datasource.password}")
    private String password;
    @Value("${ds1.datasource.driverClassName}")
    private String driverClass;
    
    @Value("${ds1.datasource.maxActive}")
    private Integer maxActive;
    @Value("${ds1.datasource.minIdle}")
    private Integer minIdle;
    @Value("${ds1.datasource.initialSize}")
    private Integer initialSize;
    @Value("${ds1.datasource.maxWait}")
    private Long maxWait;
    @Value("${ds1.datasource.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;
    @Value("${ds1.datasource.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;
    @Value("${ds1.datasource.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${ds1.datasource.testWhileIdle}")
    private Boolean testOnBorrow;
    @Value("${ds1.datasource.testOnBorrow}")
    private Boolean testOnReturn;

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
    	//jdbc配置
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        
        //连接池配置
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setValidationQuery("SELECT 'x'");
        
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        
        try {
			dataSource.setFilters("stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return dataSource;
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory ds1SqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("cn.edu.nuc.springbootmybatisdruid.entity");
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DatasourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
    
    
    @Bean
    public ServletRegistrationBean druidServlet() {
      ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
      servletRegistrationBean.setServlet(new StatViewServlet());
      servletRegistrationBean.addUrlMappings("/druid/*");
      Map<String, String> initParameters = new HashMap<String, String>();
      initParameters.put("loginUsername", "admin");// 用户名
      initParameters.put("loginPassword", "admin");// 密码
      initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
      initParameters.put("allow", ""); // IP白名单 (没有配置或者为空，则允许所有访问)
      //initParameters.put("deny", "192.168.20.38");// IP黑名单 (存在共同时，deny优先于allow)
      servletRegistrationBean.setInitParameters(initParameters);
      return servletRegistrationBean;
    }
    
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
      FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
      filterRegistrationBean.setFilter(new WebStatFilter());
      filterRegistrationBean.addUrlPatterns("/*");
      filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
      return filterRegistrationBean;
    }

}
