package br.com.bexs.airlines.api.endpoint;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.common.io.Files;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public abstract class BaseTest {

    private static final String RESOURCE_DIRECTORY = "src/test/resources";

    private static WireMockServer server = new WireMockServer(9898);

    @LocalServerPort
    private int port;

    protected String address() {
        return "http://localhost:" + getPort();
    }

    public String wireMockAddress() {
        return "http://localhost:" + server.port();
    }

    private int getPort() {
        return port;
    }

    protected static String resource(String payloadPath) throws IOException {
        File file = new File(RESOURCE_DIRECTORY + payloadPath);
        if (!file.exists()) {
            throw new IllegalArgumentException("Payload file not found: "
                    + RESOURCE_DIRECTORY + payloadPath);
        }
        return Files.toString(file, Charset.defaultCharset());
    }
}