package edu.hevttc.express.controller;

import edu.hevttc.express.enums.SysUserStatusEnum;
import edu.hevttc.express.interactive.Msg;
import edu.hevttc.express.enums.RoleEnum;
import edu.hevttc.express.pojo.Express;
import edu.hevttc.express.pojo.SysUser;
import edu.hevttc.express.service.SysUserService;
import edu.hevttc.express.utils.PasswordUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.UUID;

@RestController
public class SystemController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private GlobalFunction globalFunction;

    private static String[] imgs = new String[2];
    private static MultipartFile[] files = new MultipartFile[2];

    @Value("${session.latest_express}")
    private String SESSION_LATEST_EXPRESS;

    /**
     * 验证验证码
     */
    @PostMapping("/checkVerifyCode")
    public Msg checkVerifyCode(String data, HttpSession session) {
        String validateCode;
        try {
            validateCode = ((String) session.getAttribute("validateCode")).toLowerCase();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Msg.error("验证码 初始化错误，请刷新页面重试");
        }
        String value = data.toLowerCase();
        if (!validateCode.equals(value)) {
            return Msg.error("验证码输入错误");
        } else {
            return Msg.ok();
        }
    }

    /**
     * 登陆
     */
    @PostMapping("/login")
    public Msg login(SysUser user) {
        //Shiro实现登录
        UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            //如果获取不到用户名就是登录失败，但登录失败的话，会直接抛出异常
            subject.login(token);
        } catch (Exception e) {
            System.out.println(e);
            return Msg.error(e.getMessage());
        }
        //所有用户均重定向对应的展示配送页面
        if (subject.hasRole(RoleEnum.ADMIN.getName())) {
            return Msg.ok(null, "/express/admin/checkingstaff");
        } else if (subject.hasRole(RoleEnum.STAFF.getName())) {
            return Msg.ok(null, "/express/staff/home");
        }
        return Msg.error("授权失败");
    }


    /**
     * 图片上传
     */
    @RequestMapping("/uploadImgs")
    @ResponseBody
    public Msg uploadImgs(MultipartFile[] file) {
        if (file.length != 2) {
            return Msg.error("请上传本人近期证件照和一卡通人像面共两张图片!");
        } else {
            for (int i = 0; i < file.length; i++) {
                if (file[i].isEmpty()) {
                    files = null;
                    return Msg.error("请不要上传空文件!");
                } else {
                    files[i] = file[i];
                }
            }
            return Msg.ok("文件上传成功！");
        }
    }

    /**
     * 注册
     */
    @RequestMapping("/register")
    @ResponseBody
    public Msg register(SysUser sysUser, HttpSession session) {
        boolean flag = userService.hasExistUserName(sysUser.getEmail());
        // 判断用户是否存在
        if (!flag) {
            return Msg.error("用户已存在，请进行登录！");
        } else {
            for (int i = 0; i < files.length; i++) {
                try {
                    if (files[i] == null || files[i].isEmpty()) {
                        return Msg.error("请上传本人近期证件照和一卡通人像面共两张图片!");
                    } else {
                        // 上传到服务器的文件夹名
                        String directory = "/upload";
                        // /upload文件夹真实路径
                        String realPath = session.getServletContext().getRealPath(directory);
                        // 获取上传的文件名
                        String fileName = files[i].getOriginalFilename();
                        // 防止命名冲突的UUID新文件名
                        UUID uuid = UUID.randomUUID();
                        fileName = uuid + fileName;
                        String picFilePath = realPath + "/" + fileName;
                        files[i].transferTo(new File(picFilePath));
                        imgs[i] = directory + "/" + fileName;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return Msg.error("上传的文件被外星人劫走啦...");
                }
            }

            // 插入数据到表中
            if (StringUtils.isBlank(imgs[0]) || StringUtils.isBlank(imgs[1])) {
                System.err.println("img = " + imgs[0]);
                return Msg.error("请上传本人近期证件照和一卡通人像面共两张图片！");
            } else {
                sysUser.setCreateDate(new Date());
                sysUser.setStatus(SysUserStatusEnum.AUDITING.getIndex());
                sysUser.setPic1(imgs[0]);
                sysUser.setPic2(imgs[1]);
                sysUser.setPassword(PasswordUtils.entryptPassword(sysUser.getPassword()));
                sysUser.setRoleId(1);
                imgs[0] = "";
                imgs[1] = "";
                userService.insert(sysUser);
                return Msg.ok("注册申请成功！", "/express/registerSuccess");
            }
        }
    }

    /**
     * 获取用户名
     */
    @GetMapping("/username")
    public Msg selfName() {
        String email = (String) SecurityUtils.getSubject().getPrincipal();
        String username = userService.getByUserName(email).getUsername();
        return Msg.ok(null, username);
    }

    /**
     * 登出
     */
    @RequestMapping(value = "/logout")
    public String logout() {
        return "redirect:/express/logout";
    }

    /**
     * 保存用户输入的订单信息到Cookie
     */
    @PostMapping("/express")
    public Msg express(Express express, HttpSession session) {
        express.setCreateDate(new Date());
        String userId = globalFunction.getUserId();
        express.setUserId(userId);

        // 将订单信息保存到用户Session中
        session.setAttribute(SESSION_LATEST_EXPRESS, express);

        // 保存成功后跳转到支付页面
        return Msg.ok(null, "/express/payment");
    }

    /**
     * 重置密码
     */
    @PostMapping("/password")
    public Msg resetPassword(String oldPassword, String newPassword) {
        SysUser user = globalFunction.getUser();
        if (!PasswordUtils.validatePassword(oldPassword, user.getPassword())) {
            return Msg.error("原始密码错误");
        } else {
            user.setPassword(PasswordUtils.entryptPassword(newPassword));
            userService.updateById(user);
            return Msg.ok();
        }
    }
}
