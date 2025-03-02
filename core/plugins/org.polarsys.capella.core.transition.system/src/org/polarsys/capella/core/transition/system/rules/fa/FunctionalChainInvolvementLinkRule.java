/*******************************************************************************
 * Copyright (c) 2019, 2020 THALES GLOBAL SERVICES.
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

package org.polarsys.capella.core.transition.system.rules.fa;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementLink;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.attachment.AttachmentHelper;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.contextscope.IContextScopeHandler;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IPremise;

public class FunctionalChainInvolvementLinkRule extends FunctionalChainInvolvementRule {
  
  public FunctionalChainInvolvementLinkRule() {
    super();
    registerUpdatedReference(FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__EXCHANGE_CONTEXT);
    registerUpdatedReference(FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__EXCHANGED_ITEMS);
    registerUpdatedReference(FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__SOURCE);
    registerUpdatedReference(FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__TARGET);
    registerUpdatedReference(FaPackage.Literals.REFERENCE_HIERARCHY_CONTEXT__SOURCE_REFERENCE_HIERARCHY);
    registerUpdatedReference(FaPackage.Literals.REFERENCE_HIERARCHY_CONTEXT__TARGET_REFERENCE_HIERARCHY);
  }
  
  @Override
  protected EClass getSourceType() {
    return FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK;
  }

  @Override
  protected void premicesRelated(EObject element, ArrayList<IPremise> needed) {
    super.premicesRelated(element, needed);
    needed.addAll(createDefaultPrecedencePremices(element, FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__EXCHANGE_CONTEXT));
    needed.addAll(createDefaultPrecedencePremices(element, FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__EXCHANGED_ITEMS));
    needed.addAll(createDefaultPrecedencePremices(element, FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__SOURCE));
    needed.addAll(createDefaultPrecedencePremices(element, FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__TARGET));
    needed.addAll(createDefaultPrecedencePremices(element, FaPackage.Literals.REFERENCE_HIERARCHY_CONTEXT__SOURCE_REFERENCE_HIERARCHY));
    needed.addAll(createDefaultPrecedencePremices(element, FaPackage.Literals.REFERENCE_HIERARCHY_CONTEXT__TARGET_REFERENCE_HIERARCHY));
  }
  
  @Override
  protected void attachRelated(EObject element, EObject result, IContext context) {
    super.attachRelated(element, result, context);
    AttachmentHelper.getInstance(context).attachTracedElements(element, result, FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__EXCHANGE_CONTEXT, context);
    AttachmentHelper.getInstance(context).attachTracedElements(element, result, FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__EXCHANGED_ITEMS, context);
    AttachmentHelper.getInstance(context).attachTracedElements(element, result, FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__SOURCE, context);
    AttachmentHelper.getInstance(context).attachTracedElements(element, result, FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__TARGET, context);
    AttachmentHelper.getInstance(context).attachTracedElements(element, result, FaPackage.Literals.REFERENCE_HIERARCHY_CONTEXT__SOURCE_REFERENCE_HIERARCHY, context);
    AttachmentHelper.getInstance(context).attachTracedElements(element, result, FaPackage.Literals.REFERENCE_HIERARCHY_CONTEXT__TARGET_REFERENCE_HIERARCHY, context);
  }
  
  @Override
  protected void retrieveGoDeep(EObject source, List<EObject> result, IContext context) {
    super.retrieveGoDeep(source, result, context);
    FunctionalChainInvolvementLink fcil = (FunctionalChainInvolvementLink) source;
    IContextScopeHandler handler = ContextScopeHandlerHelper.getInstance(context);
    if (handler.contains(ITransitionConstants.SOURCE_SCOPE, source, context)) {
      result.add(fcil.getExchangeContext());
      handler.add(ITransitionConstants.SOURCE_SCOPE, fcil.getExchangeContext(), context);
    }
  }
}
