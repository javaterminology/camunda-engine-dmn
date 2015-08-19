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

package org.camunda.bpm.dmn.engine.hitpolicy;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.dmn.engine.DmnDecisionOutput;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.dmn.engine.DmnDecisionTable;
import org.camunda.bpm.dmn.engine.DmnRule;
import org.camunda.bpm.dmn.engine.impl.hitpolicy.DmnHitPolicyException;
import org.camunda.bpm.model.dmn.HitPolicy;

public interface DmnHitPolicyHandler {

  HitPolicy getHandledHitPolicy();

  boolean handlesHitPolicy(HitPolicy hitPolicy);

  /**
   * Returns all rules which should be part of the decision result. Will throw an exception if
   * the hit policy is violated by the matching rules.
   *
   * @param decisionTable the decision table evaluated
   * @param matchingRules all rules which are applicable
   * @return the ordered list of matching rules which are part of the decision result
   * @throws DmnHitPolicyException if the hit policy is violated by the matching rules
   */
  List<DmnRule> filterMatchingRules(DmnDecisionTable decisionTable, List<DmnRule> matchingRules);

  /**
   * Generate decision result from decision outputs of matching rules.
   *
   * @param decisionTable the decision table evaluated
   * @param decisionOutputs the list of decision outputs generated by the matching rules
   * @return the decision result
   * @throws DmnHitPolicyException if the hit policy cannot be applied to the decision outputs
   */
  DmnDecisionResult getDecisionResult(DmnDecisionTable decisionTable, List<DmnDecisionOutput> decisionOutputs);

}