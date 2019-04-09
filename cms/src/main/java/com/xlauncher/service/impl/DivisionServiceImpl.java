package com.xlauncher.service.impl;

import com.xlauncher.dao.AdminDivisionDao;
import com.xlauncher.dao.AlertLogDao;
import com.xlauncher.entity.AlertLog;
import com.xlauncher.entity.Division;
import com.xlauncher.service.AlertLogService;
import com.xlauncher.service.DivisionService;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.OperationLogUtil;
import com.xlauncher.util.Recursion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DivisionServiceImpl implements DivisionService {

    @Autowired
    AdminDivisionDao divisionDao;

    @Autowired
    Recursion recursion;
    @Autowired
    private OperationLogUtil logUtil;
    @Autowired
    private CheckToken checkToken;
    private static final String MODULE = "行政区划";
    private static final String SYSTEM_MODULE = "网格管理";
    private static final String CATEGORY = "运营面";

    @Override
    public List<Division> listDivisionP() {
        return divisionDao.listDivisionP();
    }

    @Override
    public List<Division> listDivisionCi() {
        return divisionDao.listDivisionCi();
    }

    @Override
    public List<Division> listDivisionC() {
        return divisionDao.listDivisionC();
    }

    @Override
    public List<Division> listDivisionT() {
        return divisionDao.listDivisionT();
    }

    @Override
    public List<Division> listDivisionV() {
        return divisionDao.listDivisionV();
    }

    @Override
    public List<Division> listDivisionPCi() {
        List<Division> list = divisionDao.listDivisionP();
        list.addAll(divisionDao.listDivisionCi());
        return list;
    }

    @Override
    public int insertDivision(Division division) {
        if (divisionDao.countDivisionP(division.getDivisionSuperiorId())!=0) {
            return divisionDao.insertDivisionP(division);
        }
        else if (divisionDao.countDivisionCi(division.getDivisionSuperiorId())!=0){
            return divisionDao.insertDivisionCi(division);
        }
        else if (divisionDao.countDivisionC(division.getDivisionSuperiorId())!=0){
            return divisionDao.insertDivisionC(division);
        }
        else if (divisionDao.countDivisionT(division.getDivisionSuperiorId())!=0){
            return divisionDao.insertDivisionT(division);
        }else{
            return divisionDao.insertDivisionV(division);
        }
    }


    @Override
    public List<Division> listDivisionB() {
        return divisionDao.listDivisionB();
    }

    @Override
    public int insertDivisionB(Division division) {
        return divisionDao.insertDivisionB(division);
    }

    @Override
    public int updateDivisionB(Division division) {
        return divisionDao.updateDivisionB(division);
    }

    @Override
    public int deleteDivisionB(BigInteger divisionId) {
        if (Objects.equals(divisionId, BigInteger.valueOf(11))) {
            return 0;
        } else {
            return recursion.recursionA(divisionId);
        }
    }

    /**
     * 行政区划编号查重
     *
     * @param divisionId
     * @return
     */
    @Override
    public int countDivisionId(BigInteger divisionId) {
        return this.divisionDao.countDivisionId(divisionId);
    }
}
