package cn.sp.mapper;

import cn.sp.bean.AppPlugin;
import cn.sp.pojo.AppPluginDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
public interface AppPluginMapper extends BaseMapper<AppPlugin> {

    List<AppPluginDTO> queryEnabledPlugins(@Param("appIds") List<Integer> appIds);
}
