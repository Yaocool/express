package edu.hevttc.express.dto;

import java.io.Serializable;

public class MonthDataDto implements Serializable {
    private Integer day;
    private Integer count;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserMonthDataDto{" +
                "day=" + day +
                ", count=" + count +
                '}';
    }
}
