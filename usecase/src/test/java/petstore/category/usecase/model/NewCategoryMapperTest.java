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
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import petstore.category.domain.Category;
import petstore.category.usecase.port.CategoryIdGenerator;

/**
 * @author fabiojose
 */
@ExtendWith(MockitoExtension.class)
public class NewCategoryMapperTest {

    @Mock
    CategoryIdGenerator generator;

    @Test
    public void should_map_the_name_attribute() {
       
        final String id = "xcat99";
        final String name = "cat name";
        final String description = "cat desc";
        NewCategory _newCategory = NewCategory.builder()
            .name(name)
            .description(description)
            .build();

        NewCategoryMapper mapper = Mappers.getMapper(NewCategoryMapper.class);
        mapper.setGenerator(generator);

        when(generator.nextId())
            .thenReturn(id);

        // act
        Category actual = mapper.map(_newCategory);

        // assert
        assertEquals(name, actual.getName());
    }

    @Test
    public void should_map_the_description_attribute() {

        final String id = "xcat99";
        final String name = "cat name";
        final String description = "cat desc";
        NewCategory _newCategory = NewCategory.builder()
            .name(name)
            .description(description)
            .build();

        NewCategoryMapper mapper = Mappers.getMapper(NewCategoryMapper.class);
        mapper.setGenerator(generator);

        when(generator.nextId())
            .thenReturn(id);

        // act
        Category actual = mapper.map(_newCategory);

        // assert
        assertEquals(description, actual.getDescription());
    }

    @Test
    public void should_fill_the_id_attribute() {

        final String id = "xcat99";
        final String name = "cat name";
        final String description = "cat desc";
        NewCategory _newCategory = NewCategory.builder()
            .name(name)
            .description(description)
            .build();

        NewCategoryMapper mapper = Mappers.getMapper(NewCategoryMapper.class);
        mapper.setGenerator(generator);

        when(generator.nextId())
            .thenReturn(id);

        // act
        Category actual = mapper.map(_newCategory);

        // assert
        assertEquals(id, actual.getId());
    }
}