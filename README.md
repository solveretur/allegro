# brzezinski_allegro_content
@author: przemyslaw.d.brzezinski@gmail.com
#### Running
In order for app to work properly you need to have mongoDB installed and working
To run the app just type:
```sh
$ gradle bootRun
```
To add video use:
```sh
$ curl -H "Content-Type: application/json" -X POST -d '{"title":"testTitle","dataUrl":"http://test.com/test.mp4","description":"testDescription","author":"testAuthor","category":"SPORT"}' http://localhost:8080/videos
```
To display all videos:
```sh
$ curl http://localhost:8080/videos
```
To display details of a video
```sh
$ curl http://localhost:8080/videos/{id}
```

You can set server port or mongoDB port in application.properties
#### Running tests
```sh
$ gradle test
```
### Technologies used:
* Java 8
* Spring 5
* JUnit 4
* Lombok
* EmbedMongo - for testing
* MongoDB
* Gradle


