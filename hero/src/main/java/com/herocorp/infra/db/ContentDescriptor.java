package com.herocorp.infra.db;

import android.content.UriMatcher;
import android.net.Uri;

import com.herocorp.infra.db.tables.schemas.FAQTable;
import com.herocorp.infra.db.tables.schemas.NewsTable;
import com.herocorp.infra.db.tables.schemas.ProductAttachmentTable;
import com.herocorp.infra.db.tables.schemas.ProductCategoryTable;
import com.herocorp.infra.db.tables.schemas.ProductDetailTable;
import com.herocorp.infra.db.tables.schemas.ProductTable;
import com.herocorp.infra.db.tables.schemas.ReportIssueTable;
import com.herocorp.infra.db.tables.schemas.UserDetailTable;
import com.herocorp.infra.db.tables.schemas.UserTable;
import com.herocorp.infra.db.tables.schemas.ValueAddedServicesTable;
import com.herocorp.infra.db.tables.schemas.products.ProductBreakTable;
import com.herocorp.infra.db.tables.schemas.products.ProductColorModelTable;
import com.herocorp.infra.db.tables.schemas.products.ProductCompareTable;
import com.herocorp.infra.db.tables.schemas.products.ProductDimensionTable;
import com.herocorp.infra.db.tables.schemas.products.ProductElectricalTable;
import com.herocorp.infra.db.tables.schemas.products.ProductEngineTable;
import com.herocorp.infra.db.tables.schemas.products.ProductFeatureTable;
import com.herocorp.infra.db.tables.schemas.products.ProductGalleryTable;
import com.herocorp.infra.db.tables.schemas.products.ProductRotationTable;
import com.herocorp.infra.db.tables.schemas.products.ProductSuperFeatureTable;
import com.herocorp.infra.db.tables.schemas.products.ProductSuspensionTable;
import com.herocorp.infra.db.tables.schemas.products.ProductTransmissionTable;
import com.herocorp.infra.db.tables.schemas.products.ProductTyreTable;

/**
 * Defines the Authority, Base URI and validates the other URI.
 * @author JitainSharma
 */
public class ContentDescriptor {

    public static final String AUTHORITY = "com.herocorp.provider";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        //Add the URIs
        matcher.addURI(AUTHORITY, UserTable.PATH, UserTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, UserDetailTable.PATH, UserDetailTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, FAQTable.PATH, FAQTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, NewsTable.PATH, NewsTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductAttachmentTable.PATH, ProductAttachmentTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductCategoryTable.PATH, ProductCategoryTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductDetailTable.PATH, ProductDetailTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductTable.PATH, ProductTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ReportIssueTable.PATH, ReportIssueTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ValueAddedServicesTable.PATH, ValueAddedServicesTable.PATH_TOKEN);

        //PRODUCT SUB TABLES
        matcher.addURI(AUTHORITY, ProductColorModelTable.PATH, ProductColorModelTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductFeatureTable.PATH, ProductFeatureTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductSuperFeatureTable.PATH, ProductSuperFeatureTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductEngineTable.PATH, ProductEngineTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductTransmissionTable.PATH, ProductTransmissionTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductSuspensionTable.PATH, ProductSuspensionTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductBreakTable.PATH, ProductBreakTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductTyreTable.PATH, ProductTyreTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductElectricalTable.PATH, ProductElectricalTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductDimensionTable.PATH, ProductDimensionTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductGalleryTable.PATH, ProductGalleryTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductRotationTable.PATH, ProductRotationTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProductCompareTable.PATH, ProductCompareTable.PATH_TOKEN);
        return matcher;
    }
}