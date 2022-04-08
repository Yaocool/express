package edu.hevttc.express.controller.admin;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import edu.hevttc.express.controller.GlobalFunction;
import edu.hevttc.express.interactive.Msg;
import edu.hevttc.express.interactive.SysUserSelectWrapper;
import edu.hevttc.express.dto.SysUserDto;
import edu.hevttc.express.enums.RoleEnum;
import edu.hevttc.express.enums.SysUserStatusEnum;
import edu.hevttc.express.pojo.SysUser;
import edu.hevttc.express.service.SysUserService;
import edu.hevttc.express.utils.SendEmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职员管理
 */
@RestController
@RequestMapping("/admin/staff")
public class StaffController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private GlobalFunction globalFunction;
    // 用户状态邮件通知前缀
    private static final String USER_STATUS_EMAIL_NOTICE_PREFIX = "您的用户状态已被修改为：";
    private static final String USER_STATUS_EMAIL_NOTICE_SUFFIX  = "，如有疑问，请联系管理员进行处理！";

    private void changeUserStatus(String[] ids, Integer status) {
        String message = "";
        for(String id : ids) {
            SysUser user = userService.selectById(id);
            user.setStatus(status);
            userService.updateById(user);
            String content = SysUserStatusEnum.getName(status);
            message = USER_STATUS_EMAIL_NOTICE_PREFIX + content + USER_STATUS_EMAIL_NOTICE_SUFFIX;
            SendEmailUtil.sendEmail(user.getEmail(), message);
        }
    }

    /**
     * 获取用户的状态列表
     */
    @GetMapping("/status")
    public Msg listStaffStatus() {
        List<Map<String,Object>> result = new ArrayList<>();
        for(SysUserStatusEnum enums :SysUserStatusEnum.values()) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",enums.getIndex());
            map.put("name",enums.getName());
            result.add(map);
        }
        return Msg.ok(null,result);
    }

    /**
     * 获取所有的职员名,用于分配订单
     */
    @GetMapping("/listName")
    public Msg listStaff() {
        // 获取所有在职的职员
        List<SysUser> staffs = userService.selectList(new EntityWrapper<SysUser>()
            .eq("status", SysUserStatusEnum.ACTIVE.getIndex())
            .eq("role_id", RoleEnum.STAFF.getIndex()));

        return Msg.ok(null,staffs);
    }

    /**
     * 获取所有新注册职员
     */
    @GetMapping("/checkingStaff")
    public Map<String, Object> checkingStaff(Integer rows, Integer page, SysUserSelectWrapper usw) {
        // Get请求中文编码
        try {
            usw.setName(globalFunction.iso8859ToUtf8(usw.getName()));
            usw.setAddress(globalFunction.iso8859ToUtf8(usw.getAddress()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 得到筛选条件
        EntityWrapper<SysUser> userWrapper = globalFunction.getSysUserWrapper(usw);
        // 不显示admin角色
        userWrapper.ne("role_id", RoleEnum.ADMIN.getIndex());
        // 显示注册审核中用户
        userWrapper.eq("status", SysUserStatusEnum.AUDITING.getIndex());
        Page<SysUser> selectPage = userService.selectPage(new Page<>(page, rows), userWrapper);
        List<SysUserDto> list = globalFunction.sysUser2dto(selectPage.getRecords());
        Map<String,Object> map = new HashMap<>();
        map.put("total", selectPage.getTotal());
        map.put("rows", list);
        return map;
    }

    /**
     * 获取所有职员
     */
    @GetMapping("/list")
    public Map<String, Object> listStaff(Integer rows, Integer page, SysUserSelectWrapper usw) {
        // Get请求中文编码
        try {
            usw.setName(globalFunction.iso8859ToUtf8(usw.getName()));
            usw.setAddress(globalFunction.iso8859ToUtf8(usw.getAddress()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 得到筛选条件
        EntityWrapper<SysUser> userWrapper = globalFunction.getSysUserWrapper(usw);
        // 不显示admin角色
        userWrapper.ne("role_id", RoleEnum.ADMIN.getIndex());
        // 不显示注册审核中用户
        userWrapper.ne("status", SysUserStatusEnum.AUDITING.getIndex());
        Page<SysUser> selectPage = userService.selectPage(new Page<>(page, rows), userWrapper);
        List<SysUserDto> list = globalFunction.sysUser2dto(selectPage.getRecords());
        Map<String,Object> map = new HashMap<>();
        map.put("total", selectPage.getTotal());
        map.put("rows", list);
        return map;
    }

    /**
     * 更新用户信息
     */
    @PostMapping("")
    public Msg update(SysUser user) {
        userService.updateById(user);
        return Msg.ok();
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/{id}")
    public Msg getById(@PathVariable String id) {
        SysUser user = userService.selectById(id);
        return Msg.ok(null,user);
    }

    /**
     * 修改员工为在职
     */
    @PostMapping("/active")
    public Msg changeActive(String[] ids) {
        changeUserStatus(ids, SysUserStatusEnum.ACTIVE.getIndex());
        return Msg.ok();
    }

    /**
     * 修改员工为冻结
     */
    @PostMapping("/freeze")
    public Msg changeFreeze(String[] ids) {
        changeUserStatus(ids, SysUserStatusEnum.FREEZE.getIndex());
        return Msg.ok();
    }

    /**
     * 修改员工为离职
     */
    @PostMapping("/leave")
    public Msg changeLeave(String[] ids) {
        changeUserStatus(ids, SysUserStatusEnum.LEAVE.getIndex());
        return Msg.ok();
    }

    /**
     * 审核未通过
     */
    @PostMapping("/auditFailed")
    public Msg changeAuditFailed(String[] ids, HttpServletRequest request) {
        String message = "抱歉，您的注册申请审核未通过，请您修改信息后重试！";
        for (int i = 0; i < ids.length; i++) {
            // 删除已上传文件
            SysUser sysUser = userService.selectById(ids[i]);
            String pic1Path = sysUser.getPic1();
            String pic2Path = sysUser.getPic2();
            String pic1RealPath = request.getSession().getServletContext().getRealPath(pic1Path);
            String pic2RealPath = request.getSession().getServletContext().getRealPath(pic2Path);
            File pic1 = new File(pic1RealPath);
            File pic2 = new File(pic2RealPath);
            if (pic1.exists() && pic1.isFile()) {
                pic1.delete();
            }
            if (pic2.exists() && pic2.isFile()) {
                pic2.delete();
            }
            // 发送邮件通知
            SendEmailUtil.sendEmail(userService.selectById(ids[i]).getEmail(), message);
            // 删除相关用户信息
            userService.deleteById(ids[i]);
        }
        return Msg.ok();
    }
}
