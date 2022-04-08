package edu.hevttc.express.dto;

import edu.hevttc.express.pojo.SysUser;

public class SysUserDto extends SysUser {
    private String roleName;

    private String statusName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
