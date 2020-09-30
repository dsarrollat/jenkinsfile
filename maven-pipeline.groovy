pipeline{
    agent any
    tools{
       maven "Default_mvn"
    }
    stages{
        stage('checkout'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '', url: 'https://github.com/dsarrollat/maven-project.git']]]) 
            }
        }
        stage('build'){
            steps{
                sh 'mvn clean package'
            }
            post{
                success{
                    echo 'Archive de los artifactos...'
                    archiveArtifacts artifacts: '**/*.war' 
                }
            }
        }
        
        stage('deploy-to-PRE'){
            steps{
                build job:'deploy-PRE'   
            }
        }
    }
}
