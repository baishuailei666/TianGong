package com.xlauncher.util;

import com.xlauncher.dao.AdminDivisionDao;
import com.xlauncher.dao.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class Recursion {

    @Autowired
    AdminDivisionDao adminDivisionDao;

    @Autowired
    OrganizationDao organizationDao;
    /**
     * 递归删除行政区划
     * @param divisionId
     */
    public int recursionA(BigInteger divisionId){
        List<BigInteger> list = adminDivisionDao.listfollower(divisionId);
        if (list.size()!=0){
            for (BigInteger id:
                 list) {
                recursionA(id);
                adminDivisionDao.deleteDivisionB(id);
            }
        }
        return adminDivisionDao.deleteDivisionB(divisionId);
    }

    /**
     * 递归删除组织
     * @param orgId
     */
    public void resuisionO(String orgId){
        List<String> list = organizationDao.listfollower(orgId);
        if (list.size()!=0){
            for (String id : list) {
                resuisionO(id);
                System.out.println(id);
                organizationDao.deleteOrganization(id);
            }
        }
    }

}
