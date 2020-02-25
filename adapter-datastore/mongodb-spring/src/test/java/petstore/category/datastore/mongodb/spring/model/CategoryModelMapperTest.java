package petstore.category.datastore.mongodb.spring.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import petstore.category.datastore.mongodb.spring.model.CategoryModel;
import petstore.category.datastore.mongodb.spring.model.CategoryModelMapper;
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
