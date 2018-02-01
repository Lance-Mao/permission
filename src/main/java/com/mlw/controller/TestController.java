package com.mlw.controller;

import com.mlw.common.ApplicationContextHelper;
import com.mlw.common.JsonData;
import com.mlw.dao.SysAclModuleMapper;
import com.mlw.exception.ParamException;
import com.mlw.model.SysAclModule;
import com.mlw.param.TestVo;
import com.mlw.util.BeanValidator;
import com.mlw.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("test")
@Slf4j
public class TestController {

    @RequestMapping("hello.json")
    @ResponseBody
    public JsonData hello() {
        log.info("hello");
        return JsonData.success("hello permission");
    }

//    @RequestMapping("validate.json")
//    @ResponseBody
//    public JsonData validate(TestVo vo) {
//        log.info("validate");
//        try {
//            Map<String, String> map = BeanValidator.validateObject(vo);
//            if (map != null && map.entrySet().size() > 0) {
//                for (Map.Entry<String, String> entry : map.entrySet()) {
//                    log.info("{}->{}", entry.getKey(), entry.getValue());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return JsonData.success("test validate");
//    }

    @RequestMapping("validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) throws ParamException {
        log.info("validate");
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(module));
        BeanValidator.check(vo);
        return JsonData.success("test validate");
    }

}
