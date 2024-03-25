pipeline {
    environment {
        PATH = " /home/ubuntu/.local/lib/python3.10/site-packages:$PATH"
        AZURE_VM = ''
        AZURE_VM_SSH_KEY_CREDENTIALS = credentials('deployserver')
    }
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']],
                extensions: [],
                userRemoteConfigs: [[url: 'https://github.com/saikiranmerugu74/project3.git']]) 
                
            }
        }
        stage ('Clean Up'){
            steps{
                sh returnStatus: true, script: 'terraform destroy'
                //sh returnStatus: true, script: ''

            }
        }
    
        stage('Deploy') {
            steps {
                sh label: '', script: "terraform init"
            }
        }
        stage ('Test'){
            steps {
                sh "python3 -m pytest testapp.py"
            }
        }
        stage('Deploy on Azure_VM') {  
            steps {
                script {
                    sshagent(['deployserver']) {
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} 'git clone https://github.com/saikiranmerugu74/project3.git -b main'"
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} 'cp -r project3/* /home/ubuntu'"
                        //sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} 'ls'"
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} 'pwd'"
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_HOST} 'docker-compose up -d --build'"
                        }

                }
            }
        }
    }
}
