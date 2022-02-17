
# Persona Sample Application (Android)

## Overview
Android application intended to showcase the Persona library integration

 - Persona provides a powerful, secure platform to help organizations collect, verify, store, and analyze the identity of any individual in the world. A frontend, conversion-optimized user interface makes it easy to start verifying identities in minutes.

## Requirements

Our configuration is currently set to the following:
```groovy
minSdkVersion = 21
targetSdkVersion = 30
compileSdkVersion 31
```

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
- [Solid API Integration](https://documenter.getpostman.com/view/13543869/TWDfEDwX#ce8c0e57-0dcf-45ea-87d8-6f03a302e027)
  