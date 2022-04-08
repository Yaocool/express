package edu.hevttc.express.controller.admin;

import edu.hevttc.express.controller.GlobalFunction;
import edu.hevttc.express.interactive.Msg;
import edu.hevttc.express.pojo.SysUser;
import edu.hevttc.express.service.SysUserService;
import edu.hevttc.express.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 密码Controller
 */
@RestController
@RequestMapping("/password")
public class PasswordController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private GlobalFunction globalFunction;

    /**
     * 重置密码
     */
    @PostMapping("/reset")
    public Msg resetPassword(String oldPassword, String newPassword) {
        SysUser user = globalFunction.getUser();

        if(!PasswordUtils.validatePassword(oldPassword, user.getPassword())) {
            return Msg.error("原始密码错误");
        } else {
            user.setPassword(PasswordUtils.entryptPassword(newPassword));
            userService.updateById(user);
            return Msg.ok();
        }
    }
}
