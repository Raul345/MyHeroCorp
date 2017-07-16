package com.herocorp.infra.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.herocorp.core.interfaces.SyncServiceCallBack;
import com.herocorp.core.interfaces.iNetworkResponseCallback;
import com.herocorp.core.models.ProductBreakModel;
import com.herocorp.core.models.ProductCategoryModel;
import com.herocorp.core.models.ProductCompareModel;
import com.herocorp.core.models.ProductModel;
import com.herocorp.core.models.aggregates.ProductDetailAggregate;
import com.herocorp.infra.db.repositories.product.ProductBreakRepo;
import com.herocorp.infra.db.repositories.product.ProductCategoryRepo;
import com.herocorp.infra.db.repositories.product.ProductColorModelRepo;
import com.herocorp.infra.db.repositories.product.ProductCompareRepo;
import com.herocorp.infra.db.repositories.product.ProductDimensionRepo;
import com.herocorp.infra.db.repositories.product.ProductElectricalRepo;
import com.herocorp.infra.db.repositories.product.ProductEngineRepo;
import com.herocorp.infra.db.repositories.product.ProductFeatureRepo;
import com.herocorp.infra.db.repositories.product.ProductGalleryRepo;
import com.herocorp.infra.db.repositories.product.ProductRepo;
import com.herocorp.infra.db.repositories.product.ProductSuperFeatureRepo;
import com.herocorp.infra.db.repositories.product.ProductSuspensionRepo;
import com.herocorp.infra.db.repositories.product.ProductTransmissionRepo;
import com.herocorp.infra.db.repositories.product.ProductTyreRepo;
import com.herocorp.infra.netio.ProductIOService;
import com.herocorp.ui.app.App;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Data sync service class.
 * This service will start fetching data from the server for
 * the different modules, saves in database and callback the status
 * to the registered callbacks.
 * Created by JitainSharma on 12/06/16.
 */
public class DataSyncService extends Service {

    private final String TAG = DataSyncService.class.getSimpleName();
    private static SyncServiceCallBack syncServiceCallBack;

    static boolean isRunning = false;

    private enum syncProductType {
        CATEGORY, PRODUCT, DETAIL, COMPARE, IMAGE, NONE
    }

    private ProductCategoryRepo productCategoryRepo;
    private ProductRepo productRepo;

    private ProductBreakRepo productBreakRepo;
    private ProductElectricalRepo electricalRepo;
    private ProductEngineRepo engineRepo;
    private ProductSuspensionRepo suspensionRepo;
    private ProductDimensionRepo dimensionRepo;
    private ProductColorModelRepo colorModelRepo;
    private ProductFeatureRepo featureRepo;
    private ProductSuperFeatureRepo superfeatureRepo;
    private ProductTransmissionRepo transmissionRepo;
    private ProductTyreRepo tyreRepo;
    private ProductGalleryRepo galleryRepo;
    private ProductCompareRepo compareRepo;
    private static ImageDownloader imageDownloader;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);

        if (!isRunning()) {
            startSyncProcess(syncProductType.CATEGORY);
        }
    }

    public static void registerCallback(SyncServiceCallBack syncServiceCallBack) {
        DataSyncService.syncServiceCallBack = syncServiceCallBack;
    }

    public static boolean isRunning() {
        return isRunning || imageDownloader.isCompleted();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        isRunning = true;
        productCategoryRepo = new ProductCategoryRepo(getApplicationContext());
        productRepo = new ProductRepo(getApplicationContext());

        productBreakRepo = new ProductBreakRepo(getApplicationContext());
        electricalRepo = new ProductElectricalRepo(getApplicationContext());
        engineRepo = new ProductEngineRepo(getApplicationContext());
        suspensionRepo = new ProductSuspensionRepo(getApplicationContext());
        dimensionRepo = new ProductDimensionRepo(getApplicationContext());
        colorModelRepo = new ProductColorModelRepo(getApplicationContext());
        featureRepo = new ProductFeatureRepo(getApplicationContext());
        superfeatureRepo = new ProductSuperFeatureRepo(getApplicationContext());
        transmissionRepo = new ProductTransmissionRepo(getApplicationContext());
        tyreRepo = new ProductTyreRepo(getApplicationContext());
        galleryRepo = new ProductGalleryRepo(getApplicationContext());
        compareRepo = new ProductCompareRepo(getApplicationContext());


        imageDownloader = new ImageDownloader(getApplicationContext(), new CallBack());

        startSyncProcess(syncProductType.CATEGORY);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void startSyncProcess(syncProductType syncProductType) {

        //Start data sync
        try {

            //Sync data for Category, Product, Details
            switch (syncProductType) {

                case CATEGORY:
                    syncCategory();
                    return;

                case PRODUCT:
                    syncProduct(0, productCategoryRepo.getRecords(null, null, null, null));
                    return;

                case DETAIL:
                    syncProductDetails(0, productRepo.getRecords(null, null, null, null));
                    return;
                case COMPARE:
                    syncCompareDetails();
                    return;
                case IMAGE:
                    imageDownloader.start();

                    break;


            }

            isRunning = false;

        } catch (Exception e) {

            isRunning = false;
            Log.e(TAG, "startSyncProcess", e);
            //Notify the Listeners
            notifyCallBack(false);

        }

    }

    private void syncCategory() throws Exception {

        ProductIOService.getProductCategories(TAG, new iNetworkResponseCallback<ProductCategoryModel>() {
            @Override
            public void onSuccess(ArrayList<ProductCategoryModel> data) {

                try {
                    //Insert / Update Category
                    productCategoryRepo.applyBatch(productCategoryRepo.constructOperation(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                App.setProgress(7);
                startSyncProcess(syncProductType.PRODUCT);

            }

            @Override
            public void onSuccess(ProductCategoryModel data) {

            }

            @Override
            public void onFailure(String message, Boolean showToast) {
                startSyncProcess(syncProductType.NONE);
            }
        });

    }


    private void syncProduct(final int index, final ArrayList<ProductCategoryModel> modelArrayList) throws Exception {

        ProductIOService.getProductByCategory(TAG, modelArrayList.get(index).getCategoryID(), new iNetworkResponseCallback<ProductModel>() {
            @Override
            public void onSuccess(ArrayList<ProductModel> data) {

                try {
                    // Insert / Update Product Records
                    productRepo.applyBatch(productRepo.constructOperation(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int i = index + 1;
                if (i < modelArrayList.size()) {
                    try {
                        syncProduct(i, modelArrayList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    App.setProgress(8);
                    startSyncProcess(syncProductType.DETAIL);
                }

            }

            @Override
            public void onSuccess(ProductModel data) {

            }

            @Override
            public void onFailure(String message, Boolean showToast) {
                startSyncProcess(syncProductType.NONE);
            }
        });
    }

    private void syncProductDetails(final int index, final ArrayList<ProductModel> modelArrayList) throws Exception {

        ProductIOService.getProductDetails(TAG, modelArrayList.get(index).getProductID(), new iNetworkResponseCallback<ProductDetailAggregate>() {
            @Override
            public void onSuccess(ArrayList<ProductDetailAggregate> data) {

            }

            @Override
            public void onSuccess(ProductDetailAggregate data) {

                //Save Product Details
                try {
                    // Insert / Update Product Details Records
                    productBreakRepo.applyBatch(productBreakRepo.constructOperation(new ArrayList<>(Arrays.asList(data.getBreakModel()))));
                    electricalRepo.applyBatch(electricalRepo.constructOperation(new ArrayList<>(Arrays.asList(data.getElectricalModel()))));
                    engineRepo.applyBatch(engineRepo.constructOperation(new ArrayList<>(Arrays.asList(data.getEngineModel()))));
                    suspensionRepo.applyBatch(suspensionRepo.constructOperation(new ArrayList<>(Arrays.asList(data.getSuspensionModel()))));
                    dimensionRepo.applyBatch(dimensionRepo.constructOperation(new ArrayList<>(Arrays.asList(data.getDimensionModel()))));
                    colorModelRepo.applyBatch(colorModelRepo.constructOperation(data.getColorModel()));
                    featureRepo.applyBatch(featureRepo.constructOperation(data.getFeatureModel()));
                    transmissionRepo.applyBatch(transmissionRepo.constructOperation(new ArrayList<>(Arrays.asList(data.getTransmissionModel()))));
                    tyreRepo.applyBatch(tyreRepo.constructOperation(new ArrayList<>(Arrays.asList(data.getTyreModel()))));
                    galleryRepo.applyBatch(galleryRepo.constructOperation(data.getGalleryModelList()));
                    superfeatureRepo.applyBatch(superfeatureRepo.constructOperation(data.getSuperfeatureModelList()));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                int i = index + 1;
                if (i < modelArrayList.size()) {

                    try {
                        App.setProgress(15.0f / modelArrayList.size());
                        syncProductDetails(i, modelArrayList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    startSyncProcess(syncProductType.COMPARE);
                   /* startSyncProcess(syncProductType.IMAGE);*/
                }

            }

            @Override
            public void onFailure(String message, Boolean showToast) {
                startSyncProcess(syncProductType.COMPARE);
            }
        });
    }


    private void syncCompareDetails() throws Exception {

        ProductIOService.getCompareDetails(TAG, new iNetworkResponseCallback<ProductCompareModel>() {
            @Override
            public void onSuccess(ArrayList<ProductCompareModel> data) {

                try {
                    //Insert / Update CompareData
                    compareRepo.applyBatch(compareRepo.constructOperation(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // App.setProgress(7);
                startSyncProcess(syncProductType.IMAGE);
              /*  startSyncProcess(syncProductType.COMPARE);*/
            }

            @Override
            public void onSuccess(ProductCompareModel data) {

            }

            @Override
            public void onFailure(String message, Boolean showToast) {
                startSyncProcess(syncProductType.IMAGE);
            }
        });
    }


    private void notifyCallBack(boolean success) {

        if (DataSyncService.syncServiceCallBack == null) {
            return;
        }

        if (success) {
            DataSyncService.syncServiceCallBack.completed();
            return;
        }

        DataSyncService.syncServiceCallBack.error();

    }

    class CallBack implements SyncServiceCallBack {

        @Override
        public void completed() {
            //Notify the Listeners
            notifyCallBack(true);

        }

        @Override
        public void error() {
            //Notify the Listeners
            notifyCallBack(false);
        }
    }
}