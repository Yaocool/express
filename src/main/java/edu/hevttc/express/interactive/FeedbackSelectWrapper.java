package edu.hevttc.express.interactive;

/**
 * 反馈筛选条件
 */
public class FeedbackSelectWrapper {
    /**
     * 反馈类型
     */
    private Integer type;

    /**
     * 反馈状态
     */
    private Integer status;

    private String id;

    /**
     * 反馈人姓名
     */
    private String name;

    /**
     * 反馈人手机号码
     */
    private String tel;

    /**
     * 处理人姓名
     */
    private String staffName;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
