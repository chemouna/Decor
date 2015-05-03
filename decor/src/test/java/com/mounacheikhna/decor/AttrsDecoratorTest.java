package com.mounacheikhna.decor;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.test.mock.MockContext;
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
import org.mockito.Mockito;
import org.mockito.Spy;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowResources;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
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
    @Mock ViewGroup parent;
    @Mock AttributeSet attributeSet;
    Context context;
    TestAttrsDecorator attrsDecorator;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        context = RuntimeEnvironment.application;
        attrsDecorator = spy(new TestAttrsDecorator()); //spying on this object under test is temporary TODO: don't use Spy
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
        spyWithTypedArray(null);
        attrsDecorator.apply(textView, parent, name, context, attributeSet);
        assertThat(attrsDecorator.values).isNull();
    }

    @Test
    public void decorNotAppliedOnWidgetWithAttrWithoutValue() throws Exception {
        TypedArray typedArray = mockTypedArray(1, false);// we suppose we dont have custom attr here
        spyWithTypedArray(typedArray);
        String name = "android.widget.TextView";
        attrsDecorator.apply(textView, parent, name, context, attributeSet);
        assertThat(attrsDecorator.values).isNotNull();
        assertThat(attrsDecorator.attributeIndexes.size()).isEqualTo(0);
        verify(typedArray).recycle();
    }

    @Test
    public void decorAppliedWithAttrValue() throws Exception {
        TypedArray typedArray = mockTypedArray(1, true);
        when(typedArray.getString(0)).thenReturn("test");
        spyWithTypedArray(typedArray);
        String name = "android.widget.TextView";
        attrsDecorator.apply(textView, parent, name, context, attributeSet);
        assertThat(attrsDecorator.values).isNotNull();
        assertThat(attrsDecorator.values.length()).isGreaterThan(0);
        assertThat(attrsDecorator.attributeIndexes.size()).isGreaterThan(0);
        verify(typedArray).recycle();
        assertThat(attrsDecorator.getDecorStrValue()).isEqualTo("test");
    }

    private void spyWithTypedArray(TypedArray typedArray) {
        doReturn(typedArray).when(attrsDecorator).obtainAttributes(context, attributeSet);
    }

    private void mockResources(TypedArray typedArray) {
        Resources resources = mock(Resources.class);
        when(resources.obtainAttributes(attributeSet, attrsDecorator.attrs())).thenReturn(typedArray);
        when(context.getResources()).thenReturn(resources);
    }

    private void mockTheme(TypedArray typedArray) {
        Resources.Theme theme = mock(Resources.Theme.class);
        when(theme.obtainStyledAttributes(attributeSet, attrsDecorator.styleable(), 0, 0)).thenReturn(typedArray);
        when(context.getTheme()).thenReturn(theme);
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
