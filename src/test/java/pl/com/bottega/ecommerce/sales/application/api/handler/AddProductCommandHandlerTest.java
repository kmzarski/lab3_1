package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.Client;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.system.application.SystemContext;
import pl.com.bottega.ecommerce.system.application.SystemUser;

import java.util.ArrayList;

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

    @Before
    public void setUp() {
        addProductCommand = new AddProductCommand(new Id("1"), new Id("1"), 100);
        systemContext = new SystemContext();
        client = new Client();
        addProductCommandHandler =
                new AddProductCommandHandler(reservationRepository, productRepository, suggestionService, clientRepository, systemContext);

    }

    @Test
    public void testReservationForOneAvailableProduct() {
        addProductCommandHandler = new AddProductCommandHandler(reservationRepository,
                productRepository,
                suggestionService,
                clientRepository,
                systemContext);
        addProductCommandHandler.handle(addProductCommand);
        verify(reservation).add(products.capture(), Mockito.anyInt());
        verify(product, times(1)).isAvailable();
    }

    @Test
    public void testForAddMethodOneUsage() {
        addProductCommandHandler = new AddProductCommandHandler(reservationRepository,
                productRepository,
                suggestionService,
                clientRepository,
                systemContext);
        addProductCommand = new AddProductCommand(new Id("1"), new Id("2"), 10);
        addProductCommandHandler.handle(addProductCommand);
        verify(reservation, times(1)).add(product, 10);
    }

    @Test
    public void testForReservationRepository() {
        addProductCommandHandler = new AddProductCommandHandler(reservationRepository,
                productRepository,
                suggestionService,
                clientRepository,
                systemContext);
        addProductCommandHandler.handle(addProductCommand);
        verify(reservationRepository,times(1)).save(reservation);
    }
    @Test
    public void testForSuggestionService(){
        addProductCommandHandler = new AddProductCommandHandler(reservationRepository,
                productRepository,
                suggestionService,
                clientRepository,
                systemContext);
        when(product.isAvailable()).thenReturn(false);
        addProductCommandHandler.handle(addProductCommand);
        verify(suggestionService).suggestEquivalent(product,client);
    }
}

