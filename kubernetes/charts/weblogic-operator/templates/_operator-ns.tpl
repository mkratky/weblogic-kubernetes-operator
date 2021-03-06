# Copyright 2018 Oracle Corporation and/or its affiliates.  All rights reserved.
# Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.

{{- define "operator.operatorNamespace" }}
---
apiVersion: "v1"
kind: "Namespace"
metadata:
  labels:
    weblogic.operatorName: {{ .operatorNamespace | quote }}
    weblogic.resourceVersion: "operator-v1"
  name: {{ .operatorNamespace | quote }}
{{- end }}
