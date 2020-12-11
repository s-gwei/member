package org.jeecg.modules.member.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.member.entity.ConsumptRecord;
import org.jeecg.modules.member.entity.MeMember;
import org.jeecg.modules.member.service.IConsumptRecordService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.member.vo.ConsumptRecordVo;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 消费记录表
 * @Author: jeecg-boot
 * @Date:   2020-12-09
 * @Version: V1.0
 */
@Api(tags="消费记录表")
@RestController
@RequestMapping("/member/consumptRecord")
@Slf4j
public class ConsumptRecordController extends JeecgController<ConsumptRecord, IConsumptRecordService> {
	@Autowired
	private IConsumptRecordService consumptRecordService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@AutoLog(value = "消费记录表-分页列表查询")
	@ApiOperation(value="消费记录表-分页列表查询", notes="消费记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ConsumptRecordVo consumptRecordVo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
		Page<ConsumptRecordVo> page = new Page<ConsumptRecordVo>(pageNo, pageSize);
		IPage<ConsumptRecordVo> pageList = consumptRecordService.queryPageList(page, consumptRecordVo);
		return Result.OK(pageList);
	}

	 @AutoLog(value = "消费记录表-通过id查询")
	 @ApiOperation(value="消费记录表-通过id查询", notes="消费记录表-通过id查询")
	 @GetMapping(value = "/selectMember")
	 public Result<?> selectMember(@RequestParam(name="phone",required=true) String phone) {
		 MeMember meMember = consumptRecordService.selectMember(phone);
		 if(  meMember==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(  meMember);
	 }
	/**
	 *   添加
	 *
	 * @param consumptRecord
	 * @return
	 */
	@AutoLog(value = "消费记录表-添加")
	@ApiOperation(value="消费记录表-添加", notes="消费记录表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ConsumptRecord consumptRecord) {
		Result result = consumptRecordService.addRecord(consumptRecord);
		return result;
	}
	
	/**
	 *  编辑
	 *
	 * @param consumptRecord
	 * @return
	 */
	@AutoLog(value = "消费记录表-编辑")
	@ApiOperation(value="消费记录表-编辑", notes="消费记录表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ConsumptRecord consumptRecord) {
		consumptRecordService.updateById(consumptRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "消费记录表-通过id删除")
	@ApiOperation(value="消费记录表-通过id删除", notes="消费记录表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		consumptRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "消费记录表-批量删除")
	@ApiOperation(value="消费记录表-批量删除", notes="消费记录表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.consumptRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "消费记录表-通过id查询")
	@ApiOperation(value="消费记录表-通过id查询", notes="消费记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ConsumptRecord consumptRecord = consumptRecordService.getById(id);
		if(consumptRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(consumptRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param consumptRecord
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ConsumptRecord consumptRecord) {
        return super.exportXls(request, consumptRecord, ConsumptRecord.class, "消费记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ConsumptRecord.class);
    }

}
