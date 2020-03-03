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

import java.util.Optional;

import lombok.Getter;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * @author fabiojose
 */
@Getter
@ToString
@EqualsAndHashCode
public class NewCategory {

    private String name;
    private String description;

	/**
	 * @throws IllegalArgumentException When some argument is non-valid
	 */
    @Builder(toBuilder = true)
    NewCategory(String name, String description) {
        this.name = requireNonEmpty(name, "name");
        this.description = requireNonEmpty(description, "description");
    }

	static <T> T requireNonEmpty(T value, String argName) {

		Optional.ofNullable(value)
			.orElseThrow(() ->
				new IllegalArgumentException(argName
						+ ".should.not.be.null"));

		if(value instanceof String) {
			if (((String)value).trim().isEmpty()) {
				throw new IllegalArgumentException(argName
						+ ".should.not.be.empty");
			}
		}

		return value;
	}
}
