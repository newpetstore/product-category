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
package petstore.category.datastore.mongodb.spring;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import petstore.category.datastore.mongodb.spring.model.CategoryModelMapper;
import petstore.category.domain.Category;
import petstore.category.usecase.port.CategoryDatastore;

/**
 *
 * @author fabiojose
 *
 */
@Component
public class MongoCategoryDatastore implements CategoryDatastore {

	@Autowired
	CategoryRepository repository;

	@Override
	public void put(Category category) {

		Optional.ofNullable(category)
			.map(CategoryModelMapper.INSTANCE::map)
			.ifPresent(repository::save);

	}

	@Override
	public Optional<Category> get(String id) {

		return repository.findById(id)
				.map(CategoryModelMapper.INSTANCE::map);

	}

	@Override
	public void del(String id) {

		repository.deleteById(id);

	}

}
