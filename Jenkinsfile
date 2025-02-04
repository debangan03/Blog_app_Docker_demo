pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = 'docker-hub-credentials' // Jenkins credentials ID for Docker Hub
        DOCKER_IMAGE_TAG = "latest"
        SPRING_APP_IMAGE = "debangan03/blog-app"
        POSTGRES_IMAGE = "debangan03/postgres-db"
        SONARQUBE_ENV = 'SonarQube' // Name of your SonarQube server in Jenkins configuration
    }

    stages {
        stage('Clone Repository') {
            steps {
                // Clone the repository from GitHub
                git branch: 'main', url: 'https://github.com/debangan03/Blog_app_Docker_demo.git'
            }
        }

        stage('Build Application') {
            steps {
                script {
                    // For Windows
                    bat 'mvnw.cmd clean install'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv(SONARQUBE_ENV) {
                    script {
                        bat 'mvnw.cmd sonar:sonar'
                    }
                }
            }
        }

        stage('Build Docker Images') {
            parallel {
                stage('Build Spring App Image') {
                    steps {
                        script {
                            bat "docker build -t ${SPRING_APP_IMAGE}:${DOCKER_IMAGE_TAG} -f Dockerfile ."
                        }
                    }
                }
                stage('Build PostgreSQL Image') {
                    steps {
                        script {
                            bat "docker build -t ${POSTGRES_IMAGE}:${DOCKER_IMAGE_TAG} -f Dockerfile.postgres ."
                        }
                    }
                }
            }
        }

        stage('Login to Docker Hub') {
            steps {
                script {
                    // Login to Docker Hub
                    withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        bat """
                        echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin
                        """
                    }
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    // Push Docker images to Docker Hub
                    bat "docker push ${SPRING_APP_IMAGE}:${DOCKER_IMAGE_TAG}"
                    bat "docker push ${POSTGRES_IMAGE}:${DOCKER_IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Deploy to Kubernetes using kubectl
                    bat "kubectl apply -f deployment_pg.yaml"
                    bat "kubectl apply -f deployment_app.yaml"
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    // Verify that the pods are running correctly
                    bat "kubectl get pods"
                }
            }
        }
    }

    post {
        always {
            // Clean up Docker images
            bat "docker system prune -f"
        }
    }
}
