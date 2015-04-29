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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
        view = new View(context);
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
    public void decorNotAplliedOnWidgetOfAnotherType() throws Exception {
        when(textViewDecorator.clazz()).thenReturn(TextView.class);
        decorators.add(textViewDecorator);
        String name = "android.widget.TextView";
        decorFactory.onViewCreated(view, name, parent, context, attributeSet);
        verify(textViewDecorator, never()).apply(any(TextView.class), any(DecorValue.class));
    }

    @Test
    public void decorNotAppliedOnWidgetButWithoutAttr() throws Exception {
        when(textViewDecorator.clazz()).thenReturn(TextView.class);
        decorators.add(textViewDecorator);
        TypedArray typedArray = mock(TypedArray.class);
        when(typedArray.length()).thenReturn(0);// we suppose we dont have custom attr here
        textViewDecorator.mAttributeIndexes = mock(SparseIntArray.class);
        when(textViewDecorator.obtainAttributes(context, attributeSet)).thenReturn(typedArray);
        String name = "android.widget.TextView";
        decorFactory.onViewCreated(textView, name, parent, context, attributeSet);
        //verify(textViewDecorator).apply(textView, parent, name, context, attributeSet);
        verify(textViewDecorator, never()).apply(any(TextView.class), any(DecorValue.class));
        verify(typedArray, times(1)).recycle();
    }

    @Test
    public void decorNotAppliedOnWidgetWithAttrWithoutValue() throws Exception {
        when(textViewDecorator.clazz()).thenReturn(TextView.class);
        decorators.add(textViewDecorator);
        TypedArray typedArray = mock(TypedArray.class);
        when(typedArray.length()).thenReturn(1);
        when(typedArray.hasValue(0)).thenReturn(false);
        textViewDecorator.mAttributeIndexes = mock(SparseIntArray.class);
        when(textViewDecorator.obtainAttributes(context, attributeSet)).thenReturn(typedArray);
        String name = "android.widget.TextView";
        decorFactory.onViewCreated(textView, name, parent, context, attributeSet);
        //verify(textViewDecorator, times(1)).apply(textView, parent, name, context, attributeSet);
        verify(textViewDecorator, never()).apply(any(TextView.class), any(DecorValue.class));
        verify(typedArray, times(1)).recycle();
    }

    @Test
    public void decorAppliedWithAttrValue() throws Exception {
        when(textViewDecorator.clazz()).thenReturn(TextView.class);
        when(textViewDecorator.attrs()).thenReturn(new int[]{1});
        decorators.add(textViewDecorator);
        TypedArray typedArray = mock(TypedArray.class);
        when(typedArray.length()).thenReturn(1);
        when(typedArray.hasValue(0)).thenReturn(true);
        when(typedArray.getValue(eq(0), any(TypedValue.class))).thenReturn(true);

        textViewDecorator.mAttributeIndexes = mock(SparseIntArray.class);
        when(textViewDecorator.obtainAttributes(context, attributeSet)).thenReturn(typedArray);
        String name = "android.widget.TextView";
        decorFactory.onViewCreated(textView, name, parent, context, attributeSet);
        //verify(textViewDecorator).apply(textView, parent, name, context, attributeSet);
        verify(textViewDecorator).apply(any(TextView.class), any(DecorValue.class));
        verify(typedArray, times(1)).recycle();
    }

}