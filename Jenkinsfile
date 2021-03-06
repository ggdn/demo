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
        def imageBuilded
        def scmVars = checkout scm
        def commitHash = scmVars.GIT_COMMIT
        def name = "gcr.io/winter-campus-211113/demo"
        def img = name+":"+commitHash
        def latest = name+":latest"
        stage('Maven Build') {
            container('maven') {
                sh 'mvn clean install -B'
            }
        }

        stage('Check running containers') {
            container('docker') {
                imageBuilded = docker.build(img)
                docker.withRegistry('https://gcr.io', 'gcr:winter-campus-211113') {
                    imageBuilded.push()
                    imageBuilded.push('latest')
                }
            }
        }

        stage('Run kubectl') {
            container('kubectl') {
                sh "kubectl apply -f ./deploy.yaml --namespace=env-production"
                sh "kubectl set image deployment/demo --namespace=env-production demo="+img
                sh "kubectl rollout status deployment/demo --namespace=env-production"
            }
        }

    }
}
