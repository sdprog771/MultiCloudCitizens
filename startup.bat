docker network create person-net
docker run -d --name mysql --network person-net --network-alias mysql -v mysql:/var/lib/mysql -e MYSQL_RANDOM_ROOT_PASSWORD=yes -e MYSQL_USER=person -e MYSQL_PASSWORD=person1234 -e MYSQL_DATABASE=citizens mysql:8.3.0
timeout /t 40 >nul
docker run -d --name person --network person-net -p 8080:8080 -e DB_HOST=mysql -e DB_USER=person -e DB_PASSWORD=person1234 -e DB_NAME=citizens person:0.1
