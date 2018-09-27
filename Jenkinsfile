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
        def name = "gcr.io/bcl/demo"
        def img = name+":"+commitHash
        def latest = name+":latest"
        stage('Maven Build') {
            container('maven') {
                sh 'mvn clean install -B'
            }
        }

        stage('Check running containers') {
            container('docker') {
                myImage = docker.build(img)    
                sh 'docker tag '+img+' '+latest
                docker.withRegistry('https://gcr.io', 'gcr:winter-campus-211113') { 
                        docker.push(name)
                }     
                sh 'docker push '+name
            }
        }

        stage('Run kubectl') {
            container('kubectl') {
                sh "kubectl apply -f ./deploy.yaml --namespace=env-production"
                sh "kubectl set image deployment/demo --namespace=env-production demo="+name
                sh "kubectl set image deployment/demo --namespace=env-production demo="+latest
            }
        }

    }
}
