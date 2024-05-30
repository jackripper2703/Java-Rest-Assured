timeout(30) {
    node("win") {
        echo "Загрузка проекта!"
        checkout scm: [
                $class: 'GitSCM',
                branches: [[name: 'main']],
                userRemoteConfigs: [[
                    credentialsId: '0cd62a59-c1d1-43fd-b534-cf33eb2de9c2',
                    url: 'git@github.com:jackripper2703/Java-Rest-Assured.git'
                ]]
            ]
        laballedShell(label: 'Run Test' , script: '''
        mkdir newfolder
        chmod +x pom.xml
        ./mvn test || echo
        ''')
        allure([
                includeProperties: true,
                jdk : '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results : [[path: 'target/allure-results']]



        ])
    }
}