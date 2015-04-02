package mona.android.decor.decorators;

/**
 * Created by cheikhna on 06/03/2015.
 */
public class CircularImageDecorator { /* extends AttrsDecorator<ImageView> {

    @NotNull
    @Override
    protected int[] attrs() {
        return new int[] {R.attr.circular};
    }

    @NotNull
    @Override
    protected Class<ImageView> clazz() {
        return ImageView.class;
    }

    @Override
    protected void apply(ImageView view, int attr, TypedValue value) {

        Bitmap bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(
                                            view.getContext().getResources(), bitmap);
        roundedBitmapDrawable.setCornerRadius(Math.min(roundedBitmapDrawable.getMinimumWidth(),
                                        roundedBitmapDrawable.getMinimumHeight()) / 2.0F);
        view.setImageDrawable(roundedBitmapDrawable);
    }*/

}