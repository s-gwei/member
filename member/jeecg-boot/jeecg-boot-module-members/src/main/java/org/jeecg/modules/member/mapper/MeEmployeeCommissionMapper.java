package org.jeecg.modules.member.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.member.entity.MeEmployeeCommission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.member.vo.MeEmployeeCommissionVo;
import org.springframework.stereotype.Repository;

/**
 * @Description: 员工提成表
 * @Author: jeecg-boot
 * @Date:   2020-12-07
 * @Version: V1.0
 */
@Repository
public interface MeEmployeeCommissionMapper extends BaseMapper<MeEmployeeCommission> {

    IPage<MeEmployeeCommissionVo> queryPageList(Page<MeEmployeeCommissionVo> page,
                                                @Param("meEmployeeCommissionVo") MeEmployeeCommissionVo meEmployeeCommissionVo,
                                                @Param("startTime") String startTime,
                                                @Param("endTime") String endTime);
}
