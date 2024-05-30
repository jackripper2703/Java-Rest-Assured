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

        try {
            bat """
                mkdir newfolder
                mvn test
            """
        } catch (Exception e) {
            echo "Тесты завершились с ошибками, но продолжим выполнение для генерации отчета Allure."
            currentBuild.result = 'UNSTABLE' // Помечаем сборку как нестабильную, если тесты не прошли
        } finally {
            // Всегда генерируем Allure отчет
            allure([
                includeProperties: true,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])
        }
    }
}
