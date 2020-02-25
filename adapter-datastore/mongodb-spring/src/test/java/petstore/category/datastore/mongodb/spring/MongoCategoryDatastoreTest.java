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
package petstore.category.datastore.mongodb.spring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import petstore.category.datastore.mongodb.spring.model.CategoryModel;
import petstore.category.datastore.mongodb.spring.model.CategoryModelMapper;
import petstore.category.domain.Category;
import petstore.category.domain.CategoryValidationException;

/**
 *
 * @author fabiojose
 *
 */
@Tag("integration")
@SpringBootTest
public class MongoCategoryDatastoreTest {

	@Autowired
	CategoryRepository repository;

	@Autowired
	MongoCategoryDatastore datastore;

	@Test
	public void should_throw_when_id_is_null_on_read() {

		assertThrows(IllegalArgumentException.class, () ->
				datastore.get(null));

	}

	@Test
	public void should_throw_when_id_is_null_on_delete() {

		assertThrows(IllegalArgumentException.class, () ->
				datastore.del(null));

	}

	@Test
	public void should_throw_when_read_inconsistent() {

		String categoryId = "catincx5";
		CategoryModel inconsistent = new CategoryModel();
		inconsistent.setId(categoryId);
		inconsistent.setName("  ");
		inconsistent.setDescription(null);

		repository.save(inconsistent);

		assertThrows(CategoryValidationException.class, () ->
			datastore.get(categoryId));
	}

	@Test
	public void should_create_a_category() {

		Category expected = Category.builder()
			.id("catx03")
			.name("Cat 03")
			.description("Cat 03 Description")
			.build();

		datastore.put(expected);

		Optional<CategoryModel> found = repository.findById(expected.getId());
		assertTrue(found.isPresent());

		Category actual = CategoryModelMapper.INSTANCE.map(found.get());

		assertEquals(expected, actual);
	}

	@Test
	public void should_update_an_existing_category() {

		String categoryId = "cat0x4";

		Category create = Category.builder()
				.id(categoryId)
				.name("Cat 04")
				.description("Cat 04 Description")
				.build();

		datastore.put(create);

		Category expected = Category.builder()
				.id(categoryId)
				.name("Category 04")
				.description("A test for update")
				.build();

		datastore.put(expected);

		Optional<CategoryModel> found = repository.findById(categoryId);
		assertTrue(found.isPresent());

		Category actual = CategoryModelMapper.INSTANCE.map(found.get());

		assertEquals(expected, actual);
	}

	@Test
	public void should_delete_a_category() {

		String categoryId = "cat009";
		CategoryModel create = new CategoryModel();
		create.setId(categoryId);
		create.setName("Cat 9");
		create.setDescription("Cat 9 Description");

		repository.save(create);
		assertTrue(repository.findById(categoryId).isPresent());

		datastore.del(categoryId);

		assertFalse(repository.findById(categoryId).isPresent());
	}

	@Test
	public void should_read_a_category_by_id() {

		String categoryId = "c04";
		CategoryModel create = new CategoryModel(categoryId, "cat 4", "cat desc");
		Category expected = CategoryModelMapper.INSTANCE.map(create);

		repository.save(create);

		Optional<Category> found = datastore.get(categoryId);
		assertTrue(found.isPresent());

		assertEquals(expected, found.get());
	}

	@Test
	public void should_result_an_empty_when_category_not_found() {

		Optional<Category> actual = datastore.get("not-found");
		assertNotNull(actual);

		assertFalse(actual.isPresent());
	}
}
