package citrus.test;

import citrus.features.CustomMarshaller;
import com.consol.citrus.TestActionRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.junit.jupiter.CitrusSupport;
import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.consol.citrus.ws.actions.SoapActionBuilder.soap;

@CitrusSupport
public class SOAPWebServiceTests {

  @CitrusResource
  private TestContext context;
  @CitrusResource
  private TestActionRunner runner;

  @Test
  @DisplayName("SOAP test")
  @CitrusTest
  public void soapActionTest() {
    CustomMarshaller<Class<NumberToDollars>> ptxRq = new CustomMarshaller<>();
    CustomMarshaller<Class<NumberToDollarsResponse>> ptxRs = new CustomMarshaller<>();
    runner.$(soap()
        .client("soapClient")
        .send()
        .message()
        .body(ptxRq.convert(NumberToDollars.class, getNumberToDollarsRequest(),
            "http://www.dataaccess.com/webservicesserver/", "NumberToDollars")));

    runner.$(soap()
        .client("soapClient")
        .receive()
        .message()
        .body(ptxRs.convert(NumberToDollarsResponse.class, getNumberToDollarsResponse(),
            "http://www.dataaccess.com/webservicesserver/", "NumberToDollarsResponse")));
  }

  public NumberToDollars getNumberToDollarsRequest() {
    NumberToDollars numberToDollars = new NumberToDollars();
    numberToDollars.setDNum(new BigDecimal("75"));
    return numberToDollars;
  }

  public NumberToDollarsResponse getNumberToDollarsResponse() {
    NumberToDollarsResponse numberToDollarsResponse = new NumberToDollarsResponse();
    numberToDollarsResponse.setNumberToDollarsResult("seventy five dollars");
    return numberToDollarsResponse;
  }
}
