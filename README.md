Decor
======

*Decor* is a library that applies decorators to android layout with additional attributes
without the need to extend and create a cutom view for each functionnality.

Decor plugs into  Android layout inflation and applies custom attributes to views.
this library was inspired by Pretty https://github.com/madisp/pretty.

have you ever written AutofitTextViewWithFont to make a textview resizes it's text to be
 no larger than the width of the view and to apply a font to it and for example if we want to
 animate it we can create another custom view AnimatedAutofitTextViewWithFont,
 Decor comes to the rescue to solve this unnecessary class explosion:
    create AutoFitDecorator , FontDecorator, AnimateDecorator (See module decorators)
     and register them by overriding attachBaseContext in activity class and in your layout :
     
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
 more of them will be added soon.

If you want to not apply all decorators :

```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(DecorContextWrapper.wrap(newBase)
            .with(new FontDecorator());
}
```

Snapshot
========

Decor is still under heavy development but you can try it now via Maven Central snapshots.

Gradle:
```groovy
repositories {
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
}

dependencies {
    compile 'com.mounacheikhna:decor:0.1-SNAPSHOT@aar'
    compile 'com.mounacheikhna:decorators:0.1-SNAPSHOT@aar'
}
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

