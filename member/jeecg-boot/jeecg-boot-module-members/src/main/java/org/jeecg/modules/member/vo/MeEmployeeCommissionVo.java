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
public class MeEmployeeCommissionVo implements Serializable {

    private java.lang.String id;

    private java.lang.String createBy;

    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**更新日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
    /**员工id*/
    @Excel(name = "员工id", width = 15)
    @ApiModelProperty(value = "员工id")
    private java.lang.String employeeId;
    private String employeeName;
    /**消费项目*/
    @Excel(name = "消费项目", width = 15)
    @ApiModelProperty(value = "消费项目")
    private java.lang.String consumptionItemsId;

    private String itemName;
    /**提成金额*/
    @Excel(name = "提成金额", width = 15)
    @ApiModelProperty(value = "提成金额")
    private java.lang.Double commissionAmount;
    /**充值记录*/
    @Excel(name = "充值记录", width = 15)
    @ApiModelProperty(value = "充值记录")
    private java.lang.String rechargeRecordId;
    /**消费记录*/
    @Excel(name = "消费记录", width = 15)
    @ApiModelProperty(value = "消费记录")
    private java.lang.String consumptRecordId;
    /**会员id*/
    @Excel(name = "会员id", width = 15)
    @ApiModelProperty(value = "会员id")
    private java.lang.String memberId;
    private String memberName;
}
