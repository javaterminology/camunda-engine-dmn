/* Licensed under the Apache License, Version 2.0 (the "License");
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

package org.camunda.bpm.dmn.engine.impl.hitpolicy;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.dmn.engine.DmnDecisionOutput;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.dmn.engine.DmnDecisionTable;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionResultImpl;

public abstract class AbstractDmnHitPolicyNumberAggregator extends AbstractDmnHitPolicyAggregator {

  public DmnDecisionResult aggregate(DmnDecisionTable decisionTable, List<DmnDecisionOutput> decisionOutputs) {
    List<Object> values = collectSingleValues(decisionOutputs);
    if (values.isEmpty()) {
      // return empty result if not values to aggregate
      return new DmnDecisionResultImpl();
    }
    else {
      String name = getDecisionOutputName(decisionOutputs);
      return aggregateNumberValues(name, values);
    }
  }

  protected DmnDecisionResult aggregateNumberValues(String name, List<Object> values) {
    try {
      List<Integer> intValues = convertValuesToInteger(values);
      return aggregateIntegerValues(name, intValues);
    }
    catch (DmnHitPolicyException e) {
      // ignore
    }

    try {
      List<Long> longValues = convertValuesToLong(values);
      return aggregateLongValues(name, longValues);
    }
    catch (DmnHitPolicyException e) {
      // ignore
    }

    try {
      List<Double> doubleValues = convertValuesToDouble(values);
      return aggregateDoubleValues(name, doubleValues);
    }
    catch (DmnHitPolicyException e) {
      // ignore
    }

    throw LOG.unableToConvertValuesToAggregatableTypes(values, Integer.class, Long.class, Double.class);
  }

  protected abstract DmnDecisionResult aggregateIntegerValues(String name, List<Integer> intValues);

  protected abstract DmnDecisionResult aggregateLongValues(String name, List<Long> longValues);

  protected abstract DmnDecisionResult aggregateDoubleValues(String name, List<Double> doubleValues);

  protected List<Integer> convertValuesToInteger(List<Object> values) {
    List<Integer> intValues = new ArrayList<Integer>();
    for (Object value : values) {
      if (value instanceof Integer) {
        intValues.add((Integer) value);
      }
      else {
        try {
          intValues.add(Integer.valueOf(value.toString()));
        }
        catch (NumberFormatException e) {
          throw LOG.unableToConvertValueTo(Integer.class, value, e);
        }
      }
    }
    return intValues;
  }

  protected List<Long> convertValuesToLong(List<Object> values) {
    List<Long> longValues = new ArrayList<Long>();
    for (Object value : values) {
      if (value instanceof Long) {
        longValues.add((Long) value);
      }
      else {
        try {
          longValues.add(Long.valueOf(value.toString()));
        }
        catch (NumberFormatException e) {
          throw LOG.unableToConvertValueTo(Long.class, value, e);
        }
      }
    }
    return longValues;
  }

  protected List<Double> convertValuesToDouble(List<Object> values) {
    List<Double> doubleValues = new ArrayList<Double>();

    for (Object value : values) {
      if (value instanceof Double) {
        doubleValues.add((Double) value);
      }
      else {
        try {
          doubleValues.add(Double.valueOf(value.toString()));
        }
        catch (NumberFormatException e) {
          throw LOG.unableToConvertValueTo(Double.class, value, e);
        }
      }
    }

    return doubleValues;
  }
}