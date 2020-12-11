package org.jeecg.modules.member.service;

import org.jeecg.modules.member.entity.MeMployee;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 员工表
 * @Author: jeecg-boot
 * @Date:   2020-12-07
 * @Version: V1.0
 */
public interface IMeMployeeService extends IService<MeMployee> {

    List<MeMployee> queryEmployInfo();
}
