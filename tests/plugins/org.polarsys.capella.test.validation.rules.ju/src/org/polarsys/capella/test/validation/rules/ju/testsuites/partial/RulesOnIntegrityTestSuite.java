/*******************************************************************************
 * Copyright (c) 2006, 2020 THALES GLOBAL SERVICES.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.test.validation.rules.ju.testsuites.partial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.polarsys.capella.test.framework.api.BasicTestArtefact;
import org.polarsys.capella.test.framework.api.BasicTestSuite;
import org.polarsys.capella.test.validation.rules.ju.testcases.i.IRulesTestSuite;

import junit.framework.Test;

public class RulesOnIntegrityTestSuite extends BasicTestSuite {

  /**
   * Returns the suite. This is required to unary launch this test.
   */
  public static Test suite() {
    return new RulesOnIntegrityTestSuite();
  }

  /**
   * @see org.polarsys.capella.test.framework.api.BasicTestSuite#getTests()
   */
  protected List<BasicTestArtefact> getTests() {
    List<BasicTestArtefact> tests = new ArrayList<BasicTestArtefact>();
    tests.add(new IRulesTestSuite());
    return tests;
  }

  @Override
  public List<String> getRequiredTestModels() {   
    return Arrays.asList(new String [] {"RulesOnIntegrityTest"});  //$NON-NLS-1$
  }
}
