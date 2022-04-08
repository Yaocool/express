package edu.hevttc.express.controller.admin;

import edu.hevttc.express.dto.YearDataDto;
import edu.hevttc.express.service.ExpressService;
import edu.hevttc.express.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/UsersData")
public class StaffUsersDataController {
    @Autowired
    private ExpressService expressService;
    @Autowired
    private SysUserService sysUserService;

    private static final String[] MONTH = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};

    /**
     * 查询当前用户当前年的所有订单成交量
     * @return
     */
    @GetMapping("/ordersYearData")
    @ResponseBody
    public Map<String, Object> queryCurrentYearOrders() {
        List<YearDataDto> yearDataDtos = expressService.queryAllUsersOrdersYearData();
        Integer[] count = new Integer[12];
        for (YearDataDto yearDataDto : yearDataDtos) {
            for (int i = 1; i <= 12; i++) {
                if (yearDataDto.getMonth() == i) {
                    count[i-1] = yearDataDto.getCount();
                }
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("month", MONTH);
        map.put("count", count);
        return map;
    }

    /**
     * 查询当前用户当前年的所有订单成交量
     * @return
     */
    @GetMapping("/usersYearData")
    @ResponseBody
    public Map<String, Object> queryCurrentYearUsers() {
        List<YearDataDto> yearDataDtos = sysUserService.queryCurrentYearUsersCount();
        Integer[] count = new Integer[12];
        for (YearDataDto yearDataDto : yearDataDtos) {
            for (int i = 1; i <= 12; i++) {
                if (yearDataDto.getMonth() == i) {
                    count[i-1] = yearDataDto.getCount();
                }
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("month", MONTH);
        map.put("count", count);
        return map;
    }
}
