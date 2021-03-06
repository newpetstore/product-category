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

import java.util.Objects;

import org.mapstruct.factory.Mappers;

import petstore.category.domain.Category;
import petstore.category.domain.CategoryValidationException;
import petstore.category.usecase.exception.CategoryAlreadyExistsException;
import petstore.category.usecase.port.CategoryDatastore;
import petstore.category.usecase.port.CategoryIdGenerator;
import petstore.category.usecase.model.CategoryCreated;
import petstore.category.usecase.model.CategoryCreatedMapper;
import petstore.category.usecase.model.NewCategory;
import petstore.category.usecase.model.NewCategoryMapper;

/**
 *
 * @author fabiojose
 *
 */
public class RegisterNewCategory {

    private final CategoryDatastore categories;

	private final NewCategoryMapper mapper;

    /**
     * @throws NullPointerException When any argument is {@code null}
     */
    public RegisterNewCategory(CategoryDatastore categories, 
            CategoryIdGenerator idGenerator) {
        this.categories = Objects.requireNonNull(categories);

        this.mapper = Mappers.getMapper(NewCategoryMapper.class);
		this.mapper.setGenerator(Objects.requireNonNull(idGenerator));
	}

	/**
	 *
	 * @param newCategory An instance to create inside the domain
	 * @throws CategoryAlreadyExistsException When the same entity already
	 * exists inside the domain
	 * @throws CategoryValidationException When the entity instance is invalid
	 * @throws NullPointerException When category argument is <code>null</code>
	 */
	public CategoryCreated create(NewCategory newCategory) {

        NewCategory _newCategory = Objects.requireNonNull(newCategory);
        
        Category _category = mapper.map(_newCategory);

		// Already exists?
		categories.get(_category.getId())
			.ifPresent((found) -> {
				throw new CategoryAlreadyExistsException(found.getId());
			});

		// Create using the data store
		categories.put(_category);

		//TODO Fire event about category creation?

        return CategoryCreatedMapper.INSTANCE.map(_category);
	}

}
