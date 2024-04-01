pipeline {
    environment {
        CLIENT_ID = credentials('client_id')
        SUBSCRIPTION_ID = credentials('subscription_id')
        TENANT_ID = credentials('tenant_id')
        CLIENT_SECRET = credentials('client_secret')

    }
    agent any
    stages {
        stage('Azure Login') {
            steps {
                script {
            // Execute 'az login' command
                    sh "az login --service-principal -u ${CLIENT_ID} -p ${CLIENT_SECRET} --tenant ${TENANT_ID}"
                    sh "az account set -s ${SUBSCRIPTION_ID}"
            }
        }
    }      
        stage('Deploy on TestVM') {  
            steps {
                script {
                    sshagent(['deployserver']) {
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} 'git clone https://github.com/saikiranmerugu74/project3.git -b main'"
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} 'cp -r project3/* /home/ubuntu'"
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} 'terraform init'"
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} 'terraform plan'"
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} 'terraform apply -auto-approve'"
                        }

                }
            }
        }     
    }
}