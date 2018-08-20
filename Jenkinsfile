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
        properties([
                pipelineTriggers([cron('0 * * * *')]),
        ])
        def scmVars = checkout scm
        def commitHash = scmVars.GIT_COMMIT
        stage('Maven Build') {
            container('maven') {
                sh 'mvn clean install'
            }
        }

        stage('Check running containers') {
            container('docker') {
                def name = "registry.192.168.99.100.nip.io:443/bcl/demo"
                def img = name+":"+commitHash
                def latest = name+":latest"
                sh 'docker build -t '+img+' .'
                sh 'docker tag '+img+' '+latest
                sh 'docker push '+name
            }
        }

        stage('Run kubectl') {
            container('kubectl') {
                sh "kubectl apply -f ./deploy.yaml --namespace=env-production"
            }
        }

    }
}
