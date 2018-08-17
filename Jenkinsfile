podTemplate(label: 'mypod', containers: [
        containerTemplate(name: 'maven', image: 'maven:3.3.9-jdk-8-alpine', command: 'cat', ttyEnabled: true),
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
                sh 'docker build -t jenkins:1.0 .'
                sh 'docker ps'
            }
        }

    }
}
