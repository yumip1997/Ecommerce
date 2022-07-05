package com.plateer.ec1.promotion.apply.mapper;

import com.plateer.ec1.promotion.apply.vo.PdPrmVO;
import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
import com.plateer.ec1.promotion.apply.vo.PrmCartAplyVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrmApplyMapper {

    List<PrmAplyVO> getApplicablePdCupList(PrmRequestBaseVO prmRequestBaseVO);

    List<PrmCartAplyVO> getApplicableCartCupList(PrmRequestBaseVO prmRequestBaseVO);

}
