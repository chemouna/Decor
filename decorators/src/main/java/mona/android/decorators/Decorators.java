package mona.android.decorators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mona.android.decor.Decorator;

/**
 * Created by cheikhna on 11/04/2015.
 */

/**
 * A utility class to obtain all decorators defined in this library
 */
public final class Decorators {

    /**
     * Get all the decorators already defined
     * @return available decorators
     */
    public static Decorator[] getAll() {
        return new Decorator[] {
            new AutofitDecorator(),
            new BlurDecorator(),
            new ColorFilterDecorator(),
            new ErrorDecorator(),
            new FontDecorator()
        };
    }

}
