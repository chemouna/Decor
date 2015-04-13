Decor
======

Decor is a library that applies decorators to android layout with additional attributes
without the need to extend and create a cutom view for each functionnality.

Decor plugs into  Android layout inflation and applies custom attributes to views.
this library was inspired by Pretty https://github.com/madisp/pretty.

have you ever written AutofitTextViewWithFont to make a textview resizes it's text to be
 no larger than the width of the view and to apply a font to it and for example if we want to
 animate it we can create another custom view AnimatedAutofitTextViewWithFont,
 Decor comes to the rescue to solve this unnecessary class explosion:
    create AutoFitDecorator , FontDecorator, AnimateDecorator (See module decorators)
     and register them by overriding
        @Override
        protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(DecorContextWrapper.wrap(newBase)
                    .with(Decorators.getAll()));
        }
        in activity class and in your layout

         <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="I'm a TextView"
                app:decorTypefaceAsset="Ubuntu-M.ttf"
                app:decorAutoFit="true"
                app:decorAnimate="true"/>

 this has the advantage of reusing these decorators in other views.

the module decorators contains some examples of useful decorators that you can start using now,
 more of them will be added soon.

If you want to not apply all decorators :

            @Override
            protected void attachBaseContext(Context newBase) {
                super.attachBaseContext(DecorContextWrapper.wrap(newBase)
                        .with(new FontDecorator());
            }



