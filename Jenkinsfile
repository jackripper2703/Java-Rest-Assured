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

        def testResult = bat(returnStatus: true, script: """
            mkdir newfolder
            mvn test
        """)

        // Всегда генерировать отчеты Allure, даже если тесты не прошли
        allure([
            includeProperties: true,
            jdk: '',
            properties: [],
            reportBuildPolicy: 'ALWAYS',
            results: [[path: 'target/allure-results']]
        ])

        // Проверка результата тестов
        if (testResult != 0) {
            echo "Есть ошибки в тестах. Проверьте отчеты для получения подробной информации."
            currentBuild.result = 'UNSTABLE' // Отмечаем сборку как нестабильную, если тесты не прошли
        }
    }
}
