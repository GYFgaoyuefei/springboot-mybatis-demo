package cn.edu.nuc.springbootmybatisdynamicwr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Hello world!
 * 可以统一用@MapperScan指定扫描的dao，也可以在每个dao上添加@Mapper
 */
@SpringBootApplication
//mapper 接口类扫描包配置
@MapperScan("cn.edu.nuc.springbootmybatisdynamicwr.dao.mysql")
public class App {
    public static void main( String[] args ) {
    	SpringApplication.run(App.class, args);
    }
}
