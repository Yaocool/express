package edu.hevttc.express.dto;

import java.io.Serializable;

public class YearDataDto implements Serializable {
    private Integer month;
    private Integer count;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserYearDataDto{" +
                "month=" + month +
                ", count=" + count +
                '}';
    }
}
