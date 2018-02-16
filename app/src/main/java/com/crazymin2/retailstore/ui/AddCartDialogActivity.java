package com.crazymin2.retailstore.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crazymin2.retailstore.CommonConstants;
import com.crazymin2.retailstore.R;
import com.crazymin2.retailstore.home.data.Product;
import com.crazymin2.retailstore.util.ImageLoader;

import static com.crazymin2.retailstore.util.LogUtils.LOGD;
import static com.crazymin2.retailstore.util.LogUtils.makeLogTag;

public class AddCartDialogActivity extends BaseActivity implements OnClickListener {

    private static final String TAG = makeLogTag(AddCartDialogActivity.class);

    public static final String SELECTED_PRODUCT_ITEM = "com.crazymin2.retailstore.ui.SELECTED_PRODUCT_ITEM";

    public static final String CART_STATUS = "com.crazymin2.retailstore.ui.CART_STATUS";
    public static final String FAV_STATUS = "com.crazymin2.retailstore.ui.FAV_STATUS";


    private Product selectedItem;
    private Button cartButt, favButt;

    public static final int REQUEST_TO_ADD_IN_CART = 1;
    public static final int REQUEST_TO_ADD_IN_FAV = 2;
    private ImageLoader mImageLoader;

    enum MyCart {
        ADD_TO_CART(
                0) {
            @Override
            public String action() {
                return "Add to cart";
            }
        },
        IN_CART(
                1) {
            @Override
            public String action() {
                return "In Cart";
            }
        },
        UPDATE_CART(
                2) {
            @Override
            public String action() {
                return "Update";
            }
        };
        private int value;

        public abstract String action();

        private MyCart(int value) {
            this.value = value;
        }
    }


    enum MyFav {
        ADD_TO_FAV(
                0) {
            @Override
            public String action() {
                return "Add to Favourite";
            }
        },
        IN_FAV(
                1) {
            @Override
            public String action() {
                return "In Favourite";
            }
        },
        UPDATE_FAV(
                2) {
            @Override
            public String action() {
                return "Update Favourite";
            }
        };
        private int value;

        public abstract String action();

        private MyFav(int value) {
            this.value = value;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);
        mImageLoader = new ImageLoader(this, R.drawable.default_logo);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedItem = extras.getParcelable(SELECTED_PRODUCT_ITEM);
        }
        setTitle("");
        cartButt = (Button) findViewById(R.id.cartButt);
        favButt = (Button) findViewById(R.id.favButt);
        cartButt.setOnClickListener(this);
        favButt.setOnClickListener(this);

        ImageView btnClose = (ImageView) findViewById(R.id.btn_Close);
        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCartDialogActivity.this.finish();
            }
        });

        TextView itemName = (TextView) findViewById(R.id.itemName);
        TextView itemPrice = (TextView) findViewById(R.id.itemPrice);
        if (selectedItem != null) {
            itemName.setText(selectedItem.name);
            String decimalString = CommonConstants.getDecimalString(selectedItem.price);
            itemPrice.setText("Approx. Price: " + decimalString);
            mImageLoader.loadAssetsImage(this, Uri.parse("file:///android_asset/product/" + selectedItem.imageUrlMedium), (ImageView) findViewById(R.id.productImage));
        }

        displayCartButtBehavior();
    }

    private void displayCartButtBehavior() {
        if (selectedItem.isInCart == true && selectedItem.isInFav == true) {

            cartButt.setEnabled(false);
            cartButt.setTag(MyCart.IN_CART);
            cartButt.setText(MyCart.IN_CART.action());

            favButt.setEnabled(false);
            favButt.setTag(MyFav.IN_FAV);
            favButt.setText(MyFav.IN_FAV.action());

        } else if (selectedItem.isInCart) {

            cartButt.setEnabled(false);
            cartButt.setTag(MyCart.IN_CART);
            cartButt.setText(MyCart.IN_CART.action());

            favButt.setEnabled(true);
            favButt.setText(MyFav.ADD_TO_FAV.action());
            favButt.setTag(MyFav.ADD_TO_FAV);

        } else if (selectedItem.isInFav) {

            cartButt.setEnabled(true);
            cartButt.setText(MyCart.ADD_TO_CART.action());
            cartButt.setTag(MyCart.ADD_TO_CART);

            favButt.setEnabled(false);
            favButt.setText(MyFav.IN_FAV.action());
            favButt.setTag(MyFav.IN_FAV);


        } else {

            cartButt.setEnabled(true);
            cartButt.setText(MyCart.ADD_TO_CART.action());
            cartButt.setTag(MyCart.ADD_TO_CART);

            favButt.setEnabled(true);
            favButt.setText(MyFav.ADD_TO_FAV.action());
            favButt.setTag(MyFav.ADD_TO_FAV);

        }
    }

    private void saveProduct() {
        cartPresenter.addItem(selectedItem);
    }

    private void saveFavourite() {
        //System.out.println("AddCartDialogActivity.saveFavourite");
        favPresenter.addItem(selectedItem);
    }

    @Override
    public void onFavActionResponse(boolean isActionSuccessful) {
        super.onFavActionResponse(isActionSuccessful);
        if (isActionSuccessful) {
            Toast.makeText(this, "Item added to favorites", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra(FAV_STATUS, true);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Error while inserting data", Toast.LENGTH_SHORT).show();
            LOGD(TAG, "Item not added into local database");
        }
    }

    @Override
    public void onProductActionResponse(boolean isActionSuccessful) {
        super.onProductActionResponse(isActionSuccessful);
        if (isActionSuccessful) {
            Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra(CART_STATUS, true);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Error while inserting data", Toast.LENGTH_SHORT).show();
            LOGD(TAG, "Item not added into local database");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cartButt:
                MyCart mycart = (MyCart) cartButt.getTag();
                switch (mycart) {
                    case ADD_TO_CART:
                        LOGD(TAG, "Add to cart");
                        saveProduct();
                        break;
                    case IN_CART:
                        LOGD(TAG, "In cart");
                        break;
                    case UPDATE_CART:
                        LOGD(TAG, "Update cart");
                        break;
                }
                break;
            case R.id.favButt:
                MyFav myFav = (MyFav) favButt.getTag();
                //System.out.println("AddCartDialogActivity.onClick "+myFav.toString());
                switch (myFav) {
                    case ADD_TO_FAV:
                        //System.out.println("AddCartDialogActivity.onClick "+myFav.toString());
                        LOGD(TAG, "Add to Favourite");
                        saveFavourite();
                        break;
                    case IN_FAV:
                        LOGD(TAG, "In Favourite");
                        break;
                    case UPDATE_FAV:
                        LOGD(TAG, "Update Favourite");
                        break;
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        cartPresenter.onDestroy();
        favPresenter.onDestroy();
        super.onDestroy();
    }
}
