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
public class MeMemberVo implements Serializable {


    private java.lang.String id;
    /**创建人*/
    private java.lang.String createBy;
    /**创建日期*/
    private java.util.Date createTime;
    /**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;

    private java.util.Date updateTime;

    /**姓名*/
    private java.lang.String name;
    /**手机号*/

    private java.lang.String phone;
    /**余额*/

    private java.lang.Double balance;
    private String consumtWay;

    private java.lang.String employeeId;


}
