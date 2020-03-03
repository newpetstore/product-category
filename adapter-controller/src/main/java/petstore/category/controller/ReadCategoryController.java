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
package petstore.category.controller;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import petstore.category.controller.model.CategoryResponse;
import petstore.category.usecase.ReadCategory;

/**
 * @author fabiojose
 */
public class ReadCategoryController {

    private final ReadCategory usecase;

    public ReadCategoryController(ReadCategory usecase) {
        this.usecase = Objects.requireNonNull(usecase);
    }

    public CompletableFuture<Optional<CategoryResponse>> getById(Supplier<String> id) {
        Objects.requireNonNull(id);

        return 
        CompletableFuture.supplyAsync(() -> usecase.byId(id.get()))
            .thenApply(found -> found.orElse(null))
            .thenApply(CategoryResponse.Mapper.INSTANCE::map)
            .thenApply(Optional::ofNullable);

    }
}