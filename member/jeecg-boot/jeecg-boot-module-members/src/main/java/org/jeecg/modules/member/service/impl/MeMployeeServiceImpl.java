package org.jeecg.modules.member.service.impl;

import org.jeecg.modules.member.entity.MeMployee;
import org.jeecg.modules.member.mapper.MeMployeeMapper;
import org.jeecg.modules.member.service.IMeMployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 员工表
 * @Author: jeecg-boot
 * @Date:   2020-12-07
 * @Version: V1.0
 */
@Service
public class MeMployeeServiceImpl extends ServiceImpl<MeMployeeMapper, MeMployee> implements IMeMployeeService {


    @Autowired
    MeMployeeMapper meMployeeMapper;

    @Override
    public List<MeMployee> queryEmployInfo() {
        return meMployeeMapper.selectList(null);
    }
}
