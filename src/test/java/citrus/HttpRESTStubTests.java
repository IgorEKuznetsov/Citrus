package citrus;

import com.consol.citrus.TestActionRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.junit.jupiter.CitrusSupport;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import services.UserService;
import java.util.stream.IntStream;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;


@CitrusSupport
public class HttpRESTStubTests {

  @CitrusResource
  private TestContext context;
  @CitrusResource
  private TestActionRunner runner;

  @Test
  @DisplayName("Get all users stub test")
  @CitrusTest
  public void getAllUsersStubTest() {

    runner.$(http()
        .client("httpRESTClient")
        .send()
        .get("/get/all"));

    runner.$(http()
        .client("httpRESTClient")
        .receive()
        .response(HttpStatus.OK)
        .message()
        .validate(jsonPath()
            .expression("$.[0].userId", 1)
            .expression("$.[0].mark", null)
            .expression("$.[0].name", "@matches('^[a-zA-Z\\s]*$')@")
            .expression("$.[0].age", "@isNumber()@")
            .expression("$.[0].keySet()", "[name, course, userId, email, age, mark]"))

        .type(MessageType.JSON));


    //check context
    context.setVariable("myVariable", "some value");
    context.getVariable("userId");

    runner.$(echo("We have userId = " + context.getVariable("userId")));
    runner.$(echo("Property \"userId\" = " + "${userId}"));

  }

  @Test
  @DisplayName("Get user with mark stub test")
  @CitrusTest
  public void getUserStubTest() {
    runner.$(http()
        .client("httpRESTClient")
        .send()
        .get("/get/" + context.getVariable("userId")));

    runner.$(http()
        .client("httpRESTClient")
        .receive()
        .response(HttpStatus.OK)
        .message()
        .body(new ObjectMappingPayloadBuilder(new UserService().getUserWithMark(35), "objectMapper"))
        .type(MessageType.JSON));

  }

  @ParameterizedTest
  @MethodSource("userProvider")
  @DisplayName("Get user with mark stub parametrized test")
  @CitrusTest
  public void getUserStubParametrizedTest(int id) {
    runner.$(http()
        .client("httpRESTClient")
        .send()
        .get("/get/" + id));

    runner.$(http()
        .client("httpRESTClient")
        .receive()
        .response(HttpStatus.OK)
        .message()
        .body(new ObjectMappingPayloadBuilder(new UserService().getUserWithMark(id), "objectMapper"))
        .type(MessageType.JSON));
  }

  private static IntStream userProvider() {
    return IntStream.range(1, 100);
  }
}
