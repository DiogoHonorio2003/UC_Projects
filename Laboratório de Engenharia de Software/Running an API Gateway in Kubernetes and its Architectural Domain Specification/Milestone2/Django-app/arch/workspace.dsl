workspace {

    !identifiers hierarchical

    model {

        user = person "User" "User of the Django App"
        loadGenerator = softwareSystem "Load Generator" "Use of Hey to overload system and test HPA"

        
        k8sSystem = softwareSystem "Kubernetes System" "Auto-scaling web application" {

            apisix = container "APISIX" "API Gateway and load balancer" "APISIX" {
                dashboard = component "APISIX Dashboard" "Web administration interface for routing management and configuration" "Web"
                gateway = component "APISIX Gateway" "HTTP request processing and routing" "Nginx/OpenResty"
                metrics = component "Metrics Exporter" "Exposes Prometheus metrics endpoint" "Prometheus"
            }
            
            django = container "Django Application" "Server of Django Application" "Python/Django" 
        
            postgresql = container "PostgreSQL Database" "Main Database" "PostgreSQL" {
                tags "Database"
            }

            prometheus = container "Prometheus Monitoring" "Metrics collection, storage, and querying system for Kubernetes monitoring and auto-scaling" "Prometheus"{
                prometheusServer = component "Prometheus Server" "Metrics collection and monitoring system" "Prometheus" 
                prometheusAdapter = component "Prometheus Adapter" "Custom metrics adapter for Kubernetes" "Kubernetes"
            }
            hpa = container "HPA Controller" "Horizontal Pod Autoscaler controller" "Kubernetes" {
                controller = component "Scaling Controller" "Uses Metrics for the Scaling decision logic"
                metricsAPI = component "Metrics API" "Analyses Metrics for scaling decisions"
            }
            
            metricsServer = container "Metrics Server" "Standard Metrics of Kubernetes" "Kubernetes"
        }

        
        user -> k8sSystem.apisix.gateway "Makes HTTP Requests"
        loadGenerator -> k8sSystem.apisix "Creates test data to overload the system"
        

        k8sSystem.apisix.gateway -> k8sSystem.django "Redirects HTTP Requests"
        
        k8sSystem.apisix.dashboard -> k8sSystem.apisix.gateway "Provides the routes"
        
        k8sSystem.prometheus.prometheusServer -> k8sSystem.apisix.metrics "Scraping of metrics for APISIX"
        
        k8sSystem.prometheus.prometheusAdapter -> k8sSystem.prometheus.prometheusServer "View customized metrics"
        k8sSystem.hpa.metricsAPI -> k8sSystem.prometheus.prometheusAdapter "Sends Scaling Metrics"
        
        k8sSystem.hpa.metricsAPI -> k8sSystem.hpa.controller "Provides metrics for the decision"
        
        k8sSystem.metricsServer -> k8sSystem.hpa.metricsAPI "Gets Standard Metrics "
        k8sSystem.hpa.controller -> k8sSystem.django "Adjusts the number of Replicas according to metrics"
        k8sSystem.django -> k8sSystem.postgresql "Read and Write data"
        
        development = deploymentEnvironment "Development" {

            deploymentNode "Minikube Cluster" "Local Kubernetes environment" "Kubernetes" {
        
                deploymentNode "APISIX Gateway" "API Gateway" "Docker" {
                    containerInstance k8sSystem.apisix
                }
        
                deploymentNode "Django Application" "Web Application" "Docker" {
                    containerInstance k8sSystem.django
                }
        
                deploymentNode "PostgreSQL Database" "Database" "Docker" {
                    containerInstance k8sSystem.postgresql
                }
        
                deploymentNode "Monitoring Stack" "Observability" "Docker" {
                    containerInstance k8sSystem.prometheus
                    containerInstance k8sSystem.hpa
                    containerInstance k8sSystem.metricsServer
                }
            }
        }
    }

    views {
        systemContext k8sSystem "ContextDiagram" {
            include *
            autolayout lr
        }
          
        container k8sSystem "ContainersDiagram" {
            include *
            autolayout tb
        }
        
        component k8sSystem.apisix "APISIXComponents" {
            include *
            autolayout lr
        }
        
        component k8sSystem.hpa "HPAComponents" {
            include *
            autolayout lr
        }
        
        component k8sSystem.prometheus "PrometheusComponents" {
            include *
            autolayout lr
        }
        
        deployment * development {
            include *
            autolayout tb
        }

   
        styles {
            element "Element" {
                background #08427b
                color #ffffff
                stroke #0773af
                strokeWidth 7
                shape roundedbox
            }
            element "Person" {
                shape person
                background #08427b
                color #ffffff
            }
            element "Software System" {
                background #1168bd
                color #ffffff
            }
            element "Container" {
                background #438dd5
                color #ffffff
            }
            element "Component" {
                background #85bbf0
                color #000000
            }
            element "Database" {
                shape cylinder
                background #438dd5
                color #ffffff
            }
            element "API Gateway" {
                background #ff6b6b
                color #ffffff
            }
            element "Monitoring" {
                background #4ecdc4
                color #000000
            }
            element "Kubernetes" {
                background #95a5a6
                color #ffffff
            }
            element "Boundary" {
                strokeWidth 5
            }
            relationship "Relationship" {
                thickness 4
                color #ffffff
            }
        }
    }
}