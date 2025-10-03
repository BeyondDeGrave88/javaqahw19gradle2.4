package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static ru.netology.web.data.DataHelper.getAuthInfo;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyBetweenCards() {
        var info = getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogin(info);
        var dashBoardPage = verificationPage.validVerify(verificationCode);
    }
}
