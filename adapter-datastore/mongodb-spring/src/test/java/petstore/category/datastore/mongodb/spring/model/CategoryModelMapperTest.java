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
package petstore.category.datastore.mongodb.spring.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import petstore.category.domain.Category;

/**
 *
 * @author fabiojose
 *
 */
@Tag("unit")
public class CategoryModelMapperTest {

	@Test
	public void model_to_category_should_maps_all_attributes() {

		CategoryModel model =
			new CategoryModel("modelId", "modelName", "modelDesc");

		Category actual = CategoryModelMapper.INSTANCE.map(model);

		assertEquals(model.getId(), actual.getId());
		assertEquals(model.getName(), actual.getName());
		assertEquals(model.getDescription(), actual.getDescription());

	}

	@Test
	public void category_to_model_should_maps_all_attributes() {

		Category cat = Category.builder()
				.id("catId")
				.name("catName")
				.description("catDesc")
				.build();

		CategoryModel actual = CategoryModelMapper.INSTANCE.map(cat);

		assertEquals(cat.getId(), actual.getId());
		assertEquals(cat.getName(), actual.getName());
		assertEquals(cat.getDescription(), actual.getDescription());
	}
}
