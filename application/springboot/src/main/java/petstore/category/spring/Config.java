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
package petstore.category.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import petstore.category.controller.CategoryRegisterController;
import petstore.category.controller.ReadCategoryController;
import petstore.category.usecase.ReadCategory;
import petstore.category.usecase.RegisterNewCategory;
import petstore.category.usecase.port.CategoryDatastore;
import petstore.category.usecase.port.CategoryIdGenerator;

/**
 * @author fabiojose
 */

// To enable spring boot application resources
@ComponentScan()

// To enable the datastore components
@ComponentScan("petstore.category.datastore.mongodb.spring")

// To enable mongodb repositories
@EnableMongoRepositories("petstore.category.datastore.mongodb.spring")
@Configuration
public class Config {

    @Bean
    public static RegisterNewCategory registerNewCategoryUsecase(
        CategoryDatastore datastore,
        CategoryIdGenerator idGenerator) {

        return new RegisterNewCategory(datastore, idGenerator);
    }

    @Bean
    public static CategoryRegisterController categoryRegisterController(
        RegisterNewCategory usecase){

        return new CategoryRegisterController(usecase);
    }

    @Bean
    public static ReadCategory readCategoryUsecase(
        CategoryDatastore datastore) {

        return new ReadCategory(datastore);
    }

    @Bean
    public static ReadCategoryController readCategoryController(
        ReadCategory usecase) {

        return new ReadCategoryController(usecase);
    }
}
