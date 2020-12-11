package org.jeecg.modules.member.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.member.entity.MeRechargeRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.member.vo.MeRechargeRecordVo;
import org.springframework.stereotype.Repository;

/**
 * @Description: 会员充值记录表
 * @Author: jeecg-boot
 * @Date:   2020-12-09
 * @Version: V1.0
 */
@Repository
public interface MeRechargeRecordMapper extends BaseMapper<MeRechargeRecord> {

    IPage<MeRechargeRecordVo> queryPageList(Page<MeRechargeRecordVo> page, MeRechargeRecordVo meRechargeRecordVo);
}
