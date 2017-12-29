package www.diandianxing.com.diandianxing.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author: duke
 * @DateTime: 2017-05-27 16:21
 * @Description: 系统拍照、选择相册、裁剪工具类
 */
public class CropUtils {
    public static final int REQUEST_CODE_CAMERA = 0xF1;
    public static final int REQUEST_CODE_ALBUM = 0xF2;
    public static final int REQUEST_CODE_CROP_OF_BITMAP = 0xF3;
    public static final int REQUEST_CODE_CROP_OF_URI = 0xF4;

    /**
     * 调用系统拍照
     *
     * @param activity 上下文
     * @param saveUri  保存路径
     */
    public static void startCamera(Activity activity, Uri saveUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //这是了此行代码，onActivityResult的intent == null
        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);
        activity.startActivityForResult(intent, CropUtils.REQUEST_CODE_CAMERA);
    }

    /**
     * 调用系统相册
     *
     * @param activity 上下文
     */
    public static void startAlbum(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);//直接进相册
        //先进到文件夹选择界面，再选择相册
        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, CropUtils.REQUEST_CODE_ALBUM);
    }

    public static void startCrop(Activity activity, Intent intent, Uri saveUri, int requestCode) {
        if (intent == null || activity == null) {
            return;
        }
        startCrop(activity, intent.getData(), saveUri, requestCode);
    }

    /**
     * 图片裁剪两个大坑：<br/>
     * 1、intent.setDataAndType(sourceUri, "image/*");参数不要分开写，别问为什么 ，自己试。 <br/>
     * 2、intent.putExtra("return-data", true)时，可以在onActivityResult中获取bitmap。获取方法如下：<br/>
     * //bitmap = intent.getParcelableExtra("data");<br/>
     * //bitmap = intent.getExtras().getParcelable("data");<br/>
     * 直接在intent中通过"data"这个key获取bitmap对象。上面两种方法等效。<br/>
     * 且不需要设置//intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);<br/>
     * 3、intent.putExtra("return-data", false)时，可以在onActivityResult中获取裁剪后保存的uri。获取方法如下：<br/>
     * Uri uri = intent.getData();<br/>
     * 如果此时设置了//intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);<br/>
     * 则intent.getData()、intent.getParcelableExtra("data")、intent.getExtras().getParcelable("data")全部为null<br/>
     * 裁剪的uri保存到saveUri中了。切记这点。<br/>
     *
     * @param activity    上下文对象
     * @param sourceUri   待裁剪的uri
     * @param saveUri     裁剪结果uri
     * @param requestCode 裁剪请求码
     */
    public static void startCrop(Activity activity, Uri sourceUri, Uri saveUri, int requestCode) {
        //saveUri不要加null判断，上面有重载的方法没传递saveUri
        if (sourceUri == null || activity == null) {
            return;
        }
        //是否以bitmap的形式返回
        boolean isBitmap = false;
        if (requestCode == REQUEST_CODE_CROP_OF_BITMAP) {
            isBitmap = true;
        } else if (requestCode == REQUEST_CODE_CROP_OF_URI) {
            isBitmap = false;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        //***此参数,不能分开写***
        intent.setDataAndType(sourceUri, "image/*");
        intent.putExtra("scale", true );//去黑边
        intent.putExtra("crop", false);//是否可以裁剪
        intent.putExtra("circleCrop", "true");//圆形裁剪区域
        intent.putExtra("aspectX", 0);// 设置剪切框1:1比例
        intent.putExtra("aspectY", 0);// 设置剪切框1:1比例
        intent.putExtra("outputX", 220);//输出图片宽
        intent.putExtra("outputY", 180);//输出图片高
        intent.putExtra("noFaceDetection", true); //是否使用人脸识别
        intent.putExtra("return-data", isBitmap);//是否将数据保留在Bitmap中返回
        if (!isBitmap && saveUri != null) {
            //裁剪后图片的保存路径
            //设置了此行代码后intent.getData() == null
            intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(intent, requestCode);
    }

    public static Bitmap onActivityResult(int requestCode, int resultCode, Intent data, Uri cameraUri, Activity activity) {
        return onActivityResult(requestCode, resultCode, data, cameraUri, activity, null);
    }

    /**
     * 裁剪回调
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        结果intent(从相册选择会走这里返回数据)
     * @param cameraUri   拍照结果保存路径(相机拍照的返回设置到这里了)
     * @param activity    上下文
     * @param listener    回调函数
     */
    public static Bitmap onActivityResult(int requestCode, int resultCode, Intent data, Uri cameraUri, Activity activity, OnCropReturnListener listener) {
        if (requestCode != CropUtils.REQUEST_CODE_CAMERA
                && requestCode != CropUtils.REQUEST_CODE_ALBUM
                && requestCode != CropUtils.REQUEST_CODE_CROP_OF_BITMAP
                && requestCode != CropUtils.REQUEST_CODE_CROP_OF_URI) {
            return null;
        }
        if (activity == null) {
            return null;
        }
        //测试了红米4手机，6.0系统，resultCode貌似等于RESULT_CANCELED
        /*if (resultCode != Activity.RESULT_OK) {
            return null;
        }*/
        Bitmap bitmap = null;
        switch (requestCode) {
            case CropUtils.REQUEST_CODE_CAMERA:
                //拍照返回，开始裁剪图片
                if (cameraUri == null) {
                    if (data != null) {
                        //拍照时设置了MediaStore.EXTRA_OUTPUT，则此时data == null
                        cameraUri = data.getData();
                    }
                }
                /**
                 * 裁剪结果继续放到cameraUri中
                 */
                CropUtils.startCrop(activity, cameraUri, cameraUri, CropUtils.REQUEST_CODE_CROP_OF_URI);
                break;
            case CropUtils.REQUEST_CODE_ALBUM:
                //选择相册返回，开始裁剪图片
                /**
                 * 裁剪结果继续放到cameraUri中
                 */
                CropUtils.startCrop(activity, data, cameraUri, CropUtils.REQUEST_CODE_CROP_OF_URI);
                break;
            case CropUtils.REQUEST_CODE_CROP_OF_URI:
            case CropUtils.REQUEST_CODE_CROP_OF_BITMAP:
                //裁剪结果
                bitmap = CropUtils.getCropBitmap(activity, data, cameraUri, requestCode);
                if (listener != null) {
                    listener.onCropReturn(bitmap);
                }
                break;
        }
        return bitmap;
    }

    /**
     * 获取裁剪后的图片
     *
     * @param activity    上下文
     * @param intent      裁剪返回的intent
     * @param requestCode 裁剪请求码
     * @return bitmap
     */
    public static Bitmap getCropBitmap(Activity activity, Intent intent, int requestCode) {
        return getCropBitmap(activity, intent, null, requestCode);
    }

    /**
     * 获取裁剪后的图片
     *
     * @param activity    上下文
     * @param intent      裁剪返回的intent
     * @param saveUri     裁剪返回图片保存路径
     * @param requestCode 裁剪请求码
     * @return bitmap
     */
    public static Bitmap getCropBitmap(Activity activity, Intent intent, Uri saveUri, int requestCode) {
        //saveUri不要加null判断，上面有重载的方法没传递saveUri
        if (activity == null) {
            return null;
        }
        Bitmap bitmap = null;
        if (requestCode == REQUEST_CODE_CROP_OF_BITMAP) {
            //此行代码与intent.getExtras().getParcelable("data");等效
            if (intent == null) {
                return null;
            }
            bitmap = intent.getParcelableExtra("data");
            if (bitmap != null) {
                return bitmap;
            }
            //intent.getExtras().get("data");
            bitmap = intent.getExtras().getParcelable("data");
            if (bitmap != null) {
                return bitmap;
            }
        } else if (requestCode == REQUEST_CODE_CROP_OF_URI) {
            if (saveUri != null) {
                bitmap = uriToBitmap(activity, saveUri);
                if (bitmap != null) {
                    return bitmap;
                }
            }
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);
            //如果裁剪时设置了MediaStore.EXTRA_OUTPUT，则intent.getData() == null
            if (intent == null) {
                return null;
            }
            Uri uri = intent.getData();
            if (uri != null) {
                bitmap = uriToBitmap(activity, uri);
                if (bitmap != null) {
                    return bitmap;
                }
            }
        }
        return bitmap;
    }

    public static Bitmap uriToBitmap(Activity activity, Uri uri) {
        if (activity == null || uri == null) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Drawable bitmap2Drawable(Resources resources, Bitmap bitmap) {
        if (resources == null || bitmap == null) {
            return null;
        }
        return new BitmapDrawable(resources, bitmap);
    }

    public static String getRandomFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE);
        return "IMG_" + dateFormat.format(date) + ".jpg";
    }

    public static String getRandomFileName(String basePath) {
        if (TextUtils.isEmpty(basePath)) {
            return getRandomFileName();
        }
        if (!basePath.endsWith("/")) {
            basePath += "/";
        }
        return basePath + getRandomFileName();
    }

    public interface OnCropReturnListener {
        void onCropReturn(Bitmap bitmap);
    }
}