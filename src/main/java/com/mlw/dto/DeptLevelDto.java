package com.mlw.dto;

import com.google.common.collect.Lists;
import com.mlw.model.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class DeptLevelDto extends SysDept {

    private List<DeptLevelDto> deptList = Lists.newArrayList();

    public static DeptLevelDto adapt(SysDept dept) {
        DeptLevelDto dto = new DeptLevelDto();
        //通过反射将一个对象的值赋值个另外一个对象（前提是对象中属性的名字相同）
        BeanUtils.copyProperties(dept, dto);
        return dto;
    }
}
