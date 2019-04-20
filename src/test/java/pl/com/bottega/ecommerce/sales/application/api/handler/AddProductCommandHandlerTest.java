package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

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

    @Mock
    private SystemContext systemContext;

    @Mock
    private Reservation reservation;

    @Mock
    private Product product;

    @Mock
    private AddProductCommand addProductCommand;

    @Mock
    private Client client;

    private AddProductCommandHandler addProductCommandHandler;

    @Mock
    private SystemUser systemUser;

    @Before
    public void setUp() throws Exception {
        when(reservationRepository.load(any(Id.class))).thenReturn(reservation);
        when(productRepository.load(any(Id.class))).thenReturn(product);
        when(suggestionService.suggestEquivalent(any(), any())).thenReturn(product);
        when(clientRepository.load(any())).thenReturn(client);
        when(systemContext.getSystemUser()).thenReturn(systemUser);
        // when(systemContext.getSystemUser().getClientId()).thenReturn(any(Id.class));
    }

    @Test
    public void simpleProductCommandHandlerTest() {
        addProductCommandHandler = new AddProductCommandHandler(reservationRepository,
                productRepository,
                suggestionService,
                clientRepository,
                systemContext);
        addProductCommandHandler.handle(addProductCommand);
    }
}

