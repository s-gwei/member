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
import org.jeecg.modules.member.entity.ConsumptionItems;
import org.jeecg.modules.member.service.IConsumptionItemsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: 消费项目
 * @Author: jeecg-boot
 * @Date:   2020-12-09
 * @Version: V1.0
 */
@Api(tags="消费项目")
@RestController
@RequestMapping("/member/consumptionItems")
@Slf4j
public class ConsumptionItemsController extends JeecgController<ConsumptionItems, IConsumptionItemsService> {
	@Autowired
	private IConsumptionItemsService consumptionItemsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param consumptionItems
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "消费项目-分页列表查询")
	@ApiOperation(value="消费项目-分页列表查询", notes="消费项目-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ConsumptionItems consumptionItems,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ConsumptionItems> queryWrapper = QueryGenerator.initQueryWrapper(consumptionItems, req.getParameterMap());
		Page<ConsumptionItems> page = new Page<ConsumptionItems>(pageNo, pageSize);
		IPage<ConsumptionItems> pageList = consumptionItemsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 列表查询
	  */
	 @AutoLog(value = "消费项目-分页列表查询")
	 @ApiOperation(value="消费项目-分页列表查询", notes="消费项目-分页列表查询")
	 @GetMapping(value = "/itemList")
	 public Result<?> itemList() {
		 List itemList = consumptionItemsService.list();
		 return Result.OK(itemList);
	 }
	
	/**
	 *   添加
	 *
	 * @param consumptionItems
	 * @return
	 */
	@AutoLog(value = "消费项目-添加")
	@ApiOperation(value="消费项目-添加", notes="消费项目-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ConsumptionItems consumptionItems) {
		consumptionItemsService.save(consumptionItems);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param consumptionItems
	 * @return
	 */
	@AutoLog(value = "消费项目-编辑")
	@ApiOperation(value="消费项目-编辑", notes="消费项目-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ConsumptionItems consumptionItems) {
		consumptionItemsService.updateById(consumptionItems);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "消费项目-通过id删除")
	@ApiOperation(value="消费项目-通过id删除", notes="消费项目-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		consumptionItemsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "消费项目-批量删除")
	@ApiOperation(value="消费项目-批量删除", notes="消费项目-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.consumptionItemsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "消费项目-通过id查询")
	@ApiOperation(value="消费项目-通过id查询", notes="消费项目-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ConsumptionItems consumptionItems = consumptionItemsService.getById(id);
		if(consumptionItems==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(consumptionItems);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param consumptionItems
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ConsumptionItems consumptionItems) {
        return super.exportXls(request, consumptionItems, ConsumptionItems.class, "消费项目");
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
        return super.importExcel(request, response, ConsumptionItems.class);
    }

}
