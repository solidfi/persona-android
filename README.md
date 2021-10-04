
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

## Locate your Template ID (other than from API):

- Sign up for a free Persona account which comes with Sandbox access
- Login to the Persona Dashboard and go to the Development section.
- Select the Template you want to use from the drop-down and copy the Template ID for later.

## Required fields:

- Person ID   : You can generate and provide a unique id which we will associate with the inquiry.
                The identifier can be used to monitor user progress in newly created inquiries.
- Template ID : You can find your template ID on the Persona Dashboard under [Development](https://withpersona.com/dashboard/development)
- Environment : The Persona API environment on which to create inquiries. For sandbox and production

#### Reference links :

- [Persona Integration](https://docs.withpersona.com/docs/ios-inquiry-sdk-integration-guide)
- [Solid API Integration](https://documenter.getpostman.com/view/13543869/TWDfEDwX#ce8c0e57-0dcf-45ea-87d8-6f03a302e027)
  