package edu.hevttc.express.dto;

import edu.hevttc.express.pojo.Feedback;

public class FeedbackDto extends Feedback {
    /**
     * 类型名
     */
    private String typeName;

    /**
     * 状态名
     */
    private String statusName;

    /**
     * 处理人
     */
    private String staffName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
