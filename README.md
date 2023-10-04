## start the app

- Change something in the tests. maybe add a variable to AppTest.kt
- run the tests `./gradlew test`

### Results

- 3s to start showing running AppTest.

  ![1.png](docs/1.png)
- another 2s with `executing AppTest`. Which is a very small test.

  ![2.png](docs/2.png)
- In total, there seems to be 14 seconds delay before the first test starts executing.


- Total runtime 17s, which seems quite a lot. For example vitest in JS, can run 30 tests within sub second range.

  ![3.png](docs/3.png)


using intel macbook pro 2019. 16gb ram