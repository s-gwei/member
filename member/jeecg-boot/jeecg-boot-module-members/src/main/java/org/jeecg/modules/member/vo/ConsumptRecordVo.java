package org.jeecg.modules.member.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@Data
public class ConsumptRecordVo implements Serializable {



    private java.lang.String id;


    private java.util.Date updateTime;

    private java.util.Date createTime;

    private java.lang.String memberId;

    private String memberName;
    /**消费项目*/
    private java.lang.String consumptionItemsIdd;

    private String consumpName;
    /**员工id*/
    @Excel(name = "员工id", width = 15)
    @ApiModelProperty(value = "员工id")
    private java.lang.String employeeId;

    private String employeeName;
    //支付方式
    private String payWay;
    /**消费金额*/
    private java.lang.Double consumtAmount;
}
