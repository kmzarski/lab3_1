package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.util.ArrayList;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookKeeperTest {

    @Mock
    private InvoiceRequest invoiceRequest;
    @Mock
    private InvoiceFactory invoiceFactory;
    @Mock
    private TaxPolicy taxPolicy;
    @Mock
    private Invoice invoice;
    @Mock
    private RequestItem requestItem;

    private BookKeeper bookKeeper;

    private ArrayList<InvoiceLine> invoiceLines;
    private ArrayList<RequestItem> requestItems;

    @Before
    public void setUp() throws Exception {
        invoiceLines = new ArrayList<>();
        requestItems = new ArrayList<>();
        when(taxPolicy.calculateTax(any(), any())).thenReturn(mock(Tax.class));
        when(invoiceRequest.getItems()).thenReturn(requestItems);
        when(invoiceFactory.create(any(ClientData.class))).thenReturn(invoice);
        Mockito.doAnswer((Answer) invocation -> {
            invoiceLines.add((InvoiceLine) invocation.getArguments()[0]);
            return null;
        }).when(invoice).addItem(Matchers.any(InvoiceLine.class));
    }

    @Test
    public void shouldReturnOneInvoice() {
        bookKeeper = new BookKeeper(invoiceFactory);
        when(requestItem.getProductData()).thenReturn(mock(ProductData.class));
        when(requestItem.getTotalCost()).thenReturn(mock(Money.class));
        requestItems.add(requestItem);
        bookKeeper.issuance(invoiceRequest, taxPolicy);
        assertThat(invoiceLines.size(), is(1));
    }

    @Test
    public void shouldReturnTwoCountForCalculateTaxMethodForTwoInvoice() {
        bookKeeper = new BookKeeper(invoiceFactory);
        when(requestItem.getProductData()).thenReturn(mock(ProductData.class));
        when(requestItem.getTotalCost()).thenReturn(mock(Money.class));
        requestItems.add(requestItem);
        requestItems.add(requestItem);
        bookKeeper.issuance(invoiceRequest, taxPolicy);
        assertThat(invoiceLines.size(), is(2));
    }

    @Test
    public void shouldReturnZeroCountForCalculateTaxMethodForZeroInvoice() {
        bookKeeper = new BookKeeper(invoiceFactory);
        when(requestItem.getProductData()).thenReturn(mock(ProductData.class));
        when(requestItem.getTotalCost()).thenReturn(mock(Money.class));
        bookKeeper.issuance(invoiceRequest, taxPolicy);
        assertThat(invoiceLines.size(), is(0));
    }

    @Test
    public void shouldReturnOnesAfterOneTaxCalculatorUsingVerifyMethod() {
        bookKeeper = new BookKeeper(invoiceFactory);
        when(requestItem.getProductData()).thenReturn(mock(ProductData.class));
        when(requestItem.getTotalCost()).thenReturn(mock(Money.class));
        requestItems.add(requestItem);
        bookKeeper.issuance(invoiceRequest, taxPolicy);
        verify(taxPolicy, times(1)).calculateTax(any(), any());
    }

    @Test
    public void shouldReturnRandomValueAfterOneTaxCalculator() {
        bookKeeper = new BookKeeper(invoiceFactory);
        when(requestItem.getProductData()).thenReturn(mock(ProductData.class));
        when(requestItem.getTotalCost()).thenReturn(mock(Money.class));
        Random generator = new Random();
        int iterationNumber = generator.nextInt(100);

        for (int i = 0; i < iterationNumber; i++) {
            requestItems.add(requestItem);
        }

        bookKeeper.issuance(invoiceRequest, taxPolicy);
        verify(taxPolicy, times(iterationNumber)).calculateTax(any(), any());
    }

    @Test
    public void testingValueRemovingFromListAfterissuance() {
        bookKeeper = new BookKeeper(invoiceFactory);
        when(requestItem.getProductData()).thenReturn(mock(ProductData.class));
        when(requestItem.getTotalCost()).thenReturn(mock(Money.class));
        requestItems.add(requestItem);
        bookKeeper.issuance(invoiceRequest, taxPolicy);
        requestItems.remove(requestItems.size() - 1);
        verify(taxPolicy, times(1)).calculateTax(any(), any());
    }
}