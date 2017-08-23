#Домашнее задание L9.1 и L10 (пункты 1 и 2)
##Запуск
Для работы запуска основного кода требуется MySQL база данных. 
Базу с необходимой схемой можно запустить в виде Docker образа. 
Для этого необходим Docker и Docker Compose. 

Для поднятия контейнера необходимо в консоли выполнить комаду

`docker-compose up`

Для завершения работы нужно выполнить:
 
 `docker-compose down`
 
MySQL поднимается на порту 33306 с базой otus и логином/паролем otus/otus.

##Тесты
Тесты используют in-memory БД H2 и не требуют отдельных сервисов.