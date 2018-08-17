podTemplate(label: 'mypod', containers: [
<<<<<<< HEAD
=======
    containerTemplate(name: 'git', image: 'alpine/git', ttyEnabled: true, command: 'cat'),
>>>>>>> 24cec2090249ded02c561b4157c106164cd73ff4
    containerTemplate(name: 'maven', image: 'maven:3.3.9-jdk-8-alpine', command: 'cat', ttyEnabled: true),
    containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true)
  ],
  volumes: [
    hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock'),
  ]
  ) {
    node('mypod') {
        stage('Check running containers') {
            container('docker') {
                // example to show you can run docker commands when you mount the socket
                sh 'hostname'
                sh 'hostname -i'
                sh 'docker ps'
            }
        }

        stage('Maven Build') {
            container('maven') {
<<<<<<< HEAD
                sh 'ls'
=======
>>>>>>> 24cec2090249ded02c561b4157c106164cd73ff4
                sh 'hostname'
                sh 'hostname -i'
                sh 'mvn clean install'
            }
        }
    }
}
