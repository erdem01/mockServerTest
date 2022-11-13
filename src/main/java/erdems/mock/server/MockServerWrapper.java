package erdems.mock.server;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;

import java.util.concurrent.TimeUnit;

import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;

public class MockServerWrapper {
	
	private ClientAndServer mockServer;
	
	public void startServer() {
		mockServer = startClientAndServer(1080);
	}

	public void createExpectation(String requestBody, String responseBody) {
		new MockServerClient("127.0.0.1", 1080)
        .when(
            request()
                .withMethod("POST")
                .withPath("/validate")
                .withHeader("Content-type", "application/json")
                .withBody(exact(requestBody)),
                exactly(1)
        )
        .respond(
            response()
                .withStatusCode(401)
                .withHeaders(
                    new Header("Content-Type", "application/json; charset=utf-8"),
                    new Header("Cache-Control", "public, max-age=86400")
            )
                .withBody(responseBody)
                .withDelay(TimeUnit.SECONDS,1)
        );
	}
	
	public void reset() {
		mockServer.reset();
	}
	
	public void stop() {
		mockServer.stop();
	}
	
}
