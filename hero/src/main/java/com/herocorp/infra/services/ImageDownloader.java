package com.herocorp.infra.services;

import android.content.Context;
import android.util.Log;

import com.herocorp.core.constants.URLConstants;
import com.herocorp.core.interfaces.SyncServiceCallBack;
import com.herocorp.core.models.ProductColorModel;
import com.herocorp.core.models.ProductCompareModel;
import com.herocorp.core.models.ProductFeatureModel;
import com.herocorp.core.models.ProductGalleryModel;
import com.herocorp.core.models.ProductModel;
import com.herocorp.core.models.ProductSuperFeatureModel;
import com.herocorp.infra.db.repositories.product.ProductColorModelRepo;
import com.herocorp.infra.db.repositories.product.ProductCompareRepo;
import com.herocorp.infra.db.repositories.product.ProductFeatureRepo;
import com.herocorp.infra.db.repositories.product.ProductGalleryRepo;
import com.herocorp.infra.db.repositories.product.ProductRepo;
import com.herocorp.infra.db.repositories.product.ProductSuperFeatureRepo;
import com.herocorp.infra.db.tables.schemas.ProductTable;
import com.herocorp.infra.netio.ProductIOService;
import com.herocorp.infra.utils.ImageHandler;
import com.herocorp.infra.utils.NetConnections;
import com.herocorp.ui.app.App;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Downloads the Product images from the net to local.
 * Created by JitainSharma on 18/06/16.
 */
public class ImageDownloader {

    private final String TAG = ImageDownloader.class.getSimpleName();
    private WeakReference<Context> ctxWeakReference;
    private ProductRepo productRepo;
    private ProductColorModelRepo colorModelRepo;
    private ProductFeatureRepo featureRepo;
    private ProductSuperFeatureRepo superfeatureRepo;
    private ProductGalleryRepo galleryRepo;
    private ProductCompareRepo compareRepo;

    private ArrayList<String> downloadList = new ArrayList<>(0);
    private boolean isCompleted = false;

    private SyncServiceCallBack syncServiceCallBack;

    private int downloadListSize;

    public ImageDownloader(Context context, SyncServiceCallBack syncServiceCallBack) {

        ctxWeakReference = new WeakReference<>(context);
        this.syncServiceCallBack = syncServiceCallBack;

        productRepo = new ProductRepo(ctxWeakReference.get());
        colorModelRepo = new ProductColorModelRepo(ctxWeakReference.get());
        featureRepo = new ProductFeatureRepo(ctxWeakReference.get());
        superfeatureRepo = new ProductSuperFeatureRepo(ctxWeakReference.get());
        galleryRepo = new ProductGalleryRepo(ctxWeakReference.get());
        compareRepo=new ProductCompareRepo(ctxWeakReference.get());

    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void start() throws Exception {

        //Product Images
        ArrayList<ProductModel> productModels = productRepo.getRecords(null,
                ProductTable.Cols.PRODUCT_ICON + " IS NOT NULL",
                null, null);
        if (productModels != null) {
            for (ProductModel model : productModels) {

                //Check if image is already being downloaded
                if (ImageHandler.getInstance(ctxWeakReference.get()).isFileExists(model.getProductIcon())) {
                    continue;
                }

                downloadList.add(URLConstants.GET_PRODUCT_IMAGE + "#" + model.getProductIcon());
            }
        }

        //Start Feature Image
        ArrayList<ProductFeatureModel> featureModels = featureRepo.getRecords(null,
                null, null, null);
        if (productModels != null) {
            for (ProductFeatureModel featureModel : featureModels) {

                //Check if image is already being downloaded
                if (ImageHandler.getInstance(ctxWeakReference.get()).isFileExists(featureModel.getFeatureImg())) {
                    continue;
                }

                downloadList.add(URLConstants.GET_PRODUCT_IMAGE + "#" + featureModel.getFeatureImg());

            }
        }

        //Start Super Feature Image
        ArrayList<ProductSuperFeatureModel> superfeatureModels = superfeatureRepo.getRecords(null,
                null, null, null);
        if (productModels != null) {
            for (ProductSuperFeatureModel featureModel : superfeatureModels) {

                //Check if image is already being downloaded
                if (ImageHandler.getInstance(ctxWeakReference.get()).isFileExists(featureModel.getFeatureImg())) {
                    continue;
                }
                downloadList.add(URLConstants.GET_PRODUCT_IMAGE + "#" + featureModel.getFeatureImg());
            }
        }

        //Start Product Color Image
        ArrayList<ProductColorModel> colorModels = colorModelRepo.getRecords(null,
                null, null, null);
        if (productModels != null) {
            for (ProductColorModel colorModel : colorModels) {

                //Check if image is already being downloaded
                if (ImageHandler.getInstance(ctxWeakReference.get()).isFileExists(colorModel.getImageName())) {
                    continue;
                }

                downloadList.add(URLConstants.GET_PRODUCT_IMAGE + "#" + colorModel.getImageName());
                downloadList.add(URLConstants.GET_PRODUCT_COLOR_IMAGE + "#" + colorModel.getImgColorIcon());

            }
        }

        //Start Product Gallery Img
        ArrayList<ProductGalleryModel> galleryModels = galleryRepo.getRecords(null,
                null,null,null);
        if (galleryModels != null) {
            for (ProductGalleryModel galleryModel : galleryModels) {

                //Check if image is already being downloaded
                if (ImageHandler.getInstance(ctxWeakReference.get()).isFileExists(galleryModel.getGalleryImg())) {
                    continue;
                }

                downloadList.add(URLConstants.GET_PRODUCT_IMAGE + "#" + galleryModel.getGalleryImg());

            }
        }

        //Start Products Compare Img
        ArrayList<ProductCompareModel> compareModels =compareRepo.getRecords(null,
                null,null,null);
        if (compareModels != null) {
            for (ProductCompareModel compareModel : compareModels) {

                //Check if image is already being downloaded
                if (ImageHandler.getInstance(ctxWeakReference.get()).isFileExists(compareModel.getProductFeatureImages())) {
                    continue;
                }

                downloadList.add(URLConstants.GET_COMPAREPRODUCT_IMAGE + "#" + compareModel.getProductFeatureImages());

            }
        }

        Log.e(TAG, "Image Downloading Started");

        downloadListSize = downloadList.size();
        downloadImage();

    }

    private void downloadImage() {

        if (downloadList.size() <= 0 || !NetConnections.isConnected(ctxWeakReference.get())) {

            Log.e(TAG, "Image Downloading Completed");
            isCompleted = true;
            syncServiceCallBack.completed();

            return;
        }

        //To ensure the initilization
        ImageHandler.getInstance(ctxWeakReference.get());

        String[] splitURL = downloadList.get(0).split("#");
        ProductIOService.fetchImage(TAG, URLConstants.BASE_URL + splitURL[0] + splitURL[1], splitURL[1],
                new SyncServiceCallBack() {
                    @Override
                    public void completed() {
                        check();
                    }

                    @Override
                    public void error() {
                        check();
                    }
                });
    }

    private void check() {

        if (downloadList.size() > 0) {
            downloadList.remove(0);
        }
        App.setProgress(68.0f/downloadListSize);
        downloadImage();
    }
}