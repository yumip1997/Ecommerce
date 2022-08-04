package com.plateer.ec1.order.mapper;

import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.order.vo.OrderBasicVO;
import com.plateer.ec1.order.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdTrxMapper {

    void insertOpOrdBase(OpOrdBase opOrdBase);

    void insertOpGoodsInfo(List<OpGoodsInfo> opGoodsInfoList);

    void insertOpClmInfoList(List<OpClmInfo> opClmInfoList);

    void insertOpDvpAreaInfoList(List<OpDvpAreaInfo> opDvpAreaInfoList);

    void insertOpDvpInfoList(List<OpDvpInfo> opDvpInfoList);

    void insertOpOrdCostInfoList(List<OpOrdCostInfo> OpOrdCostInfoList);

    void insertOpOrdBnfInfoList(List<OpOrdBnfInfo> opOrdBnfInfoList);

    void insertOpOrdBnfRelInfoList(List<OpOrdBnfRelInfo> opOrdBnfRelInfoList);

}
