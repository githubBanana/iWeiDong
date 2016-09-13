package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.zf.weisport.R;
import com.zf.weisport.databinding.ActivityPersonBinding;
import com.zf.weisport.manager.util.FileUtils;
import com.zf.weisport.manager.util.GlideUtil;
import com.zf.weisport.ui.activity.base.BaseActivity;
import com.zf.weisport.ui.callback.IPersionCallback;
import com.zf.weisport.ui.viewmodel.PersionViewModel;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.dialog.PhotoDialog;

/**
 * @version V1.0 <个人资料 界面>
 * @author: Xs
 * @date: 2016-09-13 14:03
 * @email Xs.lin@foxmail.com
 */
public class PersionActivity extends BaseActivity<PersionViewModel,ActivityPersonBinding>
        implements IPersionCallback,PhotoDialog.OnListenPhotoDialog {

    public static final int REQUEST_SELECT_PHOTO_CODE = 0x1;
    public static final int REQUEST_CORP_PHOTO_CODE = 0x2;
    private Uri     _outPutUri;
    private boolean isChangedAvator;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, PersionActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected PersionViewModel initViewModel() {
        return new PersionViewModel(this);
    }

    @Override
    protected void toBinding() {
        getBinding().setPersionViewModel(getViewModel());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person, true);
        GlideUtil.showHead(getViewModel().getHeadUrl(), getBinding().persionHeadId);
        getBinding().persionHeadLinearId.setOnClickListener(view ->
                new PhotoDialog(PersionActivity.this).builder().show());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.tv_persondata);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.commit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.commit_menuId) {
            showLoadingView();
            if (isChangedAvator) {// need to upload head file
                getViewModel().commitHeadImg();
            } else {
                getViewModel().commit(null);//commit user info(name/sex,imgId="")
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnListenTouchPhoto() {
        selectImage();
    }

    @Override
    public void OnListenTouchCancle() {

    }

    @Override
    public void OnListenTouchTakePic() {
        selectImage();
    }

    /**
     * 选择图片
     */
    private void selectImage() {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        startActivityForResult(intent, REQUEST_SELECT_PHOTO_CODE);
    }

    /**
     * 裁剪图片
     *
     * @param fileUri
     * @param outPutUri
     */
    private void cropImage(Uri fileUri, Uri outPutUri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fileUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", true);
        intent.putExtra("aspectX", 0.8f);
        intent.putExtra("aspectY", 0.8f);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        startActivityForResult(intent, REQUEST_CORP_PHOTO_CODE);
    }


    /**
     * 头像选择返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("onActivityResult", "resultCode =" + resultCode);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_SELECT_PHOTO_CODE://裁剪界面回调
                ArrayList<String> arrayList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                String path = arrayList.get(0);
                File file = new File(path);
                Uri fileUri = Uri.fromFile(file);
                File tempFile = new File(FileUtils.getCacheDir(this), System.currentTimeMillis() + ".jpg");
                if (tempFile.exists()) {
                    tempFile.delete();
                }
                _outPutUri = Uri.fromFile(tempFile);
                cropImage(fileUri, _outPutUri);
                break;
            case REQUEST_CORP_PHOTO_CODE://完成裁剪回调

                Log.e("REQUEST_CORP_PHOTO_CODE", "data=" + data + "  " + _outPutUri);
                isChangedAvator = true;
                getViewModel().setFileUri(_outPutUri);
                GlideUtil.showHead(_outPutUri,getBinding().persionHeadId);

                break;
        }

    }


    @Override
    public void onCommitSuccess() {
        finish();
    }
}
