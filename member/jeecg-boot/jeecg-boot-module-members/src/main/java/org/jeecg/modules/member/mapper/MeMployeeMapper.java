package org.jeecg.modules.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.member.entity.MeMployee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 员工表
 * @Author: jeecg-boot
 * @Date:   2020-12-07
 * @Version: V1.0
 */
@Repository
public interface MeMployeeMapper extends BaseMapper<MeMployee> {

}
