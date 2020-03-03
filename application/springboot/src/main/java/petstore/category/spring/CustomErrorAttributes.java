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

import java.util.Map;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * 
 * @author fabiojose
 *
 */
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

	@Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
    		boolean includeStackTrace) {
		
		var attributes = 
				super.getErrorAttributes(request, includeStackTrace);
		
		// Remove the identification of requests
		attributes.remove("requestId");
		
		// Remove the time stamp
		attributes.remove("timestamp");
		
		// TODO Resolve message based in the message key
		attributes.get("message");
		
		
		return attributes;
	}
}
