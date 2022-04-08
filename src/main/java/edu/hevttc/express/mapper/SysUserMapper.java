package edu.hevttc.express.mapper;

import edu.hevttc.express.dto.YearDataDto;
import edu.hevttc.express.pojo.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 职员表 Mapper 接口
 * </p>
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    // 查询当前年所有
    List<YearDataDto> queryCurrentYearUsersCount();

}
