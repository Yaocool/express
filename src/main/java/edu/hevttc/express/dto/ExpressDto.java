package edu.hevttc.express.dto;

import edu.hevttc.express.pojo.Express;

public class ExpressDto extends Express {
    /**
     * 订单状态名
     */
    private String statusName;

    /**
     * 订单配送员名称
     */
    private String staffName;

    /**
     * 订单配送员联系电话
     */
    private String staffTel;

    /**
     * 订单支付方式
     */
    private String paymentType;
    /**
     * 订单支付状态
     */
    private String paymentStatus;
    /**
     * 线上支付金额
     */
    private Double onlinePayment;
    /**
     * 线下支付金额
     */
    private Double offlinePayment;

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public String getStaffTel() {
        return staffTel;
    }

    public void setStaffTel(String staffTel) {
        this.staffTel = staffTel;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Double getOnlinePayment() {
        return onlinePayment;
    }

    public void setOnlinePayment(Double onlinePayment) {
        this.onlinePayment = onlinePayment;
    }

    public Double getOfflinePayment() {
        return offlinePayment;
    }

    public void setOfflinePayment(Double offlinePayment) {
        this.offlinePayment = offlinePayment;
    }
}
