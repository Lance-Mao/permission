package com.mlw.controller;

import com.mlw.common.JsonData;
import com.mlw.dto.DeptLevelDto;
import com.mlw.param.DeptParam;
import com.mlw.service.SysDeptService;
import com.mlw.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("sys/dept")
@Slf4j
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param) {
        sysDeptService.save(param);
        return JsonData.success();
    }

    @RequestMapping("tree.json")
    @ResponseBody
    public JsonData tree() {
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }

    @RequestMapping("update.json")
    @ResponseBody
    public JsonData update(DeptParam param) {
        sysDeptService.update(param);
        return JsonData.success();
    }
}
