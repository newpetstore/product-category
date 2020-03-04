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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import petstore.category.controller.model.CategoryResponse;
import petstore.category.domain.Category;
import petstore.category.spring.Entrypoint;
import petstore.category.usecase.port.CategoryDatastore;

/**
 * @author fabiojose
 */
@Tag("integration")
@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    classes = Entrypoint.class
)
public class GetCategoryRestControllerTest {

    @Autowired
    CategoryDatastore datastore;

    @Autowired
    WebTestClient http;

    @Test
    public void should_res_404_when_id_not_found() {

        http.get()
            .uri("/categories/catx880_NotFound")
            .exchange()
                .expectStatus().isNotFound();
    
    }

    @Test
    public void should_res_200_when_id_found() {

        final String id = "idCat990";
        final String name = "Cat 990";
        final String description = "Cat 990 Description";

        Category category = Category.builder()
            .id(id)
            .name(name)
            .description(description)
            .build();

        datastore.put(category);

        http.get()
            .uri("/categories/" + id)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
                .isOk();
        
    }

    @Test
    public void should_res_with_found_category() {

        final String id = "idCat990";
        final String name = "Cat 990";
        final String description = "Cat 990 Description";

        Category category = Category.builder()
            .id(id)
            .name(name)
            .description(description)
            .build();

        datastore.put(category);

        CategoryResponse expected = new CategoryResponse();
        expected.setId(id);
        expected.setName(name);
        expected.setDescription(description);

        http.get()
            .uri("/categories/" + id)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
                .isOk()
                .expectBody(CategoryResponse.class)
                .isEqualTo(expected);
        
    }
}