pretty
======

Pretty is a library that enables one to enhance Android layout inflation without
boilerplate and knowledge about *LayoutInflater* internals. Pretty hooks into
the Android layout inflation so you could add new attributes to existing View
subtypes.

Example
-------

Lets take a standard example - using a custom font with TextViews. If you have
used a custom font chances are that you have something like this in your
codebase:

```java
TextView text1 = (TextView) findViewById(R.id.text1);
Typeface tf = Typeface.createFromAsset(getResources().getAssets(), "my-font.ttf");
text1.setTypeface(tf);
```

This approach doesn't really scale that well once you have a bunch of complex
layouts. This usually results in either creating a `TextView` subclass or some
trickery with walking the view tree and replacing the typeface for all
`TextView` instances.

Also one shouldn't mix presentation definition (the font)
with logic (the Java code) if at all possible. Wouldn't it be nice if you could
just point to your custom font right there in the layout? E.g.:

```xml
<TextView android:id="@+id/text1"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:typeface_asset="my-font.ttf" />
```

With some `LayoutInflater` trickery it is actually possible. Pretty does all the
trickery for you so you can focus on the code that matters. Lets write a pretty
decorator that enables us to use a custom attribute to load the typeface.

First off we need to define our custom attribute in `res/values/attrs.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
  <attr name="typeface_asset" format="string" />
</resources>
```

After having a custom attribute we can reference it in java code through the
`R.attr` class and in our layouts using the `res-auto` package namespace. Now we
can write a decorator that uses the attribute to change the typeface for any
TextView instances.

We could write a general `Decor` implementation but there is
a base class for writing decorators that work explicitly with attributes -
`AttrsDecor<T extends View>`. Lets use it to implement our `typeface_asset`
attribute:

```java
public class FontDecor extends AttrsDecor<TextView> {
	@Override
	protected int[] attrs() {
    // return the attrs this decorator applies to
		return new int[] { R.attr.typeface_asset };
	}

	@Override
	protected Class<TextView> clazz() {
    // we want to modify all the TextView instances. This includes descendants,
    // like Button or EditText.
		return TextView.class;
	}

	@Override
	protected void apply(TextView view, int attr, TypedValue value) {
    // this method is called if we have a TextView with any of the attributes
    // we previously declared. Usually one would have a switch here but as this
    // decorator only supports a single attr we don't need to do that as we know
    // it can only be R.attr.typeface_asset.
    // So lets just replace the typeface here.
		view.setTypeface(Typeface.createFromAsset(view.getResources().getAssets(), value.string.toString()));
	}
}
```

Now we can use our attribute in a layout:

```xml
<LinearLayout
  xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:auto="http://schemas.android.com/apk/res-auto"
  android:orientation="vertical"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  android:padding="16dp">
  <TextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Woop, custom fonts!"
    auto:typeface_asset="my-font.ttf" />
</LinearLayout>
```

Pretty straightforward, right? Now all we need to do is to attach pretty to an
activity so it can take care of all the `LayoutInflater` magic. We also need to
register our new shiny decorator.

```java
public class SampleActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Pretty.wrap(this).with(new FontDecor());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
}
```

You can try out this code by checking out the repository and looking at the
`samples` project.

How pretty works
----------------

Pretty does its magic by replacing the view `Factory` in the `LayoutInflater`.
This idea was borrowed from the android support library - thats how the support
library enables one to use the `<fragment>` tags in OS versions that do not know
about the tag.

Limitations
-----------

Currently using attributes with the auto prefix for system widgets raises a lint
error *Unexpected namespace prefix "auto" found for tag T*. Fear not, this is a
red herring. The solution is to suppress that check in your gradle buildfile:

```groovy
android {
  lintOptions {
    disable 'MissingPrefix'
  }
}
```

License
-------

Standard MIT. See the LICENSE file.
