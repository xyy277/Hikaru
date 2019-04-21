package savvy.wit.framework.core.algorithm.model.key;


import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : KeyStore
 * Author : zhoujiajun
 * Date : 2019/3/26 14:37
 * Version : 1.0
 * Description : 
 ******************************/
public interface KeyStore {

    String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCn6dre5CkrpxmUzh_UcPEKxJawwh1hDaR5pQS0W5VskSYC-Aq6SMYz7KImLet4uIkqVUVfX_03JMUZ6YDNvEHc6ZL3ssKez08LHKzQbPNtAvccDCz_0cpqp6dLpSYCd0Evr-D2ow5HxsDm70kX-qszG8E_L3M1xO25-1uvJs-wowIDAQAB";

    String RSA_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKfp2t7kKSunGZTOH9Rw8QrElrDCHWENpHmlBLRblWyRJgL4CrpIxjPsoiYt63i4iSpVRV9f_TckxRnpgM28Qdzpkveywp7PTwscrNBs820C9xwMLP_Rymqnp0ulJgJ3QS-v4PajDkfGwObvSRf6qzMbwT8vczXE7bn7W68mz7CjAgMBAAECgYAWpDTsGy3mAfk0FHs8RSJrhkifev1FKfbEpFi3DSZf5k6O1e10Yv2-4KiYp1Z6B8APIkJhcusM9XtSXCcPFtR2gbJk7DPkUZvzJDW3uLMwJct9IGFGc20lwD-wQg8xGZEcapFmmiTBusfa1qfDP19pxV5RI9aiz3yRxhCLrdmvuQJBAPNamSsTOCTNjZHuVg33W5uauD5x54Ga92kRiUsdY28KEs8Mj6qxWIT6z7TJbJigvW_e5xc8IVk0h8kzbDwtv6cCQQCwo6cjZkSVd2qXb8VcJfYzxwcE9po4JxKt0EqDiItPpaFkaaVpa0cpoCb5Ea00QCKfkdpsDujn2FSQWwXPKMalAkEA1Q-2WOtb1YUOdP0J5PJ0BzmgJDQP7_5grWIIJXbWjupv601hH55kFRGO9wb_iGX7Vc3_3-zqebKvS-40zj9zswJAcS3rAfudJkgFvFKZmpmYT0TPcpav6hrfFQ_JLs5mtPnjl3s5yXo7dqhvUFfLKxeNF8FUurgy85rhVD1-U2ZFIQJAG4RRuIE9mx4NscEiDOmrm8-U_dGwbJq1KUr-IexPT-icr4YqUAu0sCr_f42ao47mHK_bQjHC7PMzw99EhzD9eA";

    RSAPublicKey getRsaPublicKey();

    RSAPrivateKey getRsaPrivateKey();

    String getPublicKey();

    String getPrivateKey();

}
