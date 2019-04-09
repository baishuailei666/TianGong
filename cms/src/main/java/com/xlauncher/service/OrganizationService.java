package com.xlauncher.service;

import com.xlauncher.entity.Organization;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrganizationService {

    List<Organization> listOrganization(String token);

    int insertOrganization(Organization organization, String token);

    int updateOrganization(Organization organization, String token);

    int deleteOrganization(String orgId, String token);
}
