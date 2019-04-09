package com.xlauncher.dao;

import com.xlauncher.entity.Division;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public interface AdminDivisionDao {

    int insertDivisionP(Division division);

    int insertDivisionCi(Division division);

    int insertDivisionC(Division division);

    int insertDivisionT(Division division);

    int insertDivisionV(Division division);

    int deleteDivision(int divisionId);

    int updateDivision(Division division);

    List<Division> listDivisionP();

    List<Division> listDivisionCi();

    List<Division> listDivisionC();

    List<Division> listDivisionT();

    List<Division> listDivisionV();

    int countDivisionP(BigInteger divisionSuperiorId);

    int countDivisionCi(BigInteger divisionSuperiorId);

    int countDivisionC(BigInteger divisionSuperiorId);

    int countDivisionT(BigInteger divisionSuperiorId);

    int countDivisionV(BigInteger divisionSuperiorId);



    List<Division> listDivisionB();

    int insertDivisionB(Division division);

    int updateDivisionB(Division division);

    int deleteDivisionB(BigInteger divisionId);

    List<BigInteger> listfollower(BigInteger divisionSuperiorId);

    /**
     * 根据最后一级区划获取上级区划
     * @param divisionId
     * @return
     */
    Division listSuperior(BigInteger divisionId);

    /**
     * 行政区划编号查重
     * @param divisionId
     * @return
     */
    int countDivisionId(BigInteger divisionId);
}
