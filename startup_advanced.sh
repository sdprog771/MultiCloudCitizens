docker network create person-net
docker run -d --name mysql --network person-net --network-alias mysql -v mysql:/var/lib/mysql -e MYSQL_RANDOM_ROOT_PASSWORD=yes -e MYSQL_DATABASE=citizens -e MYSQL_USER=person -e MYSQL_PASSWORD=person1234 mysql:8.3.0

while ! docker exec -it mysql mysql -uperson -pperson1234 -e "SELECT 1" >/dev/null 2>&1; do
    sleep 1
done

docker run -d --name person --network book-net -p 8080:8080 -e DB_HOST=mysql -e DB_USER=person -e DB_PASSWORD=person1234 -e DB_NAME=citizens person:0.1
