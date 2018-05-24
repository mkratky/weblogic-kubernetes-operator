// Copyright 2017, 2018, Oracle Corporation and/or its affiliates.  All rights reserved.
// Licensed under the Universal Permissive License v 1.0 as shown at
// http://oss.oracle.com/licenses/upl.

package oracle.kubernetes.operator.steps;

import oracle.kubernetes.operator.ProcessingConstants;
import oracle.kubernetes.operator.helpers.DomainPresenceInfo;
import oracle.kubernetes.operator.helpers.LifeCycleHelper;
import oracle.kubernetes.operator.helpers.NonClusteredServerConfig;
import oracle.kubernetes.operator.work.NextAction;
import oracle.kubernetes.operator.work.Packet;
import oracle.kubernetes.operator.work.Step;
import oracle.kubernetes.weblogic.domain.v1.Domain;
import oracle.kubernetes.weblogic.domain.v1.DomainSpec;

public class BeforeAdminServiceStep extends Step {
  public BeforeAdminServiceStep(Step next) {
    super(next);
  }

  @Override
  public NextAction apply(Packet packet) {
    DomainPresenceInfo info = packet.getSPI(DomainPresenceInfo.class);

    Domain dom = info.getDomain();
    DomainSpec spec = dom.getSpec();

    packet.put(ProcessingConstants.SERVER_NAME, spec.getAsName());
    packet.put(ProcessingConstants.PORT, spec.getAsPort());
    NonClusteredServerConfig adminServerConfig =
        LifeCycleHelper.instance().getEffectiveNonClusteredServerConfig(dom, spec.getAsName());
    if (adminServerConfig != null) {
      packet.put(ProcessingConstants.NODE_PORT, adminServerConfig.getNodePort());
    }
    return doNext(packet);
  }
}
