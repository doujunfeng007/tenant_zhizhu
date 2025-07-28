//package com.minigod.zero.trade.service;
//
//import com.minigod.zero.core.tool.api.R;
//import com.minigod.zero.trade.fund.vo.request.TotalAccountRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//public class IFundServiceTest {
//
//    @InjectMocks
//    private IFundService iFundService;
//
//    @Mock
//    private TotalAccountRequest totalAccountRequest;
//
//    @Mock
//    private R mockResponse;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testGetTotalAccountWhenGivenValidRequestThenReturnCorrectResponse() {
//        when(iFundService.getTotalAccount(totalAccountRequest)).thenReturn(mockResponse);
//
//        R response = iFundService.getTotalAccount(totalAccountRequest);
//
//        assertEquals(mockResponse, response);
//        verify(iFundService, times(1)).getTotalAccount(totalAccountRequest);
//    }
//
//    @Test
//    public void testGetTotalAccountWhenGivenRequestThenCallCorrectServices() {
//        iFundService.getTotalAccount(totalAccountRequest);
//
//        verify(iFundService, times(1)).getTotalAccount(totalAccountRequest);
//    }
//
//    @Test
//    public void testGetTotalAccountWhenGivenInvalidRequestThenReturnError() {
//        when(iFundService.getTotalAccount(totalAccountRequest)).thenReturn(R.fail("Invalid request"));
//
//        R response = iFundService.getTotalAccount(totalAccountRequest);
//
//        assertEquals("Invalid request", response.getMsg());
//        verify(iFundService, times(1)).getTotalAccount(totalAccountRequest);
//    }
//}
