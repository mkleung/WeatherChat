# CST2335 – Graphical Interface Programming

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/leun0090/AndroidLabs.git
```

### Lab/Branch 1 - Setting up

Install Android studio

### Lab/Branch 2 - Layouts

Notes

**Error importing Snackbar**

add below dependency in your build.gradle
```bash
implementation 'com.android.support:design:27.1.1'
```

Than Build > Clean Project,  then Re-Build Project

**Error: Java “lambda expressions not supported at this language level”**

For Android 3.0+ Go File > Project Structure > Module > app
and In Properties Tab set Source Compatibility and Target Compatibility to 1.8 (Java 8)


### Lab/Branch 3 - Lifecycle/SharedPreferences/Camera/Intents

Notes

**View SharedPreferences file**

On the terminal, enter

```bash
adb shell
run-as com.example.androidlabs (packagename)
cd /data/data/com.example.androidlabs/shared_prefs
more filename.xml
```

Or

Click on Device File Explorer on bottom right and navigate to /data/data/com.example.androidlabs/shared_prefs


### Lab/Branch 4 - ListView / AlertDialog


### Lab/Branch 5 - Database