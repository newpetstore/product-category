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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import petstore.category.domain.Category;

/**
 * @author fabiojose
 */
public class CategoryCreatedMapperTest {

    @Test
    public void should_map_the_id_attribute() {

        final String id = "catx99";
        final String name = "catname";
        final String description = "cat desc";

        Category _category = Category.builder()
            .id(id)
            .name(name)
            .description(description)
            .build();

        CategoryCreatedMapper mapper = 
            Mappers.getMapper(CategoryCreatedMapper.class);

        // act
        CategoryCreated actual = mapper.map(_category);

        // assert
        assertEquals(id, actual.getId());
    }

    @Test
    public void should_map_the_name_attribute() {

        final String id = "catx99";
        final String name = "catname";
        final String description = "cat desc";

        Category _category = Category.builder()
            .id(id)
            .name(name)
            .description(description)
            .build();

        CategoryCreatedMapper mapper = 
            Mappers.getMapper(CategoryCreatedMapper.class);

        // act
        CategoryCreated actual = mapper.map(_category);

        // assert
        assertEquals(name, actual.getName());
    }

    @Test
    public void should_map_the_description_attribute() {

        final String id = "catx99";
        final String name = "catname";
        final String description = "cat desc";

        Category _category = Category.builder()
            .id(id)
            .name(name)
            .description(description)
            .build();

        CategoryCreatedMapper mapper = 
            Mappers.getMapper(CategoryCreatedMapper.class);

        // act
        CategoryCreated actual = mapper.map(_category);

        // assert
        assertEquals(description, actual.getDescription());
    }
}