/*
 * Copyright 2017 Google, Inc.
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

package com.netflix.spinnaker.halyard.config.model.v1

import com.netflix.spinnaker.halyard.config.model.v1.node.DeploymentConfiguration
import com.netflix.spinnaker.halyard.config.model.v1.node.Halconfig
import spock.lang.Specification

class HalconfigSpec extends Specification {
  void "build correct iterator of deployments"() {
    setup:
    def halconfig = new Halconfig()
    halconfig.deploymentConfigurations.add(new DeploymentConfiguration())
    def iterator = halconfig.getChildren()

    when:
    def i = 0
    while (iterator.getNext() != null) { i++ }

    then:
    i == halconfig.deploymentConfigurations.size()
  }

  void "default deployments have non-empty entries"() {
    setup:
    def halconfig = new Halconfig()

    when:
    def deployment = halconfig.getDeploymentConfigurations()[0]

    then:
    deployment.getNodeName()
    deployment.getName()
    deployment.getVersion()
    deployment.getProviders()
    deployment.getWebhooks()
  }

  void "default deployment is selected when instantiated"() {
    setup:
    def halconfig = new Halconfig()

    when:
    def currentDeployment = halconfig.getCurrentDeployment()

    then:
    halconfig.getDeploymentConfigurations().any { d -> d.getNodeName() == currentDeployment }
  }
}