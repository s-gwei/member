package org.jeecg.modules.member.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 员工提成表
 * @Author: jeecg-boot
 * @Date:   2020-12-07
 * @Version: V1.0
 */
@Data
@TableName("me_employee_commission")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="me_employee_commission对象", description="员工提成表")
public class MeEmployeeCommission implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
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
	/**员工id*/
	@Excel(name = "员工id", width = 15)
    @ApiModelProperty(value = "员工id")
    private java.lang.String employeeId;
	/**消费项目*/
	@Excel(name = "消费项目", width = 15)
    @ApiModelProperty(value = "消费项目")
    private java.lang.String consumptionItemsId;
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
}
