package com.xlauncher.util;

import com.xlauncher.dao.AdminDivisionDao;
import com.xlauncher.dao.OrganizationDao;
import com.xlauncher.entity.Division;
import com.xlauncher.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Objects;

/**
 * 行政区划和组织的工具类
 * @author 白帅雷
 * @date 2018-07-20
 */
@Component
public class DivAndOrgUtil {
    @Autowired
    AdminDivisionDao divisionDao;
    @Autowired
    OrganizationDao organizationDao;

    /**
     * 遍历得到行政区划
     * @param divisionId
     * @return
     */
    public String divisionName(BigInteger divisionId) {
        StringBuilder sb = new StringBuilder();
        Division division = divisionDao.listSuperior(divisionId);
        while (!Objects.equals(division.getDivisionSuperiorId(), BigInteger.valueOf(0))) {
            BigInteger superiorId = division.getDivisionSuperiorId();
            String divisionName = division.getName();
            if (!Objects.equals(superiorId, BigInteger.valueOf(0))) {
                division = divisionDao.listSuperior(superiorId);
                sb.append("-").append(divisionName);
            } else {
                sb.append("-").append(divisionName);
            }
        }
        sb.append("-").append(division.getName());
        return String.valueOf(sb.substring(1));
    }

    /**
     * 遍历得到组织
     *
     * @param orgId
     * @return
     */
    public String orgName(String orgId) {
        String sid = "null";
        StringBuilder strOrg = new StringBuilder();
        Organization organization = organizationDao.listSuperior(orgId);
        while (!Objects.equals(organization.getOrgSuperiorId(), sid) || !Objects.equals(organization.getOrgSuperiorId(), null)){
            String superiorId = organization.getOrgSuperiorId();
            String orgName = organization.getName();
            if (!Objects.equals(superiorId, sid)) {
                organization = organizationDao.listSuperior(superiorId);
                strOrg.append("-").append(orgName);
            } else {
                strOrg.append("-").append(orgName);
                return String.valueOf(strOrg.substring(1));
            }
        }
        strOrg.append("-").append(organization.getName());
        return String.valueOf(strOrg.substring(1));
    }

//    /**
//     * 遍历得到组织
//     * @param orgId
//     * @return
//     */
//    public String orgName(String orgId) {
//        StringBuilder strOrg = new StringBuilder();
//        Organization organization = organizationDao.listSuperior(orgId);
//        while (!Objects.equals(organization.getOrgSuperiorId(), "0") || !Objects.equals(organization.getOrgSuperiorId(), null)){
//            String superiorId = organization.getOrgSuperiorId();
//            String orgName = organization.getName();
//            if (!Objects.equals(superiorId, "0") || !Objects.equals(superiorId, null)) {
//                organization = organizationDao.listSuperior(superiorId);
//                strOrg.append("-").append(orgName);
//            } else {
//                strOrg.append("-").append(orgName);
//            }
//        }
//        strOrg.append("-").append(organization.getName());
//        return String.valueOf(strOrg.substring(1));
//    }
}
