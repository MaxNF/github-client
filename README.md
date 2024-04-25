# github-client

## Before using the app please put your own github personal access token to API_KEY property located in
## common/data/src/main/java/com/maksimbagalei/githubclient/data/network/HeaderInterceptor.kt file.

This app uses architecture recommended by Google:
```Ui layer -> domain layer -> data layer```
MVVM pattern is used for the UI layer.

Ui is written by Jetpack Compose.
All necessary unit tests (except ViewModel tests) are written. ViewModel tests take more time to
write and I didn't have that time.

The build configuration is done using convention plugins (see build-logic module).

Module division is done by feature. Every feature has its own data, domain, and ui submodule.
There is also a common module with several submodules for the code that is shared across the app.

Possible enhancements:

* Write ViewModel and ui tests
* Use MVI instead of MVVM to avoid passing multiple lambdas down the compose tree.