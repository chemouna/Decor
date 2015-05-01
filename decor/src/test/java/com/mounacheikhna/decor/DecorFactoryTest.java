package com.mounacheikhna.decor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by cheikhna on 28/04/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DecorFactoryTest {

    @Mock Context context;
    @Mock View view;
    @Mock Decorator decorator;
    @Mock ViewGroup parent;
    @Mock AttributeSet attributeSet;

    private DecorFactory decorFactory;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        Collection<Decorator> decorators = new ArrayList<>();
        decorators.add(decorator);
        decorFactory = new DecorFactory(decorators);
    }

    @Test
    public void addDecoratorDoesApplyIt() throws Exception {
        String name = "android.view.View";
        decorFactory.onViewCreated(view, name, parent, context, attributeSet);
        verify(decorator).apply(view, parent, name, context, attributeSet);
    }

    @Test
    public void NoDecoratorAppliedToNullView() throws Exception {
        String name = "android.view.View";
        decorFactory.onViewCreated(null, name, parent, context, attributeSet);
        verify(decorator, never()).apply(null, parent, name, context, attributeSet);
    }

}
