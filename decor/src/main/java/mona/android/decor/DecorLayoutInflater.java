package mona.android.decor;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by cheikhna on 05/04/2015.
 */
public class DecorLayoutInflater extends LayoutInflater implements DecorActivityFactory {
    //TODO: If DecorActivityFactory is not realy needed delete it

    private static final String[] CLASS_PREFIX_LIST = {
            "android.widget.",
            "android.webkit."
    };

    private final DecorFactory mDecorFactory;
    private Collection<Decorator> mDecorators;
    private boolean mSetPrivateFactory = false;
    
    public DecorLayoutInflater(LayoutInflater original, Context newContext, Collection<Decorator> decorators) {
        super(original, newContext);
        mDecorators = decorators;
        mDecorFactory = new DecorFactory(mDecorators);
        initLayoutFactories();
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new DecorLayoutInflater(this, newContext, mDecorators);
    }

    
    private void initLayoutFactories() {
        // If we are HC+ we get and set Factory2 otherwise we just wrap Factory1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (getFactory2() != null && !(getFactory2() instanceof WrapperFactory2)) {
                // Sets both Factory/Factory2
                setFactory2(getFactory2());
            }
        }
        // We can do this as setFactory2 is used for both methods.
        if (getFactory() != null && !(getFactory() instanceof WrapperFactory)) {
            setFactory(getFactory());
        }
    }

    @Override
    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        setPrivateFactoryInternal();
        return super.inflate(resource, root, attachToRoot);
    }

    private void setPrivateFactoryInternal() {
        // Already tried to set the factory.
        if (mSetPrivateFactory) return;
        //if (!hasReflection()) return;
        // Skip if not attached to an activity.
        if (!(getContext() instanceof Factory2)) {
            mSetPrivateFactory = true;
            return;
        }

        final Method setPrivateFactoryMethod = ReflectionUtils
                .getMethod(LayoutInflater.class, "setPrivateFactory");

        if (setPrivateFactoryMethod != null) {
            ReflectionUtils.invokeMethod(this,
                    setPrivateFactoryMethod,
                    new PrivateWrapperFactory2((Factory2) getContext(), this, mDecorFactory));
        }
        mSetPrivateFactory = true;
    }

    //TODO: understand this
    
    @Override
    public void setFactory(Factory factory) {
        // Only set our factory and wrap calls to the Factory trying to be set!
        if (!(factory instanceof WrapperFactory)) {
            super.setFactory(new WrapperFactory(factory, this, mDecorFactory));
        } else {
            super.setFactory(factory);
        }
    }

    //TODO: understand this
    
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setFactory2(Factory2 factory2) {
        // Only set our factory and wrap calls to the Factory2 trying to be set!
        if (!(factory2 instanceof WrapperFactory2)) {
            super.setFactory2(new WrapperFactory2(factory2, mDecorFactory));
        } else {
            super.setFactory2(factory2);
        }
    }

    /**
     * The LayoutInflater onCreateView is the fourth port of call for LayoutInflation.
     * BUT only for none CustomViews.
     */
    
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        return mDecorFactory.onViewCreated(
                super.onCreateView(parent, name, attrs), name, parent, getContext(), attrs);
    }

    /**
     * The LayoutInflater onCreateView is the fourth port of call for LayoutInflation.
     * BUT only for none CustomViews.
     * Basically if this method doesn't inflate the View nothing probably will.
     */
    
    @Override
    protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        // This mimics the {@code PhoneLayoutInflater} in the way it tries to inflate the base
        // classes, if this fails its pretty certain the app will fail at this point.
        View view = null;
        for (String prefix : CLASS_PREFIX_LIST) {
            try {
                view = createView(name, prefix, attrs);
            } catch (ClassNotFoundException ignored) {
            }
        }
        // In this case we want to let the base class take a crack
        // at it.
        if (view == null) view = super.onCreateView(name, attrs);

        return mDecorFactory.onViewCreated(view, name, null, view.getContext(), attrs);
    }

    @Override
    public View onActivityCreateView(View parent, View view, String name, Context context, AttributeSet attrs) {
        return mDecorFactory.onViewCreated(createCustomViewInternal(parent, view, name, context, attrs), context, attrs);
    }


    /**
     * Wrapped factories for Pre and Post HC
     */

    /**
     * Factory 1 is the first port of call for LayoutInflation
     */
    private static class WrapperFactory implements Factory {

        private final Factory mFactory;
        private final DecorLayoutInflater mInflater;
        private final DecorFactory mDecorFactory;

        public WrapperFactory(Factory factory, DecorLayoutInflater inflater, DecorFactory decorFactory) {
            this.mFactory = factory;
            this.mInflater = inflater;
            this.mDecorFactory = decorFactory;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            //TODO: handle this commented case (cf. calligraphy)
            /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                return  mDecorFactory.onViewCreated(
                            mInflater.createCustomViewInternal(
                                null, mFactory.onCreateView(name, context, attrs), name, context, attrs
                        ),
                        context, attrs
                );
            }*/
            return mDecorFactory.onViewCreated(
                        mFactory.onCreateView(name, context, attrs),
                        name, null, context, attrs
            );
        }
    }


    /**
     * Factory 2 is the second port of call for LayoutInflation
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static class WrapperFactory2 implements Factory2 {
        protected final Factory2 mFactory2;
        protected final DecorFactory mDecorFactory;

        public WrapperFactory2(Factory2 mFactory2, DecorFactory mDecorFactory) {
            this.mFactory2 = mFactory2;
            this.mDecorFactory = mDecorFactory;
        }

        
        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return mDecorFactory.onViewCreated(
                    mFactory2.onCreateView(name, context, attrs),
                    name, null, context, attrs);
        }

        
        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            return mDecorFactory.onViewCreated(
                    mFactory2.onCreateView(parent, name, context, attrs),
                    name, parent, context, attrs);
        }
    }

    /**
     * Private factory is step three for Activity Inflation, this is what is attached to the
     * Activity on HC+ devices.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static class PrivateWrapperFactory2 extends WrapperFactory2 {

        private final DecorLayoutInflater mInflater;

        public PrivateWrapperFactory2(Factory2 factory2, DecorLayoutInflater inflater, DecorFactory calligraphyFactory) {
            super(factory2, calligraphyFactory);
            mInflater = inflater;
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            return mDecorFactory.onViewCreated(
                    mInflater.createCustomViewInternal(parent,
                            mFactory2.onCreateView(parent, name, context, attrs),
                            name, context, attrs
                    ),
                    name, parent, context, attrs
            );
        }
    }


    //TODO: understand this and why we need it
    /**
     * Nasty method to inflate custom layouts that haven't been handled else where. If this fails it
     * will fall back through to the PhoneLayoutInflater method of inflating custom views where
     * Calligraphy will NOT have a hook into.
     *
     * @param parent      parent view
     * @param view        view if it has been inflated by this point, if this is not null this method
     *                    just returns this value.
     * @param name        name of the thing to inflate.
     * @param viewContext Context to inflate by if parent is null
     * @param attrs       Attr for this view which we can steal fontPath from too.
     * @return view or the View we inflate in here.
     */
    private View createCustomViewInternal(View parent, View view, String name, Context viewContext, AttributeSet attrs) {
        // I by no means advise anyone to do this normally, but Google have locked down access to
        // the createView() method, so we never get a callback with attributes at the end of the
        // createViewFromTag chain (which would solve all this unnecessary rubbish).
        // We at the very least try to optimise this as much as possible.
        // We only call for customViews (As they are the ones that never go through onCreateView(...)).
        // We also maintain the Field reference and make it accessible which will make a pretty
        // significant difference to performance on Android 4.0+.

        // If CustomViewCreation is off skip this.
        //TODO: have a DecorConfig that activate either all decors or only needed one + possibility deactivate customViewCreate
        //if (!DecorConfig.get().isCustomViewCreation()) return view;

        //TODO: uncomment this implementation
       /* if (view == null && name.indexOf('.') > -1) {
            if (mConstructorArgs == null)
                mConstructorArgs = ReflectionUtils.getField(LayoutInflater.class, "mConstructorArgs");

            final Object[] mConstructorArgsArr = (Object[]) ReflectionUtils.getValue(mConstructorArgs, this);
            final Object lastContext = mConstructorArgsArr[0];
            // The LayoutInflater actually finds out the correct context to use. We just need to set
            // it on the mConstructor for the internal method.
            // Set the constructor ars up for the createView, not sure why we can't pass these in.
            mConstructorArgsArr[0] = viewContext;
            ReflectionUtils.setValue(mConstructorArgs, this, mConstructorArgsArr);
            try {
                view = createView(name, null, attrs);
            } catch (ClassNotFoundException ignored) {
            } finally {
                mConstructorArgsArr[0] = lastContext;
                ReflectionUtils.setValue(mConstructorArgs, this, mConstructorArgsArr);
            }
        }*/
        return view;
    }


}
