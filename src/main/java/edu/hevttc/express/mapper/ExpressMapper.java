package edu.hevttc.express.mapper;

import edu.hevttc.express.dto.MonthDataDto;
import edu.hevttc.express.dto.YearDataDto;
import edu.hevttc.express.pojo.Express;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 */
public interface ExpressMapper extends BaseMapper<Express> {
    @Select("select * from express where id = #{id} for update")
    Express searchExpressById(@Param("id") String id);

    @Update("update express e set e.name = #{name}, e.tel = #{tel}, e.message = #{message}, e.address = #{address}, e.remark = #{remark} where e.id = #{id}")
    void editExpressInfo(@Param("id") String id, @Param("name") String name, @Param("tel") String tel, @Param("message") String message, @Param("address") String address, @Param("remark") String remark);

    // 查询当前用户本年各月份订单成交量
    List<YearDataDto> queryYearDataByCurrentUser(@Param("userId") String userId);

    // 查询当前用户近30天订单成交量
    List<MonthDataDto> queryCurrentMonthDataByCurrentUser(@Param("userId") String userId);

    // 查询所有用户本年各月份订单成交量
    List<YearDataDto> queryAllUsersOrdersYearData();

}
