package edu.hevttc.express.service;

import com.baomidou.mybatisplus.service.IService;
import edu.hevttc.express.dto.MonthDataDto;
import edu.hevttc.express.dto.YearDataDto;
import edu.hevttc.express.interactive.Msg;
import edu.hevttc.express.pojo.Express;

import java.util.List;

/**
 * <p>
 * 订单信息表 服务类
 * </p>
 */
public interface ExpressService extends IService<Express> {

    /**
     * 创建订单
     *
     * @return 订单号
     */
    String createExpress(Express express);

    /**
     * 修改订单
     */
    Msg editExpressInfo(String id, String tel, String name, String message, String address, String remark);

    /**
     * 查询当前年当前用户的所有完成订单
     */
    List<YearDataDto> queryAllCurrentYearOrdersByUserId(String userId);

    /**
     * 查询近30天当前用户的所有完成订单
     */
    List<MonthDataDto> queryAllCurrentMonthOrdersByUserId(String userId);

    /**
     * 查询当前年所有用户的所有完成订单
     */
    List<YearDataDto> queryAllUsersOrdersYearData();

}
