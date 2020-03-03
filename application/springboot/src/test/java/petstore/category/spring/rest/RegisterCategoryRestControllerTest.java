/*
 * Java (TM) Pet Store Modernized Edition - 2020
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package petstore.category.spring.rest;

import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import petstore.category.controller.model.CategoryRequest;
import petstore.category.controller.model.CategoryResponse;
import petstore.category.spring.Entrypoint;
import petstore.category.usecase.port.CategoryIdGenerator;

/**
 * @author fabiojose
 */
@Tag("integration")
@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    classes = Entrypoint.class
)
@ExtendWith(MockitoExtension.class)
public class RegisterCategoryRestControllerTest {

    @MockBean
    CategoryIdGenerator idGenerator;

    @Autowired
    WebTestClient http;

    @Test
    public void should_res_400_when_there_is_no_req_body() {

        http.post()
            .uri("/categories")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    public void should_res_400_when_req_name_is_null() {

        // setup
        CategoryRequest req = new CategoryRequest(null, "desc");

        Map<String, Object> expected = Map.of(
            "path", "/categories",
            "status", 400,
            "error", "Bad Request",
            "message", "name.should.not.be.null");

        // act & assert
        http.post()
            .uri("/categories")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Map.class)
                .isEqualTo(expected);

    }

    @Test
    public void should_res_400_when_req_name_is_empty() {

        // setup
        CategoryRequest req = new CategoryRequest("  ", "desc");

        Map<String, Object> expected = Map.of(
            "path", "/categories",
            "status", 400,
            "error", "Bad Request",
            "message", "name.should.not.be.empty");

        // act & assert
        http.post()
            .uri("/categories")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Map.class)
                .isEqualTo(expected);

    }

    @Test
    public void should_res_400_when_req_description_is_null() {

        // setup
        CategoryRequest req = new CategoryRequest("cat", null);

        Map<String, Object> expected = Map.of(
            "path", "/categories",
            "status", 400,
            "error", "Bad Request",
            "message", "description.should.not.be.null");

        // act & assert
        http.post()
            .uri("/categories")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Map.class)
                .isEqualTo(expected);

    }

    @Test
    public void should_res_400_when_req_description_is_empty() {

        // setup
        CategoryRequest req = new CategoryRequest("cat", "   ");

        Map<String, Object> expected = Map.of(
            "path", "/categories",
            "status", 400,
            "error", "Bad Request",
            "message", "description.should.not.be.empty");

        // act & assert
        http.post()
            .uri("/categories")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Map.class)
                .isEqualTo(expected);

    }

    @Test
    public void should_res_200_when_save_the_catetory() {

        // setup
        CategoryRequest req = new CategoryRequest("cat", "desc");
        
        when(idGenerator.nextId())
            .thenReturn("an.id");

        // act & assert
        http.post()
            .uri("/categories")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
                .expectStatus().isOk();

    }

    @Test
    public void should_res_the_id_of_category_when_save_it() {

        // setup
        final String id = "catID99";
        final String name = "Cat 99";
        final String description = "Cat 99 Description";
        CategoryRequest req = new CategoryRequest(name, description);

        CategoryResponse expected = new CategoryResponse();
        expected.setId(id);
        expected.setName(name);
        expected.setDescription(description);

        when(idGenerator.nextId())
            .thenReturn(id);

        // act & assert
        http.post()
            .uri("/categories")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
                .expectStatus().isOk()
                .expectBody(CategoryResponse.class)
                .isEqualTo(expected);
    }
}