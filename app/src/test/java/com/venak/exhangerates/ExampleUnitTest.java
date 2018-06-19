package com.venak.exhangerates;

import com.venak.exhangerates.listeners.DataAccessListener;
import com.venak.exhangerates.model.ExchangeRate;
import com.venak.exhangerates.services.handler.ServiceExecutor;
import com.venak.exhangerates.services.handler.implementation.GetExchangeRatesServiceImpl;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doAnswer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Mock
    ServiceExecutor serviceExecutor;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Mock
    GetExchangeRatesServiceImpl getCurrencyService;
    List<ExchangeRate> exchangeRates = null;

    @Test
    public void testDoAction() {
        // Cause service.doAction to be called
        getCurrencyService = Mockito.mock(GetExchangeRatesServiceImpl.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation)   {
                System.out.print(invocation.getArguments().length);
                ((DataAccessListener) invocation.getArguments()[0]).onSuccess(exchangeRates);
                assertNotNull(exchangeRates);
                return true;
            }
        }).when(getCurrencyService).executeRequestCommand();
    }


    public void testLatest() {
//        serviceExecutor = Mockito.mock(ServiceExecutor.class);
//        final CompletableFuture<List<ExchangeRate>> future = new CompletableFuture<>();
////        final GetExchangeRatesServiceImpl getCurrencyService = new GetExchangeRatesServiceImpl(new DataAccessListener<List<ExchangeRate>>() {
////            @Override
////            public void onSuccess(List<ExchangeRate> results) {
////                future.complete(results);
////            }
////
////            @Override
////            public void onFailed(String message) {
////
////            }
////        });
//        synchronized (future) {
//            serviceExecutor.executeService(new GetExchangeRatesServiceImpl(new DataAccessListener<List<ExchangeRate>>() {
//                @Override
//                public void onSuccess(List<ExchangeRate> results) {
//                    future.complete(results);
//                }
//
//                @Override
//                public void onFailed(String message) {
//
//                }
//            }));
//        }
//        try {
//            assertNotNull(future.get());
//        } catch (Exception e) {
//
//        }

    }
}