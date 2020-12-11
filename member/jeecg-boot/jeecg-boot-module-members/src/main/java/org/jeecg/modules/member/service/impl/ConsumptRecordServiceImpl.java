package org.jeecg.modules.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.member.entity.ConsumptRecord;
import org.jeecg.modules.member.entity.ConsumptionItems;
import org.jeecg.modules.member.entity.MeEmployeeCommission;
import org.jeecg.modules.member.entity.MeMember;
import org.jeecg.modules.member.mapper.ConsumptRecordMapper;
import org.jeecg.modules.member.mapper.ConsumptionItemsMapper;
import org.jeecg.modules.member.mapper.MeEmployeeCommissionMapper;
import org.jeecg.modules.member.mapper.MeMemberMapper;
import org.jeecg.modules.member.service.IConsumptRecordService;
import org.jeecg.modules.member.vo.ConsumptRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @Description: 消费记录表
 * @Author: jeecg-boot
 * @Date:   2020-12-09
 * @Version: V1.0
 */
@Service
public class ConsumptRecordServiceImpl extends ServiceImpl<ConsumptRecordMapper, ConsumptRecord> implements IConsumptRecordService {

    @Autowired
    ConsumptRecordMapper consumptRecordMapper;

    @Autowired
    MeMemberMapper meMemberMapper;

    @Autowired
    ConsumptionItemsMapper consumptionItemsMapper;

    @Autowired
    MeEmployeeCommissionMapper meEmployeeCommissionMapper;
    @Override
    public MeMember selectMember(String phone) {
        //条件封装
        QueryWrapper<MeMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(phone != null, "phone", phone);
        List<MeMember> meMemberList = meMemberMapper.selectList(queryWrapper);
        if(meMemberList.size() !=1){
            return null;
        }
        return meMemberList.get(0);
    }

    @Override
    public Result addRecord(ConsumptRecord consumptRecord) {
        Result result = new Result();
        Date date = new Date();
        ConsumptionItems consumptionItems = consumptionItemsMapper.selectById(consumptRecord.getConsumptionItemsIdd());
        //会员消费
        MeEmployeeCommission meEmployeeCommission = new MeEmployeeCommission();
        if(consumptRecord.getMemberId() != null){
            MeMember meMember = meMemberMapper.selectById(consumptRecord.getMemberId());
            //查看会员余额是否足够支付此次消费
            if(meMember.getBalance() - consumptionItems.getMemberPrice() < 0){
                result.setMessage("会员余额不足，请及时充值");
                return  result;
            }
            meMember.setBalance(meMember.getBalance() - consumptionItems.getMemberPrice());
            meMember.setUpdateTime(date);
            meMemberMapper.updateById(meMember);
            consumptRecord.setConsumtAmount(consumptionItems.getMemberPrice());
            consumptRecord.setConsumtWay("会员");
            consumptRecord.setCreateTime(date);
            consumptRecordMapper.insert(consumptRecord);
            meEmployeeCommission.setMemberId(meMember.getId());
        }
        //非会员消费
        else{
            consumptRecord.setConsumtAmount(consumptionItems.getOriPrice());
            consumptRecord.setCreateTime(date);
            consumptRecordMapper.insert(consumptRecord);
        }

        meEmployeeCommission.setEmployeeId(consumptRecord.getEmployeeId());
        meEmployeeCommission.setCreateTime(date);
        meEmployeeCommission.setConsumptionItemsId(consumptRecord.getConsumptionItemsIdd());
        meEmployeeCommission.setConsumptRecordId(consumptRecord.getId());
        meEmployeeCommission.setCommissionAmount(consumptionItems.getCommissionAmount());
        meEmployeeCommissionMapper.insert(meEmployeeCommission);
        result.setSuccess(true);
        return result;
    }

    @Override
    public IPage<ConsumptRecordVo> queryPageList(Page<ConsumptRecordVo> page, ConsumptRecordVo consumptRecordVo) {
        return consumptRecordMapper.queryPageList(page,consumptRecordVo);
    }
}
