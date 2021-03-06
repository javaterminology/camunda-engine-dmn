/*
 * Copyright © 2015-2018 camunda services GmbH and various authors (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.dmn.engine.impl.metrics;

import java.util.concurrent.atomic.AtomicLong;

import org.camunda.bpm.dmn.engine.delegate.DmnDecisionEvaluationEvent;
import org.camunda.bpm.dmn.engine.delegate.DmnDecisionEvaluationListener;
import org.camunda.bpm.dmn.engine.delegate.DmnDecisionTableEvaluationEvent;
import org.camunda.bpm.dmn.engine.spi.DmnEngineMetricCollector;

public class DefaultEngineMetricCollector implements DmnEngineMetricCollector, DmnDecisionEvaluationListener {

  protected AtomicLong executedDecisionElements = new AtomicLong();

  public void notify(DmnDecisionTableEvaluationEvent evaluationEvent) {
    // collector is registered as decision evaluation listener
  }

  public void notify(DmnDecisionEvaluationEvent evaluationEvent) {
    long executedDecisionElements = evaluationEvent.getExecutedDecisionElements();
    this.executedDecisionElements.getAndAdd(executedDecisionElements);
  }

  public long getExecutedDecisionElements() {
    return executedDecisionElements.get();
  }

  public long clearExecutedDecisionElements() {
    return executedDecisionElements.getAndSet(0);
  }

}
