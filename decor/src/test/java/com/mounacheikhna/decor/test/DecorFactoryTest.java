package com.mounacheikhna.decor.test;

import android.content.Context;
import android.test.mock.MockContext;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mounacheikhna.decor.AttrsDecorator;
import com.mounacheikhna.decor.DecorFactory;
import com.mounacheikhna.decor.DecorValue;
import com.mounacheikhna.decor.Decorator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by cheikhna on 28/04/15.
 */
public class DecorFactoryTest {

    @Mock Decorator decorator;
    @Mock AttrsDecorator<TextView> textViewDecorator;

    Context context;
    View view;
    TextView textView;
    @Mock ViewGroup parent;
    @Mock AttributeSet attributeSet;

    private DecorFactory decorFactory;
    private Collection<Decorator> decorators;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        context = new MockContext();
        view  = new View(context);
        parent.addView(view); //this is maybe just useless since parent is a mock
        textView = new TextView(context);
        parent.addView(textView); //maybe useless ?

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

    @Test
    public void applyDecoratorIsAplliedOnlyOnCorrectWidget() throws Exception {
        decorators.add(textViewDecorator);
        String name = "android.widget.TextView";
        decorFactory.onViewCreated(view, name, parent, context, attributeSet);
        //verify(textViewDecorator, never()).apply(view, any(DecorValue.class)); //pb here is that apply is
                                                // protected (we can't make it public, it changes a lot)
    }
}
