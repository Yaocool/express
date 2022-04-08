package edu.hevttc.express.controller.staff;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import edu.hevttc.express.controller.GlobalFunction;
import edu.hevttc.express.enums.SysUserStatusEnum;
import edu.hevttc.express.interactive.ExpressSelectWrapper;
import edu.hevttc.express.interactive.Msg;
import edu.hevttc.express.dto.ExpressDto;
import edu.hevttc.express.enums.ExpressStatusEnum;
import edu.hevttc.express.pojo.Express;
import edu.hevttc.express.pojo.ExpressPayment;
import edu.hevttc.express.pojo.SysUser;
import edu.hevttc.express.service.ExpressPaymentService;
import edu.hevttc.express.service.ExpressService;
import edu.hevttc.express.service.SysUserService;
import edu.hevttc.express.utils.MaskNameAndTelInfoUtil;
import edu.hevttc.express.utils.SendEmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/staff/express")
public class StaffExpressController {
    @Autowired
    private ExpressService expressService;
    @Autowired
    private ExpressPaymentService expressPaymentService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private GlobalFunction globalFunction;

    /**
     * 获取订单的状态列表
     */
    @GetMapping("/status")
    public Msg listExpressStatus() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (ExpressStatusEnum enums : ExpressStatusEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", enums.getIndex());
            map.put("name", enums.getName());
            result.add(map);
        }
        return Msg.ok(null, result);
    }

    /**
     * 获取所有等待接单的订单
     */
    @RequestMapping("/list")
    public Map<String, Object> listExpress(Integer rows, Integer page, ExpressSelectWrapper esw, @RequestParam(defaultValue = "createDate") String order) {

        // Get请求中文编码
        try {
            esw.setName(globalFunction.iso8859ToUtf8(esw.getName()));
            esw.setAddress(globalFunction.iso8859ToUtf8(esw.getAddress()));
            esw.setStaffName(globalFunction.iso8859ToUtf8(esw.getStaffName()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 得到筛选条件
        EntityWrapper<Express> expressWrapper = globalFunction.getExpressWrapper(esw);
        // 设置仅查找等待接单的订单
        expressWrapper.eq("status", ExpressStatusEnum.WAIT_DIST.getIndex()).ne("user_id", globalFunction.getUserId());
        Page<Express> selectPage = expressService.selectPage(new Page<>(page, rows, order, false), expressWrapper);

        List<ExpressDto> list = globalFunction.express2dto(selectPage.getRecords());

        list.forEach(expressDto -> {
            expressDto.setName(MaskNameAndTelInfoUtil.maskName(expressDto.getName()));
            expressDto.setTel(MaskNameAndTelInfoUtil.maskTel(expressDto.getTel()));
        });

        Map<String, Object> map = new HashMap<>();
        map.put("total", selectPage.getTotal());
        map.put("rows", list);
        return map;
    }

    /**
     * 获取所有已接收的订单
     */
    @RequestMapping("/selfList")
    public Map<String, Object> listSelfExpress(Integer rows, Integer page, ExpressSelectWrapper esw, @RequestParam(defaultValue = "createDate") String order) {
        // Get请求中文编码
        try {
            esw.setName(globalFunction.iso8859ToUtf8(esw.getName()));
            esw.setAddress(globalFunction.iso8859ToUtf8(esw.getAddress()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 得到筛选条件
        EntityWrapper<Express> expressWrapper = globalFunction.getExpressWrapper(esw);
        // 设置查找当前用户的订单
        expressWrapper.eq("staff", globalFunction.getUserId());
        Page<Express> selectPage = expressService.selectPage(new Page<>(page, rows, order, false), expressWrapper);

        List<ExpressDto> list = globalFunction.express2dto(selectPage.getRecords());
        Map<String, Object> map = new HashMap<>();
        map.put("total", selectPage.getTotal());
        map.put("rows", list);
        return map;
    }

    /**
     * 获取所有自己已发布的订单
     */
    @RequestMapping("/selfPublishList")
    public Map<String, Object> listSelfPublishExpress(Integer rows, Integer page, ExpressSelectWrapper esw, @RequestParam(defaultValue = "createDate") String order) {
        // Get请求中文编码
        try {
            esw.setName(globalFunction.iso8859ToUtf8(esw.getName()));
            esw.setAddress(globalFunction.iso8859ToUtf8(esw.getAddress()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 得到筛选条件
        EntityWrapper<Express> expressWrapper = globalFunction.getExpressWrapper(esw);
        // 设置查找当前用户的订单
        String userId = globalFunction.getUserId();
        expressWrapper.eq("user_id", userId);
        Page<Express> selectPage = expressService.selectPage(new Page<>(page, rows, order, false), expressWrapper);

        List<ExpressDto> list = globalFunction.express2dto(selectPage.getRecords());
        Map<String, Object> map = new HashMap<>();
        map.put("total", selectPage.getTotal());
        map.put("rows", list);
        return map;
    }

    /**
     * 获取单个订单详情
     */
    @GetMapping("/{id}")
    public Msg getById(@PathVariable String id) {
        Express express = expressService.selectById(id);
        ExpressDto expressDto = globalFunction.express2dto(express);
        String userId = globalFunction.getUserId();
        // 判断是否是已接单订单 且是否是自己的订单  若未接单且不是自己的则隐藏信息
        if (expressDto.getStaff() == null && !express.getUserId().equals(userId)) {
            expressDto.setName(MaskNameAndTelInfoUtil.maskName(expressDto.getName()));
            expressDto.setTel(MaskNameAndTelInfoUtil.maskTel(expressDto.getTel()));
        }
        return Msg.ok(null, expressDto);
    }

    /**
     * 修改订单
     */
    @RequestMapping("/editExpress")
    @ResponseBody
    public Msg editExpress(String id, String name, String tel, String message, String address, String remark) {
        return expressService.editExpressInfo(id, name, tel, message, address, remark);
    }

    /**
     * 删除订单
     */
    @PostMapping("/deleteExpress")
    @ResponseBody
    public Msg deleteExpress(String id) {
        Express express = expressService.selectById(id);
        if (express != null) {
            boolean flag = expressService.deleteById(id);
            if (flag) {
                return Msg.ok();
            } else {
                return Msg.error("删除失败！");
            }
        } else {
            return Msg.error("删除失败！");
        }
    }


    /**
     * 接单
     */
    @PostMapping("")
    public Msg acceptExpress(String[] ids) {
        SysUser user = globalFunction.getUser();
        if (user.getStatus().equals(SysUserStatusEnum.FREEZE.getIndex())) {
            return Msg.error("账户已被冻结，请联系管理员进行解封！");
        } else {
            for (String id : ids) {
                Express express = expressService.selectById(id);
                express.setStaff(globalFunction.getUserId());
                express.setStatus(ExpressStatusEnum.TRANSPORT.getIndex());
                expressService.updateById(express);
            }
            return Msg.ok();
        }
    }

    /**
     * 确认订单
     */
    @PostMapping("/confirm")
    public Msg confirmExpress(ExpressPayment payment) {
        String id = payment.getExpressId();

        Express express = expressService.selectById(id);
        express.setStatus(ExpressStatusEnum.COMPLTE.getIndex());
        expressService.updateById(express);

        expressPaymentService.updateById(payment);

        return Msg.ok();
    }

    /**
     * 退单
     */
    @RequestMapping("/chargeback")
    public Msg chargebackExpress(String id) {
        try {
            Express express = expressService.selectById(id);
            express.setStatus(ExpressStatusEnum.WAIT_DIST.getIndex());
            express.setStaff("");
            express.setStaffRemark("");
            expressService.updateById(express);
            SysUser sysUser = sysUserService.selectById(express.getUserId());
            SendEmailUtil.sendEmail(sysUser.getEmail(), "对不起，您的订单号为：" + id + "订单已被退单，请留意您的订单状态！");
            return Msg.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.error("退单失败");
        }
    }

    /**
     * 异常订单
     */
    @PostMapping("/error")
    public Msg errorExpress(String[] ids, String text) {
        for (String id : ids) {
            Express express = expressService.selectById(id);
            // 只有订单状态为TRANSPORT时才要确认
            if (ExpressStatusEnum.TRANSPORT.getName().equals(ExpressStatusEnum.getName(express.getStatus()))) {
                express.setStatus(ExpressStatusEnum.ERROR.getIndex());
                express.setStaffRemark(text);
                expressService.updateById(express);
            }
        }
        return Msg.ok();
    }
}
