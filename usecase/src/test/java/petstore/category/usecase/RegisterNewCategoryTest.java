/*
 * Java (TM) Pet Store Modernized Edition - 2019
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
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import petstore.category.domain.Category;
import petstore.category.usecase.model.CategoryCreated;
import petstore.category.usecase.model.NewCategory;
import petstore.category.usecase.exception.CategoryAlreadyExistsException;
import petstore.category.usecase.port.CategoryDatastore;
import petstore.category.usecase.port.CategoryIdGenerator;

/**
 *
 * @author fabiojose
 *
 */
@ExtendWith(MockitoExtension.class)
public class RegisterNewCategoryTest {

	@Mock
    CategoryDatastore categories;
    
    @Mock
    CategoryIdGenerator idGenerator;

	@InjectMocks
	RegisterNewCategory usecase;

	@Test
	public void should_throw_when_categories_arg_is_null() {

		assertThrows(NullPointerException.class, () ->
				new RegisterNewCategory(null, idGenerator));

    }
    
	@Test
	public void should_throw_when_id_generator_arg_is_null() {

		assertThrows(NullPointerException.class, () ->
				new RegisterNewCategory(categories, null));

	}

	@Test
	public void should_throw_when_entity_already_exists() {

		String id = "catx0";
		NewCategory newCategory = NewCategory.builder()
				.name("cat")
				.description("desc")
				.build();

        Category category = Category.builder()
                .id(id)
				.name("cat")
				.description("desc")
                .build();
                
		when(categories.get(id))
            .thenReturn(Optional.of(category));
            
        when(idGenerator.nextId())
            .thenReturn(id);

		assertThrows(CategoryAlreadyExistsException.class, () ->
				usecase.create(newCategory));

	}

	@Test
	public void should_throw_when_new_category_is_null() {

		assertThrows(NullPointerException.class, () -> {
			usecase.create(null);
		});

	}

	@Test
	public void should_register_new_category() {

		String id = "catx0";
		NewCategory newCategory = NewCategory.builder()
				.name("cat")
				.description("desc")
				.build();

		CategoryDatastore datastore = new CategoryDatastore(){
			private Category category;
			@Override
			public void put(Category category) {
				this.category = category;
			}
		
			@Override
			public Optional<Category> get(String id) {
				return Optional.ofNullable(category);
			}
		
			@Override
			public void del(String id) {
				category = null;
			}
		};

        when(idGenerator.nextId())
			.thenReturn(id);
			
		RegisterNewCategory _usecase = 
			new RegisterNewCategory(datastore, idGenerator);

		// act
		_usecase.create(newCategory);

		Category actual = datastore.get(id).get();

		// assert
		assertEquals(id, actual.getId());
		assertEquals(newCategory.getName(), actual.getName());
		assertEquals(newCategory.getDescription(), actual.getDescription());
	}

	@Test
	public void should_result_in_category_created() {

		String id = "catx0";
		NewCategory newCategory = NewCategory.builder()
				.name("cat")
				.description("desc")
				.build();

        when(idGenerator.nextId())
			.thenReturn(id);
			
		// act
		CategoryCreated actual = 
			usecase.create(newCategory);


		// assert
		assertEquals(id, actual.getId());
		assertEquals(newCategory.getName(), actual.getName());
		assertEquals(newCategory.getDescription(), actual.getDescription());
	}
}
