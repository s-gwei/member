package org.jeecg.modules.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.member.entity.ConsumptRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.member.entity.MeMember;
import org.jeecg.modules.member.vo.ConsumptRecordVo;

/**
 * @Description: 消费记录表
 * @Author: jeecg-boot
 * @Date:   2020-12-09
 * @Version: V1.0
 */
public interface IConsumptRecordService extends IService<ConsumptRecord> {

    MeMember selectMember(String phone);

    Result addRecord(ConsumptRecord consumptRecord);

    IPage<ConsumptRecordVo> queryPageList(Page<ConsumptRecordVo> page, ConsumptRecordVo consumptRecordVo);
}
