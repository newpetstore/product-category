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
package petstore.category.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import petstore.category.domain.Category;
import petstore.category.usecase.model.CategoryCreated;
import petstore.category.usecase.port.CategoryDatastore;

/**
 * @author fabiojose
 */
@ExtendWith(MockitoExtension.class)
public class ReadCategoryTest {

    @Mock
    CategoryDatastore datastore;

    @InjectMocks
    ReadCategory usecase;

    @Test
    public void should_throw_when_construct_with_null_datastore() {

        assertThrows(NullPointerException.class, () -> {
            new ReadCategory(null);
        });

    }

    @Test
    public void should_throw_when_id_is_null() {

        assertThrows(NullPointerException.class, () -> {
            usecase.byId(null);
        });

    }

    @Test
    public void should_result_empty_when_id_not_found() {

        Optional<CategoryCreated> actual = usecase.byId("catx8");

        // assert
        assertTrue(actual.isEmpty());

    }

    @Test
    public void should_be_ok_when_id_found() {

        final String id = "catx77";
        final Category category = Category.builder()
            .id(id)
            .name("Cat name")
            .description("Cat desc")
            .build();

        when(datastore.get(id))
            .thenReturn(Optional.of(category));

        // act
        Optional<CategoryCreated> actual = usecase.byId(id);

        // assert
        assertTrue(actual.isPresent());
        assertEquals(category.getId(), actual.get().getId());
        assertEquals(category.getName(), actual.get().getName());
        assertEquals(category.getDescription(), actual.get().getDescription());
    }
}