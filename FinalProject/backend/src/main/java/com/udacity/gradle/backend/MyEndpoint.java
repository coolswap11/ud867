/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.udacity.gradle.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.gradle.jokesource.JokeSource;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "tellJoke",
  version = "v2",
  namespace = @ApiNamespace(
    ownerDomain = "backend.gradle.udacity.com",
    ownerName = "backend.gradle.udacity.com",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "tellJoke")
    public MyBean tellJoke() {
        MyBean response = new MyBean();
        JokeSource jokeSource = new JokeSource();
        response.setData(jokeSource.makeMeLaugh());
        return response;
    }

}
