#Default values for helm.
# This is a YAML-formatted file.
# Declare variables to be passed into your templates.
environment: "override-me"
name: starter-api
owner: starter
replicaCount: 1
image:
  repository: docker.pkg.github.com/thoughtworks-dps/dps-multi-module-starterkit-java/dps-multi-module-starterkit-java
  pullPolicy: IfNotPresent
  tag: "latest"
initContainerName: db-init
imagePullSecret: github-packages-secret
nameOverride: ""
fullnameOverride: ""

serviceAccount:
  create: true
  annotations: {}
  name: ""

podAnnotations: {}

podSecurityContext: {}

securityContext: {}

service:
  type: ClusterIP
  ports:
    - port: 80
      targetPort: 8080
      protocol: TCP
      name: http

ingress:
  enabled: false
  annotations: {}
  hosts:
    - host: chart-example.local
      paths: []
  tls: []

resources:
  requests:
    cpu: 1
    memory: 250m
  limits:
    cpu: 1
    memory: 250m

autoscaling:
  enabled: false
  minReplicas: 1
  maxReplicas: 100
  targetCPUUtilizationPercentage: 80

nodeSelector: {}

tolerations: []

affinity: {}

ports:
  - name: http
    containerPort: 8080
    protocol: TCP
  - name: http-health
    containerPort: 8081
    protocol: TCP

nsAnyVars:
  - name: JAVA_OPTS
    value: -Xmx512m
  - name: SPRING_PROFILE
    value: prod

livenessProbe:
  httpGet:
    path: /actuator/health/liveness
    port: 8081
  initialDelaySeconds: 10
  periodSeconds: 10
  timeoutSeconds: 10

readinessProbe:
  httpGet:
    path: /actuator/health/readiness
    port: 8081
  initialDelaySeconds: 10
  periodSeconds: 10
  timeoutSeconds: 10

podDisruptionBudget: 35%
