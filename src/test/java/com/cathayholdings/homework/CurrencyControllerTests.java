package com.cathayholdings.homework;

import com.cathayholdings.homework.controller.request.CurrencyCodeReq;
import com.cathayholdings.homework.controller.request.CurrencyReq;
import com.cathayholdings.homework.controller.response.CurrencyRes;
import com.cathayholdings.homework.controller.response.MyBpiCurrentpriceRes;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CurrencyControllerTests {

    static {
        System.setProperty("spring.profiles.active", "test");
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    /**
     * 1. 測試呼叫查詢幣別對應表資料 API，並顯示其內容
     */
    @Test
    @Order(1)
    void testCreate() {
        List<CurrencyReq> reqList = new ArrayList<>(3);
        reqList.add(new CurrencyReq("USD", "美金"));
        reqList.add(new CurrencyReq("GBP", "英鎊"));
        reqList.add(new CurrencyReq("EUR", "歐元"));
        reqList.add(new CurrencyReq("TWD", "新臺幣"));

        for (CurrencyReq req : reqList) {
            ResponseEntity<String> res = testRestTemplate.postForEntity(getCurrencyUrl(), req, String.class);
            Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
        }
    }

    /**
     * 2. 測試呼叫新增幣別對應表資料 API
     */
    @Test
    @Order(2)
    void testQuery() {
        ResponseEntity<CurrencyRes> res = getCurrencies();

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
        Assertions.assertEquals(4, Objects.requireNonNull(res.getBody()).getCurrencyInfoList().size());

        System.out.println(res.getBody());
    }

    private ResponseEntity<CurrencyRes> getCurrencies() {
        String url = "http://localhost:" + port + "/currencies";

        return testRestTemplate.getForEntity(url, CurrencyRes.class);
    }

    /**
     * 3. 測試呼叫更新幣別對應表資料 API，並顯示其內容
     */
    @Test
    @Order(3)
    void testUpdate() {
        String targetCode = "TWD";
        String newTwdName = "~台幣~";
        CurrencyReq req = new CurrencyReq("TWD", newTwdName);

        HttpEntity<CurrencyReq> reqEntity = new HttpEntity<>(req);
        ResponseEntity<String> res = testRestTemplate.exchange(getCurrencyUrl(), HttpMethod.PATCH, reqEntity, String.class);

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());

        CurrencyRes currencies = getCurrencies().getBody();
        Assertions.assertEquals(4, Objects.requireNonNull(currencies).getCurrencyInfoList().size());
        Assertions.assertTrue(
                Objects.requireNonNull(currencies).getCurrencyInfoList()
                        .stream()
                        .filter(c -> targetCode.equals(c.getCode()))
                        .anyMatch(c -> newTwdName.equals(c.getName()))
        );
    }

    private String getCurrencyUrl() {
        return "http://localhost:" + port + "/currency";
    }

    /**
     * 4. 測試呼叫刪除幣別對應表資料 API
     */
    @Test
    @Order(4)
    void testDelete() {
        String deleteCurrencyCode = "TWD";
        CurrencyCodeReq req = new CurrencyCodeReq(deleteCurrencyCode);
        HttpEntity<CurrencyCodeReq> reqEntity = new HttpEntity<>(req);

        ResponseEntity<String> res =
                testRestTemplate.exchange(URI.create(getCurrencyUrl()), HttpMethod.DELETE, reqEntity, String.class);

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());

        CurrencyRes currencies = getCurrencies().getBody();
        Assertions.assertEquals(3, Objects.requireNonNull(currencies).getCurrencyInfoList().size());
    }

    /**
     * 5. 測試呼叫 coindesk API，並顯示其內容
     */
    @Test
    @Order(5)
    void testCallCoindeskApi() {
        String url = "http://localhost:" + port + "/doindesk/bpi/currentprice";
        ResponseEntity<String> res = testRestTemplate.getForEntity(url, String.class);

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(res.getBody()).contains("bpi"));
        Assertions.assertTrue(Objects.requireNonNull(res.getBody()).contains("USD"));
        Assertions.assertTrue(Objects.requireNonNull(res.getBody()).contains("GBP"));
        Assertions.assertTrue(Objects.requireNonNull(res.getBody()).contains("EUR"));

        System.out.println(res.getBody());
    }

    /**
     * 6. 測試呼叫資料轉換的 API，並顯示其內容
     */
    @Test
    @Order(6)
    void testCallCoindeskApiAndTransferCurrency() {
        String url = "http://localhost:" + port + "/doindesk/my/bpi/currentprice";
        ResponseEntity<MyBpiCurrentpriceRes> res = testRestTemplate.getForEntity(url, MyBpiCurrentpriceRes.class);

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());

        Assertions.assertTrue((Objects.requireNonNull(res.getBody()).getRateList().size() == 3));

        System.out.println(res.getBody());
    }

}
