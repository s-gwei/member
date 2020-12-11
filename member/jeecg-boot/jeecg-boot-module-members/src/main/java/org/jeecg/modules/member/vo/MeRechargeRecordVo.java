package org.jeecg.modules.member.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@Data
public class MeRechargeRecordVo implements Serializable {
    private java.lang.String id;
    /**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
    /**会员id*/
    @Excel(name = "会员id", width = 15)
    @ApiModelProperty(value = "会员id")
    private java.lang.String memberId;
    private java.lang.String memberName;

    /**充值金额*/
    @Excel(name = "充值金额", width = 15)
    @ApiModelProperty(value = "充值金额")
    private java.lang.Double rechargeAmount;
    /**充值方式*/
    @Excel(name = "充值方式", width = 15)
    @ApiModelProperty(value = "充值方式")
    private String rechargeMode;
    /**销售员工id*/
    @Excel(name = "销售员工id", width = 15)
    @ApiModelProperty(value = "销售员工id")
    private java.lang.String employeeId;
    private java.lang.String employeeName;

}
