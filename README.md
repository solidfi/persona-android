
# Persona Sample Application (Android)

## Overview
Android application intended to showcase the Persona library integration

 - Persona provides a powerful, secure platform to help organizations collect, verify, store, and analyze the identity of any individual in the world. A frontend, conversion-optimized user interface makes it easy to start verifying identities in minutes.

## Requirements

Our configuration is currently set to the following:
```groovy
  compileSdk 31
  minSdk 21
  targetSdk 31
```

Android Studio - Android Studio Chipmunk | 2021.2.1 Patch 1


## Configure this project

```groovy
git clone git@github.com:solidfi/persona-android.git
# or
git clone https://github.com/solidfi/persona-android.git
```
- Open it in [Android Studio](https://developer.android.com/studio)
- Run the project and test it out.

## Parameters:

- In order to start building and running the Persona sample, you'll need 'enquiry-url' to open in Webview.
- In a real-life integration, the 'enquiry-url' would have to be requested from idv-submit api.
- In order to get the callback response from persona we need use redirection-uri from the inquiry url.
- Once the process is done you can see the status of the verification on the persona dashboard.

#### Reference links :

- [Persona Hosted Flow Integration](https://docs.withpersona.com/docs/quickstart-hosted-flow)
- [Solid Dev Center](https://www.solidfi.com/docs/introduction)
  
