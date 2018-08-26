package cn.edu.nuc.springbootmybatisdynamicwr.dao.mysql.config;

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
    static final String PACKAGE = "cn.edu.nuc.springbootmybatisdynamicwr.dao.mysql";
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Value("${ds1.w.datasource.url}")
    private String wurl;
    @Value("${ds1.w.datasource.username}")
    private String wuser;
    @Value("${ds1.w.datasource.password}")
    private String wpassword;
    @Value("${ds1.w.datasource.driverClassName}")
    private String wdriverClass;
    
    @Value("${ds1.r.datasource.url}")
    private String rurl;
    @Value("${ds1.r.datasource.username}")
    private String ruser;
    @Value("${ds1.r.datasource.password}")
    private String rpassword;
    @Value("${ds1.r.datasource.driverClassName}")
    private String rdriverClass;
    
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

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dynamicDataSource() {
    	DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();

    	//jdbc配置
        DruidDataSource wdataSource = new DruidDataSource();
        wdataSource.setDriverClassName(wdriverClass);
        wdataSource.setUrl(wurl);
        wdataSource.setUsername(wuser);
        wdataSource.setPassword(wpassword);
        
        //连接池配置
        wdataSource.setMaxActive(maxActive);
        wdataSource.setMinIdle(minIdle);
        wdataSource.setInitialSize(initialSize);
        wdataSource.setMaxWait(maxWait);
        wdataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        wdataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        wdataSource.setTestWhileIdle(testWhileIdle);
        wdataSource.setTestOnBorrow(testOnBorrow);
        wdataSource.setTestOnReturn(testOnReturn);
        wdataSource.setValidationQuery("SELECT 'x'");
        wdataSource.setPoolPreparedStatements(true);
        wdataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        try {
        	wdataSource.setFilters("stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        //jdbc配置
        DruidDataSource rdataSource = new DruidDataSource();
        rdataSource.setDriverClassName(rdriverClass);
        rdataSource.setUrl(rurl);
        rdataSource.setUsername(ruser);
        rdataSource.setPassword(rpassword);
        
        //连接池配置
        rdataSource.setMaxActive(maxActive);
        rdataSource.setMinIdle(minIdle);
        rdataSource.setInitialSize(initialSize);
        rdataSource.setMaxWait(maxWait);
        rdataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        rdataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        rdataSource.setTestWhileIdle(testWhileIdle);
        rdataSource.setTestOnBorrow(testOnBorrow);
        rdataSource.setTestOnReturn(testOnReturn);
        rdataSource.setValidationQuery("SELECT 'x'");
        rdataSource.setPoolPreparedStatements(true);
        rdataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        try {
			rdataSource.setFilters("stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        Map<Object,Object> map = new HashMap<>();
        map.put("ds1_w", wdataSource);
        map.put("ds1_r", rdataSource);
        
        dynamicDataSource.setTargetDataSources(map);
        dynamicDataSource.setDefaultTargetDataSource(rdataSource);
        
        return dynamicDataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dynamicDataSource);
        sessionFactory.setTypeAliasesPackage("cn.edu.nuc.springbootmybatisdynamicwr.entity");
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
