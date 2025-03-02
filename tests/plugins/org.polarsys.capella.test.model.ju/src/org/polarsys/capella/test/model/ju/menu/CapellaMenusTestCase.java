/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.test.model.ju.menu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.sirius.viewpoint.DRepresentationDescriptor;
import org.eclipse.ui.ISources;
import org.polarsys.capella.common.mdsofa.common.constant.ICommonConstants;
import org.polarsys.capella.common.ui.toolkit.browser.content.provider.wrapper.PrimitiveWrapper;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.libraries.model.ICapellaModel;
import org.polarsys.capella.core.libraries.utils.ScopeModelWrapper;
import org.polarsys.capella.core.platform.sirius.ui.handlers.CopyPathHandler;
import org.polarsys.capella.core.platform.sirius.ui.handlers.DeleteHandler;
import org.polarsys.capella.core.sirius.ui.handlers.CopyTextHandler;
import org.polarsys.capella.core.ui.properties.richtext.handlers.CopyAsDescriptionLinkHandler;
import org.polarsys.capella.shared.id.handler.IScope;
import org.polarsys.capella.shared.id.handler.IdManager;
import org.polarsys.capella.test.model.ju.model.MiscModel;

public class CapellaMenusTestCase extends MiscModel {
  ICapellaModel model;
  IScope scope;
  
  @Override
  public void test() throws Exception {
    init();
    
    EObject selectedElement = IdManager.getInstance().getEObject(LC_1, scope);
    assertNotNull(selectedElement);
    testMenus(LC_1, selectedElement);
    
    // PAB Structure
    String pabDiagramID = "_HY2uQekKEem8xqbBNWv2mQ";
    EObject pabDiagram = IdManager.getInstance().getEObject(pabDiagramID, scope);
    assertTrue(pabDiagram instanceof DRepresentationDescriptor);
    testDiagramMenus(pabDiagramID, (DRepresentationDescriptor) pabDiagram);
    
    testCopyAsTextNumericValue(5);
    testCopyAsTextNumericValue(5, 8, 9);
  }
  
  protected void init() {
    model = getTestModel();
    scope = new ScopeModelWrapper(model);
  }
  
  protected void testMenus(String selectedElementId, EObject selectedElement) {
    testDeleteCommand(selectedElementId, selectedElement);
    testCopyQualifiedNameCommand(selectedElement);
    testCopyAsDescriptionLinkCommand(selectedElement);
    testCopyAsTextCommand(selectedElement);
  }
  
  protected void testDiagramMenus(String selectedElementId, DRepresentationDescriptor selectedElement) {
    testDeleteCommand(selectedElementId, selectedElement);
    testCopyAsDescriptionLinkCommand(selectedElement);
    testCopyAsTextCommand(selectedElement);
    testCopyAsTextValue(selectedElement, selectedElement.getName());
  }
  
  protected void testDeleteCommand(String selectedElementId, EObject selectedElement) {
    try {
      new DeleteHandler() {
        protected boolean withConfirmDeletion() {
          return false;
        }
      }.execute(createExecutionEvent(selectedElement));
    } catch (ExecutionException e) {
    }

    if (selectedElement instanceof CapellaElement) {
      CapellaElement element = (CapellaElement) selectedElement;
      String id = element.getId();
      assertEquals(id, selectedElementId);
    }
    
    // check that the element was deleted
    EObject object = IdManager.getInstance().getEObject(selectedElementId, scope);
    assertNull(object);
  }
  
  protected void testCopyQualifiedNameCommand(EObject selectedElement) {
    try {
      new CopyPathHandler().execute(createExecutionEvent(selectedElement));
    } catch (ExecutionException e) {
    }
  }
  
  protected void testCopyAsDescriptionLinkCommand(EObject selectedElement) {
    try {
      new CopyAsDescriptionLinkHandler().execute(createExecutionEvent(selectedElement));
    } catch (ExecutionException e) {
    }
  }
  
  protected void testCopyAsTextCommand(EObject selectedElement) {
    try {
      new CopyTextHandler().execute(createExecutionEvent(selectedElement));
    } catch (ExecutionException e) {
    }
  }
  
  protected void testCopyAsTextValue(EObject selectedElement, String checkLabel) {
    assertTrue("Copied label should be: " + checkLabel, checkLabel.equals(getCopyAsText(selectedElement)));
  }
  
  protected void testCopyAsTextNumericValue(Integer... values) {
    Object[] wrappers = Arrays.stream(values).map(value -> new PrimitiveWrapper(value)).toArray();
    String valuesStr = Arrays.stream(values).map(value -> String.valueOf(value))
        .collect(Collectors.joining(ICommonConstants.LINE_SEPARATOR));
    assertEquals(valuesStr, getCopyAsText(wrappers));
  }

  private String getCopyAsText(Object... values) {
    ExecutionEvent event = createExecutionEvent(values);
    String result = new CopyTextHandler() {
      protected IStructuredSelection getSelection() {
        IEvaluationContext context = (IEvaluationContext) event.getApplicationContext();
        return (IStructuredSelection) context.getVariable("selection");
      }
    }.getSelectionAsText();

    return result;
  }

  private ExecutionEvent createExecutionEvent(Object ...elements) {
    IEvaluationContext context = new EvaluationContext(null, new Object());
    Map<String, String> parameters = new HashMap<>();
    ExecutionEvent event = new ExecutionEvent(null, parameters, null, context);

    context.addVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME, new StructuredSelection(Arrays.asList(elements)));

    return event;
  }

}
