pipeline {
    agent any

    stages {
        stage ('Compile Stage') {

            steps {
                echo "Starting maven build"
                    sh "whoami"
                    sh "ls -la"
                    sh 'java -version'
                withMaven(maven : 'maven_3_5_3') {
                    sh 'mvn --version'
                    sh 'mvn clean compile'
                }
            }
        }

        stage ('Testing Stage') {

            steps {
                withMaven(maven : 'maven_3_5_3') {
                    sh 'mvn test'
                }
            }
        }


        stage ('Installation Stage') {
            steps {
                withMaven(maven : 'maven_3_5_3') {
                    sh 'mvn install'
                }
            }
        }

        stage ('Run  Stage') {
                    steps {
                        withMaven(maven : 'maven_3_5_3') {
                            sh 'pwd'
                            sh 'ls -la'
                            sh 'chmod a+rx /root/.jenkins/workspace/cicdtest/target/example1-0.0.1-SNAPSHOT.jar'
                            sh 'firewall-cmd --zone=public --add-port=8085/tcp --permanent'
                            sh 'firewall-cmd --reload'
                            sh 'java -jar /root/.jenkins/workspace/cicdtest/target/example1-0.0.1-SNAPSHOT.jar'
                        }
                    }
                }
    }
}

