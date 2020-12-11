package org.jeecg.modules.member.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.member.entity.MeEmployeeCommission;
import org.jeecg.modules.member.mapper.MeEmployeeCommissionMapper;
import org.jeecg.modules.member.service.IMeEmployeeCommissionService;
import org.jeecg.modules.member.vo.MeEmployeeCommissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 员工提成表
 * @Author: jeecg-boot
 * @Date:   2020-12-07
 * @Version: V1.0
 */
@Service
public class MeEmployeeCommissionServiceImpl extends ServiceImpl<MeEmployeeCommissionMapper, MeEmployeeCommission> implements IMeEmployeeCommissionService {


    @Autowired
    MeEmployeeCommissionMapper meEmployeeCommissionMapper;
    @Override
    public IPage<MeEmployeeCommissionVo> queryPageList(Page<MeEmployeeCommissionVo> page, MeEmployeeCommissionVo meEmployeeCommissionVo) {
        return meEmployeeCommissionMapper.queryPageList(page,meEmployeeCommissionVo);
    }
}
