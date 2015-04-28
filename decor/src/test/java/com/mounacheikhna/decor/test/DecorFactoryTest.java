package com.mounacheikhna.decor.test;

import android.content.Context;
import android.test.mock.MockContext;
import android.util.AttributeSet;
import android.view.View;

import com.mounacheikhna.decor.DecorFactory;
import com.mounacheikhna.decor.Decorator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by cheikhna on 28/04/15.
 */
public class DecorFactoryTest {

    @Mock Decorator decorator;
    Context context;
    View view;
    View parent;
    @Mock AttributeSet attributeSet;

    private DecorFactory decorFactory;
    private Collection<Decorator> decorators;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        context = new MockContext();
        view  = new View(context);
        parent = new View(context);
        decorators = new ArrayList<>();
        decorFactory = new DecorFactory(decorators);
    }

    @Test
    public void addDecoratorDoesApplyIt() throws Exception {
        decorators.add(decorator);

        //TODO: give attributeSet mock whats expected from it
        String name = "android.view.View";
        decorFactory.onViewCreated(view, name, parent, context, attributeSet);
        verify(decorator, times(1)).apply(view, parent, name, context, attributeSet);

    }
}
