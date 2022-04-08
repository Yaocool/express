package edu.hevttc.express.controller.staff;

import edu.hevttc.express.controller.GlobalFunction;
import edu.hevttc.express.dto.MonthDataDto;
import edu.hevttc.express.dto.YearDataDto;
import edu.hevttc.express.service.ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/staff/userData")
public class StaffDataController {
    @Autowired
    private GlobalFunction globalFunction;
    @Autowired
    private ExpressService expressService;

    private static final String[] MONTH = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
    private static final String[] DAY = {"1日", "2日", "3日", "4日", "5日", "6日", "7日", "8日", "9日", "10日", "11日", "12日", "13日", "14日", "15日", "16日", "17日", "18日", "19日", "20日", "21日", "22日", "23日", "24日", "25日", "26日", "27日", "28日", "29日", "30日", "31日"};

    /**
     * 查询当前用户当前年的所有订单成交量
     *
     * @return
     */
    @GetMapping("/staffYearData")
    @ResponseBody
    public Map<String, Object> queryCurrentYearOrdersByUserId() {
        List<YearDataDto> yearDataDtos = expressService.queryAllCurrentYearOrdersByUserId(globalFunction.getUserId());
        Integer[] count = new Integer[12];
        for (YearDataDto yearDataDto : yearDataDtos) {
            for (int i = 1; i <= 12; i++) {
                if (yearDataDto.getMonth() == i) {
                    count[i - 1] = yearDataDto.getCount();
                }
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("month", MONTH);
        map.put("count", count);
        return map;
    }

    /**
     * 查询当前用户近30天的所有订单成交量
     *
     * @return
     */
    @GetMapping("/staffMonthData")
    @ResponseBody
    public Map<String, Object> queryNearlyThirtyDaysOrdersByUserId() {
        List<MonthDataDto> MonthDataDtos = expressService.queryAllCurrentMonthOrdersByUserId(globalFunction.getUserId());
        Integer[] num = new Integer[31];
        for (MonthDataDto MonthDataDto : MonthDataDtos) {
            for (int i = 1; i <= 31; i++) {
                if (MonthDataDto.getDay() == i) {
                    num[i - 1] = MonthDataDto.getCount();
                }
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("day", DAY);
        map.put("num", num);
        return map;
    }
}
