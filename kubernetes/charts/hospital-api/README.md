# Hospital API Helm Chart

This Helm chart deploys the Hospital API application on a Kubernetes cluster.

## Prerequisites

- Kubernetes 1.19+
- Helm 3.2.0+
- ArgoCD and Argo Rollouts installed (for production deployments)

## Installing the Chart

To install the chart with the release name `hospital-api`:

```bash
helm install hospital-api ./charts/hospital-api
```

## Configuration

The following table lists the configurable parameters of the Hospital API chart and their default values.

| Parameter                | Description             | Default        |
| ------------------------ | ----------------------- | -------------- |
| `replicaCount`           | Number of replicas      | `3`            |
| `image.repository`       | Image repository        | `${DOCKER_HUB_USERNAME}/hospital-api` |
| `image.tag`              | Image tag               | `latest`       |
| `image.pullPolicy`       | Image pull policy       | `IfNotPresent` |
| `service.type`           | Service type            | `ClusterIP`    |
| `service.port`           | Service port            | `80`           |
| `service.targetPort`     | Container port          | `8080`         |
| `ingress.enabled`        | Enable ingress          | `true`         |
| `resources.limits.cpu`   | CPU limit               | `2000m`        |
| `resources.limits.memory`| Memory limit            | `2Gi`          |
| `resources.requests.cpu` | CPU request             | `1000m`        |
| `resources.requests.memory` | Memory request       | `1Gi`          |
| `autoscaling.enabled`    | Enable autoscaling      | `true`         |
| `autoscaling.minReplicas`| Minimum replicas        | `3`            |
| `autoscaling.maxReplicas`| Maximum replicas        | `10`           |
| `rollout.enabled`        | Enable Argo Rollouts    | `true` for prod, `false` otherwise |

## ArgoCD Rollouts

For production deployments, this chart supports ArgoCD Rollouts for progressive delivery. When `rollout.enabled` is set to `true`, the chart will create an Argo Rollout resource instead of a Kubernetes Deployment.

The rollout strategy can be configured in the `values.yaml` file:

```yaml
rollout:
  enabled: true
  strategy:
    canary:
      steps:
        - setWeight: 20
        - pause: {duration: 5m}
        - setWeight: 40
        - pause: {duration: 5m}
        - setWeight: 60
        - pause: {duration: 5m}
        - setWeight: 80
        - pause: {duration: 5m}
```

## Production Deployment

For production deployments, use the `values-prod.yaml` file:

```bash
helm install hospital-api ./charts/hospital-api -f ./charts/hospital-api/values-prod.yaml
```

Or use ArgoCD for GitOps-based deployments:

```bash
kubectl apply -f argocd/hospital-api-prod.yaml
```