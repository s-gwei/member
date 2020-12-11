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
import org.jeecg.modules.member.entity.MeMember;
import org.jeecg.modules.member.entity.MeRechargeRecord;
import org.jeecg.modules.member.service.IMeMemberService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.member.vo.MeMemberVo;
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
 * @Description: 会员表
 * @Author: jeecg-boot
 * @Date:   2020-12-07
 * @Version: V1.0
 */
@Api(tags="会员表")
@RestController
@RequestMapping("/member/meMember")
@Slf4j
public class MeMemberController extends JeecgController<MeMember, IMeMemberService> {
	@Autowired
	private IMeMemberService meMemberService;
	
	/**
	 * 分页列表查询
	 *
	 * @param meMember
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "会员表-分页列表查询")
	@ApiOperation(value="会员表-分页列表查询", notes="会员表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MeMember meMember,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MeMember> queryWrapper = QueryGenerator.initQueryWrapper(meMember, req.getParameterMap());
		Page<MeMember> page = new Page<MeMember>(pageNo, pageSize);
		IPage<MeMember> pageList = meMemberService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param meMember
	 * @return
	 */
	@AutoLog(value = "会员表-添加")
	@ApiOperation(value="会员表-添加", notes="会员表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MeMemberVo meMember) {
		Result result = meMemberService.edit(meMember);
		return result;
	}
	
	/**
	 *  编辑
	 *
	 * @param meMember
	 * @return
	 */
	@AutoLog(value = "会员表-编辑")
	@ApiOperation(value="会员表-编辑", notes="会员表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MeMember meMember) {
		meMemberService.updateById(meMember);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "会员表-通过id删除")
	@ApiOperation(value="会员表-通过id删除", notes="会员表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		meMemberService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "会员表-批量删除")
	@ApiOperation(value="会员表-批量删除", notes="会员表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.meMemberService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "会员表-通过id查询")
	@ApiOperation(value="会员表-通过id查询", notes="会员表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MeMember meMember = meMemberService.getById(id);
		if(meMember==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(meMember);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param meMember
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MeMember meMember) {
        return super.exportXls(request, meMember, MeMember.class, "会员表");
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
        return super.importExcel(request, response, MeMember.class);
    }

}
