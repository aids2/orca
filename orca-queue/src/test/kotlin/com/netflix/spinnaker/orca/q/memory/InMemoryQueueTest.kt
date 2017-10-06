/*
 * Copyright 2017 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.orca.q.memory

import com.netflix.spinnaker.orca.q.DeadMessageCallback
import com.netflix.spinnaker.orca.q.QueueTest
import com.netflix.spinnaker.orca.q.metrics.MonitorableQueueTest
import org.funktionale.partials.invoke
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import java.time.Clock

object InMemoryQueueTest : QueueTest<InMemoryQueue>(createQueue(p3 = null))

object InMemoryMonitorableQueueTest : MonitorableQueueTest<InMemoryQueue>(
  createQueue
)

private val createQueue = {
  clock: Clock,
  deadLetterCallback: DeadMessageCallback,
  publisher: ApplicationEventPublisher? ->
  InMemoryQueue(
    clock = clock,
    deadMessageHandler = deadLetterCallback,
    publisher = publisher ?: (object : ApplicationEventPublisher {
      override fun publishEvent(event: ApplicationEvent?) {}
      override fun publishEvent(event: Any?) {}
    })
  )
}
