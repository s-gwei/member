package org.jeecg.modules.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.member.entity.ConsumptionItems;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 消费项目
 * @Author: jeecg-boot
 * @Date:   2020-12-09
 * @Version: V1.0
 */
@Repository
public interface ConsumptionItemsMapper extends BaseMapper<ConsumptionItems> {

}
