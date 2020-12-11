package org.jeecg.modules.member.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.member.entity.MeMember;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.member.vo.MeMemberVo;

/**
 * @Description: 会员表
 * @Author: jeecg-boot
 * @Date:   2020-12-07
 * @Version: V1.0
 */
public interface IMeMemberService extends IService<MeMember> {

    Result edit(MeMemberVo meMember);
}
