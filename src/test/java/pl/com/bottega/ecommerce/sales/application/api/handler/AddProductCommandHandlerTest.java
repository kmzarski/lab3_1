package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.Client;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.sharedkernel.Money;
import pl.com.bottega.ecommerce.system.application.SystemContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddProductCommandHandlerTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private SuggestionService suggestionService;
    @Mock
    private ClientRepository clientRepository;

    private AddProductCommandHandler addProductCommandHandler;
    private AddProductCommand addProductCommand;
    private Client client;
    private SystemContext systemContext;
    private Product product;
    private Reservation reservation;

    @Before
    public void setUp() {
        addProductCommand = new AddProductCommand(new Id("1"), new Id("1"), 100);
        systemContext = new SystemContext();
        client = new Client();
        addProductCommandHandler =
                new AddProductCommandHandler(reservationRepository, productRepository, suggestionService, clientRepository, systemContext);
        product = new Product(Id.generate(), new Money(BigDecimal.TEN, Money.DEFAULT_CURRENCY), "pasta", ProductType.FOOD);
        reservation = new Reservation(Id.generate(), Reservation.ReservationStatus.OPENED, new ClientData(Id.generate(), "name"), new Date());
        when(reservationRepository.load(any(Id.class))).thenReturn(reservation);
        when(productRepository.load(any(Id.class))).thenReturn(product);
    }

    @Test
    public void testRepositoryForUsedOnce() {
        addProductCommandHandler.handle(addProductCommand);
        verify(reservationRepository, times(1)).load(any());
        verify(productRepository, times(1)).load(any());
        verify(reservationRepository, times(1)).save(any());
        assertThat(true, is(true));
    }

    @Test
    public void testForMulitpleLoadMethodUsage() {
        int randValue = new Random().nextInt(100);
        for (int i = 0; i < randValue; i++) {
            addProductCommandHandler.handle(addProductCommand);
        }
        verify(productRepository, times(randValue)).load(any());
        verify(reservationRepository, times(randValue)).load(any());
        assertThat(true, is(true));
    }

    @Test
    public void testForMulitpleSaveMethodUsage() {
        int randValue = new Random().nextInt(100);
        for (int i = 0; i < randValue; i++) {
            addProductCommandHandler.handle(addProductCommand);
        }
        verify(reservationRepository, times(randValue)).save(any());
        assertThat(true, is(true));
    }

}

