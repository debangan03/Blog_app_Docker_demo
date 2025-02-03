pipeline {
    agent any

    environment {
        DB_HOST = "localhost"
        DB_PORT = "5432"
        DB_NAME = "postgres"
        DB_USER = "postgres"
        DB_PASSWORD = "10102003"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/debangan03/Blog_app_Docker_demo.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    // Build the application using Maven Wrapper
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Start Services') {
            steps {
                script {
                    // Start Docker Compose services (PostgreSQL + Spring Boot)
                    sh 'docker-compose up -d'

                    // Wait for services to be ready
                    sh 'sleep 10'
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Run tests
                    sh './mvnw test'
                }
            }
        }

        stage('Stop Services') {
            steps {
                script {
                    // Stop all running containers
                    sh 'docker-compose down'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution finished.'
        }
    }
}
