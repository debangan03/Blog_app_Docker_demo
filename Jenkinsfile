// pipeline {
//     agent any
//
//     environment {
//         DOCKER_HUB_CREDENTIALS = 'docker-hub-credentials' // Jenkins credentials ID for Docker Hub
//         DOCKER_IMAGE_TAG = "latest"
//         SPRING_APP_IMAGE = "debangan03/blog-app"
//         POSTGRES_IMAGE = "debangan03/postgres-db"
//         SONARQUBE_ENV = 'SonarQube' // Name of your SonarQube server in Jenkins configuration
//     }
//
//     stages {
//         stage('Clone Repository') {
//             steps {
//                 // Clone the repository from GitHub
//                 git branch: 'main', url: 'https://github.com/debangan03/Blog_app_Docker_demo.git'
//             }
//         }
//
//         stage('Build Application') {
//             steps {
//                 script {
//                     // For Windows
//                     bat 'mvnw.cmd clean install'
//                 }
//             }
//         }
//
//         stage('SonarQube Analysis') {
//             steps {
//                 withSonarQubeEnv(SONARQUBE_ENV) {
//                     script {
//                         bat 'mvnw.cmd clean install' // Ensure code is compiled before analysis
//                         bat 'mvnw.cmd org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Dsonar.java.binaries=target/classes'
//                     }
//                 }
//             }
//         }
//
//
//         stage('Build Docker Images') {
//             parallel {
//                 stage('Build Spring App Image') {
//                     steps {
//                         script {
//                             bat "docker build -t ${SPRING_APP_IMAGE}:${DOCKER_IMAGE_TAG} -f Dockerfile ."
//                         }
//                     }
//                 }
//                 stage('Build PostgreSQL Image') {
//                     steps {
//                         script {
//                             bat "docker pull postgres:latest"
//                             bat "docker tag postgres:latest ${POSTGRES_IMAGE}:${DOCKER_IMAGE_TAG}"
//                         }
//                     }
//                 }
//             }
//         }
//
//         stage('Login to Docker Hub') {
//             steps {
//                 script {
//                     // Login to Docker Hub
//                     withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
//                         bat """
//                         echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin
//                         """
//                     }
//                 }
//             }
//         }
//
//         stage('Push Docker Images') {
//             steps {
//                 script {
//                     // Push Docker images to Docker Hub
//                     bat "docker push ${SPRING_APP_IMAGE}:${DOCKER_IMAGE_TAG}"
//                     bat "docker push ${POSTGRES_IMAGE}:${DOCKER_IMAGE_TAG}"
//                 }
//             }
//         }
//
// //         stage('Deploy to Kubernetes') {
// //             steps {
// //                 script {
// //                     // Deploy to Kubernetes using kubectl
// //                     bat "kubectl apply -f deployment_pg.yaml"
// //                     bat "kubectl apply -f deployment_app.yaml"
// //                 }
// //             }
// //         }
// //
// //         stage('Verify Deployment') {
// //             steps {
// //                 script {
// //                     // Verify that the pods are running correctly
// //                     bat "kubectl get pods"
// //                 }
// //             }
// //         }
//     }
//
//     post {
//         always {
//                     script {
//                         // Clean up Docker images
//                         bat "docker system prune -f"
//                     }
//                 }
//                 success {
//                     mail to: "debangan2019@gmail.com",
//                          subject: "Success",
//                          body: "Task completion success"
//                 }
//                 failure {
//                     mail to: "debangan2019@gmail.com",
//                          subject: "failure",
//                          body: "Task completion failure"
//                 }
//     }
// }




pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = 'docker-hub-credentials'
        DOCKER_IMAGE_TAG = "latest"
        SPRING_APP_IMAGE = "debangan03/blog-app"
        POSTGRES_IMAGE = "debangan03/postgres-db"
        SONARQUBE_ENV = 'SonarQube'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/debangan03/Blog_app_Docker_demo.git'
            }
        }

        stage('Build Application') {
            steps {
                script {

                    sh '''
                             chmod +x mvnw
                            ./mvnw clean install
                       '''
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    script {
                        withCredentials([string(credentialsId: 'sq1', variable: 'SONAR_TOKEN')]) {
                            sh './mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:4.0.0.4121:sonar -Dsonar.login=$SONAR_TOKEN -Dsonar.java.binaries=target/classes'
                        }
                    }
                }
            }
        }


        stage('Build Docker Images') {
            parallel {
                stage('Build Spring App Image') {
                    steps {
                        script {
                            sh "docker build -t ${SPRING_APP_IMAGE}:${DOCKER_IMAGE_TAG} -f Dockerfile ."
                        }
                    }
                }
                stage('Build PostgreSQL Image') {
                    steps {
                        script {
                            sh "docker pull postgres:latest"
                            sh "docker tag postgres:latest ${POSTGRES_IMAGE}:${DOCKER_IMAGE_TAG}"
                        }
                    }
                }
            }
        }

        stage('Login to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"
                    }
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    sh "docker push ${SPRING_APP_IMAGE}:${DOCKER_IMAGE_TAG}"
                    sh "docker push ${POSTGRES_IMAGE}:${DOCKER_IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    sh "kubectl apply -f deployment_pg.yaml"
                    sh "kubectl apply -f deployment_app.yaml"
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    sh "kubectl get pods"
                }
            }
        }
    }

    post {
        always {
            script {
                sh "docker system prune -f"
            }
        }
        success {
            emailext subject: "Jenkins Pipeline: SUCCESS",
                     body: "The Jenkins pipeline has completed successfully.",
                     to: "debangan2019@gmail.com"
        }
        failure {
            emailext subject: "Jenkins Pipeline: FAILURE",
                     body: "The Jenkins pipeline has failed. Please check the Jenkins job for details.",
                     to: "debangan2019@gmail.com"
        }
    }
}
