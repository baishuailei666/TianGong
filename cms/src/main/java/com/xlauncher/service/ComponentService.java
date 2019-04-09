package com.xlauncher.service;

import com.xlauncher.entity.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组件Service层
 * @date 2018-05-10
 * @author 白帅雷
 */
@Service
public interface ComponentService {
    /**
     * 添加组件
     * @param component 组件信息
     * @param token 用户令牌
     * @return 数据库操作情况
     */
    int addComponent(Component component, String token);

    /**
     * 删除组件
     * @param id 组件编号
     * @param token 用户令牌
     * @return 数据库操作情况
     */
    int deleteComponent(int id, String token);

    /**
     * 修改组件
     *
     * @param component 组件信息
     * @param token 用户令牌
     * @return 数据库操作情况
     */
    int updateComponent(Component component, String token);

    /**
     * 分页获取所有组件
     *
     * @param number 页码数
     * @param token 用户令牌
     * @param componentName 组件名称
     * @return list列表所有组件
     */
    List<Component> listAllComponentByNum(int number, String componentName, String token);

    /**
     * 获取行数，用于分页
     *
     * @param componentName 组件名称
     * @return 组件总数
     */
    int countPage(String componentName);

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
     * @param token 用户令牌
     * @return 单个组件
     */
    Component getComponentById(int id, String token);

    /**
     * 根据组件名称模糊查询组件信息
     *
     * @param componentName 组件名称
     * @param token 用户令牌
     * @return 组件信息
     */
    List<Component> getComponentByName(String componentName, String token);

    /**英文缩写获取组件信息
     * @param abbr
     * @return
     */
    Component getComponentByAbbr(String abbr);
}
