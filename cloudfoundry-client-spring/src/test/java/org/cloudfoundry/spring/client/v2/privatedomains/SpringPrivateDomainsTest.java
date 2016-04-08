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

package org.cloudfoundry.spring.client.v2.privatedomains;

import org.cloudfoundry.client.v2.Resource;
import org.cloudfoundry.client.v2.jobs.JobEntity;
import org.cloudfoundry.client.v2.privatedomains.CreatePrivateDomainRequest;
import org.cloudfoundry.client.v2.privatedomains.CreatePrivateDomainResponse;
import org.cloudfoundry.client.v2.privatedomains.DeletePrivateDomainRequest;
import org.cloudfoundry.client.v2.privatedomains.DeletePrivateDomainResponse;
import org.cloudfoundry.client.v2.privatedomains.GetPrivateDomainRequest;
import org.cloudfoundry.client.v2.privatedomains.GetPrivateDomainResponse;
import org.cloudfoundry.client.v2.privatedomains.ListPrivateDomainsRequest;
import org.cloudfoundry.client.v2.privatedomains.ListPrivateDomainsResponse;
import org.cloudfoundry.client.v2.privatedomains.PrivateDomainEntity;
import org.cloudfoundry.client.v2.privatedomains.PrivateDomainResource;
import org.cloudfoundry.spring.AbstractApiTest;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

public final class SpringPrivateDomainsTest {

    public static final class Create extends AbstractApiTest<CreatePrivateDomainRequest, CreatePrivateDomainResponse> {

        private final SpringPrivateDomains privateDomains = new SpringPrivateDomains(this.restTemplate, this.root, PROCESSOR_GROUP);

        @Override
        protected CreatePrivateDomainRequest getInvalidRequest() {
            return CreatePrivateDomainRequest.builder()
                .build();
        }

        @Override
        protected RequestContext getRequestContext() {
            return new RequestContext()
                .method(POST).path("/v2/private_domains")
                .requestPayload("fixtures/client/v2/private_domains/POST_request.json")
                .status(OK)
                .responsePayload("fixtures/client/v2/private_domains/POST_response.json");
        }

        @Override
        protected CreatePrivateDomainResponse getResponse() {
            return CreatePrivateDomainResponse.builder()
                .metadata(Resource.Metadata.builder()
                    .id("4af3234e-813d-453f-b3ae-fcdecfd87a47")
                    .url("/v2/private_domains/4af3234e-813d-453f-b3ae-fcdecfd87a47")
                    .createdAt("2016-01-19T19:41:12Z")
                    .build())
                .entity(PrivateDomainEntity.builder()
                    .name("exmaple.com")
                    .owningOrganizationId("22bb8ae1-6324-40eb-b077-bd1bfad773f8")
                    .owningOrganizationUrl("/v2/organizations/22bb8ae1-6324-40eb-b077-bd1bfad773f8")
                    .sharedOrganizationsUrl("/v2/private_domains/4af3234e-813d-453f-b3ae-fcdecfd87a47/shared_organizations")
                    .build())
                .build();
        }

        @Override
        protected CreatePrivateDomainRequest getValidRequest() throws Exception {
            return CreatePrivateDomainRequest.builder()
                .name("exmaple.com")
                .owningOrganizationId("22bb8ae1-6324-40eb-b077-bd1bfad773f8")
                .build();
        }

        @Override
        protected Mono<CreatePrivateDomainResponse> invoke(CreatePrivateDomainRequest request) {
            return this.privateDomains.create(request);
        }
    }

    public static final class Delete extends AbstractApiTest<DeletePrivateDomainRequest, DeletePrivateDomainResponse> {

        private final SpringPrivateDomains privateDomains = new SpringPrivateDomains(this.restTemplate, this.root, PROCESSOR_GROUP);

        @Override
        protected DeletePrivateDomainRequest getInvalidRequest() {
            return DeletePrivateDomainRequest.builder()
                .build();
        }

        @Override
        protected RequestContext getRequestContext() {
            return new RequestContext()
                .method(DELETE).path("/v2/private_domains/test-private-domain-id")
                .status(NO_CONTENT);
        }

        @Override
        protected DeletePrivateDomainResponse getResponse() {
            return null;
        }

        @Override
        protected DeletePrivateDomainRequest getValidRequest() throws Exception {
            return DeletePrivateDomainRequest.builder()
                .privateDomainId("test-private-domain-id")
                .build();
        }

        @Override
        protected Mono<DeletePrivateDomainResponse> invoke(DeletePrivateDomainRequest request) {
            return this.privateDomains.delete(request);
        }

    }

    public static final class DeleteAsync extends AbstractApiTest<DeletePrivateDomainRequest, DeletePrivateDomainResponse> {

        private final SpringPrivateDomains privateDomains = new SpringPrivateDomains(this.restTemplate, this.root, PROCESSOR_GROUP);

        @Override
        protected DeletePrivateDomainRequest getInvalidRequest() {
            return DeletePrivateDomainRequest.builder()
                .build();
        }

        @Override
        protected RequestContext getRequestContext() {
            return new RequestContext()
                .method(DELETE).path("/v2/private_domains/test-private-domain-id?async=true")
                .status(ACCEPTED)
                .responsePayload("fixtures/client/v2/private_domains/DELETE_{id}_async_response.json");
        }

        @Override
        protected DeletePrivateDomainResponse getResponse() {
            return DeletePrivateDomainResponse.builder()
                .metadata(Resource.Metadata.builder()
                    .id("2d9707ba-6f0b-4aef-a3de-fe9bdcf0c9d1")
                    .createdAt("2016-02-02T17:16:31Z")
                    .url("/v2/jobs/2d9707ba-6f0b-4aef-a3de-fe9bdcf0c9d1")
                    .build())
                .entity(JobEntity.builder()
                    .id("2d9707ba-6f0b-4aef-a3de-fe9bdcf0c9d1")
                    .status("queued")
                    .build())
                .build();
        }

        @Override
        protected DeletePrivateDomainRequest getValidRequest() throws Exception {
            return DeletePrivateDomainRequest.builder()
                .async(true)
                .privateDomainId("test-private-domain-id")
                .build();
        }

        @Override
        protected Mono<DeletePrivateDomainResponse> invoke(DeletePrivateDomainRequest request) {
            return this.privateDomains.delete(request);
        }

    }

    public static final class Get extends AbstractApiTest<GetPrivateDomainRequest, GetPrivateDomainResponse> {

        private final SpringPrivateDomains privateDomains = new SpringPrivateDomains(this.restTemplate, this.root, PROCESSOR_GROUP);

        @Override
        protected GetPrivateDomainRequest getInvalidRequest() {
            return null;
        }

        @Override
        protected RequestContext getRequestContext() {
            return new RequestContext()
                .method(GET).path("/v2/private_domains/test-private-domain-id")
                .status(OK)
                .responsePayload("fixtures/client/v2/private_domains/GET_{id}_response.json");
        }

        @Override
        protected GetPrivateDomainResponse getResponse() {
            return GetPrivateDomainResponse.builder()
                .metadata(Resource.Metadata.builder()
                    .id("3de9db5f-8e3b-4d10-a8c9-8137caafe43d")
                    .url("/v2/private_domains/3de9db5f-8e3b-4d10-a8c9-8137caafe43d")
                    .createdAt("2016-02-19T02:04:00Z")
                    .build())
                .entity(PrivateDomainEntity.builder()
                    .name("my-domain.com")
                    .owningOrganizationId("2f70efed-abb2-4b7a-9f31-d4fe4d849932")
                    .owningOrganizationUrl("/v2/organizations/2f70efed-abb2-4b7a-9f31-d4fe4d849932")
                    .sharedOrganizationsUrl("/v2/private_domains/3de9db5f-8e3b-4d10-a8c9-8137caafe43d/shared_organizations")
                    .build())
                .build();
        }

        @Override
        protected GetPrivateDomainRequest getValidRequest() throws Exception {
            return GetPrivateDomainRequest.builder()
                .privateDomainId("test-private-domain-id")
                .build();
        }

        @Override
        protected Mono<GetPrivateDomainResponse> invoke(GetPrivateDomainRequest request) {
            return this.privateDomains.get(request);
        }

    }

    public static final class List extends AbstractApiTest<ListPrivateDomainsRequest, ListPrivateDomainsResponse> {

        private final SpringPrivateDomains privateDomains = new SpringPrivateDomains(this.restTemplate, this.root, PROCESSOR_GROUP);

        @Override
        protected ListPrivateDomainsRequest getInvalidRequest() {
            return null;
        }

        @Override
        protected RequestContext getRequestContext() {
            return new RequestContext()
                .method(GET).path("/v2/private_domains?q=name%20IN%20test-name.com&page=-1")
                .status(OK)
                .responsePayload("fixtures/client/v2/private_domains/GET_response.json");
        }

        @Override
        protected ListPrivateDomainsResponse getResponse() {
            return ListPrivateDomainsResponse.builder()
                .totalResults(1)
                .totalPages(1)
                .resource(PrivateDomainResource.builder()
                    .metadata(Resource.Metadata.builder()
                        .id("3de9db5f-8e3b-4d10-a8c9-8137caafe43d")
                        .url("/v2/private_domains/3de9db5f-8e3b-4d10-a8c9-8137caafe43d")
                        .createdAt("2016-02-19T02:04:00Z")
                        .build())
                    .entity(PrivateDomainEntity.builder()
                        .name("my-domain.com")
                        .owningOrganizationId("2f70efed-abb2-4b7a-9f31-d4fe4d849932")
                        .owningOrganizationUrl("/v2/organizations/2f70efed-abb2-4b7a-9f31-d4fe4d849932")
                        .sharedOrganizationsUrl("/v2/private_domains/3de9db5f-8e3b-4d10-a8c9-8137caafe43d/shared_organizations")
                        .build())
                    .build())
                .build();
        }

        @Override
        protected ListPrivateDomainsRequest getValidRequest() throws Exception {
            return ListPrivateDomainsRequest.builder()
                .name("test-name.com")
                .page(-1)
                .build();
        }

        @Override
        protected Mono<ListPrivateDomainsResponse> invoke(ListPrivateDomainsRequest request) {
            return this.privateDomains.list(request);
        }

    }
}
