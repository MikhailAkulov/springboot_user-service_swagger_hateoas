## Модуль 6. HTTP, WEB, REST, SOAP

---

### Задача:
Добавление Swagger-документации и HATEOAS в API.

### Требования:
* Задокументировать существующее API (из задания 4) с помощью Swagger (Springdoc OpenAPI), 
чтобы можно было легко изучить и тестировать API через веб-интерфейс. 
* Добавить поддержку HATEOAS, чтобы API предоставляло ссылки для навигации по ресурсам.

---
### Описание:
* Директория с [проектом](https://github.com/MikhailAkulov/springboot_user-service_swagger_hateoas/tree/main/src/main/java/com/akulov/springboot/userservice)
* Прямые ссылки: 
  * [entity](https://github.com/MikhailAkulov/springboot_user-service_swagger_hateoas/blob/main/src/main/java/com/akulov/springboot/userservice/entity/User.java)
  * [dto](https://github.com/MikhailAkulov/springboot_user-service_swagger_hateoas/blob/main/src/main/java/com/akulov/springboot/userservice/dto/UserDto.java) - добавлено описание
  * [mappings](https://github.com/MikhailAkulov/springboot_user-service_swagger_hateoas/blob/main/src/main/java/com/akulov/springboot/userservice/utils/MappingUtils.java)
  * [repository](https://github.com/MikhailAkulov/springboot_user-service_swagger_hateoas/blob/main/src/main/java/com/akulov/springboot/userservice/repository/UserRepository.java)
  * [service](https://github.com/MikhailAkulov/springboot_user-service_swagger_hateoas/blob/main/src/main/java/com/akulov/springboot/userservice/service/UserServiceImpl.java)
  * [controller](https://github.com/MikhailAkulov/springboot_user-service_swagger_hateoas/blob/main/src/main/java/com/akulov/springboot/userservice/controller/UserController.java) - документирование Swagger и поддержка HATEOAS
  * [swagger_config](https://github.com/MikhailAkulov/springboot_user-service_swagger_hateoas/blob/main/src/main/java/com/akulov/springboot/userservice/config/SwaggerConfig.java)
  * [тесты](https://github.com/MikhailAkulov/springboot_user-service_swagger_hateoas/blob/main/src/test/java/com/akulov/springboot/userservice/controller/UserControllerTest.java) - добавлена проверка на наличие ссылок (coverage 100%)
  * [точка входа](https://github.com/MikhailAkulov/springboot_user-service_swagger_hateoas/blob/main/src/main/java/com/akulov/springboot/userservice/UserServiceApplication.java)
  * [properties](https://github.com/MikhailAkulov/springboot_user-service_swagger_hateoas/blob/main/src/main/resources/application.properties)
    * порт стандартный: 8080
    * swagger-ui: /swagger-ui.html
    * api-docs: /api-docs