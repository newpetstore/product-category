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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import petstore.category.controller.model.CategoryResponse;
import petstore.category.usecase.ReadCategory;
import petstore.category.usecase.model.CategoryCreated;

/**
 * @author fabiojose
 */
@ExtendWith(MockitoExtension.class)
public class ReadCategoryControllerTest {

    @Mock
    ReadCategory usecase;

    @InjectMocks
    ReadCategoryController controller;

    @Test
    public void should_throw_when_constructor_arg_is_null() {

        assertThrows(NullPointerException.class, () -> {
            new ReadCategoryController(null);
        });

    }

    @Test
    public void should_throw_when_id_supplier_is_null() {

        assertThrows(NullPointerException.class, () -> {
            controller.getById(null);
        });

    }

    @Test
    public void should_result_empty_when_id_not_found() throws Exception {

        when(usecase.byId(anyString()))
            .thenReturn(Optional.empty());

        // act
        CompletableFuture<Optional<CategoryResponse>> future =
            controller.getById(() -> "not-found");

        Optional<CategoryResponse> actual = future.get();

        // assert
        assertTrue(actual.isEmpty());
    }

    @Test
    public void should_be_ok_when_found_then_category() throws Exception {

        String id = "catx22";
        String name = "Cat 22";
        String description = "Cat 22 Description";

        CategoryCreated created = new CategoryCreated(id, name, description);

        when(usecase.byId(id))
            .thenReturn(Optional.of(created));

        // act
        CompletableFuture<Optional<CategoryResponse>> future = 
            controller.getById(() -> id);

        Optional<CategoryResponse> actual = future.get();
        
        // assert
        assertTrue(actual.isPresent());
        assertEquals(id, actual.get().getId());
        assertEquals(name, actual.get().getName());
        assertEquals(description, actual.get().getDescription());
    }
}