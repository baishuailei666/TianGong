package com.xlauncher.dao;

import com.xlauncher.entity.Organization;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganizationDao {

    int insertOrganization(Organization organization);

    int updateOrganization(Organization organization);

    int deleteOrganization(String orgId);

    List<Organization> listOrganization();

    List<String> listfollower(String orgSuperiorId);
    /**
     * 根据最后一级组织获取上级组织
     * @param divisionId
     * @return
     */
    Organization listSuperior(String divisionId);

    /**
     * getOrgId
     * @param orgName
     * @return
     */
    Organization getOrgId(String orgName);

    /**
     * 验证组织是否存在
     * @param orgId
     * @return
     */
    int orgExistence(String orgId);
}
