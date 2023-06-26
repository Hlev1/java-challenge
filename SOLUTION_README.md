### Harry Levick
```aidl
Layer:  --- APPLICATION ---     --- CORE ---     --- CONTROLLER ---     +++ REQUESTHANDLER +++     --- REPOSITORY ---
                                        - MIDDLEWARE -
```

The application has 5 layers of logic, 4 of which existed in the original application. In my solution, I have added the  
additional `REQUESTHANDLER` layer in an attempt to further modularise the application logic.  

With the `REQUESTHANDLER` layer, I have implemented a version of the chain-of-responsibility design pattern. I like  
to work with this design pattern because it helps to keep components slim and focused. This makes the application easy  
to maintain and extend, easier to test, and also comes with the additional bonus that we can easily add/remove functionality  
by adding/removing the 'injected' instance of the handler in the `APPLICATION` layer (*of course, we are not doing any  
injection here - but we can use our imagination 🤠).

#### Assumptions
Here I state the assumptions that I have made whilst working on my solution:
- Possibly the biggest assumption that we make here is that the user specifies the command line arguments in the pre-specified order (`path endpoint commodity asset`)
    - if the user was to call the application using any other configuration of these 4 parameters, the application will not return the correct response.

  
- We assume that the user knows the correct spelling of their asset/commodity, and that they will capitalize the first character


- We have a cunning plan for how we wish to use the `Middleware` pattern going forward 🕵🏼
  - if using the `Middleware` pattern was not originally hinted at, I probably wouldn't have gone down that path.
  

- The `Middleware` is intended to manipulate the base `Request` into a different kind of request (one that extends the base `Request`)
  

- Searching by default using a global scale may not necessarily be what we want further down the line
  - i also refactored this logic out into its separate middleware / handler so that we can have better visibility over this logic.
  

- The `REPOSITORY` layer is an abstraction of an actual database connection.
  - inside of the `ModelRepository` class, I have implemented a `findAllByCommodity()` method, which would probably be a DB query in the real world.

#### Areas For Improvement
Here I give my opinions on how this application could be further improved:
- Instead of passing around strings, we can pass request and response objects.
  - i already did a bit of work on the request side, adding `GlobalScopeSearchRequest`, `SmallestScopeSearchRequest` and `SearchRequest`.
    - in doing so, I refactored the way in which we were invoking the controller. I wasn't a big fan of searching for a  
      method signature using the `endpoint` string variable - I would be keen to hear thoughts on this 👂🏻
  - i don't like the fact that we are passing a single string around as the response (`X emission intensity for Y is Z`). Instead  
    we could create a response model containing X Y and Z, then only generate the final string at the `APPLICATION` layer.

  
- We could require the user to specify a name for each argument that they pass on the command line
  - for example `--path models --endpoint search --commodity Zinc --asset Tara`

  
- We could modify the `Asset::nameMatches()` method so that the user is able to search by asset country or continent. Not just name.
  

#### Questions
Here I state some questions that I asked myself whilst working on this:
- How do we intend to use the `Middleware` implementations during further development?
  - without more context, I cannot see the use for the `Middleware` pattern here.
  - when might we want more than 1 middleware to be applied to a request? 🤔