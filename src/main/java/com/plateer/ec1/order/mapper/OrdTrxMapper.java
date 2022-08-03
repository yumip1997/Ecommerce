package com.plateer.ec1.order.mapper;

import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.order.vo.OrderBasicVO;
import com.plateer.ec1.order.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrdTrxMapper {

    void insertOrderBasic(OrderBasicVO orderBasicVO);

    void insertOpGoodsInfo(List<OpGoodsInfo> opGoodsInfoList);

    void insertOpClmInfoList(List<OpClmInfo> opClmInfoList);

    void insertOpDvpAreaInfoList(List<OpDvpAreaInfo> opDvpAreaInfoList);

    void insertOpDvpInfoList(List<OpOrdCostInfo> opOrdCostInfoList);

    void insertOpOrdCostInfo(List<OpOrdCostInfo> OpOrdCostInfoList);

    void insertOpOrdBnfInfo(List<OpOrdBnfInfo> opOrdBnfInfoList);

    void insertOpOrdBnfRelInfo(List<OpOrdBnfRelInfo> opOrdBnfRelInfoList);

}
