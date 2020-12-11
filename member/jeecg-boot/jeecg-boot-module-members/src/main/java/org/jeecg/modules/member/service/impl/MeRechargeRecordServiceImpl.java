package org.jeecg.modules.member.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.member.entity.MeRechargeRecord;
import org.jeecg.modules.member.mapper.MeRechargeRecordMapper;
import org.jeecg.modules.member.service.IMeRechargeRecordService;
import org.jeecg.modules.member.vo.MeRechargeRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 会员充值记录表
 * @Author: jeecg-boot
 * @Date:   2020-12-09
 * @Version: V1.0
 */
@Service
public class MeRechargeRecordServiceImpl extends ServiceImpl<MeRechargeRecordMapper, MeRechargeRecord> implements IMeRechargeRecordService {

    @Autowired
    MeRechargeRecordMapper meRechargeRecordMapper;
    @Override
    public IPage<MeRechargeRecordVo> queryPageList(Page<MeRechargeRecordVo> page, MeRechargeRecordVo meRechargeRecordVo) {
        return meRechargeRecordMapper.queryPageList(page,meRechargeRecordVo);
    }
}
