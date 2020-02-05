# CST2335 – Graphical Interface Programming

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/leun0090/AndroidLabs.git
```


### Lab/Branch 1

The goal of this lab is to ensure that you can install Android Studio, create a sample project, and
commit it to a Git repository. This repository will initially be on your computer, but you are
encouraged to create a Github repository as a backup location.
You will also learn how to use string resources to support multi-lingual applications. This will
show you how the Java files integrate with XML files to build your application


### Lab/Branch 2 

The goal of this lab is become familiar with how XML and Java are combined for creating user
interfaces. The layouts and widgets are created in XML, and Java then gets references to the
objects created in XML to control the behavior of the application


**Error importing Snackbar**

add below dependency in your build.gradle
```bash
implementation 'com.android.support:design:27.1.1'
```

Than Build > Clean Project,  then Re-Build Project

**Error: Java “lambda expressions not supported at this language level”**

For Android 3.0+ Go File > Project Structure > Module > app
and In Properties Tab set Source Compatibility and Target Compatibility to 1.8 (Java 8)