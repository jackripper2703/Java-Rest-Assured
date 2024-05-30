timeout(30) {
    node("win") {
        echo "Загрузка проекта!"
        checkout([
            $class: 'GitSCM',
            branches: [[name: 'main']],
            userRemoteConfigs: [[
                credentialsId: '0cd62a59-c1d1-43fd-b534-cf33eb2de9c2',
                url: 'git@github.com:jackripper2703/Java-Rest-Assured.git'
            ]]
        ])

        bat """
            mkdir newfolder
            mvn test
        """

        allure([
            includeProperties: true,
            jdk: '',
            properties: [],
            reportBuildPolicy: 'ALWAYS',
            results: [[path: 'target/allure-results']]
        ])
    }
}
