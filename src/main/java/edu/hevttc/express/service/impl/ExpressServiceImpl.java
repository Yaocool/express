package edu.hevttc.express.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import edu.hevttc.express.dto.MonthDataDto;
import edu.hevttc.express.dto.YearDataDto;
import edu.hevttc.express.enums.ExpressStatusEnum;
import edu.hevttc.express.interactive.Msg;
import edu.hevttc.express.mapper.ExpressMapper;
import edu.hevttc.express.pojo.Express;
import edu.hevttc.express.service.ExpressService;
import edu.hevttc.express.utils.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 订单信息表 服务实现类
 */
@Service
@Transactional
public class ExpressServiceImpl extends ServiceImpl<ExpressMapper, Express> implements ExpressService {
    @Autowired
    private ExpressMapper expressMapper;

    @Override
    public String createExpress(Express express) {
        // 生成订单号
        String expressId = RandomUtils.timeId();

        express.setId(expressId);
        express.setStatus(ExpressStatusEnum.WAIT_DIST.getIndex());
        express.setCreateDate(new Date());

        expressMapper.insert(express);

        return expressId;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Msg editExpressInfo(String id, String name, String tel, String message, String address, String remark) {
        Express express = expressMapper.searchExpressById(id);
        if (express == null) {
            return Msg.error("暂无此订单！");
        } else {
            switch (express.getStatus()) {
                // 派送中
                case 2:
                    return Msg.error("订单正在派送，请勿修改！如要修改请致电配送员！");
                // 订单完成
                case 3:
                    return Msg.error("已完成订单无法进行修改操作！");
                // 订单异常
                case 4:
                    return Msg.error("异常订单无法进行修改操作！");
                // 等待派送
                case 1:
                    if (StringUtils.isBlank(name) || StringUtils.isBlank(address) || StringUtils.isBlank(message)) {
                        return Msg.error("修改失败，请填写完整信息！");
                    } else {
                        expressMapper.editExpressInfo(id, name, tel, message, address, remark);
                        return Msg.ok();
                    }
                default:
                    return Msg.error("修改失败！");
            }
        }
    }

    @Override
    public List<YearDataDto> queryAllCurrentYearOrdersByUserId(String userId) {
        return expressMapper.queryYearDataByCurrentUser(userId);
    }

    @Override
    public List<MonthDataDto> queryAllCurrentMonthOrdersByUserId(String userId) {
        return expressMapper.queryCurrentMonthDataByCurrentUser(userId);
    }

    @Override
    public List<YearDataDto> queryAllUsersOrdersYearData() {
        return expressMapper.queryAllUsersOrdersYearData();
    }
}
