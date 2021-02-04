package org.jeecg.modules.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.member.entity.MeRechargeRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.member.entity.RecordVo;
import org.jeecg.modules.member.entity.SaleCur12Vo;
import org.jeecg.modules.member.vo.MeRechargeRecordVo;

import java.util.List;

/**
 * @Description: 会员充值记录表
 * @Author: jeecg-boot
 * @Date:   2020-12-09
 * @Version: V1.0
 */
public interface IMeRechargeRecordService extends IService<MeRechargeRecord> {

    IPage<MeRechargeRecordVo> queryPageList(Page<MeRechargeRecordVo> page, MeRechargeRecordVo meRechargeRecordVo, String startTime, String endTime);

    RecordVo recordInfo();

    List<SaleCur12Vo> queryCur12Total();
}
