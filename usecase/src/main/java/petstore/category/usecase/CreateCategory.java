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

import java.util.Objects;

import petstore.category.domain.Category;
import petstore.category.domain.CategoryValidationException;
import petstore.category.usecase.exception.CategoryAlreadyExistsException;
import petstore.category.usecase.port.CategoryDatastore;

/**
 *
 * @author fabiojose
 *
 */
public class CreateCategory {

	private final CategoryDatastore categories;

	public CreateCategory(CategoryDatastore categories) {
		this.categories = Objects.requireNonNull(categories);
	}

	/**
	 *
	 * @param category An instance to create inside the domain
	 * @throws CategoryAlreadyExistsException When the same entity already
	 * exists inside the domain
	 * @throws CategoryValidationException When the entity instance is invalid
	 * @throws NullPointerException When category argument is <code>null</code>
	 */
	public void create(Category category) {

		Category _category = Objects.requireNonNull(category);

		// Already exists?
		categories.get(_category.getId())
			.ifPresent((found) -> {
				throw new CategoryAlreadyExistsException(found.getId());
			});

		// Create using the data store
		categories.put(_category);

		//TODO Fire event about category creation?

	}

}
