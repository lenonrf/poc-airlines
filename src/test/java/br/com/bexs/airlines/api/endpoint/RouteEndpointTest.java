package br.com.bexs.airlines.api.endpoint;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.jayway.restassured.RestAssured;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Test;

import java.io.File;

import static org.apache.http.HttpStatus.SC_OK;

public class RouteEndpointTest extends BaseTest {

    public static final String DATASET = "classpath:/datasets/route-dataset.xml";
    public static final String DATASET_EMPTY = "classpath:/datasets/empty-dataset.xml";

    @Test
    @DatabaseSetup(DATASET)
    @DatabaseTearDown(DATASET_EMPTY)
    public void shouldReturnAllRoutes() {

        RestAssured.given()
                .log().everything()
                .when()
                .get(address() + "/route")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(SC_OK)
                .body("$", hasSize(7));
    }

    @Test
    @DatabaseSetup(DATASET)
    @DatabaseTearDown(DATASET_EMPTY)
    public void shouldBestRoute() {

        RestAssured.given()
                .log().everything()
                .when()
                .get(address() + "/route;airport-origin=GRU;airport-destination=ORL")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(SC_OK)
                .body("description", equalTo("GRU - BRC - SCL - ORL"));
    }


    @Test
    @DatabaseSetup(DATASET)
    @DatabaseTearDown(DATASET_EMPTY)
    public void shouldNotFoundRoute() {

        RestAssured.given()
                .log().everything()
                .when()
                .get(address() + "/route;airport-origin=AAA;airport-destination=BBB")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DatabaseSetup(DATASET_EMPTY)
    @DatabaseTearDown(DATASET_EMPTY)
    @ExpectedDatabase(value= DATASET, assertionMode = NON_STRICT)
    public void shouldUploadFile() {

        File file = new File("input-file-test.txt");

        RestAssured.given()
                .log().everything()
                .multiPart(file)
                .when()
                .post(address() + "/route/file-upload")
                .then()
                .log().everything()
                .assertThat()
                .statusCode(SC_OK);
    }
}

