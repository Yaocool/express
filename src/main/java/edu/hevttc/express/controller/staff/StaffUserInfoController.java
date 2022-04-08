package edu.hevttc.express.controller.staff;

import edu.hevttc.express.controller.GlobalFunction;
import edu.hevttc.express.enums.SysUserStatusEnum;
import edu.hevttc.express.interactive.Msg;
import edu.hevttc.express.pojo.SysUser;
import edu.hevttc.express.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/staff/userInfo")
public class StaffUserInfoController {
    @Autowired
    private GlobalFunction globalFunction;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取当前用户所有信息
     */
    @RequestMapping("/getUserInfo")
    public Msg getUserInfo() {
        Map<String, Object> map = new HashMap<>();
        SysUser user = globalFunction.getUser();
        map.put("user", user);
        map.put("status", SysUserStatusEnum.getName(user.getStatus()));
        return Msg.ok(null, map);
    }


    @PostMapping("/updateUserInfo")
    public Msg updateUserInfo(String username, String tel, String sex, String birthday, String address) throws ParseException {
        SysUser sysUser = globalFunction.getUser();
        sysUser.setUsername(username);
        sysUser.setSex(sex);
        if (!StringUtils.isBlank(birthday)) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            sysUser.setBirthday(date);
        }
        sysUser.setTel(tel);
        sysUser.setAddress(address);
        boolean flag = sysUserService.updateById(sysUser);
        if (flag) {
            return Msg.ok();
        } else {
            return Msg.error("修改失败！");
        }
    }
}
