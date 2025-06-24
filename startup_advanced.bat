docker network create person-net
docker run -d --name mysql --network person-net --network-alias mysql -v mysql:/var/lib/mysql -e MYSQL_RANDOM_ROOT_PASSWORD=yes -e MYSQL_USER=person -e MYSQL_PASSWORD=person1234 -e MYSQL_DATABASE=citizens mysql:8.3.0

@echo off
:retry
echo Waiting for MySQL to become available...
timeout /t 1 >nul
docker exec -it mysql mysql -u person -p person1234 -e "SELECT 1" >nul 2>&1
if %errorlevel% neq 0 (
    goto retry
)
echo MySQL is up!

docker run -d --name person --network person-net -p 8080:8080 -e DB_HOST=citizens -e DB_USER=person -e DB_PASSWORD=person1234 -e DB_NAME=citizens person:0.1
