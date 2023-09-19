# SOFTENG 206 - EscAIpe Room

## To setup OpenAI's API

- add in the root of the project (i.e., the same level where `pom.xml` is located) a file named `apiproxy.config`
- put inside the credentials that you received from no-reply@digitaledu.ac.nz (put the quotes "")

  ```
  email: "upi123@aucklanduni.ac.nz"
  apiKey: "YOUR_KEY"
  ```
  these are your credentials to invoke the OpenAI GPT APIs

## To setup codestyle's API

- add in the root of the project (i.e., the same level where `pom.xml` is located) a file named `codestyle.config`
- put inside the credentials that you received from gradestyle@digitaledu.ac.nz (put the quotes "")

  ```
  email: "upi123@aucklanduni.ac.nz"
  accessToken: "YOUR_KEY"
  ```

 these are your credentials to invoke GradeStyle

## To run the game

`./mvnw clean javafx:run`

## To debug the game

`./mvnw clean javafx:run@debug` then in VS Code "Run & Debug", then run "Debug JavaFX"

## To run codestyle

`./mvnw clean compile exec:java@style`

## Reference

Earth animation
https://www.facebook.com/watch/?v=10155379945966772

Video editor:
https://www.veed.io/edit/6732df0c-a77f-481a-b16c-b10d0ea310e1/video

Music:
chill. by sakura Hz https://soundcloud.com/sakurahertz
Creative Commons — Attribution 3.0 Unported  — CC BY 3.0 
Free Download / Stream: http://bit.ly/chill-sakuraHz
Music promoted by Audio Library • Chill – sakura Hz (No Copyright Music)  