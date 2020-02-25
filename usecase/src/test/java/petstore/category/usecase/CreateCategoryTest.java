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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import petstore.category.domain.Category;
import petstore.category.usecase.exception.CategoryAlreadyExistsException;
import petstore.category.usecase.port.CategoryDatastore;

/**
 *
 * @author fabiojose
 *
 */
@ExtendWith(MockitoExtension.class)
public class CreateCategoryTest {

	@Mock
	CategoryDatastore categories;

	@InjectMocks
	CreateCategory usecase;

	@Test
	public void should_throw_when_categories_arg_is_null() {

		assertThrows(NullPointerException.class, () ->
				new CreateCategory(null));

	}

	@Test
	public void should_throw_when_entity_already_exists() {

		String id = "catx0";
		Category category = Category.builder()
				.id(id)
				.name("cat")
				.description("desc")
				.build();

		when(categories.get(id))
			.thenReturn(Optional.of(category));

		assertThrows(CategoryAlreadyExistsException.class, () ->
				usecase.create(category));

	}
}
