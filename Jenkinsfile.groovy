pipeline {
    environment {
        //CLIENT_ID="9c0da9e1-33dc-4044-bc8a-854f91be2c72"
        //SUBSCRIPTION_ID="488a119a-b2e2-49eb-abfa-98c93480fc34"
        //TENANT_ID="ed236744-d848-41e9-a7cd-e801c29208fc"
        //CLIENT_SECRET="eFV8Q~OIaTxwFYd8yqtkHZt9cp5Yk8rGoAEQfc2-"
        CLIENT_ID = credentials('client_id')
        SUBSCRIPTION_ID = credentials('subscription_id')
        TENANT_ID = credentials('tenant_id')
        CLIENT_SECRET = credentials('client_secret')

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


        stage('Azure Login') {
            steps {
                script {
            // Execute 'az login' command
                    sh "az login --service-principal -u ${CLIENT_ID} -p ${CLIENT_SECRET} --tenant ${TENANT_ID}"
                    sh "az account set -s ${SUBSCRIPTION_ID}"
        }
    }
}

     
    
        stage('Deploy') {
            steps {
                sh label: '', script: "terraform init"
                sh label: '', script: "terraform plan"
                sh label: '', script: "terraform apply -auto-approve"
                //sh label: '', script: "terraform destroy -auto-approve"
            }
        }       
    }
}
