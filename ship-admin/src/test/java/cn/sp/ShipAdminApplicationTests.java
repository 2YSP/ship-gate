package cn.sp;

import cn.sp.pojo.dto.AppInfoDTO;
import cn.sp.service.AppService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShipAdminApplicationTests {

    @Autowired
    private AppService appService;

    @Test
    void contextLoads() {
        List<AppInfoDTO> appInfos = appService.getAppInfos(Lists.newArrayList("order"));
        System.out.println(appInfos);
    }

}
