package com.mounacheikhna.decor;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by cheikhna on 30/04/15.
 */
public class AttrsDecoratorTest {

    @Mock Context context;
    View view;
    TextView textView;
    @Mock ViewGroup parent;
    @Mock AttributeSet attributeSet;
    private TestAttrsDecorator attrsDecorator;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        view = new View(context);
        textView = new TextView(context);
        attrsDecorator = new TestAttrsDecorator();
    }

    @Test
    public void decorNotAppliedOnWidgetOfAnotherType() throws Exception {
        String name = "android.widget.ImageView";
        ImageView imageView = new ImageView(context);
        attrsDecorator.apply(imageView, parent, name, context, attributeSet);
        assertThat(attrsDecorator.values).isNull();
    }

    @Test
    public void decorNotAppliedOnWidgetButWithoutAttr() throws Exception {
        String name = "android.widget.TextView";
        Resources resources = mock(Resources.class);
        when(resources.obtainAttributes(attributeSet, attrsDecorator.attrs())).thenReturn(null);
        when(context.getResources()).thenReturn(resources);
        attrsDecorator.apply(textView, parent, name, context, attributeSet);
        assertThat(attrsDecorator.values).isNull();
    }

    @Test
    public void decorNotAppliedOnWidgetWithAttrWithoutValue() throws Exception {
        TypedArray typedArray = spyTypedArray(1, false);// we suppose we dont have custom attr here
        Resources resources = mock(Resources.class);
        when(resources.obtainAttributes(attributeSet, attrsDecorator.attrs())).thenReturn(typedArray);
        when(context.getResources()).thenReturn(resources);
        String name = "android.widget.TextView";
        attrsDecorator.apply(textView, parent, name, context, attributeSet);
        assertThat(attrsDecorator.values).isNotNull();
        assertThat(attrsDecorator.attributeIndexes.size()).isEqualTo(0);
        verify(typedArray).recycle();
    }

    @Test
    public void decorAppliedWithAttrValue() throws Exception {
        TypedArray typedArray = spyTypedArray(1, true);
        Resources resources = mock(Resources.class);
        when(resources.obtainAttributes(attributeSet, attrsDecorator.attrs())).thenReturn(typedArray);
        when(context.getResources()).thenReturn(resources);
        String name = "android.widget.TextView";
        attrsDecorator.apply(textView, parent, name, context, attributeSet);
        assertThat(attrsDecorator.values).isNotNull();
        assertThat(attrsDecorator.attributeIndexes.size()).isEqualTo(1);
        verify(typedArray).recycle();
    }

    private TypedArray spyTypedArray(int numberOfAttrs, boolean valueToReturn) {
        TypedArray typedArray = spy(TypedArray.class);
        doReturn(numberOfAttrs).when(typedArray).length();
        doReturn(valueToReturn).when(typedArray).getValue(0, new TypedValue());
        return typedArray;
    }


}
