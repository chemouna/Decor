package mona.android.decor.decorators;

/**
 * Created by cheikhna on 02/04/2015.
 */
public class LayoutManagerDecorator {/*extends AttrsDecorator<RecyclerView> {
    @NotNull
    @Override
    protected int[] attrs() {
        return new int[] {R.attr.layoutManager, R.attr.layoutManagerOrientation*//*,
                            R.attr.animator*//*};
    }

    @NotNull
    @Override
    protected Class<RecyclerView> clazz() {
        return RecyclerView.class;
    }

    //TODO: for this to work we need to have support for TypedArray instead of TypedValue
    @Override
    protected void apply(RecyclerView view, int attr, TypedValue value) {
        RecyclerView.LayoutManager layoutManager;
        try {
            //problem : not sure that we can always instantiate layoutmanager without params
            layoutManager = (RecyclerView.LayoutManager) Class.forName(value.string.toString()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not create an instance of value.string, Make sure "+ value.string +
                                                " exist and has public access ", e);
        }
    }

    *//**
     * may change name to RecyclerViewDecorator if it can have multiple rv decors +
     * adding an item animator
     *//*
*/

}
