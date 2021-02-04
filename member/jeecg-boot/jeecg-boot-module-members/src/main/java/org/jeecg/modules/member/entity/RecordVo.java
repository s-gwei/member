package org.jeecg.modules.member.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RecordVo implements Serializable {

    public Double todayIncome;//今日进账

    public Double salesToday;//今日消费额

    public Double todayNumber;//今日消费人次

    public Double monthIncome;//本月进账
}
