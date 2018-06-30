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

        stage ('Run JAR  Stage') {
                    steps {
                        withMaven(maven : 'maven_3_5_3') {
                            sh 'pwd'
                            sh 'ls -la'
                            sh 'chmod a+rx /root/.jenkins/workspace/cicdtest/target/example1-0.0.1-SNAPSHOT.jar'
                            sh 'firewall-cmd --zone=public --add-port=8085/tcp --permanent'
                            sh 'firewall-cmd --reload'
                            sh 'nohup java -jar /root/.jenkins/workspace/cicdtest/target/example1-0.0.1-SNAPSHOT.jar &'
                            sh 'curl -X GET http://213.251.42.90:8085/cicdtest/actuator/health'
                            sh 'kill -9 $(lsof -t -i:8085)'
                        }
                    }
                }
        stage ('Docker build image   Stage') {
                    steps {
                        withMaven(maven : 'maven_3_5_3') {
                            sh 'docker ps -a'
                            sh 'mvn install dockerfile:build'
                            sh 'mvn dockerfile:push'
                        }
                    }
                }

        stage ('Docker run container    Stage') {
                            steps {
                                withMaven(maven : 'maven_3_5_3') {
                                    sh 'docker images -a'
                                    sh 'docker run -p 8085:8085 -t antonsyzko/example1'
                                }
                            }
                        }
    }
}

