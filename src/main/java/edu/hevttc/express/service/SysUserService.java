package edu.hevttc.express.service;

import edu.hevttc.express.dto.YearDataDto;
import edu.hevttc.express.pojo.SysUser;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * 职员表 服务类
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 用户名是否存在。存在：true
     */
    boolean hasExistUserName(String userName);

    /**
     * 根据用户名查询
     */
    SysUser getByUserName(String userName);

    /**
     * 查询当前年的所有用户增长量
     */
    List<YearDataDto> queryCurrentYearUsersCount();
}
