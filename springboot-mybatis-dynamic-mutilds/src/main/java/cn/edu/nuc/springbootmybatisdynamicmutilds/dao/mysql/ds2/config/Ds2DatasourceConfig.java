package cn.edu.nuc.springbootmybatisdynamicmutilds.dao.mysql.ds2.config;

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
@MapperScan(basePackages = Ds2DatasourceConfig.PACKAGE, sqlSessionFactoryRef = "ds2SqlSessionFactory")
public class Ds2DatasourceConfig {
	// 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "cn.edu.nuc.springbootmybatisdynamicmutilds.dao.mysql.ds2";
    static final String MAPPER_LOCATION = "classpath:mapper/ds2/*.xml";

    @Value("${ds2.w.datasource.url}")
    private String wurl;
    @Value("${ds2.w.datasource.username}")
    private String wuser;
    @Value("${ds2.w.datasource.password}")
    private String wpassword;
    @Value("${ds2.w.datasource.driverClassName}")
    private String wdriverClass;
    
    @Value("${ds2.r.datasource.url}")
    private String rurl;
    @Value("${ds2.r.datasource.username}")
    private String ruser;
    @Value("${ds2.r.datasource.password}")
    private String rpassword;
    @Value("${ds2.r.datasource.driverClassName}")
    private String rdriverClass;
    
    @Value("${ds2.datasource.maxActive}")
    private Integer maxActive;
    @Value("${ds2.datasource.minIdle}")
    private Integer minIdle;
    @Value("${ds2.datasource.initialSize}")
    private Integer initialSize;
    @Value("${ds2.datasource.maxWait}")
    private Long maxWait;
    @Value("${ds2.datasource.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;
    @Value("${ds2.datasource.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;
    @Value("${ds2.datasource.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${ds2.datasource.testWhileIdle}")
    private Boolean testOnBorrow;
    @Value("${ds2.datasource.testOnBorrow}")
    private Boolean testOnReturn;

    @Bean(name = "ds2DynamicDataSource")
    public Ds2DynamicDataSource ds2DynamicDataSource() {
    	Ds2DynamicDataSource dynamicDataSource = Ds2DynamicDataSource.getInstance();

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
        map.put("ds2_w", wdataSource);
        map.put("ds2_r", rdataSource);
        
        dynamicDataSource.setTargetDataSources(map);
        dynamicDataSource.setDefaultTargetDataSource(rdataSource);
        
        return dynamicDataSource;
    }

    @Bean(name = "ds2TransactionManager")
    public DataSourceTransactionManager ds2TransactionManager() {
        return new DataSourceTransactionManager(ds2DynamicDataSource());
    }

    @Bean(name = "ds2SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("ds2DynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dynamicDataSource);
        sessionFactory.setTypeAliasesPackage("cn.edu.nuc.springbootmybatisdynamicmutilds.entity");
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(Ds2DatasourceConfig.MAPPER_LOCATION));
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
