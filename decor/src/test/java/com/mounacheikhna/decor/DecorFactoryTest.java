package com.mounacheikhna.decor;

import android.content.Context;
import android.content.res.TypedArray;
import android.test.mock.MockContext;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by cheikhna on 28/04/15.
 */
public class DecorFactoryTest {

    /**
     * A pb is that i'm kind of testing DecorFactory and AttrsDecorator .. pretty bad
     */

    Context context;
    @Mock View view;
    @Mock TextView textView;
    @Mock Decorator decorator;
    @Mock ViewGroup parent;
    @Mock AttributeSet attributeSet;

    private DecorFactory decorFactory;
    private Collection<Decorator> decorators;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        context = new MockContext();
        //view = new View(context);
        //textView = new TextView(context);
        decorators = new ArrayList<>();
        decorFactory = new DecorFactory(decorators);
    }

    @Test
    public void addDecoratorDoesApplyIt() throws Exception {
        decorators.add(decorator);
        String name = "android.view.View";
        decorFactory.onViewCreated(view, name, parent, context, attributeSet);
        verify(decorator).apply(view, parent, name, context, attributeSet);
    }

    @Test
    public void decorNotAppliedOnWidgetOfAnotherType() throws Exception {
        AttrsDecorator<TextView> textViewDecorator = spyTextViewDecorator();
        decorators.add(textViewDecorator);
        String name = "android.widget.TextView";
        decorFactory.onViewCreated(view, name, parent, context, attributeSet);
        verify(textViewDecorator, never()).apply(any(TextView.class), any(DecorValue.class));
    }

    @Test
    public void decorNotAppliedOnWidgetButWithoutAttr() throws Exception {
        AttrsDecorator<TextView> textViewDecorator = spyTextViewDecorator();
        TypedArray typedArray = mockTypedArray(0, false); // we suppose we dont have custom attr here
        textViewDecorator.mAttributeIndexes = mock(SparseIntArray.class);
        doReturn(typedArray).when(textViewDecorator).obtainAttributes(context, attributeSet);
        decorators.add(textViewDecorator);
        String name = "android.widget.TextView";
        decorFactory.onViewCreated(textView, name, parent, context, attributeSet);
        verify(textViewDecorator).apply(textView, parent, name, context, attributeSet);
        verify(textViewDecorator, never()).apply(any(TextView.class), any(DecorValue.class));
    }

    @Test
    public void decorNotAppliedOnWidgetWithAttrWithoutValue() throws Exception {
        AttrsDecorator<TextView> textViewDecorator = spyTextViewDecorator();
        TypedArray typedArray = mockTypedArray(1, false);// we suppose we dont have custom attr here
        textViewDecorator.mAttributeIndexes = mock(SparseIntArray.class);
        doReturn(typedArray).when(textViewDecorator.obtainAttributes(context, attributeSet));
        decorators.add(textViewDecorator);
        String name = "android.widget.TextView";
        decorFactory.onViewCreated(textView, name, parent, context, attributeSet);
        verify(textViewDecorator).apply(textView, parent, name, context, attributeSet);
        verify(textViewDecorator, never()).apply(any(TextView.class), any(DecorValue.class));
    }

    @Test
    public void decorAppliedWithAttrValue() throws Exception {
        AttrsDecorator<TextView> textViewDecorator = spyTextViewDecorator();
        doReturn(new int[]{1}).when(textViewDecorator).attrs();
        TypedArray typedArray = mockTypedArray(1, true);
        when(typedArray.getValue(eq(0), any(TypedValue.class))).thenReturn(true);
        textViewDecorator.mAttributeIndexes = mock(SparseIntArray.class);
        doReturn(typedArray).when(textViewDecorator).obtainAttributes(context, attributeSet);
        decorators.add(textViewDecorator);
        String name = "android.widget.TextView";
        //assertTrue(textView != null);
        decorFactory.onViewCreated(textView, name, parent, context, attributeSet);
        verify(textViewDecorator).apply(textView, parent, name, context, attributeSet);
        verify(textViewDecorator).apply(any(TextView.class), any(DecorValue.class));
        verify(typedArray).recycle();
    }

    @SuppressWarnings("unchecked")
    private AttrsDecorator<TextView> spyTextViewDecorator() {
        AttrsDecorator<TextView> textViewDecorator = (AttrsDecorator<TextView>) spy(AttrsDecorator.class);
        doReturn(TextView.class).when(textViewDecorator).clazz();
        return textViewDecorator;
    }

    private TypedArray mockTypedArray(int numberOfAttrs, boolean valueToReturn) {
        TypedArray typedArray = mock(TypedArray.class);
        when(typedArray.length()).thenReturn(numberOfAttrs);
        when(typedArray.hasValue(0)).thenReturn(valueToReturn);
        return typedArray;
    }
}
