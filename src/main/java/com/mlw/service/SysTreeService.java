package com.mlw.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mlw.dao.SysDeptMapper;
import com.mlw.dto.DeptLevelDto;
import com.mlw.model.SysDept;
import com.mlw.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    public List<DeptLevelDto> deptTree() {
        //取出所有的数据
        List<SysDept> deptList = sysDeptMapper.getAllDept();

        //对数据进行简单的封装
        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept dept : deptList) {
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        }
        return deptListToTree(dtoList);
    }

    /**
     * 处理第一层，也就是处理ROOT
     * @param deptLevelList
     * @return
     */
    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList) {
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }
        // level -> [dept1, dept2, ...] Map<String, List<Object>>
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();

        for (DeptLevelDto dto : deptLevelList) {
            levelDeptMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }

        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);

        return rootList;
    }

    /**
     * 处理每个当前层级下面的数据
     * @param rootList
     * @param level
     * @param levelDeptMap
     */
    public void transformDeptTree(List<DeptLevelDto> rootList, String level, Multimap<String, DeptLevelDto> levelDeptMap) {
        //读取rootList里面的元素
        for (int i = 0; i < rootList.size(); i++) {
            //遍历该层的每个元素
            DeptLevelDto deptLevelDto = rootList.get(i);
            //处理当前层级的数据，取出下一级要用到的level
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            //处理下一层
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);

            if (CollectionUtils.isEmpty(tempDeptList)) {
                //排序
                Collections.sort(tempDeptList, deptSeqComparator);
                //设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                //进入到下一层处理
                transformDeptTree(tempDeptList,nextLevel,levelDeptMap);
            }
        }
    }

    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
