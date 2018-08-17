podTemplate(label: 'mypod', containers: [
        containerTemplate(name: 'maven', image: 'maven:3.5.4-jdk-8-alpine', command: 'cat', ttyEnabled: true),
        containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl:v1.11.2', command: 'cat', ttyEnabled: true),
        containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true)
],
        volumes: [
                hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock'),
        ]
) {
    node('mypod') {
        def myRepo = checkout scm
        stage('Maven Build') {
            container('maven') {
                sh 'mvn clean install'
            }
        }

        stage('Check running containers') {
            container('docker') {
                sh 'docker build -t registry.192.168.99.100.nip.io:443/bcl/demo:1.0 .'
                sh 'docker push registry.192.168.99.100.nip.io:443/bcl/demo:1.0'
                sh 'docker ps'
            }
        }

        stage('Run kubectl') {
            container('kubectl') {
                sh "kubectl get pods --namespace=env-production"
                sh "kubectl apply -f ./deploy.yaml --namespace=env-production"
            }
        }

    }
}
