package com.plateer.ec1.payment.utils;

import java.util.Arrays;
import java.util.List;

public class InicisApiConstants {

    public static final List<String> VACCT_DPST_NTC_LIST = Arrays.asList("203.238.37.15","39.115.212.9","183.109.71.153");

    public static final String BASE_URL = "https://iniapi.inicis.com/api/v1";

    public static final String VIRTUAL_ACCOUNT_SEQ_URL = BASE_URL + "/formpay";

    public static final String VIRTUAL_ACCOUNT_REFUND = BASE_URL + "/refund";


}
