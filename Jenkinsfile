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
         /*
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
           */
        stage ('Docker build image   Stage') {
                    steps {
                        withMaven(maven : 'maven_3_5_3') {
                            sh 'docker ps -a'
                            sh 'mvn install dockerfile:build'
                            sh 'echo $DOCKER_USERNAME'
                            sh 'docker login -u antonsyzko -p AntonSyzkoDockerhub123'
                            sh 'docker tag antonsyzko/example1  antonsyzko/example1:latest'
                            //sh 'mvn dockerfile:push'
                        }
                    }
                }

        stage ('Docker run container    Stage') {
                            steps {
                                withMaven(maven : 'maven_3_5_3') {
                                    sh 'docker images '
                                    sh 'docker run --rm -d --name cicdtest -p 8085:8085  antonsyzko/example1'
                                    sh 'docker ps'
                                }
                            }
                        }
        stage ('Docker remove  container  and remove image   Stage') {
                                    steps {
                                            sh 'docker images '
                                            sh 'docker stop cicdtest'
                                            sh 'docker ps'
                                            sh 'docker rmi -f antonsyzko/example1:latest '
                                            sh 'docker images '
                                    }
                                }
        stage ('scp to remote stage and java jar ') {
                                            steps {
                                                 sh 'docker stop cicdtest >/dev/null 2>&1 &  exit'
                                                 sh 'ssh root@213.251.40.102 "rm example1-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &  exit"'
                                                 sh 'scp /root/.jenkins/workspace/cicdtest/target/example1-0.0.1-SNAPSHOT.jar root@213.251.40.102:/root/'
                                                 sh 'ssh root@213.251.40.102 "cd /root/ && ls -la | grep example1-0.0.1-SNAPSHOT.jar && exit"'
                                                 sh 'ssh root@213.251.40.102  "java -jar example1-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &  exit"'
                                            }
                                        }

        stage ('Run docker container remotely ') {
                                   steps {
                                      sh 'docker stop cicdtest >/dev/null 2>&1 &  exit'
                                      sh 'docker login -u antonsyzko -p AntonSyzkoDockerhub123'
                                      sh 'ssh root@213.251.40.102 "docker run --rm -d --name cicdtest -p 8086:8085  antonsyzko/example1 && exit "'
                                      sh 'ssh root@213.251.40.102  "docker ps  && exit"'
                                          }
                                }


    }
}

