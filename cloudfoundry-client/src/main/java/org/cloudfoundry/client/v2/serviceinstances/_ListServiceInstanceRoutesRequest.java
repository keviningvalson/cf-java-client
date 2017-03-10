/*
 * Copyright 2013-2017 the original author or authors.
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

package org.cloudfoundry.client.v2.serviceinstances;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v2.InFilterParameter;
import org.cloudfoundry.client.v2.PaginatedRequest;
import org.immutables.value.Value;

import java.util.List;

/**
 * The request payload for the List all Routes for the Service Instance operation
 */
@Value.Immutable
abstract class _ListServiceInstanceRoutesRequest extends PaginatedRequest {

    /**
     * The domain id
     */
    @InFilterParameter("domain_guid")
    @Nullable
    abstract List<String> getDomainId();

    /**
     * The host
     */
    @InFilterParameter("host")
    @Nullable
    abstract List<String> getHost();

    /**
     * The organization id
     */
    @InFilterParameter("organization_guid")
    @Nullable
    abstract List<String> getOrganizationId();

    /**
     * The path
     */
    @InFilterParameter("path")
    @Nullable
    abstract List<String> getPath();

    /**
     * The port
     */
    @InFilterParameter("port")
    @Nullable
    abstract List<String> getPort();

    /**
     * The service instance id
     */
    @JsonIgnore
    abstract String getServiceInstanceId();

}