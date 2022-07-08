# junit-test-result-accumulator

It is project to enhance Junit testing in gradle.
It listens to tests and store their execution data as Json files.
Then this is used by the plugin which provide task to create HTML report which shows timeline view of tests.
![](../../Desktop/Screenshot 2022-07-08 at 10.55.06 AM.png)
![](../../Desktop/Screenshot 2022-07-08 at 10.54.57 AM.png)
![](../../Desktop/Screenshot 2022-07-08 at 10.59.06 AM.png)
![](../../Desktop/Screenshot 2022-07-08 at 10.59.24 AM.png)

To use the project publish the jar of junit-test-result-accumulator project to your repo(ex-mavenLocal or nexus)
and then add plugin files to project and apply plugin