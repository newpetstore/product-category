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
import java.util.Optional;

import petstore.category.usecase.model.CategoryCreated;
import petstore.category.usecase.model.CategoryCreatedMapper;
import petstore.category.usecase.port.CategoryDatastore;

/**
 * @author fabiojose
 */
public class ReadCategory {

    private final CategoryDatastore datastore;

    /**
     * @throws NullPointerException When some argument is null
     */
    public ReadCategory(CategoryDatastore datastore) {
        this.datastore = Objects.requireNonNull(datastore);
    }

    /**
     * @throws NullPointerException When some argument is null
     * @return {@link Optional#empty()} when category not found
     */
    public Optional<CategoryCreated> byId(String categoryId) {
        Objects.requireNonNull(categoryId);

        return 
            datastore.get(categoryId)
                .map(CategoryCreatedMapper.INSTANCE::map);
    }
}
