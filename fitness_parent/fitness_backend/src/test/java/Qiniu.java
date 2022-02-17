import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

public class Qiniu {


    public  void text1() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "Kacegsk_WQNUhU1uKEAMO53DJyTNVkut7PmEXHK8";
        String secretKey = "RwZHAuZaHVce6REPjGYSs0XUrRGllHSILoF3eW_T";
        String bucket = "ddddddddddddddddd";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "D:\\BaiduNetdiskDownload\\资料-传智健康项目\\day4\\资源\\图片资源\\03a36073-a140-4942-9b9b-712cecb144901.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}

