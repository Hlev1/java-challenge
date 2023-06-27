### Harry Levick

You can also view my solution as a Pull Request [here](https://github.com/Hlev1/java-challenge/pull/1)

```aidl
Layer:  --- APPLICATION ---     --- CORE ---     --- CONTROLLER ---     +++ REQUESTHANDLER +++     --- REPOSITORY ---
                                        - MIDDLEWARE -
```

The application has 5 layers of logic, 4 of which existed in the original application. In my solution, I have added the  
additional `REQUESTHANDLER` layer in an attempt to further modularise the application logic.  

With the `REQUESTHANDLER` layer, I have implemented a version of the chain-of-responsibility design pattern. I feel that this  
helps to keep components slim and focused. Making the application easy to maintain, extend, test, and also comes with the additional   
bonus that we can easily add/remove functionality by adding/removing the 'injected' instance of the handler in the `APPLICATION` layer   
(*of course, we are not doing any injection here - but we can use our imagination 🤠).

#### Solution Disclaimers
Here I give some disclaimers about my solution:
- Firstly, I have refactored the directory structure of the application to make for a less flat structure. This would  
  not be something that I take lightly, and would first check with the rest of the team that this change both:
  - makes sense
  - does not ignore any organisation policies etc.
<table>
<tr>
<th>Before</th>
<th>After</th>
</tr>
<tr>
<td>

```
.
└── src/
    ├── main/java/io/carbonchain/hiring/java/
    │   ├── app/controller/
    │   │   └── ModelsController
    │   ├── domain/
    │   │   ├── Asset
    │   │   ├── AssetRepository
    │   │   ├── Model
    │   │   └── ModelRepository
    │   ├── Application
    │   ├── ApplicationException
    │   ├── Controller
    │   ├── Core
    │   ├── Middleware
    │   └── Request
    └── test/java/io/carbonchain/hiring/java/domain/
        ├── AssetTest
        └── ModelTest
```


</td>
<td>

```
.
└── src/
    └── main/java/io/carbonchain/hiring/java/
        ├── app/
        │   ├── Application
        │   └── Core
        ├── controller/
        │   ├── Controller
        │   └── ModelsController
        ├── domain/
        │   ├── Asset
        │   ├── AssetRepository
        │   ├── Model
        │   └── ModelRepository
        ├── exception/
        │   ├── ApplicationException
        │   └── ...
        ├── handler/
        │   └── ...
        ├── middleware/
        │   ├── Middleware
        │   └── ...
        └── models/
            └── request/
                ├── Request
                └── ...
```

</td>
</tr>
</table>

- I noticed that there are newer available release versions for some dependencies. I bumped those to the latest version.
- In my solution I have moved away from the Reflection approach of calling the controller by looking up the method name.  
  Instead I have gone for a more OOP approach, which in my opinion is easier to read and understand - happy to discuss   
  the pros/cons of this further 🤠


#### Assumptions
Here I state the assumptions that I have made whilst working on my solution:
- Possibly the biggest assumption that we make here is that the user specifies the command line arguments in the pre-specified order (`path endpoint commodity asset`)
    - if the user was to call the application using any other configuration of these 4 parameters, the application will not return the correct response.

  
- We assume that the user knows the correct spelling of their asset/commodity, and that they will capitalize the first character


- The `Middleware` layer is intended to manipulate the base `Request` into a different kind of request (one that extends the base `Request`)
  

- Searching by default using a global scale may not necessarily be what we want further down the line
  - i also refactored this logic out into its separate middleware / handler so that we can have better visibility over this logic.
  

- The `REPOSITORY` layer is an abstraction of an actual database connection.
  - inside of the `ModelRepository` class, I have implemented a `findAllByCommodity()` method, which would probably be a DB query in the real world.  
    And as such we should ensure that the query is optimised, using a DB index on the `commodity` column most likely 🤔.

#### Areas For Improvement
Here I give my opinions on how this application could be further improved:
- I am not completely happy with my current implementation of the search request handlers. The `SmallestScopeSearchRequestHandler`  
  has a dependency on the `GlobalScopeRequestHandler` - and this smells to me. We have this dependency because at the time  
  of calling the `.canHandle()` method of the `SmallestScopeSearchRequestHandler`, we do not know if there is an available scope,
  or if we have to default back to the global scope.  
  An alternative approach could be to remove the `.canHandle()` method of the handler and have the `.handle()` method either  
  return a response or `null`. That way it could be the controllers responsibility to default back to the `GlobalScopeRequestHandler`.


- Instead of passing around strings, we can pass request and response objects.
  - i already did a bit of work on the request side, adding `GlobalScopeSearchRequest`, `SmallestScopeSearchRequest` and `SearchRequest`.
    - in doing so, I refactored the way in which we were invoking the controller to use an OOP approach instead of a Reflection approach.  
      In my opinion, this approach is easier to read and understand - I would be keen to hear thoughts on this 👂🏻
  - i don't like the fact that we are passing a single string around as the response (`X emission intensity for Y is Z`). Instead  
    we could create a response model containing X Y and Z, then only generate the final string at the `APPLICATION` layer.

  
- We could require the user to specify a name for each argument that they pass on the command line.
  - this would both simplify the current middleware layer, in addition to making it more resilient.
  - for example `--path models --endpoint search --commodity Zinc --asset Tara`
  - we could implement this using the [Apache Commons CLI Library](https://commons.apache.org/proper/commons-cli/usage.html)

  
- We could modify the `Asset::nameMatches()` method so that the user is able to search by asset country or continent. Not just name.
  

#### Questions
Here I state some questions that I asked myself whilst working on this:
- Under which circumstances might we want more than 1 middleware to be applied to a request? 🤔


#### Conclusion
Overall, I have really enjoyed getting a small glimpse into the codebase of CarbonChain and learning more about the different
domain entities that exist in the business. I am looking forward to iterating on this project further in the next stages of the interview!