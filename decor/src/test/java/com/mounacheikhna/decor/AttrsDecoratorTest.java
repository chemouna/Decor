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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by cheikhna on 30/04/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AttrsDecoratorTest {

    @Mock View view;
    @Mock TextView textView;
    @Mock Context context;
    @Mock ViewGroup parent;
    @Mock AttributeSet attributeSet;
    private TestAttrsDecorator attrsDecorator;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        attrsDecorator = new TestAttrsDecorator();
    }

    @Test
    public void decorNotAppliedOnWidgetOfAnotherType() throws Exception {
        String name = "android.widget.ImageView";
        ImageView imageView = mock(ImageView.class);
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
        TypedArray typedArray = mockTypedArray(1, false);// we suppose we dont have custom attr here
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
        TypedArray typedArray = mockTypedArray(1, true);
        Resources resources = mock(Resources.class);
        when(resources.obtainAttributes(attributeSet, attrsDecorator.attrs()))
                                .thenReturn(typedArray);
        when(context.getResources()).thenReturn(resources);
        String name = "android.widget.TextView";
        attrsDecorator.apply(textView, parent, name, context, attributeSet);
        assertThat(attrsDecorator.values).isNotNull();
        assertThat(attrsDecorator.decorValue).isNotNull();
        assertThat(attrsDecorator.attributeIndexes).isNotNull();
        assertThat(attrsDecorator.attributeIndexes.size()).isGreaterThan(0);
        verify(typedArray).recycle();
    }

    private TypedArray mockTypedArray(int length, boolean valueToReturn) {
        TypedArray typedArray = mock(TypedArray.class);
        when(typedArray.length()).thenReturn(length);
        when(typedArray.hasValue(0)).thenReturn(valueToReturn);
        when(typedArray.getValue(eq(0), any(TypedValue.class))).thenReturn(valueToReturn);
        doNothing().when(typedArray).recycle();
        return typedArray;
    }

}
