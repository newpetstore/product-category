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
package petstore.category.usecase.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author fabiojose
 */
public class NewCategoryTest {

    @Test
    public void should_be_ok_with_valid_attributes() {

        // setup
        final String name = "Cat name";
        final String description = "Cat desc";

        // act
        NewCategory _category = NewCategory.builder()
            .name(name)
            .description(description)
            .build();

        // assert
        assertEquals(name, _category.getName());
        assertEquals(description, _category.getDescription());
    }

    @Test
    public void should_throw_when_name_is_null() {

        // assert
        IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> {
            // act
            NewCategory.builder()
                .name(null)
                .description("Cat Desc")
                .build();
        });

        assertEquals("name.should.not.be.null", e.getMessage());
    }

    @Test
    public void should_throw_when_description_is_null() {

        IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> {
            // act
            NewCategory.builder()
                .name("Cat Name")
                .description(null)
                .build();
        });

        assertEquals("description.should.not.be.null", e.getMessage());
    }

    @Test
    public void should_throw_when_name_is_empty() {

        IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> {
            // act
            NewCategory.builder()
                .name("  ")
                .description("Cat Desc")
                .build();
        });

        assertEquals("name.should.not.be.empty", e.getMessage());
    }

    @Test
    public void should_throw_when_description_is_empty() {

        // assert
        IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> {
            // act
            NewCategory.builder()
                .name("Desc Name")
                .description("  ")
                .build();
        });

        // assert
        assertEquals("description.should.not.be.empty", e.getMessage());
    }

}
