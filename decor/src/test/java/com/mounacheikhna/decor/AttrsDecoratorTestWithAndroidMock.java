package com.mounacheikhna.decor;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.test.mock.MockContext;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowResources;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by cheikhna on 03/05/2015.
 */
public class AttrsDecoratorTestWithAndroidMock {

    private Context context;
    private TestAttrsDecorator attrsDecorator;
    TextView textView;
    @Mock AttributeSet attributeSet;
    @Mock ViewGroup parent;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        context = new MockContext();
        textView = new TextView(context);
        attrsDecorator = new TestAttrsDecorator();
    }

    @Test
    public void decorAppliedWithAttrValue() throws Exception {
        TypedArray typedArray = mockTypedArray(1, true);
        //mockResources(typedArray);
        mockTheme(typedArray);
        String name = "android.widget.TextView";
        attrsDecorator.apply(textView, parent, name, context, attributeSet);
        assertThat(attrsDecorator.values).isNotNull();
        assertThat(attrsDecorator.values.length()).isGreaterThan(0);
        assertThat(attrsDecorator.decorValue).isNotNull();
        assertThat(attrsDecorator.attributeIndexes).isNotNull();
        assertThat(attrsDecorator.attributeIndexes.size()).isGreaterThan(0);
        //verify(typedArray).recycle();
    }

    private void mockTheme(TypedArray typedArray) {
        Resources.Theme theme = mock(Resources.Theme.class);
        when(theme.obtainStyledAttributes(attributeSet, attrsDecorator.styleable(), 0, 0)).thenReturn(typedArray);
        //when(context.getTheme()).thenReturn(theme);
        when(context.getTheme().obtainStyledAttributes(attributeSet, attrsDecorator.styleable(), 0, 0)).thenReturn(typedArray);
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
