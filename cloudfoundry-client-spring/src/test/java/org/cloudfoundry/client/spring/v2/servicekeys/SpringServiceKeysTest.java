/*
 * Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudfoundry.client.spring.v2.servicekeys;

import org.cloudfoundry.client.spring.AbstractApiTest;
import org.cloudfoundry.client.v2.servicekeys.CreateServiceKeyRequest;
import org.cloudfoundry.client.v2.servicekeys.CreateServiceKeyResponse;
import org.cloudfoundry.client.v2.servicekeys.DeleteServiceKeyRequest;
import org.cloudfoundry.client.v2.servicekeys.ServiceKeyEntity;
import org.reactivestreams.Publisher;

import static org.cloudfoundry.client.v2.Resource.Metadata;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

public final class SpringServiceKeysTest {

    public static final class Create extends AbstractApiTest<CreateServiceKeyRequest, CreateServiceKeyResponse> {

        private final SpringServiceKeys serviceKeys = new SpringServiceKeys(this.restTemplate, this.root, PROCESSOR_GROUP);

        @Override
        protected CreateServiceKeyRequest getInvalidRequest() {
            return CreateServiceKeyRequest.builder().build();
        }

        @Override
        protected RequestContext getRequestContext() {
            return new RequestContext()
                    .method(POST).path("v2/service_keys")
                    .requestPayload("v2/service_keys/POST_request.json")
                    .status(CREATED)
                    .responsePayload("v2/service_keys/POST_response.json");
        }

        @Override
        protected CreateServiceKeyResponse getResponse() {
            return CreateServiceKeyResponse.builder()
                    .metadata(Metadata.builder()
                            .createdAt("2015-07-27T22:43:22Z")
                            .id("79aa4b11-99f3-484b-adfc-a63fa818c4d1")
                            .url("/v2/service_keys/79aa4b11-99f3-484b-adfc-a63fa818c4d1")
                            .build())
                    .entity(ServiceKeyEntity.builder()
                            .credential("creds-key-392", "creds-val-392")
                            .name("name-960")
                            .serviceInstanceId("132944c8-c31d-4bb8-9155-ae4e2ebe1a0c")
                            .serviceInstanceUrl("/v2/service_instances/132944c8-c31d-4bb8-9155-ae4e2ebe1a0c")
                            .build())
                    .build();
        }

        @Override
        protected CreateServiceKeyRequest getValidRequest() throws Exception {
            return CreateServiceKeyRequest.builder()
                    .name("name-960")
                    .serviceInstanceId("132944c8-c31d-4bb8-9155-ae4e2ebe1a0c")
                    .build();
        }

        @Override
        protected Publisher<CreateServiceKeyResponse> invoke(CreateServiceKeyRequest request) {
            return this.serviceKeys.create(request);
        }
    }

    public static final class Delete extends AbstractApiTest<DeleteServiceKeyRequest, Void> {

        private final SpringServiceKeys serviceKeys = new SpringServiceKeys(this.restTemplate, this.root, PROCESSOR_GROUP);

        @Override
        protected DeleteServiceKeyRequest getInvalidRequest() {
            return DeleteServiceKeyRequest.builder().build();
        }

        @Override
        protected RequestContext getRequestContext() {
            return new RequestContext()
                    .method(DELETE).path("/v2/service_keys/test-service-key-id")
                    .status(NO_CONTENT);
        }

        @Override
        protected Void getResponse() {
            return null;
        }

        @Override
        protected DeleteServiceKeyRequest getValidRequest() throws Exception {
            return DeleteServiceKeyRequest.builder()
                    .serviceKeyId("test-service-key-id")
                    .build();
        }

        @Override
        protected Publisher<Void> invoke(DeleteServiceKeyRequest request) {
            return this.serviceKeys.delete(request);
        }
    }

}