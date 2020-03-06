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

import io.quarkus.test.junit.QuarkusTest;
import petstore.category.domain.Category;

import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import javax.inject.Inject;

import com.mongodb.client.MongoClient;

/**
 * @author fabiojose
 */
@Tag("integration")
@Testcontainers
@QuarkusTest
public class MongoCategoryDatastoreTest {

    private static final int PORT = 27017;
    
    @ConfigProperty(name = "quarkus.mongodb.database")
    String database;

    @Inject
    MongoCategoryDatastore datastore;

    @Inject
    MongoClient mongo;

    @Container
    static GenericContainer MONGO = new GenericContainer("mongo:4.0")
            .withExposedPorts(PORT);

    @BeforeAll
    public static void beforeAll() {

        System.setProperty("QUARKUS_MONGODB_URI",
            "mongodb://" +
            MONGO.getContainerIpAddress() + ":" + MONGO.getMappedPort(PORT));
        
    }

    @Test
    public void should_throw_when_arg_is_null_for_put() {

        // setup
        Category category = null;

        // assert
        assertThrows(NullPointerException.class, () -> {
            // act
            datastore.put(category);
        });

    }

    @Test
    public void should_throw_on_duplicated_id() {

        // setup
        final String id = "idxToThrow-duplication";
        final String name = "Cat X";
        final String description = "Cat X Description";
        Document category = new Document()
            .append(MongoCategoryDatastore.ID, id)
            .append(MongoCategoryDatastore.NAME, name)
            .append(MongoCategoryDatastore.DESCRIPTION, description);

        mongo.getDatabase(database)
            .getCollection(MongoCategoryDatastore.COLLECTION)
            .insertOne(category);

        Category toput = Category.builder()
            .id(id)
            .name(name)
            .description(description)
            .build();
        
        // assert
        assertThrows(RuntimeException.class, () -> {
            //act
            datastore.put(toput);
        });
        
    }

    @Test
    public void should_put_a_new_category() {

        final String id = "catx44";
        Category category = Category.builder()
            .id(id)
            .name("cat name")
            .description("Cat Decription")
            .build();

        // act
        datastore.put(category);

        Document actual = 
            mongo.getDatabase(database)
                .getCollection(MongoCategoryDatastore.COLLECTION)
                .find(MongoCategoryDatastore.queryById(id))
                .first();

        // assert
        assertNotNull(actual);
        assertEquals(id,
            actual.getString(MongoCategoryDatastore.ID));

        assertEquals(category.getName(),
            actual.getString(MongoCategoryDatastore.NAME));

        assertEquals(category.getDescription(),
            actual.getString(MongoCategoryDatastore.DESCRIPTION));
        
    }

    @Test
    public void should_throw_when_arg_is_null_for_get() {

        assertThrows(NullPointerException.class, () -> {
            datastore.get(null);
        });

    }

    @Test
    public void should_result_category_when_get() {

        // setup
        final String id = "newXidCat00";
        final String name = "Cat X";
        final String description = "Cat X Description";
        Document category = new Document()
            .append(MongoCategoryDatastore.ID, id)
            .append(MongoCategoryDatastore.NAME, name)
            .append(MongoCategoryDatastore.DESCRIPTION, description);

        mongo.getDatabase(database)
            .getCollection(MongoCategoryDatastore.COLLECTION)
            .insertOne(category);

        //act
        Optional<Category> actual = datastore.get(id);

        //assert
        assertTrue(actual.isPresent());
        actual.ifPresent(c -> {
            assertEquals(id, c.getId());
            assertEquals(name, c.getName());
            assertEquals(description, c.getDescription());
        });
    }

    @Test
    public void should_throw_when_arg_is_null_for_del() {

        assertThrows(NullPointerException.class, () -> {
            datastore.del(null);
        });
    }

    @Test
    public void should_result_empty_when_get_empty_id() {

        Optional<Category> actual = datastore.get("  ");

        //assert
        assertFalse(actual.isPresent());
    
    }

    @Test
    public void should_be_ok_when_del_non_existing_id() {

        datastore.del("NotF0und-ID");

    }
}