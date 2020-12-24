package cn.sp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = "cn.sp.mapper")
@SpringBootApplication
public class ShipAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShipAdminApplication.class, args);
    }

}
