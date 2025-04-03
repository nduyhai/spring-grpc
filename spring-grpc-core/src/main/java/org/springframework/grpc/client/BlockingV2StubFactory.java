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
package org.springframework.grpc.client;

import org.springframework.core.Ordered;

import io.grpc.stub.AbstractBlockingStub;

public class BlockingV2StubFactory extends AbstractStubFactory<AbstractBlockingStub<?>> implements Ordered {

	public BlockingV2StubFactory() {
		super(AbstractBlockingStub.class);
	}

	@Override
	public boolean supports(Class<?> type) {
		return super.supports(type) && type.getSimpleName().contains("BlockingV2");
	}

	@Override
	public int getOrder() {
		return SimpleStubFactory.SIMPLE_STUB_ORDER - 30;
	}

	@Override
	protected String methodName() {
		return "newBlockingStub";
	}

}
