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
package petstore.category.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import petstore.category.domain.CategoryValidationException;

/**
 *
 * @author fabiojose
 *
 */
public class CategoryTest {

	@Test
	public void should_throw_on_null_id() {

		Category.CategoryBuilder builder = Category.builder()
				.id(null)
				.name("a name")
				.description("a desc");

		CategoryValidationException e =
			assertThrows(CategoryValidationException.class, () ->
					builder.build());

		assertEquals("id.should.not.be.null", e.getMessage());
	}

	@Test
	public void should_throw_on_empty_id() {

		Category.CategoryBuilder builder = Category.builder()
				.id("  ")
				.name("a name")
				.description("a desc");

		CategoryValidationException e =
			assertThrows(CategoryValidationException.class, () ->
					builder.build());

		assertEquals("id.should.not.be.empty", e.getMessage());
	}

	@Test
	public void should_throw_on_null_name() {

		Category.CategoryBuilder builder = Category.builder()
				.id("an id")
				.name(null)
				.description("a desc");

		CategoryValidationException e =
			assertThrows(CategoryValidationException.class, () ->
					builder.build());

		assertEquals("name.should.not.be.null", e.getMessage());
	}

	@Test
	public void should_throw_on_empty_name() {

		Category.CategoryBuilder builder = Category.builder()
				.id("an id")
				.name("  ")
				.description("a desc");

		CategoryValidationException e =
			assertThrows(CategoryValidationException.class, () ->
					builder.build());

		assertEquals("name.should.not.be.empty", e.getMessage());
	}

	@Test
	public void should_throw_on_null_description() {

		Category.CategoryBuilder builder = Category.builder()
				.id("an id")
				.name("a name")
				.description(null);

		CategoryValidationException e =
			assertThrows(CategoryValidationException.class, () ->
					builder.build());

		assertEquals("description.should.not.be.null", e.getMessage());
	}

	@Test
	public void should_throw_on_empty_description() {

		Category.CategoryBuilder builder = Category.builder()
				.id("an id")
				.name("a name")
				.description("  ");

		CategoryValidationException e =
			assertThrows(CategoryValidationException.class, () ->
					builder.build());

		assertEquals("description.should.not.be.empty", e.getMessage());
	}
}
