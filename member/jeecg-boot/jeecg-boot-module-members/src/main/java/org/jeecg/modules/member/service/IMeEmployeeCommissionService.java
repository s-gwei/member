package org.jeecg.modules.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.member.entity.MeEmployeeCommission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.member.vo.MeEmployeeCommissionVo;

/**
 * @Description: 员工提成表
 * @Author: jeecg-boot
 * @Date:   2020-12-07
 * @Version: V1.0
 */
public interface IMeEmployeeCommissionService extends IService<MeEmployeeCommission> {

    IPage<MeEmployeeCommissionVo> queryPageList(Page<MeEmployeeCommissionVo> page, MeEmployeeCommissionVo meEmployeeCommissionVo);
}
