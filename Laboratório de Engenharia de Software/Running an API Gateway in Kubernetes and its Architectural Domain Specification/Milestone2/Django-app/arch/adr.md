# ADR-001: Kubernetes with Horizontal Pod Autoscaling 

## Context 
We need a Django application that can automatically scale under heavy loads of requests, ensuring high availability and optimal resource utilization.

## Decision 
Use Kubernetes with Horizontal Pod Autoscaling (HPA) to automatically scale the Django application pods based on the used metrics. 

## Consequences 
### Positive:

Automatic scaling based on actual load

Better resource utilization and cost efficiency

High availability during traffic spikes

Kubernetes provides robust container orchestration

### Negative:

Increased complexity in deployment and management

Dependancy of the metrics precision



# ADR-002: APISIX as API Gateway

## Context
We need a robust API gateway that can handle load balancing, routing, and expose metrics for auto-scaling decisions.

## Decision
Use APISIX as the API gateway because of its built-in Prometheus metrics and Kubernetes-native integration.

## Consequences
### Positive:

Built-in Prometheus metrics endpoint

Excellent Kubernetes integration

Advanced routing and load balancing capabilities

Web-based dashboard for management

### Negative:

Additional component to maintain

Requires configuration and management



# ADR-003: Development Environment with Minikube

## Context
We need a consistent development and testing environment that mirrors production as closely as possible while being accessible for developers.

## Decision
Use Minikube for local development and testing, with the same deployment configuration that would be used in production Kubernetes clusters.

## Consequences
### Positive:

Consistent environment across development and production

Easy local testing of Kubernetes configurations

Fast iteration cycles

Low resource requirements for development

### Negative:

May not perfectly replicate production scale

Limited to single-node cluster


# ADR-004: Load Testing Strategy

## Context
We need to validate that the auto-scaling mechanism works correctly and understand the system's behavior under load.

## Decision
Use dedicated load testing tools (like "hey") to generate controlled traffic and trigger auto-scaling events, monitoring the system response and scaling behavior.

## Consequences
### Positive:

Validates auto-scaling configuration

Identifies performance bottlenecks

Provides confidence in production readiness

Helps tune scaling parameters

### Negative:

Requires additional testing infrastructure

May impact development environment performance

Additional complexity in test automation

