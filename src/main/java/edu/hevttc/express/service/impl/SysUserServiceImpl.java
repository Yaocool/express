package edu.hevttc.express.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import edu.hevttc.express.dto.YearDataDto;
import edu.hevttc.express.pojo.SysUser;
import edu.hevttc.express.mapper.SysUserMapper;
import edu.hevttc.express.service.SysUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 职员表 服务实现类
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public boolean hasExistUserName(String email) {
        List<SysUser> list = userMapper.selectList(new EntityWrapper<SysUser>().eq("email", email));
        return list == null || list.size() == 0;
    }

    @Override
    public SysUser getByUserName(String email) {
        List<SysUser> list = userMapper.selectList(new EntityWrapper<SysUser>().eq("email", email));
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<YearDataDto> queryCurrentYearUsersCount() {
        return userMapper.queryCurrentYearUsersCount();
    }
}
