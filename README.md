Decor
======

<a href='https://travis-ci.org/chemouna/Decor/builds/'><img src='https://travis-ci.org/chemouna/Decor.svg?branch=master'></a>


*Decor* is a library that applies decorators to android layout with additional attributes
without the need to extend and create a cutom view for each functionnality.

Decor plugs into  Android layout inflation and applies custom attributes to views.
this library was inspired by Pretty https://github.com/madisp/pretty.

If you have written a custom view like AutofitTextViewWithFont : to make a textview resizes it's text and have a specific font
and If you want to  animate it you can write a custom view like AnimatedAutofitTextViewWithFont and if there's another runtime
custom attribute you want to add you will have to yet another custom view.
 Decor comes to the rescue to solve this unnecessary class explosion by using a seperate decorator for each functionality :
    : AutoFitDecorator , FontDecorator, AnimateDecorator (See decorators module for examples of how to create a decorator)
     and register them in attachBaseContext :
     
```java 
@Override
protected void attachBaseContext(Context newBase) {
   super.attachBaseContext(DecorContextWrapper.wrap(newBase)
           .with(Decorators.getAll()));
}
```
```xml
 <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="I'm a TextView"
        app:decorTypefaceAsset="Ubuntu-M.ttf"
        app:decorAutoFit="true"
        app:decorAnimate="true"/>
```

this has the advantage of reusing these decorators in other views.
the module decorators contains some examples of useful decorators that you can start using now,
or write your own by extending ``` AttrsDecorator<T>``` where T is the type of the view the decor will be applied on.

If you want to apply only a subset of decorators :

```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(DecorContextWrapper.wrap(newBase)
            .with(new FontDecorator());
}
```

Check the samples for a working example.

Binaries
========

Binaries and dependency information for Maven, Ivy, Gradle and others can be found at [http://search.maven.org](http://search.maven.org/#search%7Cga%7C1%7Ccom.mounacheikhna).

<a href='http://search.maven.org/#search%7Cga%7C1%7Ccom.mounacheikhna.decor'><img src='http://img.shields.io/maven-central/v/com.mounacheikhna/decor.svg'></a>
<a href='http://search.maven.org/#search%7Cga%7C1%7Ccom.mounacheikhna.decorators'><img src='http://img.shields.io/maven-central/v/com.mounacheikhna/decorators.svg'></a>

for Gradle:
```groovy
compile 'com.mounacheikhna:decor:0.2'
compile 'com.mounacheikhna:decorators:0.2'
```

and for Maven:

```xml
<dependency>
    <groupId>com.mounacheikhna</groupId>
    <artifactId>decor</artifactId>
    <version>0.2</version>
</dependency>
<dependency>
    <groupId>com.mounacheikhna</groupId>
    <artifactId>decorators</artifactId>
    <version>0.2</version>
</dependency>
```

and for Ivy:

```xml
<dependency org="com.mounacheikhna" name="decor" rev="0.2" />
<dependency org="com.mounacheikhna" name="decorators" rev="0.2" />
```

Want to help?
=============

File new issues to discuss specific aspects of the API or the implementation and to propose new
features or add new decorators.


Licence
=======

    Copyright 2015 Mouna Cheikhna

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

