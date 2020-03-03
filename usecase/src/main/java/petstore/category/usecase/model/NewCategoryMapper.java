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

import static java.util.Objects.requireNonNull;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import petstore.category.domain.Category;
import petstore.category.usecase.port.CategoryIdGenerator;

/**
 * @author fabiojose
 */
@Mapper
public abstract class NewCategoryMapper {

    protected CategoryIdGenerator generator;

    /**
     * @throws NullPointException When argument is {@code null}
     */
    public void setGenerator(CategoryIdGenerator generator){
        this.generator = requireNonNull(generator);
    }

    /**
     * Maps {@link NewCategory} instances into {@link Category} ones
     */
    @Mapping(target = "id", expression = "java(generator.nextId())")
    public abstract Category map(NewCategory newCategory);
}
