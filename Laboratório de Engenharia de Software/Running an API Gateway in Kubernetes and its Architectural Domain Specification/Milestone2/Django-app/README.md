## Iniciar Minikube ##

minikube start --driver=docker

## Verificar se está a funcionar##

minikube status
kubectl get nodes

## Deployments ##

kubectl apply -f postgres-deployment.yaml
kubectl apply -f django-deployment.yaml

## Adicionar repositório e namespace APISIX ##

helm repo add apisix https://charts.apiseven.com
helm repo update
kubectl create namespace apisix

## Instalar APISIX ##

helm install apisix apisix/apisix --namespace apisix --set gateway.type=NodePort --set admin.allow.ipList="0.0.0.0/0" --set ingress-controller.enabled=true

## Instalar APISIX Dashboard ##

helm install apisix-dashboard apisix/apisix-dashboard --namespace apisix --set service.type=NodePort --set allow.ipList="0.0.0.0/0"

## Confirmar Pods estão running ##

kubectl get pods -n apisix -w

## URL APISIX Gateway ##

minikube service apisix-gateway -n apisix --url

## Port-foward da Dashboard ##

kubectl port-forward -n apisix svc/apisix-dashboard 8888:80
http://localhost:8888

## Criar Routes ##

Path: /admin*

UpStream-> 

Algorithm: Round Robin
Upstream Type: Node
Host: django.default.svc.cluster.local
Port: 8000
Weight: 1

## Redirect para Django ##

curl.exe -v http://127.0.0.1:PORTAPISIX/admin/

## HPA ##

minikube addons enable metrics-server
kubectl apply -f hpa.yaml

### HPA check ###

kubectl get hpa django-hpa -w
kubectl top pods -l app=django

### HPA load ###

kubectl run load --rm -it --image=demisto/rakyll-hey:1.0.0.108484 --restart=Never -- hey -z 60s -c 10 -q 10 http://django.default.svc.cluster.local:8000
