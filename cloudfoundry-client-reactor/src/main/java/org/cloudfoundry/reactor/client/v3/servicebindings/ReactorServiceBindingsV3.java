/*
 * Copyright 2013-2019 the original author or authors.
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

package org.cloudfoundry.reactor.client.v3.servicebindings;

import org.cloudfoundry.client.v3.servicebindings.CreateServiceBindingRequest;
import org.cloudfoundry.client.v3.servicebindings.CreateServiceBindingResponse;
import org.cloudfoundry.client.v3.servicebindings.DeleteServiceBindingRequest;
import org.cloudfoundry.client.v3.servicebindings.GetServiceBindingRequest;
import org.cloudfoundry.client.v3.servicebindings.GetServiceBindingResponse;
import org.cloudfoundry.client.v3.servicebindings.ListServiceBindingsRequest;
import org.cloudfoundry.client.v3.servicebindings.ListServiceBindingsResponse;
import org.cloudfoundry.client.v3.servicebindings.ServiceBindingsV3;
import org.cloudfoundry.reactor.ConnectionContext;
import org.cloudfoundry.reactor.TokenProvider;
import org.cloudfoundry.reactor.client.v3.AbstractClientV3Operations;
import reactor.core.publisher.Mono;

/**
 * The Reactor-based implementation of {@link ServiceBindingsV3}
 */
public final class ReactorServiceBindingsV3 extends AbstractClientV3Operations implements ServiceBindingsV3 {

    /**
     * Creates an instance
     *
     * @param connectionContext the {@link ConnectionContext} to use when communicating with the server
     * @param root              the root URI of the server.  Typically something like {@code https://api.run.pivotal.io}.
     * @param tokenProvider     the {@link TokenProvider} to use when communicating with the server
     */
    public ReactorServiceBindingsV3(ConnectionContext connectionContext, Mono<String> root, TokenProvider tokenProvider) {
        super(connectionContext, root, tokenProvider);
    }

    @Override
    public Mono<CreateServiceBindingResponse> create(CreateServiceBindingRequest request) {
        return post(request, CreateServiceBindingResponse.class, builder -> builder.pathSegment("service_bindings"))
            .checkpoint();
    }

    @Override
    public Mono<Void> delete(DeleteServiceBindingRequest request) {
        return delete(request, Void.class, builder -> builder.pathSegment("service_bindings", request.getServiceBindingId()))
            .checkpoint();
    }

    @Override
    public Mono<GetServiceBindingResponse> get(GetServiceBindingRequest request) {
        return get(request, GetServiceBindingResponse.class, builder -> builder.pathSegment("service_bindings", request.getServiceBindingId()))
            .checkpoint();
    }

    @Override
    public Mono<ListServiceBindingsResponse> list(ListServiceBindingsRequest request) {
        return get(request, ListServiceBindingsResponse.class, builder -> builder.pathSegment("service_bindings"))
            .checkpoint();
    }

}
