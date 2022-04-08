package edu.hevttc.express.enums;

/**
 * 用户状态枚举
 */
public enum SysUserStatusEnum {
    /**
     * 在职
     */
    ACTIVE("在职", 0),

    /**
     * 冻结
     */
    FREEZE("冻结", 1),

    /**
     * 离职
     */
    LEAVE("离职", 2),

    /**
     * 注册审核中
     */
    AUDITING("审核中", 3);

    private String name;
    private int index;

    SysUserStatusEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (SysUserStatusEnum userStatusEnum : SysUserStatusEnum.values()) {
            if (userStatusEnum.getIndex() == index) {
                return userStatusEnum.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
