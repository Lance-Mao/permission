package com.mlw.service;

import com.mlw.dao.SysDeptMapper;
import com.mlw.exception.ParamException;
import com.mlw.model.SysDept;
import com.mlw.param.DeptParam;
import com.mlw.util.BeanValidator;
import com.mlw.util.LevelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    public void save(DeptParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        SysDept dept = SysDept.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();

        dept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        dept.setOperator("system");  //TODO:
        dept.setOperateIp("127.0.0.1");
        dept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(dept);
    }

    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        //TODO:
        return true;
    }

    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }
}
