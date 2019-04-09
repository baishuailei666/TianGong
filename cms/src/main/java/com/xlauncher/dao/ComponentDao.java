package com.xlauncher.dao;

import com.xlauncher.entity.Component;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组件Dao层
 * @date 2018-05-10
 * @author 白帅雷
 */
@Service
public interface ComponentDao {
    /**
     * 添加组件
     * @param component 组件信息
     * @return 数据库操作情况
     */
    int addComponent(Component component);

    /**
     * 删除组件
     * @param id 组件编号
     * @return 数据库操作情况
     */
    int deleteComponent(int id);

    /**
     * 修改组件
     *
     * @param component 组件信息
     * @return 数据库操作情况
     */
    int updateComponent(Component component);

    /**
     * 分页获取所有组件
     *
     * @param number 页码数
     * @param componentName 组件名称
     * @return list列表所有组件
     */
    List<Component> listAllComponentByNum(@Param("number") int number, @Param("componentName") String componentName);

    /**
     * 获取行数，用于分页
     *
     * @param componentName 组件名称
     * @return 组件总数
     */
    int countPage(@Param("componentName") String componentName);

    /**
     * 获取所有组件
     *
     * @return list列表所有组件
     */
    List<Component> listComponent();

    /**
     * 根据组件编号查询组件
     *
     * @param id 组件编号
     * @return 单个组件
     */
    Component getComponentById(int id);

    /**
     * 根据组件名称模糊查询组件信息
     *
     * @param componentName 组件名称
     * @return 组件信息
     */
    List<Component> getComponentByName(String componentName);

    /**
     * 根据组件缩写查询组件信息
     *
     * @param componentAbbr 组件缩写
     * @return 组件信息
     */
    Component getComponentByAbbr(String componentAbbr);
}
