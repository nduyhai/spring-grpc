/*
 * Copyright 2024-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.grpc.autoconfigure.server;

import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcServerInterceptor;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.server.ServerBuilderCustomizer;

@AutoConfiguration(
		afterName = "org.springframework.boot.actuate.autoconfigure.observation.ObservationAutoConfiguration")
@ConditionalOnClass(value = { ObservationRegistry.class, ObservationGrpcServerInterceptor.class })
@ConditionalOnBean(ObservationRegistry.class)
@ConditionalOnProperty(name = "spring.grpc.server.observation.enabled", havingValue = "true", matchIfMissing = true)
public class GrpcServerObservationAutoConfiguration {

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Bean
	ServerInterceptor observationGrpcServerInterceptor(ObservationRegistry observationRegistry) {
		return new ObservationGrpcServerInterceptor(observationRegistry);
	}

	@Bean
	<T extends ServerBuilder<T>> ServerBuilderCustomizer<T> observationGrpcServerInterceptorCustomizer(
			ServerInterceptor observationGrpcServerInterceptor) {
		return (serverBuilder) -> serverBuilder.intercept(observationGrpcServerInterceptor);
	}

}