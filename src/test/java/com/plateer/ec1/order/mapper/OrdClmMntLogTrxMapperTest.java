package com.plateer.ec1.order.mapper;

import com.plateer.ec1.common.model.order.OpOrdClmMntLog;
import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.order.vo.OrderRequestVO;
import com.plateer.ec1.resource.TestConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class OrdClmMntLogTrxMapperTest {

    @Autowired
    private OrdClmMntLogTrxMapper ordClmMntLogTrxMapper;
    private JsonReaderUtil jsonReaderUtil;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "order");
    }

    @Test
    @DisplayName("모니터링 로그 insert 후 로그 순번이 반환된다")
    void insert_test(){
        OrderRequestVO object = jsonReaderUtil.getObject("/OrderRequest.json", OrderRequestVO.class);
        OpOrdClmMntLog insertData = OpOrdClmMntLog.getInsertData(object);
        Long logSeq = ordClmMntLogTrxMapper.insertMonitoringLog(insertData);
        Assertions.assertThat(logSeq).isNotNull();
    }
}