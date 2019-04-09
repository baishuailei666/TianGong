package com.xlauncher.service;

import com.xlauncher.entity.Division;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public interface DivisionService {

    List<Division> listDivisionP();

    List<Division> listDivisionCi();

    List<Division> listDivisionC();

    List<Division> listDivisionT();

    List<Division> listDivisionV();

    List<Division> listDivisionPCi();

    int insertDivision(Division division);


    List<Division> listDivisionB();

    int insertDivisionB(Division division);

    int updateDivisionB(Division division);

    int deleteDivisionB(BigInteger divisionId);

    /**
     * 行政区划编号查重
     * @param divisionId
     * @return
     */
    int countDivisionId(BigInteger divisionId);
}
