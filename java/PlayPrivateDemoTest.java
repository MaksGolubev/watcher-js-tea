package io.folio.web.demos;

import io.folio.api.config.UserProfile;
import io.folio.api.response.mobile.bundle.BundleDataModel;
import io.folio.base.PrivateDemoTestBase;
import io.folio.model.data.demo.DemoData;
import io.folio.model.domain.UserModel;
import io.folio.test.annotation.FolioTest;
import io.folio.test.extension.LoggerExtension;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Locale;

@ExtendWith(LoggerExtension.class)
public class PlayPrivateDemoTest extends PrivateDemoTestBase {
    UserProfile userProfile;
    BundleDataModel bundleData;

    @TmsLinks({
            @TmsLink("FB-2591")
    })
    @FolioTest(pojoClass = DemoData.class, fileNames = {"fb2591"})
    public void playDemoTest(DemoData demoData) {
        userProfile = new UserProfile().withName(demoData.getBasicInformationFormData().getDemoName())
                .withLocale(Locale.ENGLISH);
        userProfile = PDEMO.registerOnDevice(userProfile, demoData);
        bundleData = PDEMO.getBundleData();
        demoData.getBasicInformationFormData().setYourEmail(userProfile.getEmail());
        PDEMO.create(demoData).play(0);
        PDEMO.registrationStep(userProfile, bundleData, demoData)
                .verificationStep(userProfile, bundleData, demoData)
                .secureLoginStep(userProfile, bundleData, demoData)
                .digitalIdentityStep(bundleData);
    }

    @TmsLinks({
            @TmsLink("FB-3186"),
            @TmsLink("FB-3187"),
            @TmsLink("FB-3195")
    })
    @FolioTest(pojoClass = DemoData.class, fileNames = {
            "fb3186",
            "fb3187",
            "fb3195"
    })
    public void playDemoWithEmailTest(DemoData demoData) {
        userProfile = new UserProfile().withName(demoData.getBasicInformationFormData().getDemoName())
                .withLocale(Locale.ENGLISH);
        userProfile = PDEMO.registerOnDevice(userProfile, demoData);
        bundleData = PDEMO.getBundleData();
        demoData.getBasicInformationFormData().setYourEmail(userProfile.getEmail());
        PDEMO.create(demoData).play(0);
        PDEMO.registrationStep(userProfile, bundleData, demoData)
                .verificationStep(userProfile, bundleData, demoData)
                .secureLoginStep(userProfile, bundleData, demoData)
                .digitalIdentityStep(bundleData);
    }

    @TmsLinks({
            @TmsLink("FB-3186")
    })
    @FolioTest(pojoClass = DemoData.class, fileNames = {"fb3186"})
    public void playDemoWithoutEmailTest(DemoData demoData) {
        userProfile = new UserProfile().withName(demoData.getBasicInformationFormData().getDemoName())
                .withLocale(Locale.ENGLISH);
        userProfile = PDEMO.registerOnDevice(userProfile, demoData);
        bundleData = PDEMO.getBundleData();
        demoData.getBasicInformationFormData().setYourEmail(userProfile.getEmail());
        PDEMO.create(demoData).play(0);
        PDEMO.registrationStep(userProfile, bundleData, demoData)
                .verificationStep(userProfile, bundleData, demoData)
                .secureLoginStep(userProfile, bundleData, demoData)
                .digitalIdentityStep(bundleData);
    }

    @TmsLinks({
            @TmsLink("FB-3224")
    })
    @FolioTest(pojoClass = DemoData.class, fileNames = {"fb3224"})
    public void playSharedDemoTest(DemoData demoData) {
        userProfile = new UserProfile().withName(demoData.getBasicInformationFormData().getDemoName())
                .withLocale(Locale.ENGLISH);
        userProfile = PDEMO.registerOnDevice(userProfile, demoData);
        bundleData = PDEMO.getBundleData();
        demoData.getBasicInformationFormData().setYourEmail(userProfile.getEmail());
        PDEMO.create(demoData);
        WEB.onDemoToolsPage().logout();
        UserModel user = new UserModel(company.getId());
        API.companyAction().createUser(user);
        WEB.toDemoTools().onLoginPage().login(user);
        WEB.onDemoToolsPage().demoTutorial().close();
        PDEMO.play(0);
        PDEMO.registrationStep(userProfile, bundleData, demoData)
                .verificationStep(userProfile, bundleData, demoData)
                .secureLoginStep(userProfile, bundleData, demoData)
                .digitalIdentityStep(bundleData);
    }

    @TmsLinks({
            @TmsLink("FB-3329")
    })
    @FolioTest(pojoClass = DemoData.class, fileNames = {"fb3329"})
    public void playFinishLaterDemoTest(DemoData demoData) {
        userProfile = new UserProfile().withName(demoData.getBasicInformationFormData().getDemoName())
                .withLocale(Locale.ENGLISH);
        userProfile = PDEMO.registerOnDevice(userProfile, demoData);
        bundleData = PDEMO.getBundleData();
        demoData.getBasicInformationFormData().setYourEmail(userProfile.getEmail());
        PDEMO.create(demoData).play(0);
        PDEMO.registrationStep(userProfile, bundleData, demoData);
        WEB.onDemoStepPage().clickFinishLaterButton();
        WEB.onDemoToolsPage().demoTutorial().closeDemoTutorial();
        PDEMO.play(0);
        WEB.onConfirmDialog().clickButtonToConfirm();
        PDEMO.verificationStep(userProfile, bundleData, demoData);
        PDEMO.secureLoginStep(userProfile, bundleData, demoData);
        WEB.onDemoStepPage().clickFinishLaterButton();
        WEB.onDemoToolsPage().demoTutorial().closeDemoTutorial();
        PDEMO.play(0);
        WEB.onConfirmDialog().clickButtonToConfirm();
        PDEMO.digitalIdentityStep(bundleData);
    }
}
