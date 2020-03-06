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
package petstore.category.datastore.mongodb.quarkus;

import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;

import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import petstore.category.domain.Category;
import petstore.category.usecase.port.CategoryDatastore;

/**
 * @author fabiojose
 */
@ApplicationScoped
public class MongoCategoryDatastore implements CategoryDatastore {

    static final String ID = "_id";
    static final String NAME = "name";
    static final String DESCRIPTION = "description";

    static final String COLLECTION = "category";

    @Inject
    MongoClient mongo;

    @ConfigProperty(name = "quarkus.mongodb.database")
    String database;

    static BasicDBObject queryById(String id) {

        BasicDBObject query = new BasicDBObject();
        query.put("_id", Objects.requireNonNull(id));

        return query;
    }

    @Override
    public void put(Category category) {

        Document document = 
            new Document()
                .append(ID, category.getId())
                .append(NAME, category.getName())
                .append(DESCRIPTION, category.getDescription());

        mongo.getDatabase(database)
            .getCollection(COLLECTION)
                .insertOne(document);
                
    }

    @Override
    public Optional<Category> get(String id) {

        return 
        Optional.ofNullable(
            mongo.getDatabase(database)
                .getCollection(COLLECTION)
                .find(queryById(id))
                    .first()
        )
        .map(found -> {
            return Category.builder()
                .id(found.getString(ID))
                .name(found.getString(NAME))
                .description(found.getString(DESCRIPTION))
                .build();
        });
    }

    @Override
    public void del(String id) {

        mongo.getDatabase(database)
            .getCollection(COLLECTION)
                .findOneAndDelete(queryById(id));
        
    }

}