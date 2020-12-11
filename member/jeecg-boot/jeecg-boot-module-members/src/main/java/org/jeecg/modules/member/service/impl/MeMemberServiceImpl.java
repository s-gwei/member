package org.jeecg.modules.member.service.impl;

import org.apache.shiro.crypto.hash.Hash;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.member.entity.MeEmployeeCommission;
import org.jeecg.modules.member.entity.MeMember;
import org.jeecg.modules.member.entity.MeRechargeRecord;
import org.jeecg.modules.member.mapper.MeEmployeeCommissionMapper;
import org.jeecg.modules.member.mapper.MeMemberMapper;
import org.jeecg.modules.member.mapper.MeRechargeRecordMapper;
import org.jeecg.modules.member.service.IMeMemberService;
import org.jeecg.modules.member.vo.MeMemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 会员表
 * @Author: jeecg-boot
 * @Date:   2020-12-07
 * @Version: V1.0
 */
@Service
public class MeMemberServiceImpl extends ServiceImpl<MeMemberMapper, MeMember> implements IMeMemberService {

    @Autowired
    MeMemberMapper meMemberMapper;

    @Autowired
    MeRechargeRecordMapper meRechargeRecordMapper;

    @Autowired
    MeEmployeeCommissionMapper meEmployeeCommissionMapper;
    @Override
    public Result edit(MeMemberVo meMemberVo) {
        Result result = new Result();
        int flag = 0;
        Date date = new Date();
        if(meMemberVo.getId() == null){
            try{
                //校验手机号是否重复
                Map map = new HashMap();
                map.put("phone",meMemberVo.getPhone());
                List list = meMemberMapper.selectByMap(map);
                if( list.size() != 0){
                    result.setMessage("该手机号已被使用，请确认");
                    result.setSuccess(false);
                    return result;
                }
                //保存会员信息
                MeMember member =new MeMember();
                member.setCreateTime(date);
                member.setUpdateTime(date);
                member.setBalance(meMemberVo.getBalance());
                member.setName(meMemberVo.getName());
                member.setPhone(meMemberVo.getPhone());
                meMemberMapper.insert(member);
                //保存充值记录
                MeRechargeRecord  meRechargeRecord = new MeRechargeRecord();
                meRechargeRecord.setCreateTime(date);
                meRechargeRecord.setMemberId(member.getId());
                meRechargeRecord.setRechargeAmount(member.getBalance());
                meRechargeRecord.setEmployeeId(meMemberVo.getEmployeeId());
                meRechargeRecord.setRechargeMode(meMemberVo.getConsumtWay());
                meRechargeRecordMapper.insert(meRechargeRecord);
                //保存员工提成信息
                MeEmployeeCommission meEmployeeCommission = new MeEmployeeCommission();
                meEmployeeCommission.setEmployeeId(meMemberVo.getEmployeeId());
                meEmployeeCommission.setCreateTime(date);
                meEmployeeCommission.setConsumptRecordId(meRechargeRecord.getId());
                meEmployeeCommission.setCommissionAmount(meRechargeRecord.getRechargeAmount()/50);
                meEmployeeCommissionMapper.insert(meEmployeeCommission);
                result.setSuccess(true);
            }catch (Exception e){
                result.setSuccess(false);
            }
        }else{
            double newBalance = meMemberVo.getBalance();
            MeMember member =meMemberMapper.selectById(meMemberVo.getId());
            member.setBalance(member.getBalance()+newBalance);
            member.setUpdateTime(new Date());
            meMemberMapper.insert(member);
            //保存充值记录
            MeRechargeRecord  meRechargeRecord = new MeRechargeRecord();
            meRechargeRecord.setCreateTime(date);
            meRechargeRecord.setMemberId(member.getId());
            meRechargeRecord.setRechargeAmount(member.getBalance());
            meRechargeRecord.setEmployeeId(meMemberVo.getEmployeeId());
            meRechargeRecord.setRechargeMode(meMemberVo.getConsumtWay());
            meRechargeRecordMapper.insert(meRechargeRecord);
            //保存员工提成信息
            MeEmployeeCommission meEmployeeCommission = new MeEmployeeCommission();
            meEmployeeCommission.setEmployeeId(meMemberVo.getEmployeeId());
            meEmployeeCommission.setCreateTime(date);
            meEmployeeCommission.setConsumptRecordId(meRechargeRecord.getId());
            meEmployeeCommission.setCommissionAmount(meRechargeRecord.getRechargeAmount()/10);
            meEmployeeCommissionMapper.insert(meEmployeeCommission);
            result.setSuccess(true);
        }
        result.setCode(flag);
        return result;
    }
}
