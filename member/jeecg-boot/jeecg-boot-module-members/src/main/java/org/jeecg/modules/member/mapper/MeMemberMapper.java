package org.jeecg.modules.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.member.entity.MeMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 会员表
 * @Author: jeecg-boot
 * @Date:   2020-12-07
 * @Version: V1.0
 */
@Repository
public interface MeMemberMapper extends BaseMapper<MeMember> {

}
