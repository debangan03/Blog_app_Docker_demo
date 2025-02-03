pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = 'debangan03' // Jenkins credentials ID for Docker Hub
        DOCKER_IMAGE_TAG = "latest"
        SPRING_APP_IMAGE = "debangan03/blog-app"
        POSTGRES_IMAGE = "debangan03/postgres-db"
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
                    sh 'chmod +x mvnw' // Make Maven wrapper executable
                    // Build the Spring Boot application using Maven
                    sh './mvnw clean install'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    // Build Docker images for the blog app and PostgreSQL
                    sh "docker build -t ${SPRING_APP_IMAGE}:${DOCKER_IMAGE_TAG} -f Dockerfile ."
                    sh "docker build -t ${POSTGRES_IMAGE}:${DOCKER_IMAGE_TAG} -f Dockerfile.postgres ."
                }
            }
        }

        stage('Login to Docker Hub') {
            steps {
                script {
                    // Login to Docker Hub
                    withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"
                    }
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    // Push Docker images to Docker Hub
                    sh "docker push ${SPRING_APP_IMAGE}:${DOCKER_IMAGE_TAG}"
                    sh "docker push ${POSTGRES_IMAGE}:${DOCKER_IMAGE_TAG}"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Set the Kubernetes context (if needed)
                    sh "kubectl config use-context minikube" // Update this with your cluster context if required

                    // Deploy to Kubernetes using kubectl
                    sh "kubectl apply -f postgres-deployment.yaml"
                    sh "kubectl apply -f blog-app-deployment.yaml"
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    // Verify that the pods are running correctly
                    sh "kubectl get pods"
                }
            }
        }
    }

    post {
        always {
            // Clean up Docker images
            sh "docker system prune -f"
        }
    }
}
