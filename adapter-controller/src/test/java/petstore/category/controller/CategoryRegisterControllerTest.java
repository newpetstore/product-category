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
package petstore.category.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import petstore.category.controller.model.CategoryRequest;
import petstore.category.controller.model.CategoryResponse;
import petstore.category.usecase.RegisterNewCategory;
import petstore.category.usecase.model.CategoryCreated;

/**
 * @author fabiojose
 */
@ExtendWith(MockitoExtension.class)
public class CategoryRegisterControllerTest {

    @Mock
    RegisterNewCategory usecase;

    @InjectMocks
    CategoryRegisterController controller;

    @Test
    public void should_throw_when_construct_with_null_usecase() {

        assertThrows(NullPointerException.class, () -> {
            new CategoryRegisterController(null);
        });

    }

    @Test
    public void should_throw_when_request_is_null() {

        assertThrows(NullPointerException.class, () -> {
            controller.register(null);
        });

    }

    @Test
    public void should_fail_when_category_is_invalid() throws Exception {

        CategoryRequest request = new CategoryRequest();
        request.setName("  ");
        request.setDescription("Cat Desc");

        CompletableFuture<CategoryResponse> future =
            controller.register(() -> request);

        // act
        Throwable e =
        assertThrows(ExecutionException.class, () -> {
            future.get();
        });

        assertEquals("name.should.not.be.empty", e.getCause().getMessage());
    }

    @Test
    public void should_register_new_categories() throws Exception {

        final String id = "catx33";
        final String name = "cat 33";
        final String description = "desc cat 33";

        CategoryRequest request = new CategoryRequest();
        request.setName(name);
        request.setDescription(description);

        CategoryCreated created = new CategoryCreated();
        created.setId(id);
        created.setName(name);
        created.setDescription(description);

        when(usecase.create(Mockito.any()))
            .thenReturn(created);

        // act
        CompletableFuture<CategoryResponse> future = 
            controller.register(() -> request);

        CategoryResponse actual = future.get();

        // assert
        assertEquals(id, actual.getId());
        assertEquals(name, actual.getName());
        assertEquals(description, actual.getDescription());
        
    }
}
