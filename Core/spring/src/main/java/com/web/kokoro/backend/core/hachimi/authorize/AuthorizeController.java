package com.web.kokoro.backend.core.hachimi.authorize;

import com.web.kokoro.backend.base.Result;
import com.web.kokoro.backend.core.user.RegisterRequest;
import com.web.kokoro.backend.core.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/hachimi/authorize")
public class AuthorizeController {

//    private final UserService userService;
//    AuthorizeController(@Autowired UserService userService){
//        this.userService = userService;
//    }

    @RequestMapping("/hint")
    public Result hint() {
        return Result.success("""
                为了给您提供更优质的服务，耄耋记账将会使用您的个人信息，请在使用前认真阅读《耄耋记账用户协议》与《耄耋记账隐私协议》，点击“同意”按钮，表示您已知情并同意以上协议和以下约定。 如果您是14周岁以下的未成年人，您需要和您的监护人一起仔细阅读《耄耋记账儿童隐私协议》，并在征得您的监护人同意后，使用我们的产品、服务或向我们提供信息。如您代表未成年人做出授权，请您确保您是未成年人的法定监护人。 1. 为了保障软件的安全运行和账户安全，我们会申请收集您的设备信息、IP地址、WLAN MAC地址。 2. 分享、上传记账图片及更换头像，需要使用您的图片存储、相机权限。 3. 产品集成友盟+SDK，友盟+SDK需要收集您的设备Mac地址、唯一设备识别码(IMEI/android ID/IDFA/OPENUDID/GUID、SIM卡 IMSI 信息)以提供统计分析服务，提供基础反作弊能力。 您同意隐私政策仅代表您已了解应用提供的功能，以及功能运行所需的必要个人信息，并不代表您已同意我们可以收集非必要个人信息，非必要个人信息会根据您使用过程中的授权情况单独征求您的同意。 我们尊重您的选择权，您可在《耄耋记账用户协议与隐私政策》中全面了解个人信息收集、使用和共享等情况，我们也为您提供注销、投诉渠道。
                """);
    }
}
