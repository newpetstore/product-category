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
package petstore.category.controller.model;

import org.mapstruct.factory.Mappers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import petstore.category.usecase.model.NewCategory;

/**
 * @author fabiojose
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CategoryRequest {

    private String name;
    private String description;

    @org.mapstruct.Mapper
    public static abstract class Mapper {

        public static final Mapper INSTANCE = 
            Mappers.getMapper(Mapper.class);

        public abstract NewCategory map(CategoryRequest request);

    }
}