package com.plateer.ec1.promotion.apply.vo.request;

import com.plateer.ec1.common.model.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
public class PrmRequestBaseVO {

    @NotNull(message = "회원 번호는 필수입니다!")
    private String mbrNo;
    private List<Product> productList;
    @NotNull(message = "쿠폰 종류 코드는 필수입니다!")
    private String cpnKindCd;
    @NotNull(message = "프로모션 종류 코드는 필수입니다!")
    private String prmKindCd;
}
